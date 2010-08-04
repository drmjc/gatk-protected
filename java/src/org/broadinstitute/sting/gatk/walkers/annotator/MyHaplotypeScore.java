/*
 * Copyright (c) 2010, The Broad Institute
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package org.broadinstitute.sting.gatk.walkers.annotator;

import org.broad.tribble.vcf.VCFHeaderLineType;
import org.broad.tribble.vcf.VCFInfoHeaderLine;
import org.broadinstitute.sting.gatk.contexts.ReferenceContext;
import org.broadinstitute.sting.gatk.contexts.StratifiedAlignmentContext;
import org.broadinstitute.sting.gatk.contexts.AlignmentContext;
import org.broadinstitute.sting.gatk.contexts.variantcontext.*;
import org.broadinstitute.sting.gatk.filters.Platform454Filter;
import org.broadinstitute.sting.gatk.refdata.RefMetaDataTracker;
import org.broadinstitute.sting.gatk.walkers.annotator.interfaces.*;
import org.broadinstitute.sting.utils.*;
import org.broadinstitute.sting.utils.collections.Pair;
import org.broadinstitute.sting.utils.pileup.*;

import java.util.*;
import net.sf.samtools.SAMRecord;
import org.broadinstitute.sting.utils.sam.ReadUtils;

public class MyHaplotypeScore implements InfoFieldAnnotation {
    private final static boolean DEBUG = false;
    private final static int MIN_CONTEXT_WING_SIZE = 10;
    private final static String REGEXP_WILDCARD = ".";

    public Map<String, Object> annotate(RefMetaDataTracker tracker, ReferenceContext ref, Map<String, StratifiedAlignmentContext> stratifiedContexts, VariantContext vc) {
        if ( !vc.isBiallelic() || !vc.isSNP() || stratifiedContexts.size() == 0 ) // size 0 means that call was made by someone else and we have no data here
            return null;

        AlignmentContext context = StratifiedAlignmentContext.joinContexts(stratifiedContexts.values());

        int contextWingSize = Math.min(((int)ref.getWindow().size() - 1)/2, MIN_CONTEXT_WING_SIZE);
        int contextSize = contextWingSize * 2 + 1;

        // Compute all haplotypes consistent with the current read pileup
        List<Haplotype> haplotypes = computeHaplotypes(context.getBasePileup(), contextSize);

        // calculate the haplotype scores by walking over all reads and comparing them to the haplotypes
        double score = 0.0;

        if (haplotypes != null)
            score = scoreReadsAgainstHaplotypes(haplotypes, context.getBasePileup(), contextSize);

        // return the score
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(getKeyNames().get(0), String.format("%.2f", score));
        return map;
    }

    private class HaplotypeComparator implements Comparator<Haplotype>{

        public int compare(Haplotype a, Haplotype b) {
            if (a.getQualitySum() < b.getQualitySum())
                return 1;
            if (a.getQualitySum() > b.getQualitySum()){
                return -1;
            }
            return 0;
        }
    }


    private List<Haplotype> computeHaplotypes(ReadBackedPileup pileup, int contextSize) {
        // Compute all possible haplotypes consistent with current pileup
        ArrayList<Haplotype> haplotypeList = new ArrayList<Haplotype>();
        ArrayList<Integer> readsPerHaplotype = new ArrayList<Integer>();
        PriorityQueue<Haplotype> haplotypeQueue = new PriorityQueue<Haplotype>(100, new HaplotypeComparator());


        for ( ExtendedPileupElement p : pileup.extendedForeachIterator() ) {
            if (ReadUtils.is454Read(p.getRead()))
                continue;
            Haplotype haplotypeFromRead = getHaplotypeFromRead(p, contextSize);


            haplotypeQueue.add(haplotypeFromRead);
            //haplotypeList.add(haplotypeFromRead);
        }

        // Now that priority queue has been built with all reads at context, we need to merge and find possible segregating haplotypes
        Haplotype elem;
        while ((elem = haplotypeQueue.poll()) != null)  {
            //System.out.print("element: "+elem.toString());
            //System.out.format(" SumQual = %f\n", elem.getQualitySum());
            boolean foundHaplotypeMatch = false;
            //Haplotype[] remainingHaplotypes = haplotypeQueue.toArray(new Haplotype[haplotypeQueue.size()]);
            for ( Haplotype haplotypeFromList : haplotypeList ) {

                Haplotype consensusHaplotype = getConsensusHaplotype(elem, haplotypeFromList);
                //System.out.format("-Checking consensus for %s:", haplotypeFromList.toString());
                if (consensusHaplotype != null)  {
                    //System.out.format("--Consensus haplotype  = %s, qual = %f\n", consensusHaplotype.toString(), consensusHaplotype.getQualitySum());
                    foundHaplotypeMatch = true;
                    if (consensusHaplotype.getQualitySum() > haplotypeFromList.getQualitySum()) {
                        haplotypeList.remove(haplotypeFromList);
                        haplotypeList.add(consensusHaplotype);
                    }
                    break;
                }
        /*        else {
                    System.out.println("no consensus found");
                }
          */
            }

            if (!foundHaplotypeMatch) {
                haplotypeList.add(elem);
            }
        }
        // Now retrieve two most popular haplotypes
        // TODO - quick and dirty solution, could use better data structures to do this automatically
        int bestIdx=0, secondBestIdx=0;
        double bestIdxVal=-1.0, secondBestIdxVal = -1.0;

        for (int k=0; k < haplotypeList.size(); k++) {

            double qualSum = haplotypeList.get(k).getQualitySum();
            if (qualSum >= bestIdxVal) {
                secondBestIdx = bestIdx;
                secondBestIdxVal = bestIdxVal;
                bestIdx = k;
                bestIdxVal = qualSum;
            }
            else if (qualSum >= secondBestIdxVal) {
                // check if current is second best
                secondBestIdx = k;
                secondBestIdxVal = qualSum;
            }
        }
        if (haplotypeList.size() > 0) {
            Haplotype haplotypeR = haplotypeList.get(bestIdx);
            Haplotype haplotypeA = haplotypeList.get(secondBestIdx);

            // Temp hack to match old implementation's scaling, TBD better behavior

            return Arrays.asList(new Haplotype(haplotypeR.bases, 60), new Haplotype(haplotypeA.bases, contextSize));
        }
        else
            return null;
    }

    private Haplotype getHaplotypeFromRead(ExtendedPileupElement p, int contextSize) {
        SAMRecord read = p.getRead();
        int readOffsetFromPileup = p.getOffset();
        int baseOffsetStart = readOffsetFromPileup - (contextSize - 1)/2;
        byte[] haplotypeBases = new byte[contextSize];

        for(int i=0; i < contextSize; i++) {
            haplotypeBases[i] = REGEXP_WILDCARD.getBytes()[0];
        }

        double[] baseQualities = new double[contextSize];
        Arrays.fill(baseQualities,0.0);

        for (int i = 0; i < contextSize; i++ ) {
            int baseOffset = i + baseOffsetStart;
            if ( baseOffset < 0 )
                continue;
            if ( baseOffset >= read.getReadLength() )
                break;

            haplotypeBases[i] = read.getReadBases()[baseOffset];
            baseQualities[i] = (double)read.getBaseQualities()[baseOffset];
        }


        return new Haplotype(haplotypeBases, baseQualities);

    }



    private Haplotype getConsensusHaplotype(Haplotype haplotypeA, Haplotype haplotypeB) {
        String a = haplotypeA.toString();
        String b = haplotypeB.toString();

        if (a.length() != b.length())
            throw new StingException("Haplotypes a and b must be of same length");

        char chA, chB;
        char wc = REGEXP_WILDCARD.charAt(0);


        char[] consensusChars = new char[a.length()];
        double[] consensusQuals = new double[a.length()];

        for (int i=0; i < a.length(); i++) {
            chA = a.charAt(i);
            chB = b.charAt(i);

            if ((chA != chB) && (chA != wc) && (chB != wc))
                return null;

            if ((chA == wc) && (chB == wc)) {
                consensusChars[i] = wc;
                consensusQuals[i] = 0.0;
            }
            else if ((chA == wc) && (chB != wc)) {
                consensusChars[i] = chB;
                consensusQuals[i] = haplotypeB.quals[i];
            }
            else if ((chA != wc) && (chB == wc)){
                consensusChars[i] = chA;
                consensusQuals[i] = haplotypeA.quals[i];
            } else {
                consensusChars[i] = chA;
                consensusQuals[i] = haplotypeA.quals[i]+haplotypeB.quals[i];
            }


        }


        return new Haplotype(new String(consensusChars), consensusQuals);
    }
    // calculate the haplotype scores by walking over all reads and comparing them to the haplotypes
    private double scoreReadsAgainstHaplotypes(List<Haplotype> haplotypes, ReadBackedPileup pileup, int contextSize) {
//        if ( DEBUG ) System.out.printf("HAP1: %s%n", haplotypes.get(0));
//        if ( DEBUG ) System.out.printf("HAP1: %s%n", haplotypes.get(1));

        double[][] haplotypeScores = new double[pileup.size()][haplotypes.size()];
        for ( ExtendedPileupElement p : pileup.extendedForeachIterator() ) {
            SAMRecord read = p.getRead();
            int readOffsetFromPileup = p.getOffset();

            if (ReadUtils.is454Read(read))
                continue;

            if ( DEBUG ) System.out.printf("--------------------------------------------- Read %s%n", read.getReadName());
            double m = 10000000;
            for ( int i = 0; i < haplotypes.size(); i++ ) {
                Haplotype haplotype = haplotypes.get(i);
                int start = readOffsetFromPileup - (contextSize - 1)/2;
                double score = scoreReadAgainstHaplotype(read, start, contextSize, haplotype);
                haplotypeScores[p.getPileupOffset()][i] = score;
                if ( DEBUG ) System.out.printf("  vs. haplotype %d = %f%n", i, score);
                m = Math.min(score, m);
            }
            if ( DEBUG ) System.out.printf("  => best score was %f%n", m);
        }

        double overallScore = 0.0;
        for ( double[] readHaplotypeScores : haplotypeScores ) {
            overallScore += MathUtils.arrayMin(readHaplotypeScores);
        }

        return overallScore;
    }

    private double scoreReadAgainstHaplotype(SAMRecord read, int baseOffsetStart, int contextSize, Haplotype haplotype ) {
        double expected = 0.0;
        double mismatches = 0.0;

        // What's the expected mismatch rate under the model that this read is actually sampled from
        // this haplotype?  Let's assume the consensus base c is a random choice one of A, C, G, or T, and that
        // the observed base is actually from a c with an error rate e.  Since e is the rate at which we'd
        // see a miscalled c, the expected mismatch rate is really e.  So the expected number of mismatches
        // is just sum_i e_i for i from 1..n for n sites
        //
        // Now, what's the probabilistic sum of mismatches?  Suppose that the base b is equal to c.  Well, it could
        // actually be a miscall in a matching direction, which would happen at a e / 3 rate.  If b != c, then
        // the chance that it is actually a mismatch is 1 - e, since any of the other 3 options would be a mismatch.
        // so the probability-weighted mismatch rate is sum_i ( matched ? e_i / 3 : 1 - e_i ) for i = 1 ... n
        for ( int i = 0; i < contextSize; i++ ) {
            int baseOffset = i + baseOffsetStart;
            if ( baseOffset < 0 )
                continue;
            if ( baseOffset >= read.getReadLength() )
                break;

            byte haplotypeBase = haplotype.bases[i];
            byte readBase = read.getReadBases()[baseOffset];

            boolean matched = BaseUtils.basesAreEqual(readBase, haplotypeBase );
            double e = QualityUtils.qualToErrorProb(read.getBaseQualities()[baseOffset]);
            expected += e;
            mismatches += matched ? e : 1 - e / 3;

            // a more sophisticated calculation would include the reference quality, but it's nice to actually penalize
            // the mismatching of poorly determined regions of the consensus

            if ( DEBUG ) System.out.printf("Read %s: scoring %c vs. %c => e = %f Q%d esum %f vs. msum %f%n",
                    read.getReadName(), (char)haplotypeBase, (char)readBase, e, read.getBaseQualities()[baseOffset], expected, mismatches);
        }

        return mismatches - expected;
    }


    private static final double[] FLAT_BASE_PRIORS = new double[BaseUtils.Base.values().length];
    static {
        for ( int i = 0; i < BaseUtils.Base.values().length; i++ )
            FLAT_BASE_PRIORS[i] = Math.log10(1.0 / BaseUtils.Base.values().length);
    }

    private class Haplotype {
        byte[] bases = null;
        double[] quals = null;

        /**
         * Create a simple consensus sequence with provided bases and a uniform quality over all bases of qual
         *
         * @param bases
         * @param qual
         */
        Haplotype(byte[] bases, int qual) {
            this.bases = bases;
            quals = new double[bases.length];
            Arrays.fill(quals, (double)qual);
        }

        Haplotype(byte[] bases, double[] quals) {
            this.bases = bases;
            this.quals = quals;
        }

        Haplotype(String bases, double[] quals) {
            this.bases = bases.getBytes();
            this.quals = quals;
        }
        public String toString() { return new String(this.bases); }

        double getQualitySum() {
            double s = 0;
            for (int k=0; k < bases.length; k++) {
                s += quals[k];
            }
            return s;
        }
    }

    public List<String> getKeyNames() { return Arrays.asList("MyHaplotypeScore"); }
    public List<VCFInfoHeaderLine> getDescriptions() { return Arrays.asList(new VCFInfoHeaderLine("MyHaplotypeScore", 1, VCFHeaderLineType.Float, "Consistency of the site with two (and only two) segregating haplotypes")); }
}