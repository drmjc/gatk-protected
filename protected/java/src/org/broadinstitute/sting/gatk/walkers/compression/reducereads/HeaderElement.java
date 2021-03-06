/*
*  By downloading the PROGRAM you agree to the following terms of use:
*  
*  BROAD INSTITUTE - SOFTWARE LICENSE AGREEMENT - FOR ACADEMIC NON-COMMERCIAL RESEARCH PURPOSES ONLY
*  
*  This Agreement is made between the Broad Institute, Inc. with a principal address at 7 Cambridge Center, Cambridge, MA 02142 (BROAD) and the LICENSEE and is effective at the date the downloading is completed (EFFECTIVE DATE).
*  
*  WHEREAS, LICENSEE desires to license the PROGRAM, as defined hereinafter, and BROAD wishes to have this PROGRAM utilized in the public interest, subject only to the royalty-free, nonexclusive, nontransferable license rights of the United States Government pursuant to 48 CFR 52.227-14; and
*  WHEREAS, LICENSEE desires to license the PROGRAM and BROAD desires to grant a license on the following terms and conditions.
*  NOW, THEREFORE, in consideration of the promises and covenants made herein, the parties hereto agree as follows:
*  
*  1. DEFINITIONS
*  1.1 PROGRAM shall mean copyright in the object code and source code known as GATK2 and related documentation, if any, as they exist on the EFFECTIVE DATE and can be downloaded from http://www.broadinstitute/GATK on the EFFECTIVE DATE.
*  
*  2. LICENSE
*  2.1   Grant. Subject to the terms of this Agreement, BROAD hereby grants to LICENSEE, solely for academic non-commercial research purposes, a non-exclusive, non-transferable license to: (a) download, execute and display the PROGRAM and (b) create bug fixes and modify the PROGRAM. 
*  The LICENSEE may apply the PROGRAM in a pipeline to data owned by users other than the LICENSEE and provide these users the results of the PROGRAM provided LICENSEE does so for academic non-commercial purposes only.  For clarification purposes, academic sponsored research is not a commercial use under the terms of this Agreement.
*  2.2  No Sublicensing or Additional Rights. LICENSEE shall not sublicense or distribute the PROGRAM, in whole or in part, without prior written permission from BROAD.  LICENSEE shall ensure that all of its users agree to the terms of this Agreement.  LICENSEE further agrees that it shall not put the PROGRAM on a network, server, or other similar technology that may be accessed by anyone other than the LICENSEE and its employees and users who have agreed to the terms of this agreement.
*  2.3  License Limitations. Nothing in this Agreement shall be construed to confer any rights upon LICENSEE by implication, estoppel, or otherwise to any computer software, trademark, intellectual property, or patent rights of BROAD, or of any other entity, except as expressly granted herein. LICENSEE agrees that the PROGRAM, in whole or part, shall not be used for any commercial purpose, including without limitation, as the basis of a commercial software or hardware product or to provide services. LICENSEE further agrees that the PROGRAM shall not be copied or otherwise adapted in order to circumvent the need for obtaining a license for use of the PROGRAM.  
*  
*  3. OWNERSHIP OF INTELLECTUAL PROPERTY 
*  LICENSEE acknowledges that title to the PROGRAM shall remain with BROAD. The PROGRAM is marked with the following BROAD copyright notice and notice of attribution to contributors. LICENSEE shall retain such notice on all copies.  LICENSEE agrees to include appropriate attribution if any results obtained from use of the PROGRAM are included in any publication.
*  Copyright 2012 Broad Institute, Inc.
*  Notice of attribution:  The GATK2 program was made available through the generosity of Medical and Population Genetics program at the Broad Institute, Inc.
*  LICENSEE shall not use any trademark or trade name of BROAD, or any variation, adaptation, or abbreviation, of such marks or trade names, or any names of officers, faculty, students, employees, or agents of BROAD except as states above for attribution purposes.
*  
*  4. INDEMNIFICATION
*  LICENSEE shall indemnify, defend, and hold harmless BROAD, and their respective officers, faculty, students, employees, associated investigators and agents, and their respective successors, heirs and assigns, (Indemnitees), against any liability, damage, loss, or expense (including reasonable attorneys fees and expenses) incurred by or imposed upon any of the Indemnitees in connection with any claims, suits, actions, demands or judgments arising out of any theory of liability (including, without limitation, actions in the form of tort, warranty, or strict liability and regardless of whether such action has any factual basis) pursuant to any right or license granted under this Agreement.
*  
*  5. NO REPRESENTATIONS OR WARRANTIES
*  THE PROGRAM IS DELIVERED AS IS.  BROAD MAKES NO REPRESENTATIONS OR WARRANTIES OF ANY KIND CONCERNING THE PROGRAM OR THE COPYRIGHT, EXPRESS OR IMPLIED, INCLUDING, WITHOUT LIMITATION, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, NONINFRINGEMENT, OR THE ABSENCE OF LATENT OR OTHER DEFECTS, WHETHER OR NOT DISCOVERABLE. BROAD EXTENDS NO WARRANTIES OF ANY KIND AS TO PROGRAM CONFORMITY WITH WHATEVER USER MANUALS OR OTHER LITERATURE MAY BE ISSUED FROM TIME TO TIME.
*  IN NO EVENT SHALL BROAD OR ITS RESPECTIVE DIRECTORS, OFFICERS, EMPLOYEES, AFFILIATED INVESTIGATORS AND AFFILIATES BE LIABLE FOR INCIDENTAL OR CONSEQUENTIAL DAMAGES OF ANY KIND, INCLUDING, WITHOUT LIMITATION, ECONOMIC DAMAGES OR INJURY TO PROPERTY AND LOST PROFITS, REGARDLESS OF WHETHER BROAD SHALL BE ADVISED, SHALL HAVE OTHER REASON TO KNOW, OR IN FACT SHALL KNOW OF THE POSSIBILITY OF THE FOREGOING.
*  
*  6. ASSIGNMENT
*  This Agreement is personal to LICENSEE and any rights or obligations assigned by LICENSEE without the prior written consent of BROAD shall be null and void.
*  
*  7. MISCELLANEOUS
*  7.1 Export Control. LICENSEE gives assurance that it will comply with all United States export control laws and regulations controlling the export of the PROGRAM, including, without limitation, all Export Administration Regulations of the United States Department of Commerce. Among other things, these laws and regulations prohibit, or require a license for, the export of certain types of software to specified countries.
*  7.2 Termination. LICENSEE shall have the right to terminate this Agreement for any reason upon prior written notice to BROAD. If LICENSEE breaches any provision hereunder, and fails to cure such breach within thirty (30) days, BROAD may terminate this Agreement immediately. Upon termination, LICENSEE shall provide BROAD with written assurance that the original and all copies of the PROGRAM have been destroyed, except that, upon prior written authorization from BROAD, LICENSEE may retain a copy for archive purposes.
*  7.3 Survival. The following provisions shall survive the expiration or termination of this Agreement: Articles 1, 3, 4, 5 and Sections 2.2, 2.3, 7.3, and 7.4.
*  7.4 Notice. Any notices under this Agreement shall be in writing, shall specifically refer to this Agreement, and shall be sent by hand, recognized national overnight courier, confirmed facsimile transmission, confirmed electronic mail, or registered or certified mail, postage prepaid, return receipt requested.  All notices under this Agreement shall be deemed effective upon receipt. 
*  7.5 Amendment and Waiver; Entire Agreement. This Agreement may be amended, supplemented, or otherwise modified only by means of a written instrument signed by all parties. Any waiver of any rights or failure to act in a specific instance shall relate only to such instance and shall not be construed as an agreement to waive any rights or fail to act in any other instance, whether or not similar. This Agreement constitutes the entire agreement among the parties with respect to its subject matter and supersedes prior agreements or understandings between the parties relating to its subject matter. 
*  7.6 Binding Effect; Headings. This Agreement shall be binding upon and inure to the benefit of the parties and their respective permitted successors and assigns. All headings are for convenience only and shall not affect the meaning of any provision of this Agreement.
*  7.7 Governing Law. This Agreement shall be construed, governed, interpreted and applied in accordance with the internal laws of the Commonwealth of Massachusetts, U.S.A., without regard to conflict of laws principles.
*/

package org.broadinstitute.sting.gatk.walkers.compression.reducereads;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.broadinstitute.sting.utils.MathUtils;
import org.broadinstitute.sting.utils.exceptions.ReviewedStingException;


/**
 * The element that describes the header of the sliding window.
 *
 * Each site has a header element containing the counts of each base, it's reference based location and whether or
 * not the site has insertions (to it's right). It also contains information about the bases that have been filtered
 * out due to mapping or base quality.
 */
public class HeaderElement {
    private BaseAndQualsCounts positiveConsensusBaseCounts;                                                                     // How many A,C,G,T (and D's) are in this site.
    private BaseAndQualsCounts negativeConsensusBaseCounts;                                                                     // How many A,C,G,T (and D's) are in this site.
    private BaseAndQualsCounts filteredBaseCounts;                                                                      // How many A,C,G,T (and D's) were filtered out in this site.
    private int insertionsToTheRight;                                                                                   // How many reads in this site had insertions to the immediate right
    private int location;                                                                                               // Genome location of this site (the sliding window knows which contig we're at

    protected static final int MIN_COUNT_FOR_USING_PVALUE = 2;

    public int getLocation() {
        return location;
    }

    /**
     * Get the base counts object for the consensus type
     *
     * @param consensusType   the type to use
     * @return non-null base counts
     */
    public BaseAndQualsCounts getBaseCounts(final SlidingWindow.ConsensusType consensusType) {
        if ( consensusType == SlidingWindow.ConsensusType.POSITIVE_CONSENSUS )
            return positiveConsensusBaseCounts;
        if ( consensusType == SlidingWindow.ConsensusType.NEGATIVE_CONSENSUS )
            return negativeConsensusBaseCounts;
        return filteredBaseCounts;
    }

    /**
     * Creates a new HeaderElement with the following default values: - empty consensusBaseCounts - empty
     * filteredBaseCounts - 0 insertions to the right - empty mappingQuality list
     *
     * @param location the reference location for the new element
     */
    public HeaderElement(final int location) {
        this(new BaseAndQualsCounts(), new BaseAndQualsCounts(), new BaseAndQualsCounts(), 0, location);
    }

    /**
     * Creates a new HeaderElement with the following default values: - empty consensusBaseCounts - empty
     * filteredBaseCounts - empty mappingQuality list
     *
     * @param location the reference location for the new element
     */
    public HeaderElement(final int location, final int insertionsToTheRight) {
        this(new BaseAndQualsCounts(), new BaseAndQualsCounts(), new BaseAndQualsCounts(), insertionsToTheRight, location);
    }

    /**
     * Creates a new HeaderElement with all given parameters
     *
     * @param positiveConsensusBaseCounts  the BaseCounts object for the running positive consensus synthetic read
     * @param negativeConsensusBaseCounts  the BaseCounts object for the running negative consensus synthetic read
     * @param filteredBaseCounts   the BaseCounts object for the filtered data synthetic read
     * @param insertionsToTheRight number of insertions to the right of this HeaderElement
     * @param location             the reference location of this reference element
     *                             HeaderElement
     */
    public HeaderElement(final BaseAndQualsCounts positiveConsensusBaseCounts, final BaseAndQualsCounts negativeConsensusBaseCounts, final BaseAndQualsCounts filteredBaseCounts, final int insertionsToTheRight, final int location) {
        this.positiveConsensusBaseCounts = positiveConsensusBaseCounts;
        this.negativeConsensusBaseCounts = negativeConsensusBaseCounts;
        this.filteredBaseCounts = filteredBaseCounts;
        this.insertionsToTheRight = insertionsToTheRight;
        this.location = location;
    }

    /**
     * Whether or not the site represented by this HeaderElement is variant according to the definitions of variant
     * by insertion, deletion and mismatches.
     *
     * @param minVariantPvalue       min p-value for deciding that a position is or is not variable due to mismatches
     * @param minVariantProportion   min proportion for deciding that a position is or is not variable due to mismatches
     * @param minIndelProportion     min proportion for deciding that a position is or is not variable due to indels
     * @return true if site is variant by any definition. False otherwise.
     */
    public boolean isVariant(final double minVariantPvalue, final double minVariantProportion, final double minIndelProportion) {
        return ( hasConsensusData(SlidingWindow.ConsensusType.POSITIVE_CONSENSUS) || hasConsensusData(SlidingWindow.ConsensusType.NEGATIVE_CONSENSUS) )
                && (isVariantFromInsertions(minIndelProportion) || isVariantFromMismatches(minVariantPvalue, minVariantProportion) || isVariantFromDeletions(minIndelProportion) || isVariantFromSoftClips());
    }

    /**
     * Adds a new base to the HeaderElement updating all counts accordingly
     *
     * @param base               the base to add
     * @param baseQual           the base quality
     * @param insQual            the base insertion quality
     * @param delQual            the base deletion quality
     * @param baseMappingQuality the mapping quality of the read this base belongs to
     * @param minBaseQual        the minimum base qual allowed to be a good base
     * @param minMappingQual     the minimum mapping qual allowed to be a good read
     * @param isSoftClipped      true if the base is soft-clipped in the original read
     * @param isNegativeStrand   true if the base comes from a read on the negative strand
     */
    public void addBase(final byte base, final byte baseQual, final byte insQual, final byte delQual, final int baseMappingQuality, final int minBaseQual, final int minMappingQual, final boolean isSoftClipped, final boolean isNegativeStrand) {
        // If the base passes the MQ filter it is included in the consensus base counts, otherwise it's part of the filtered counts
        if ( baseMappingQuality >= minMappingQual ) {
            if ( isNegativeStrand )
                negativeConsensusBaseCounts.incr(base, baseQual, insQual, delQual, baseMappingQuality, baseQual < minBaseQual, isSoftClipped);
            else
                positiveConsensusBaseCounts.incr(base, baseQual, insQual, delQual, baseMappingQuality, baseQual < minBaseQual, isSoftClipped);
        } else {
            filteredBaseCounts.incr(base, baseQual, insQual, delQual, baseMappingQuality, baseQual < minBaseQual);
        }
    }

    /**
     * Adds a new base to the HeaderElement updating all counts accordingly
     *
     * @param base               the base to add
     * @param baseQual           the base quality
     * @param insQual            the base insertion quality
     * @param delQual            the base deletion quality
     * @param baseMappingQuality the mapping quality of the read this base belongs to
     * @param minBaseQual        the minimum base qual allowed to be a good base
     * @param minMappingQual     the minimum mapping qual allowed to be a good read
     * @param isSoftClipped      true if the base is soft-clipped in the original read
     * @param isNegativeStrand   true if the base comes from a read on the negative strand
     */
    public void removeBase(final byte base, final byte baseQual, final byte insQual, final byte delQual, final int baseMappingQuality, final int minBaseQual, final int minMappingQual, final boolean isSoftClipped, final boolean isNegativeStrand) {
        // If the base passes the MQ filter it is included in the consensus base counts, otherwise it's part of the filtered counts
        if ( baseMappingQuality >= minMappingQual ) {
            if ( isNegativeStrand )
                negativeConsensusBaseCounts.decr(base, baseQual, insQual, delQual, baseMappingQuality, baseQual < minBaseQual, isSoftClipped);
            else
                positiveConsensusBaseCounts.decr(base, baseQual, insQual, delQual, baseMappingQuality, baseQual < minBaseQual, isSoftClipped);
        } else {
            filteredBaseCounts.decr(base, baseQual, insQual, delQual, baseMappingQuality, baseQual < minBaseQual);
        }
    }

    /**
     * Adds an insertions to the right of the HeaderElement and updates all counts accordingly. All insertions
     * should be added to the right of the element.
     */
    public void addInsertionToTheRight() {
        insertionsToTheRight++;
    }

    /**
     * Does this HeaderElement contain consensus data?
     *
     * @param consensusType   the type to use
     * @return whether or not this HeaderElement contains consensus data
     */
    public boolean hasConsensusData(final SlidingWindow.ConsensusType consensusType) {
        return getBaseCounts(consensusType).totalCount() > 0;
    }

    /**
     * A HeaderElement is empty if it has no consensus or filtered data
     *
     * @return whether or not this HeaderElement has no data
     */
    public boolean isEmpty() {
        return !hasConsensusData(SlidingWindow.ConsensusType.POSITIVE_CONSENSUS) && !hasConsensusData(SlidingWindow.ConsensusType.NEGATIVE_CONSENSUS) && !hasConsensusData(SlidingWindow.ConsensusType.FILTERED);
    }

    /**
     * removes an insertion from this element (if you removed a read that had an insertion)
     */
    public void removeInsertionToTheRight() {
        this.insertionsToTheRight--;
        if (insertionsToTheRight < 0)
            throw new ReviewedStingException("Removed too many insertions, header is now negative at position " + location);
    }

    public boolean hasInsertionToTheRight() {
        return insertionsToTheRight > 0;
    }

    public int numInsertionsToTheRight() {
        return insertionsToTheRight;
    }

    /**
     * Whether or not the HeaderElement is variant due to excess insertions
     *
     * @return whether or not the HeaderElement is variant due to excess insertions
     */
    private boolean isVariantFromInsertions(double minIndelProportion) {
        final int numberOfBases = totalCountForBothStrands();
        if (numberOfBases == 0)
            return (insertionsToTheRight > 0); // do we only have insertions?

        // if we have bases and insertions, check the ratio
        return ((double) insertionsToTheRight / numberOfBases) > minIndelProportion;
    }

    private int totalCountForBothStrands() {
        return positiveConsensusBaseCounts.totalCount() + negativeConsensusBaseCounts.totalCount();
    }

    /**
     * Whether or not the HeaderElement is variant due to excess deletions
     *
     * @return whether or not the HeaderElement is variant due to excess deletions
     */
    private boolean isVariantFromDeletions(double minIndelProportion) {
        return positiveConsensusBaseCounts.baseIndexWithMostCounts() == BaseIndex.D || positiveConsensusBaseCounts.baseCountProportion(BaseIndex.D) > minIndelProportion
                || negativeConsensusBaseCounts.baseIndexWithMostCounts() == BaseIndex.D || negativeConsensusBaseCounts.baseCountProportion(BaseIndex.D) > minIndelProportion;
    }

    /**
     * Whether or not the HeaderElement is variant due to excess mismatches
     *
     * @param minVariantPvalue     the minimum pvalue to call a site variant (used with low coverage).
     * @param minVariantProportion the minimum proportion to call a site variant (used with high coverage).
     * @return whether or not the HeaderElement is variant due to excess mismatches
     */
    protected boolean isVariantFromMismatches(final double minVariantPvalue, final double minVariantProportion) {
        return isVariantFromMismatches(minVariantPvalue, minVariantProportion, SlidingWindow.ConsensusType.POSITIVE_CONSENSUS) ||
                isVariantFromMismatches(minVariantPvalue, minVariantProportion, SlidingWindow.ConsensusType.NEGATIVE_CONSENSUS);
    }

    /**
     * Whether or not the HeaderElement is variant due to excess mismatches
     *
     * @param minVariantPvalue     the minimum pvalue to call a site variant (used with low coverage).
     * @param minVariantProportion the minimum proportion to call a site variant (used with high coverage).
     * @param consensusType        the consensus type to use
     * @return whether or not the HeaderElement is variant due to excess mismatches
     */
    private boolean isVariantFromMismatches(final double minVariantPvalue, final double minVariantProportion, final SlidingWindow.ConsensusType consensusType) {
        final BaseAndQualsCounts baseAndQualsCounts = getBaseCounts(consensusType);
        final int totalCount = baseAndQualsCounts.totalCountWithoutIndels();
        final BaseIndex mostCommon = baseAndQualsCounts.baseIndexWithMostProbabilityWithoutIndels();
        final int countOfOtherBases = totalCount - baseAndQualsCounts.countOfBase(mostCommon);
        return hasSignificantCount(countOfOtherBases, totalCount, minVariantPvalue, minVariantProportion);
    }

    /**
     * This handles the special case where we have more bases that came from soft clips than bases that came from
     * normal bases by forcing it to become a variant region. We don't want a consensus based on too little information.
     *
     * @return true if we had more soft clipped bases contributing to this site than matches/mismatches.
     */
    protected boolean isVariantFromSoftClips() {
        return isVariantFromSoftClips(SlidingWindow.ConsensusType.POSITIVE_CONSENSUS) || isVariantFromSoftClips(SlidingWindow.ConsensusType.NEGATIVE_CONSENSUS);
    }

    /**
     * This handles the special case where we have more bases that came from soft clips than bases that came from
     * normal bases by forcing it to become a variant region. We don't want a consensus based on too little information.
     *
     * @param consensusType        the consensus type to use
     * @return true if we had more soft clipped bases contributing to this site than matches/mismatches.
     */
    private boolean isVariantFromSoftClips(final SlidingWindow.ConsensusType consensusType) {
        final BaseAndQualsCounts baseAndQualsCounts = getBaseCounts(consensusType);
        final int nSoftClippedBases = baseAndQualsCounts.nSoftclips();
        return nSoftClippedBases > 0 && nSoftClippedBases >= (baseAndQualsCounts.totalCount() - nSoftClippedBases);
    }

    /**
     * Calculates the number of alleles necessary to represent this site.
     *
     * @param minVariantPvalue     the minimum pvalue to call a site variant.
     * @param minVariantProportion the minimum proportion to call a site variant.
     * @return the number of alleles necessary to represent this site or -1 if there are too many indels
     */
    public int getNumberOfBaseAlleles(final double minVariantPvalue, final double minVariantProportion) {
        final ObjectArrayList<BaseIndex> alleles = getAlleles(minVariantPvalue, minVariantProportion);
        return alleles == null ? -1 : alleles.size();
    }

    /**
     * Calculates the alleles necessary to represent this site.
     *
     * @param minVariantPvalue     the minimum pvalue to call a site variant.
     * @param minVariantProportion the minimum proportion to call a site variant.
     * @return the list of alleles necessary to represent this site or null if there are too many indels
     */
    public ObjectArrayList<BaseIndex> getAlleles(final double minVariantPvalue, final double minVariantProportion) {
        // make sure we have bases at all
        final int totalBaseCount = totalCountForBothStrands();
        if ( totalBaseCount == 0 )
            return new ObjectArrayList<>(0);

        // next, check for insertions; technically, the insertion count can be greater than totalBaseCount
        // (because of the way insertions are counted), so we need to account for that
        if ( hasSignificantCount(Math.min(totalBaseCount, insertionsToTheRight), totalBaseCount, minVariantPvalue, minVariantProportion) )
            return null;

        // finally, check for the bases themselves (including deletions)
        final ObjectArrayList<BaseIndex> alleles = new ObjectArrayList<>(4);
        for ( final BaseIndex base : BaseIndex.values() ) {
            final int baseCount = positiveConsensusBaseCounts.countOfBase(base) + negativeConsensusBaseCounts.countOfBase(base);
            if ( baseCount == 0 )
                continue;

            if ( hasSignificantCount(baseCount, totalBaseCount, minVariantPvalue, minVariantProportion) ) {
                if ( base == BaseIndex.D )
                    return null;
                alleles.add(base);
            }
        }
        return alleles;
    }

    /*
     * Checks whether there are a significant number of softclips.
     *
     * @param minVariantPvalue     the minimum pvalue to call a site variant.
     * @param minVariantProportion the minimum proportion to call a site variant.
     * @return true if there are significant softclips, false otherwise
     */
    public boolean hasSignificantSoftclips(final double minVariantPvalue, final double minVariantProportion) {
        return hasSignificantCount(positiveConsensusBaseCounts.nSoftclips() + negativeConsensusBaseCounts.nSoftclips(), totalCountForBothStrands(), minVariantPvalue, minVariantProportion);
    }

    /*
     * Checks whether there are a significant number of count.
     *
     * @param count                the count (k) to test against
     * @param total                the total (n) to test against
     * @param minVariantPvalue     the minimum pvalue to call a site variant.
     * @param minVariantProportion the minimum proportion to call a site variant.
     * @return true if there is a significant count given the provided pvalue, false otherwise
     */
    private boolean hasSignificantCount(final int count, final int total, final double minVariantPvalue, final double minVariantProportion) {
        if ( count == 0 || total == 0 )
            return false;

        // use p-values for low counts of k
        if ( count <= MIN_COUNT_FOR_USING_PVALUE ) {
            final double pvalue = MathUtils.binomialCumulativeProbability(total, 0, count);
            return pvalue > minVariantPvalue;
        }

        // otherwise, use straight proportions
        final int minBaseCountForSignificance = (int)(minVariantProportion * total);
        return count >= minBaseCountForSignificance;
    }
}