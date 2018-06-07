package fr.decisiontree.model;

import fr.decisiontree.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tkint on 23/11/2017.
 */
public class Entry {

	private HashMap<Integer, Integer> values;

	private Integer decision;

	private Integer count;

	public Entry(HashMap<Integer, Integer> values, Integer decision, Integer count) {
		this.values = values;
		this.decision = decision;
		this.count = count;
	}

	public static Entry fromText(Config config, String line) {
		HashMap<Integer, Integer> intAtts = new HashMap<>();
		String[] stringAtts = line.split(",");
		String stringAtt;
		String[] keyValue;
		Integer key;
		Integer value;
		for (int i = 0; i < stringAtts.length - 2; i++) {
			stringAtt = stringAtts[i];
			keyValue = stringAtt.split(":");
			key = Integer.valueOf(keyValue[0]);
			value = Integer.valueOf(keyValue[1]);
			if (config.isKeyValueValid(key, value)) {
				intAtts.put(Integer.valueOf(keyValue[0]), Integer.valueOf(keyValue[1]));
			}
		}
		Integer decision = Integer.valueOf(stringAtts[stringAtts.length - 2]);
		Integer count = Integer.valueOf(stringAtts[stringAtts.length - 1]);
		return new Entry(intAtts, decision, count);
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String toText() {
		StringBuilder str = new StringBuilder();
		for (Map.Entry<Integer, Integer> value : values.entrySet()) {
			str.append(value.getKey()).append(":").append(value.getValue()).append(",");
		}
		str.append(decision).append(",");
		str.append(count);
		return str.toString();
	}

	public void addOccurence() {
		count++;
	}
}
