package prosecco;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import prefixspan.*;


public class AlgoProsecco implements  ProgressiveSequenceDatabaseCallbacks{
	
	/** for statistics **/
	protected long startTime;
	protected long noCountTime;
	protected long endTime;
    long prevRuntime;
    long noCountTimeBlock;

	
	/** absolute minimum support */
	protected int minsuppAbsolute;
		
	/** The sequential patterns that are found  (if the user want to keep them into memory) */
	protected SequentialPatterns patterns = null;
	
	protected Map<Integer, List<Integer>> mapSequenceID;

private AlgoPrefixSpan alg = new AlgoPrefixSpan();

	/** original sequence count **/
	protected int sequenceCount = 0;
	
	/** boolean indicating whether this database contains itemsets with multiple items or not */
	boolean containsItemsetsWithMultipleItems = false;
	
    ProgressiveSequenceDatabase sequenceDatabase;
    ProseccoCallbacks callback;
       
    double minsupRelative;
	/** the number of pattern found */
	protected int progressivePatternCount;
	
	/** The sequential patterns that are found  (if the user want to keep them into memory) */
	protected SequentialPatterns progressivePatterns = null;
	
	public AlgoProsecco(ProseccoCallbacks callback){
		this.callback = callback;
	}
	
	/**
	 * Run the algorithm
	 * @param inputFile : a sequence database
	 * @param minsupRelative  :  the minimum support as a percentage (e.g. 50%) as a value in [0,1]
	 * @param outputFilePath : the path of the output file to save the result
	 *                         or null if you want the result to be saved into memory
	 * @return return the result, if saved into memory, otherwise null
	 * @throws IOException  exception if error while writing the file
	 */
	public SequentialPatterns runAlgorithm(
			String inputFilePath, 
			String outputFilePath, 
			int blockSize, 
			int dbSize,
			double errorTolerance,
			boolean topK,
			double minsupRelative) throws IOException {
		// record start time
		startTime = System.currentTimeMillis();
		MemoryLogger.getInstance().reset();
    	prevRuntime = startTime;

		// Load the sequence database
		this.minsupRelative = minsupRelative;
		sequenceDatabase = new ProgressiveSequenceDatabase();
		sequenceDatabase.loadFile(
				inputFilePath, 
				outputFilePath, 
				blockSize, 
				dbSize, 
				errorTolerance, 
				minsupRelative/2,
				topK,
				this);
				
		return null;
	}
	
    public void nextSequenceBlock(List<int[]> block, String outputFilePath, boolean isLast) {
    	
    	try {
    		double epsilon = sequenceDatabase.getError();
    		sequenceCount = sequenceDatabase.size();
    		
    		// convert to a absolute minimum support
    		this.minsuppAbsolute = (int) Math.ceil((minsupRelative - epsilon/2) * sequenceCount);

    		alg.reset();
			
    		if (progressivePatterns != null) 
    			alg.setMapSequenceID(mapSequenceID);
    		
			this.patterns = alg.prefixSpan(sequenceDatabase, outputFilePath, minsuppAbsolute);
			
			this.minsuppAbsolute  = (int) Math.ceil((minsupRelative - epsilon) * sequenceDatabase.numSequencesProcessed());
 
    		if (progressivePatterns == null) {
    			mapSequenceID = new HashMap<Integer, List<Integer>>(alg.getMapSequenceID()); 
    			progressivePatterns = patterns.clone();
    			progressivePatternCount = patterns.getSequenceCount();
    			containsItemsetsWithMultipleItems = alg.isContainsItemsetsWithMultipleItems();
    			MemoryLogger.getInstance().checkMemory();

    		} else {   
	    		merge();
				MemoryLogger.getInstance().checkMemory();
	    		countInfrequent();
	    		prune();
	    	}
    		
    		
			if (callback != null) {
				long startTime = System.currentTimeMillis();
				callback.blockUpdate(progressivePatterns, sequenceDatabase.numSequencesProcessed(), getCurrentBatchRuntime(), getCurrentError());
				long endTime = System.currentTimeMillis();

				// don't count what happens during block update
				noCountTime += endTime - startTime;
				noCountTimeBlock += endTime - startTime;
			}
			
			if (isLast) {
				endTime = System.currentTimeMillis();
			}
			
				
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void merge() {
    	    	
    	int k = 0;
    	for(List<SequentialPattern> level : progressivePatterns.getLevels()) {
    		for(SequentialPattern pattern : level) {
    			
    			pattern.setFound(false);
    			
    			if (k >= patterns.getLevelCount())
    				continue;			
    			
    			for (SequentialPattern newPattern : patterns.getLevel(k)) {

    	  			if (newPattern.equals(pattern)) {
    	    			pattern.setFound(true);
    	    			pattern.addAdditionalSupport(newPattern.getAbsoluteSupport());
        				
    	  				break;
    	  			}
    	  		}
    			
    		}
    		
    		k++;    	
    	}

    }
    
    private void countInfrequent() {
  	
    	for(List<SequentialPattern> level : progressivePatterns.getLevels()) {
    		for(SequentialPattern pattern : level) {
    		
    			if (!pattern.isFound()) {
 
	    			for (int [] sequence : sequenceDatabase.getSequences()) {
	    				
	    				if (sequence != null && Utils.isSubsequenceOf(pattern, sequence, containsItemsetsWithMultipleItems)) {
	    					pattern.addAdditionalSupport(1);
	    				} 
	 
	    			}
    			} 
    			
    		}
    	}
    }
    
    private void prune() {
    	
    	for(List<SequentialPattern> level : progressivePatterns.getLevels()) {
    	  	for(int i = level.size()-1; i >= 0; i--) {
    	  		SequentialPattern pattern = level.get(i);
        		
				
    	  		if (pattern.getAbsoluteSupport() < this.minsuppAbsolute) {
    	  			if (pattern.size() == 1) {
    	  				mapSequenceID.remove(pattern.get(0).get(0));
    	  			}
    	  			
    	  			level.remove(i);
    	  			progressivePatternCount--;
    	  		} 
        	}
    	}
    }
    
    public long getCurrentBatchRuntime() {
    	long batchRuntime = System.currentTimeMillis() - noCountTimeBlock - prevRuntime;
    	prevRuntime = System.currentTimeMillis();
    	noCountTimeBlock = 0;
    	return batchRuntime;
    }
    
    public long getTotalRuntime() {
    	return endTime - startTime - noCountTime;
    }
    
    public double getCurrentError() {
    	return sequenceDatabase.getError();
    }
    
    /**
	 * Print statistics about the algorithm execution to System.out.
	 * @param size  the size of the database
	 */
	public void printStatistics() {
		StringBuilder r = new StringBuilder(200);
		r.append("=============  PROSECCO - STATISTICS =============\n Total time ~ ");
		r.append(endTime - startTime - noCountTime);
		r.append(" ms\n");
		r.append(" Frequent sequences count : " + progressivePatternCount);
		r.append('\n');
		r.append(" Max memory (mb) : ");
		r.append(MemoryLogger.getInstance().getMaxMemory());
		r.append('\n');
		r.append(" minsup = " + minsuppAbsolute + " sequences.");
		r.append('\n');
		r.append(" Pattern count : ");
		r.append(progressivePatternCount);
		r.append('\n');
		r.append("===================================================\n");
		// if the result was save into memory, print it
		if(progressivePatterns !=null){
			progressivePatterns.printFrequentPatterns(sequenceDatabase.numSequencesProcessed(), false);
		}
		System.out.println(r.toString());
	}
}
