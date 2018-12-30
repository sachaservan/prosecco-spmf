package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import prefixspan.SequentialPattern;
import prefixspan.SequentialPatterns;


import com.google.gson.*;

public class BenchmarkUtils {

	public static SupportError countSupportErrors(SequentialPatterns correctPatterns, SequentialPatterns patterns, int dbSize, int numProcessed) {

		SupportError error = new SupportError();
		
		int k = 0;
		
		for (List<SequentialPattern> level : correctPatterns.levels) {

			for (SequentialPattern correctPattern : level) {

				if (patterns.getLevelCount() > k) {

					for (SequentialPattern pattern : patterns.getLevel(k)) {

						if (correctPattern.equals(pattern)) {
							double diff = correctPattern.getAbsoluteSupport()/(double)dbSize - pattern.getAbsoluteSupport()/(double)numProcessed;
							double normalizedError = Math.abs((correctPattern.getAbsoluteSupport()/(double)dbSize) - (pattern.getAbsoluteSupport()/(double)numProcessed)) / (correctPattern.getAbsoluteSupport()/(double)dbSize);
							double absoluteError = Math.abs((correctPattern.getAbsoluteSupport()/(double)dbSize) - (pattern.getAbsoluteSupport()/(double)numProcessed));
							error.addErrors(diff, normalizedError, absoluteError);
							break;
						}
					}

				} 

			}

			k++;
		}

		return error;
	}


	public static int countFalsePositives(SequentialPatterns correctPatterns, SequentialPatterns patterns) {

		int falsePositives = 0;
		int k = 0;
		for (List<SequentialPattern> level : patterns.levels) {
			for (SequentialPattern pattern : level) {

				boolean found = false;

				if (correctPatterns.getLevelCount() > k) {

					for (SequentialPattern correctPattern : correctPatterns.getLevel(k)) {
						if (correctPattern.equals(pattern)) {
							found = true;
							break;
						}
					}

				}

				if (!found) {
					falsePositives++;
				}
			}

			k++;
		}

		return falsePositives;
	}

	public static int countFalseNegatives(SequentialPatterns correctPatterns, SequentialPatterns patterns) {

		int falseNegatives = 0;
		int k = 0;

		for (List<SequentialPattern> level : correctPatterns.levels) {

			for (SequentialPattern correctPattern : level) {

				boolean found = false;

				if (patterns.getLevelCount() > k) {

					for (SequentialPattern pattern : patterns.getLevel(k)) {

						if (correctPattern.equals(pattern)) {
							found = true;
							break;
						}
					}

				} 

				if (!found) {
					falseNegatives++;
				}
			}

			k++;
		}

		return falseNegatives;
	}
	
	public static void shuffleFile(String shuffleCommand, String filePath) {
		Runtime rt = Runtime.getRuntime();
		try {
			// remove the file
			rt.exec("rm /tmp/shuffled");
			// shuffle the file
			rt.exec(shuffleCommand + " " + filePath + " > /tmp/shuffled");
			// copy back
			rt.exec("cp /tmp/shuffled " + filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static BenchmarkReport loadBenchmarkReport(String path) {
		Gson gson = new Gson();

		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(path));
			 Object json = gson.fromJson(bufferedReader, BenchmarkReport.class);
			 return json == null ? null : (BenchmarkReport) json;
		} catch (FileNotFoundException e) {
			return null;
		} catch (JsonSyntaxException e) {
			return null;
		}
	    
	}
	
	public static void saveBenchmarksToJSON(BenchmarkReport r, String outputFilePath) {
		Gson gson = new Gson();

		try {
			String json = gson.toJson(r, BenchmarkReport.class);
			FileWriter writer = new FileWriter(outputFilePath);
			   writer.write(json);
			   writer.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public static void deleteTmpPatterns(String filePath) {

		Runtime rt = Runtime.getRuntime();
		try {
			// remove the file
			rt.exec("rm " + filePath);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void savePatterns(SequentialPatterns sp, String filePath) {
		Gson gson = new Gson();

		try {
			gson.toJson(sp, new FileWriter(filePath));
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public static SequentialPatterns loadPatterns(String filePath) {
		Gson gson = new Gson();

		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(filePath));
			 Object json = gson.fromJson(bufferedReader, SequentialPatterns.class);
			 return json == null ? null : (SequentialPatterns) json;
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return null;
		} catch (JsonSyntaxException e) {
			System.out.println("Syntax exception");
			return null;
		}
	
	}
}
