package main;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class BenchmarkRun {

	    private int runId = 0;    
	    private long totalRuntimeInMillis = 0;
	    private List<Long> runtimePerBatch = new ArrayList<Long>();
	    private List<Double> errors = new ArrayList<Double>();
	    private List<SupportError> supportErrors = new ArrayList<SupportError>();
	    private List<Integer> falsePositivesPerBatch = new ArrayList<Integer>();
	    private List<Integer> falseNegativesPerBatch = new ArrayList<Integer>();
	    private Map<Long, Long> memoryUsagePerTimeInMillis = new HashMap<Long, Long>();
	    
	    public BenchmarkRun(
	    		int runId, 
	    		long totalRuntime, 
	    		List<Long> runtimePerBatch, 
	    		List<Double> errors, 
	    		List<SupportError> supportErrors, 
	    		List<Integer> falsePositives, 
	    		List<Integer> falseNegatives) {
	    	
	    	this.runId = runId;
	    	this.totalRuntimeInMillis = totalRuntime;
	    	this.runtimePerBatch = runtimePerBatch;
	    	this.errors = errors;
	    	this.supportErrors = supportErrors;
	    	this.falseNegativesPerBatch = falseNegatives;
	    	this.falsePositivesPerBatch = falsePositives;
	    }
	    
	    public BenchmarkRun(
	    		int runId, 
	    		Map<Long, Long> memoryUsage) {
	    	
	    	this.runId = runId;
	    	this.memoryUsagePerTimeInMillis = new HashMap<Long,Long>(memoryUsage);
	
	    }
	    
	    public BenchmarkRun(
	    		int runId, 
	    		long totalRuntime, 
	    		Map<Long, Long> memoryUsage) {
	    	
	    	this.runId = runId;
	    	this.totalRuntimeInMillis = totalRuntime;
	    	this.memoryUsagePerTimeInMillis = new HashMap<Long,Long>(memoryUsage);
	    }
}
