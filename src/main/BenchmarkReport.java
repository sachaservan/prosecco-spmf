package main;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;


public class BenchmarkReport {
	
	private String id = "DEFAULT_VALUE_FOR_REPORT_ID";
	private int blockSize = 0;
	private int numberOfRuns = 0;
	private double minSupport = 0.0;
	private String fileName = "DEFAULT_FILE_NAME";
	private List<BenchmarkRun> runs = new ArrayList<BenchmarkRun>();

    public BenchmarkReport(
    		String id, 
    		int blockSize,
    		double minSupport,
    		String fileName) {
    	
    	this.id = id;
    	this.blockSize = blockSize;
    	this.minSupport = minSupport;
    	this.fileName = fileName;
    	this.runs = new ArrayList<BenchmarkRun>();

    }
    
    public BenchmarkReport(
    		String id, 
    		double minSupport,
    		String fileName) {
    	
    	this.id = id;
    	this.minSupport = minSupport;
    	this.fileName = fileName;
    	this.runs = new ArrayList<BenchmarkRun>();
    }
    
    public void addRun(BenchmarkRun run) {
    	runs.add(run);
    }
}

