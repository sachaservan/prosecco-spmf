package prosecco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import javax.sound.midi.Sequence;

import prefixspan.Itemset;
import prefixspan.SequentialPattern;

class Transaction implements Comparable<Transaction>
{
    public int[] sequence; 
    public int numItems;  
    public long priority;
    
    public Transaction(int[] seq, int count, long priority) {
    	this.numItems = count;
    	this.sequence = seq;
    	this.priority = priority;
    }
    
    @Override
    public int compareTo(Transaction trans) {
        return (int)(trans.priority - priority);
    }
};

public class Metadata {
	
    private double errorTolerance;

    private int dbSize; // ceiling of total db size over sample size

    private int numBlocks;

    private boolean topK;
    
    private long capacity;
    
    private int blockSize;
    
    private int numberOfCapacityUpdates = 0;
    
    private boolean useDIndexBound = false;
        
    
    // current number of iterations (batches) processed
    private int numSequencesProcessed = 0;
   
    private int iteration = 1;
   
    // keep a sorted priority list of transactions for computing the capacity of a block
    private List<Transaction> capSequences;
    private List<Transaction> longestSequences;
    
    private long sIndex;
    private long dIndex;

    public Metadata(double errorTolerance, boolean topK, int blockSize, int dbSize) {
        this.errorTolerance = errorTolerance;
        this.dbSize = dbSize;
        this.blockSize = blockSize;
        this.numBlocks = (int)Math.ceil(dbSize/(float)blockSize);
        this.topK = topK;
        this.capSequences = new ArrayList<Transaction>();
        this.longestSequences = new ArrayList<Transaction>();
    }
    
    
    public void UpdateWithSequence(int[] seq, int numItems) {
        numSequencesProcessed++;
        
        if (numberOfCapacityUpdates <= blockSize || useDIndexBound) {
	        // update d-index in case we use it later.
	        UpdateDIndexWithSequence(seq, numItems);
        }
        
        // check if the transaction should be considered
        if (!useDIndexBound && Math.pow(2, numItems) > (long) Math.pow(2, sIndex))  
        {   
	        long c = getCapBound(seq);  
	        capacity = c;
	        capSequences.add(new Transaction(seq, numItems, c));
            Collections.sort(capSequences);
            
            if (capSequences.get(0).priority < Math.pow(2, capSequences.size())-1) {
            	capSequences.remove(0);
            }
            
            numberOfCapacityUpdates++;
            if (numberOfCapacityUpdates >= blockSize) {
            	useDIndexBound = true;
            }
            
//        System.out.println(Long.toString((long) Math.pow(2, numItems)-1) + " > " 
//        		+ Long.toString((long) Math.pow(2, sIndex)));
//            sIndex = capSequences.size();
//   		System.out.println(" sIndex: " + Long.toString(sIndex));
//   		System.out.println(" new capacity: " + Long.toString(capacity));
        } else if (useDIndexBound){
        	sIndex = dIndex;
        }
        
    }
    
    public boolean UpdateDIndexWithSequence(int[] seq, int numItems) {
    	
    	 long oldDIndex = dIndex;

    	 if (numItems > dIndex)  
         {   
    		Transaction trans = new Transaction(seq, numItems, numItems);
    		
    		if (!longestSequences.contains(trans)) {
    			
    			longestSequences.add(new Transaction(seq, numItems, numItems));
	            Collections.sort(longestSequences);
	
	             dIndex = 1;
	 
	             // update the set of transactions
	             for(int i = longestSequences.size() - 1; i >= 0; i--) 
	             {
	                 if (dIndex > longestSequences.get(i).numItems)
	                     break;
	                 
	                 dIndex++;
	             }
	             
	             
	             dIndex--;
	             
	             for(int i = longestSequences.size() - 1; i >= 0; i--) 
	             {
	            	   if (i + 1 > dIndex)
	            		   longestSequences.remove(i);
	                   else
	                       break;
	             }
    		}
         } 
    	 
    	 return oldDIndex != dIndex;
    }
    
    public double GetError() {

        if (numSequencesProcessed >= dbSize) {
            return 0.0;
        }

        double epsilon = Math.sqrt(

            (sIndex - Math.log(errorTolerance) + Math.log(numBlocks)) / (double)(2*numSequencesProcessed)
        );


        if (Double.isInfinite(epsilon) || Double.isNaN(epsilon))
            return 0.0;
        
        return topK ? 2*epsilon : epsilon;
    }




	private long getCapBound(int[] sequence) {
		
		LinkedList<Itemset> list = new LinkedList<Itemset>();
		Itemset it = new Itemset();
		
		int length = 0;
		for (int el : sequence) {
			if (el >= 0) {
				length++;
				it.addItem(el);
			} else if (el == -1) {
				list.add(it.cloneItemSet());
				it = new Itemset();
			} else if (el == -2) {
				break;
			}
		}
				
		// 2^||sequence|| -1
		long c = (long) Math.pow(2, length) - 1;

		while(list.size() > 1) {
			Itemset itA = list.pop();
			for(int i = 0; i < list.size(); i++) {
				if (itA.containsAll(list.get(i))) {
					c = (int) (c - Math.pow(2, list.get(i).size())+ 1);
					list.remove(i);
				}
			}
		}
		
		return c;
		
	}
	

	public double getErrorTolerance() {
		return errorTolerance;
	}


	public int getDbSize() {
		return dbSize;
	}


	public int getNumBlocks() {
		return numBlocks;
	}


	public int getNumSequencesProcessed() {
		return numSequencesProcessed;
	}


	public int getIteration() {
		return iteration;
	}



	public long getsIndex() {
		return sIndex;
	}
	
	
}
