package prefixspan;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/*
 *  Copyright (c) 2008-2012 Philippe Fournier-Viger
 * 
 * This file is part of the SPMF DATA MINING SOFTWARE
 * (http://www.philippe-fournier-viger.com/spmf).
 *
 * SPMF is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SPMF is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SPMF.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * This class is used to record the maximum memory usaged of an algorithm during
 * a given execution.
 * It is implemented by using the "singleton" design pattern.
 *
 */
public class MemoryLogger {
	
	// the only instance  of this class (this is the "singleton" design pattern)
	private static MemoryLogger instance = new MemoryLogger();

	// variable to store the maximum memory usage
	private double maxMemory = 0;
		
	private long startTime = 0;
	
	private Map<Long, Long> memoryUsage = new HashMap<Long, Long>();;
	
	/**
	 * Method to obtain the only instance of this class
	 * @return instance of MemoryLogger
	 */
	public static MemoryLogger getInstance(){
		return instance;
	}
	
	/**
	 * To get the maximum amount of memory used until now
	 * @return a double value indicating memory as megabytes
	 */
	public double getMaxMemory() {
		return maxMemory;
	}
	
	public Map<Long, Long> getMemoryUsage() {
		return memoryUsage;
	}
	
	public double currentMemoryUsage() {
		return (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())
				/ 1024d / 1024d;
	}

	/**
	 * Reset the maximum amount of memory recorded.
	 */
	public void reset(){
		System.gc();
		maxMemory = 0;
		memoryUsage = new Hashtable<Long, Long>();
	}
	
	/**
	 * Check the current memory usage and record it if it is higher
	 * than the amount of memory previously recorded.
	 */
	public void checkMemory() {
		double currentMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())
				/ 1024d / 1024d;
		if (memoryUsage.size() == 0) 
			startTime = System.currentTimeMillis();
			
		memoryUsage.put(System.currentTimeMillis() - startTime, (long)currentMemory);
		if (currentMemory > maxMemory) {
			maxMemory = currentMemory;
		}
	}
}
