package com.lemilliard.decisiontree;

public class Attribut {

	private String name;

	private String[] values;

	public Attribut(String name, String... values) {
		this.name = name;
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public boolean isPossibleValue(Integer valueIndex) {
		return valueIndex != null && valueIndex < values.length;
	}

	public Integer getIndexOfValue(String valueName) {
		int index = -1;
		int i = 0;
		while (i < values.length && index == -1) {
			if (values[i].equals(valueName)) {
				index = i;
			}
			i++;
		}
		return index;
	}
}
