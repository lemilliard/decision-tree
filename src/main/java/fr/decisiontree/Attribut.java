package fr.decisiontree;

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

	public boolean isPossibleValue(int value) {
		return value >= values.length;
	}

	public Integer getIndexOfValue(String valueName) {
		boolean found = false;
		int i = 0;
		while (i < values.length && !found) {
			if (values[i].equals(valueName)) {
				found = true;
			} else {
                            i++;
                        }
		}
		return i;
	}
}
