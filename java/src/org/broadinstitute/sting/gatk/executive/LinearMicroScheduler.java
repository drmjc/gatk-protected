package org.broadinstitute.sting.gatk.executive;

import org.broadinstitute.sting.gatk.dataSources.providers.LocusContextProvider;
import org.broadinstitute.sting.gatk.dataSources.providers.ReferenceProvider;
import org.broadinstitute.sting.gatk.dataSources.shards.Shard;
import org.broadinstitute.sting.gatk.dataSources.shards.ShardStrategy;
import org.broadinstitute.sting.gatk.dataSources.simpleDataSources.SAMDataSource;
import org.broadinstitute.sting.gatk.dataSources.simpleDataSources.SimpleDataSourceLoadException;
import org.broadinstitute.sting.gatk.iterators.StingSAMIterator;
import org.broadinstitute.sting.gatk.traversals.TraversalEngine;
import org.broadinstitute.sting.gatk.traversals.TraverseByReads;
import org.broadinstitute.sting.gatk.traversals.TraverseLociByReference;
import org.broadinstitute.sting.gatk.traversals.TraverseReads;
import org.broadinstitute.sting.gatk.walkers.LocusWalker;
import org.broadinstitute.sting.gatk.walkers.ReadWalker;
import org.broadinstitute.sting.gatk.walkers.Walker;
import org.broadinstitute.sting.utils.GenomeLoc;

import java.io.File;
import java.util.List;

/** A micro-scheduling manager for single-threaded execution of a traversal. */
public class LinearMicroScheduler extends MicroScheduler {

    private TraversalEngine traversalEngine = null;

    private boolean isAReadWalker = false;

    /** get the traversal engine */
    public TraversalEngine getTraversalEngine() {
        return traversalEngine;
    }

    /**
     * Create a new linear microscheduler to process the given reads and reference.
     *
     * @param reads   Reads file(s) to process.
     * @param refFile Reference for driving the traversal.
     */
    protected LinearMicroScheduler(List<File> reads, File refFile, Walker walker) {
        super(reads, refFile);

        // determine if we're a read walker: they get a slightly different, but not in any way worse execute methodology. I pinky swear...
        isAReadWalker = (walker instanceof ReadWalker) ? true : false;

        if (isAReadWalker) {
            traversalEngine = new TraverseByReads(reads, refFile, new java.util.ArrayList());
        } else {
            traversalEngine = new TraverseLociByReference(reads, refFile, new java.util.ArrayList());
        }
    }

    /**
     * Run this traversal over the specified subsection of the dataset.
     *
     * @param walker    Computation to perform over dataset.
     * @param locations Subset of the dataset over which to walk.
     */
    public void execute(Walker walker, List<GenomeLoc> locations) {
        ShardStrategy shardStrategy = getShardStrategy(reference, locations);
        SAMDataSource dataSource = getReadsDataSource();

        boolean walkerInitialized = false;
        Object accumulator = null;

        for (Shard shard : shardStrategy) {

            StingSAMIterator readShard = null;
            try {
                readShard = dataSource.seek(shard);
            }
            catch (SimpleDataSourceLoadException ex) {
                throw new RuntimeException(ex);
            }

            ReferenceProvider referenceProvider = new ReferenceProvider(reference, shard.getGenomeLoc());
            LocusContextProvider locusProvider = new LocusContextProvider(readShard);

            // set the sam header of the traversal engine
            traversalEngine.setSAMHeader(readShard.getHeader());

            if (!walkerInitialized) {
                walker.initialize();
                accumulator = ((LocusWalker<?, ?>) walker).reduceInit();
                walkerInitialized = true;
            }

            if (!isAReadWalker) {
                accumulator = ((TraverseLociByReference) traversalEngine).traverse(walker, shard, referenceProvider, locusProvider, accumulator);
            } else {
                accumulator = ((TraverseReads) traversalEngine).traverse(walker, shard, readShard, accumulator);
            }

            readShard.close();
        }

        String type = (isAReadWalker) ? "read" : "loci";
        traversalEngine.printOnTraversalDone(type, accumulator);

        walker.onTraversalDone(accumulator);
    }


}
