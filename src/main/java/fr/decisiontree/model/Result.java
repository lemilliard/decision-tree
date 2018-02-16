package fr.decisiontree.model;

/**
 * Created by tkint on 16/02/2018.
 */
public class Result {

	private String value;

	private double ratio;

	public Result(String value, double ratio) {
		this.value = value;
		this.ratio = ratio;
	}

	public String getValue() {
		return value;
	}

	public double getRatio() {
		return ratio;
	}
}
