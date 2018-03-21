package fr.decisiontree.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tkint on 23/11/2017.
 */
public class Entry {

	private HashMap<Integer, Integer> values;

	private Integer decision;

	public Entry(HashMap<Integer, Integer> values, Integer decision) {
		this.values = values;
		this.decision = decision;
	}

	public static Entry fromText(String line) {
		HashMap<Integer, Integer> intAtts = new HashMap<>();
		String[] stringAtts = line.split(",");
		for (int i = 0; i < stringAtts.length - 1; i++) {
			String stringAtt = stringAtts[i];
			String[] hyujk = stringAtt.split(":");
			intAtts.put(Integer.valueOf(hyujk[0]), Integer.valueOf(hyujk[1]));
		}
		Integer decision = Integer.valueOf(stringAtts[stringAtts.length - 1]);
		return new Entry(intAtts, decision);
	}

	public Integer getDecision() {
		return decision;
	}

	public void setDecision(Integer decision) {
		this.decision = decision;
	}

	public HashMap<Integer, Integer> getValues() {
		return values;
	}

	public void setValues(HashMap<Integer, Integer> values) {
		this.values = values;
	}

	public String toText() {
		StringBuilder str = new StringBuilder();
		for (Map.Entry<Integer, Integer> value : values.entrySet()) {
			str.append(value.getKey()).append(":").append(value.getValue()).append(",");
		}
		str.append(decision);
		return str.toString();
	}
}
