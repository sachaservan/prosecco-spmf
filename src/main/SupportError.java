package main;

import java.util.ArrayList;
import java.util.List;

public class SupportError {


	List<Double> diffs;
	List<Double> normalizedErrors;
	List<Double> absoluteErrors;

	public SupportError() {
		diffs = new ArrayList<Double>();
		normalizedErrors = new ArrayList<Double>();
		absoluteErrors = new ArrayList<Double>();
	}

	public void addErrors(double diff, double normalized, double absolute) {
		this.diffs.add(diff);
		this.normalizedErrors.add(normalized);
		this.absoluteErrors.add(absolute);
	}

	public List<Double> getDiffs() {
		return diffs;
	}

	public List<Double> getNormalizedErrors() {
		return normalizedErrors;
	}

	public List<Double> getAbsoluteErrors() {
		return absoluteErrors;
	}

}