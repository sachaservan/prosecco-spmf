package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import spam.*;
import prefixspan.AlgoPrefixSpan;
import prefixspan.MemoryLogger;
import prefixspan.SequentialPatterns;
import prosecco.AlgoProsecco;
import prosecco.ProseccoCallbacks;

/** "-flag opt" combination */
class Option {
	String flag, opt;
	public Option(String flag, String opt) { this.flag = flag; this.opt = opt; }
}

public class Main {

	public static void main (String [] args) {

		SequentialPatterns correctPatterns = null;

		List<String> argsList = new ArrayList<String>();  
		List<Option> optsList = new ArrayList<Option>();
		List<String> doubleOptsList = new ArrayList<String>();

		for (int i = 0; i < args.length; i++) {
			switch (args[i].charAt(0)) {
			case '-':
				if (args[i].length() < 2)
					throw new IllegalArgumentException("Not a valid argument: "+args[i]);
				if (args[i].charAt(1) == '-') {
					if (args[i].length() < 3)
						throw new IllegalArgumentException("Not a valid argument: "+args[i]);
					// --opt
					doubleOptsList.add(args[i].substring(2, args[i].length()));
				} else {
					if (args.length-1 == i)
						throw new IllegalArgumentException("Expected arg after: "+args[i]);
					// -opt
					optsList.add(new Option(args[i], args[i+1]));
					i++;
				}
				break;
			default:
				// arg
				argsList.add(args[i]);
				break;
			}
		}

		double errorTolerance = 0.05; // fixed

		String benchmarkID = null;
		String inputFile = null;
		String outputFile = null;
		String shuffleCommand = null;
		int numTrials = -1;
		double minsup = -1;
		int blockSize = -1;
		int dbSize = -1;
		boolean runPrefixSpan = true;
		boolean benchmarkRuntime = false;
		boolean benchmarkMemory = false;
		boolean runSPAM = false;
		boolean print = false;

		for (Option opt : optsList) {
			System.out.println(opt.flag + " " + opt.opt);
			if (opt.flag.equals("-z")) {
				blockSize = Integer.parseInt(opt.opt);
			} else if (opt.flag.equals("-d")) {
				dbSize = Integer.parseInt(opt.opt);
			} else if (opt.flag.equals("-f")) {
				inputFile = opt.opt;
			} else if (opt.flag.equals("-s")) {
				minsup = Double.parseDouble(opt.opt);
			} else if (opt.flag.equals("-r")) {
				shuffleCommand = opt.opt;
			} else if (opt.flag.equals("-i")) {
				benchmarkID = opt.opt;
			} else if (opt.flag.equals("-o")) {
				outputFile = opt.opt;
			} else if (opt.flag.equals("-n")) {
				numTrials = Integer.parseInt(opt.opt);
			}
		}


		if (doubleOptsList.contains("prosecco")) {
			runPrefixSpan = false;
		}
		
		if (doubleOptsList.contains("benchmarkRuntime")) {
			benchmarkRuntime = true;
		}
		
		if (doubleOptsList.contains("spam")) {
			runSPAM = true;
		}
		
		
		if (doubleOptsList.contains("benchmarkMemory")) {
			benchmarkMemory = true;
		}
		
		if (benchmarkRuntime  && benchmarkMemory) {
			throw new IllegalArgumentException("Cannot run both memory and runtime benchmarks");
		}

		if (doubleOptsList.contains("print")) {
			print = true;
		}
		
		if (numTrials == -1 && (benchmarkRuntime || benchmarkMemory)) {
			throw new IllegalArgumentException("Need to specify number of trials when running benchmarks");
		}

		if (benchmarkID == null) {
			throw new IllegalArgumentException("ID of benchmark report not specified");
		}

		if (inputFile == null) {
			throw new IllegalArgumentException("Input file not specified");
		}

		if (outputFile == null) {
			throw new IllegalArgumentException("Benchmark output file not specified");
		}

		if (shuffleCommand == null) {
			throw new IllegalArgumentException("Shuffle command not specified");
		}

		if (blockSize == -1) {
			throw new IllegalArgumentException("Block size not specified");
		}

		if (dbSize == -1) {
			throw new IllegalArgumentException("DB size not specified");
		}

		if (minsup == -1) {
			throw new IllegalArgumentException("Min support not specified");
		}
		
		if (runSPAM) {
			System.out.println("BENCHMARKING RUNTIME AND MEMORY USAGE SPAM");
			runSPAMRuntimeAndMemoryBenchmark(benchmarkID, inputFile, outputFile, minsup, numTrials);
			return;
		
		} 
		
		
		// get the correct results to benchmark correctness of prosecco
		if (benchmarkRuntime && !benchmarkMemory && !runPrefixSpan && !runSPAM) {

			try {
				AlgoPrefixSpan alg = new AlgoPrefixSpan();
				correctPatterns = alg.runAlgorithm(inputFile, minsup, null);
				alg.printStatistics();
				alg = null;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!runPrefixSpan && benchmarkRuntime) {
			System.out.println("BENCHMARKING RUNTIME AND ERROR FOR PROSECCO");
			runProseccoAlgorithmBenchmark(
					benchmarkID, 
					inputFile, 
					outputFile, 
					shuffleCommand, 
					minsup, 
					blockSize, 
					dbSize, 
					errorTolerance, 
					numTrials, 
					correctPatterns
				);
			
		} else if (!runPrefixSpan) {
			
			if (benchmarkMemory) {
				System.out.println("BENCHMARKING MEMORY USAGE FOR PROSECCO");
				runProseccoAlgorithmMemoryBenchmark(
						benchmarkID, 
						inputFile, 
						outputFile, 
						shuffleCommand, 
						minsup, 
						blockSize, 
						dbSize, 
						errorTolerance, 
						numTrials
					);
			} else {
				runProseccoAlgorithm(
						inputFile, 
						minsup, 
						blockSize, 
						dbSize, 
						errorTolerance, 
						print);
			}
			
		} else {
		
			if (benchmarkRuntime || benchmarkMemory) {
				System.out.println("BENCHMARKING RUNTIME AND MEMORY USAGE PREFIXPPAN");
				runPrefixSpanRuntimeAndMemoryBenchmark(benchmarkID, inputFile, outputFile, minsup, numTrials);
			} else {
				runPrefixSpanAlgorithm(inputFile, outputFile, minsup);
			}
		}	

	}


	private static void runProseccoAlgorithmBenchmark(
			String benchmarkID,
			String inputFile, 
			String outputFile, 
			String shuffleCommand,
			double minsup, 
			int blockSize, 
			int dbSize, 
			double errorTolerance, 
			int numRuns,
			SequentialPatterns correctPatterns) {
		
		// try to delete the file
		File file = new File(outputFile);
    	file.delete();

		BenchmarkReport report = new BenchmarkReport(benchmarkID, blockSize, minsup, inputFile);

		for (int i = 0; i < numRuns; i++) {

			// shuffle the dataset
			BenchmarkUtils.shuffleFile(shuffleCommand, inputFile);

			// keep stats
			List<Long> runtimePerBlock = new ArrayList<Long>();
			List<Double> errors = new ArrayList<Double>();
			List<SupportError> supportErrorsPerBlock = new ArrayList<SupportError>();
			List<Integer> falsePositivesPerBlock = new ArrayList<Integer>();
			List<Integer> falseNegativesPerBlock = new ArrayList<Integer>();

			ProseccoCallbacks callback = new ProseccoCallbacks() {

				public void blockUpdate(SequentialPatterns patterns, int numTransactionsProcessed, long blockRuntime, double blockErrorBound) {

					runtimePerBlock.add(blockRuntime);
					errors.add(blockErrorBound);
					int falsePositives = BenchmarkUtils.countFalsePositives(correctPatterns, patterns);
					int falseNegatives = BenchmarkUtils.countFalseNegatives(correctPatterns, patterns);
					SupportError err = BenchmarkUtils.countSupportErrors(correctPatterns, patterns, dbSize, numTransactionsProcessed);
					supportErrorsPerBlock.add(err);
					falsePositivesPerBlock.add(falsePositives);
					falseNegativesPerBlock.add(falseNegatives);
				}	

			};

			AlgoProsecco alg = new AlgoProsecco(callback);

			try {
				alg.runAlgorithm(inputFile, null, blockSize, dbSize, errorTolerance, false, minsup);
				alg.printStatistics();

			} catch (IOException e) {
				e.printStackTrace();
			}


			BenchmarkRun run = new BenchmarkRun(
					i, 
					alg.getTotalRuntime(), 
					runtimePerBlock, 
					errors, 
					supportErrorsPerBlock, 
					falsePositivesPerBlock, 
					falseNegativesPerBlock
					);

			report.addRun(run);

		}

		BenchmarkUtils.saveBenchmarksToJSON(report, outputFile);


	}

	private static void runProseccoAlgorithmMemoryBenchmark(
			String benchmarkID,
			String inputFile, 
			String outputFile, 
			String shuffleCommand,
			double minsup, 
			int blockSize, 
			int dbSize, 
			double errorTolerance, 
			int numRuns) {
		
		// try to delete the file
		File file = new File(outputFile);
    	file.delete();

		BenchmarkReport report = new BenchmarkReport(benchmarkID, blockSize, minsup, inputFile);

		for (int i = 0; i < numRuns; i++) {

			// shuffle the dataset
			BenchmarkUtils.shuffleFile(shuffleCommand, inputFile);

			ProseccoCallbacks callback = new ProseccoCallbacks() {
				public void blockUpdate(
						SequentialPatterns patterns, 
						int numTransactionsProcessed, 
						long blockRuntime, 
						double blockErrorBound) {

					// do nothing
				}	

			};
			
			try {
				MemoryLogger.getInstance().reset();
				AlgoProsecco alg = new AlgoProsecco(callback);
				alg.runAlgorithm(inputFile, null, blockSize, dbSize, errorTolerance, false, minsup);
				alg.printStatistics();

			} catch (IOException e) {
				e.printStackTrace();
			}

			BenchmarkRun run = new BenchmarkRun(
					i, 
					MemoryLogger.getInstance().getMemoryUsage()
					);

			report.addRun(run);
		}

		BenchmarkUtils.saveBenchmarksToJSON(report, outputFile);
	}

	private static void runProseccoAlgorithm(
			String inputFile, 
			double minsup, 
			int blockSize, 
			int dbSize, 
			double errorTolerance, 
			boolean print) {

		ProseccoCallbacks callback = new ProseccoCallbacks() {

			int blockNumber = 0;

			public void blockUpdate(SequentialPatterns patterns, int numTransactionsProcessed, long blockRuntime, double blockErrorBound) {
				// add processing time of current Block

				if (print) {
					System.out.println("******************************************************");
					System.out.println("Block #" + Integer.toString(blockNumber));
					System.out.println("Transactions processed: " + Integer.toString(numTransactionsProcessed) 
					+ " (" + Double.toString((100*(numTransactionsProcessed/(double)dbSize))) + "%)");
					System.out.println("Runtime: " + Long.toString(blockRuntime) + "ms");
					System.out.println("Error bound: " + Double.toString(blockErrorBound));

					patterns.printFrequentPatterns(numTransactionsProcessed, false);
				}

				blockNumber++;
			}	

		};


		try {
			AlgoProsecco alg = new AlgoProsecco(callback);
			alg.runAlgorithm(inputFile, null, blockSize, dbSize, errorTolerance, false, minsup);
			alg.printStatistics();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void runPrefixSpanAlgorithm(String inputFile, String outputFile, double minsup) {
		try {
			AlgoPrefixSpan alg = new AlgoPrefixSpan();
			alg.runAlgorithm(inputFile, minsup, null);
			alg.printStatistics();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void runPrefixSpanRuntimeAndMemoryBenchmark(
			String benchmarkID,
			String inputFile, 
			String outputFile, 
			double minsup, 
			int numRuns) {

		BenchmarkReport report = new BenchmarkReport(benchmarkID, minsup, inputFile);

		for (int i = 0; i < numRuns; i++) {

			long runtime = 0;
			try {
				MemoryLogger.getInstance().reset();
				long startTime = System.currentTimeMillis();
				AlgoPrefixSpan alg = new AlgoPrefixSpan();
				alg.runAlgorithm(inputFile, minsup, null);
				runtime = System.currentTimeMillis() - startTime;
				alg.printStatistics();

			} catch (IOException e) {
				e.printStackTrace();
			}

			BenchmarkRun run = new BenchmarkRun(
					i,
					runtime,
					MemoryLogger.getInstance().getMemoryUsage()
					);

			report.addRun(run);
		}

		BenchmarkUtils.saveBenchmarksToJSON(report, outputFile);
	}
	
	private static void runSPAMRuntimeAndMemoryBenchmark(
			String benchmarkID,
			String inputFile, 
			String outputFile, 
			double minsup, 
			int numRuns) {

		// try to delete the file
		File file = new File(outputFile);
    	file.delete();
    	
		BenchmarkReport report = new BenchmarkReport(benchmarkID, minsup, inputFile);

		for (int i = 0; i < numRuns; i++) {

			long runtime = 0;
				MemoryLogger.getInstance().reset();
				long startTime = System.currentTimeMillis();
				// Create an instance of the algorithm 
				AlgoSPAM algo = new AlgoSPAM(); 
				try {
					algo.runAlgorithm(inputFile, "/tmp/out.txt", minsup);
				} catch (IOException e) {
					e.printStackTrace();
				}    
				runtime = System.currentTimeMillis() - startTime;
				algo.printStatistics();

			
			BenchmarkRun run = new BenchmarkRun(
					i,
					runtime,
					MemoryLogger.getInstance().getMemoryUsage()
					);

			report.addRun(run);
		}

		BenchmarkUtils.saveBenchmarksToJSON(report, outputFile);
	}





}
