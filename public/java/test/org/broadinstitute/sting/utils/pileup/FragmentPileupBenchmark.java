/*
 * Copyright (c) 2011, The Broad Institute
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

package org.broadinstitute.sting.utils.pileup;

import com.google.caliper.Param;
import com.google.caliper.SimpleBenchmark;
import com.google.caliper.runner.CaliperMain;
import net.sf.samtools.SAMFileHeader;
import net.sf.samtools.SAMRecord;
import org.broadinstitute.sting.utils.GenomeLoc;
import org.broadinstitute.sting.utils.GenomeLocParser;
import org.broadinstitute.sting.utils.sam.ArtificialSAMUtils;

import java.util.*;

/**
 * Caliper microbenchmark of fragment pileup
 */
public class FragmentPileupBenchmark extends SimpleBenchmark {
    List<ReadBackedPileup> pileups;

    @Param({"0", "4", "30", "150", "1000"})
    int pileupSize; // set automatically by framework

    @Param({"200", "400"})
    int insertSize; // set automatically by framework

    @Override protected void setUp() {
        final int nPileupsToGenerate = 100;
        pileups = new ArrayList<ReadBackedPileup>(nPileupsToGenerate);
        SAMFileHeader header = ArtificialSAMUtils.createArtificialSamHeader(1, 1, 1000);
        GenomeLocParser genomeLocParser;
        genomeLocParser = new GenomeLocParser(header.getSequenceDictionary());
        GenomeLoc loc = genomeLocParser.createGenomeLoc("chr1", 50);
        final int readLen = 100;

        for ( int pileupN = 0; pileupN < nPileupsToGenerate; pileupN++ ) {
            ReadBackedPileup rbp = ArtificialSAMUtils.createReadBackedPileup(header, loc, readLen, insertSize, pileupSize);
            pileups.add(rbp);
        }
    }

    private void run(int rep, FragmentPileup.FragmentMatchingAlgorithm algorithm) {
        int nFrags = 0;
        for ( int i = 0; i < rep; i++ ) {
            for ( ReadBackedPileup rbp : pileups )
                nFrags += new FragmentPileup(rbp, algorithm).getTwoReadPileup().size();
        }
    }

    public void timeOriginal(int rep) {
        run(rep, FragmentPileup.FragmentMatchingAlgorithm.ORIGINAL);
    }

    public void timeSkipNonOverlapping(int rep) {
        run(rep, FragmentPileup.FragmentMatchingAlgorithm.skipNonOverlapping);
    }

    public static void main(String[] args) {
        CaliperMain.main(FragmentPileupBenchmark.class, args);
    }
}
