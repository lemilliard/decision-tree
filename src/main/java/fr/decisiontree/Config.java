package fr.decisiontree;

import java.util.ArrayList;
import java.util.List;

public class Config {

	private String directory;

	private List<Attribut> attributs;

	private List<String> decisions;

	public Config(String directory) {
		this.directory = directory;
		this.attributs = new ArrayList<>();
		this.decisions = new ArrayList<>();
	}

	public List<String> getDecisions() {
		return decisions;
	}

	public List<Attribut> getAttributs() {
		return attributs;
	}

	public Attribut getAttributByIndex(int attributIndex) {
		return attributs.get(attributIndex);
	}

	public void addDecision(String decision) {
		this.decisions.add(decision);
	}

	public void addAttribut(String attributName, String... values) {
		this.attributs.add(new Attribut(attributName, values));
	}

	public String getDirectory() {
		return directory;
	}

	public int getIndexOfValue(int attributIndex, String valueName) {
		return attributs.get(attributIndex).getIndexOfValue(valueName);
	}

	public List<Integer> getAttributIndexes() {
		List<Integer> indexes = new ArrayList<>();
		for (int i = 0; i < attributs.size(); i++) {
			indexes.add(i);
		}
		return indexes;
	}

	/**
	 * Retourne l'index de l'attribut basé sur son nom
	 *
	 * @param attributName
	 * @return
	 */
	public int getIndexOfAttribut(String attributName) {
		int index = -1;
		int i = 0;
		while (i < attributs.size() && index == -1) {
			if (attributs.get(i).getName().equals(attributName)) {
				index = i;
			}
			i++;
		}
		return index;
	}

	public String getValue(int attributIndex, int valueIndex) {
		return attributs.get(attributIndex).getValues()[valueIndex];
	}

	public Integer getIndexOfDecision(String decisionName) {
		int index = -1;
		int i = 0;
		while (i < decisions.size() && index == -1) {
			if (decisions.get(i).equals(decisionName)) {
				index = i;
			}
			i++;
		}
		return index;
	}

	public boolean isPossibleAttribut(Integer attributIndex) {
		return attributIndex != null && attributIndex < attributs.size();
	}

	public boolean isKeyValueValid(Integer attributIndex, Integer valueIndex) {
		return isPossibleAttribut(attributIndex) && attributs.get(attributIndex).isPossibleValue(valueIndex);
	}
}
