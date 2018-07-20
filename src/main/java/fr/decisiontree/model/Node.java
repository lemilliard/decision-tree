package fr.decisiontree.model;

import fr.decisiontree.Config;
import fr.decisiontree.utils.ConsoleColors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by tkint on 23/11/2017.
 */
public class Node {

	private int attributIndex;

	private List<Branch> branches;

	private List<Entry> entries;

	private Config config;

	public Node(Config config) {
		this.attributIndex = -1;
		this.config = config;
		this.branches = new ArrayList<>();
		this.entries = new ArrayList<>();
	}

	public Node(Config config, Integer valueToKeep, Integer att, List<Entry> entries) {
		this(config);
		for (Entry entry : entries) {
			if (entry.getValues().containsKey(att) && entry.getValues().get(att).equals(valueToKeep)) {
				addEntry(entry);
			}
		}
	}

	public int getAttributIndex() {
		return attributIndex;
	}

	public void setAttributIndex(int attributIndex) {
		this.attributIndex = attributIndex;
	}

	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public Branch addBranch(Branch branch) {
		branches.add(branch);
		return branch;
	}

	public Entry addEntry(Entry entry) {
		if (entries.contains(entry)) {
			entry.addOccurence();
		} else {
			entries.add(entry);
		}
		return entry;
	}

	private long entriesCount() {
		long count = 0;
		for (Entry entry : entries) {
			count += entry.getCount();
		}
		return count;
	}

	public Entry getEntryByValuesAndDecision(HashMap<Integer, Integer> values, Integer decision) {
		Entry entry = null;
		for (Entry e : entries) {
			if (e.getValues().equals(values) && e.getDecision().equals(decision)) {
				entry = e;
			}
		}
		if (entry == null) {
			return new Entry(values, decision, 1l);
		}
		return entry;
	}

	public double entropie(int attributIndex, int valueIndex) {
		double[] p = new double[config.getDecisions().size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = 0;
		}

		for (Entry entry : entries) {
			if (!entry.getValues().containsKey(attributIndex)) {
				return 0d;
			} else if (entry.getValues().get(attributIndex) == valueIndex) {
				p[entry.getDecision()] += entry.getCount();
			}
		}

		for (int i = 0; i < p.length; i++) {
			if (p[i] == entriesCount()) {
				return 0d;
			}
		}

		for (int i = 0; i < p.length; i++) {
			p[i] /= entriesCount();
		}

		double entropie = 0;

		for (int i = 0; i < p.length; i++) {
			entropie = entropie - (p[i] * log2(p[i]));
		}

		return entropie;
	}

	public double entropie() {
		double[] p = new double[config.getDecisions().size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = 0;
		}

		for (Entry entry : entries) {
			p[entry.getDecision()]+= entry.getCount();
		}

		for (int i = 0; i < p.length; i++) {
			if (p[i] == entriesCount()) {
				return 0d;
			}
		}

		for (int i = 0; i < p.length; i++) {
			p[i] /= entriesCount();
		}

		double entropie = 0;

		for (int i = 0; i < p.length; i++) {
			entropie = entropie - (p[i] * log2(p[i]));
		}

		return entropie;
	}

	private double log2(double x) {
		if (x == 0) {
			return 0d;
		}
		return Math.log(x) / Math.log(2.);
	}

	/**
	 * Retourne la pertinence d'un attribut
	 *
	 * @param attributIndex
	 * @return
	 */
	public Double pertinence(int attributIndex) {
		double pertinence = entropie();
		for (int valueIndex = 0; valueIndex < config.getAttributByIndex(attributIndex)
				.getValues().length; valueIndex++) {
			pertinence -= partPertinence(attributIndex, valueIndex);
		}
		return pertinence;
	}

	/**
	 * Retourne la pertinence d'une valeur d'un attribut
	 *
	 * @param attributIndex
	 * @param valueIndex
	 * @return
	 */
	private double partPertinence(int attributIndex, int valueIndex) {
		return getValueRatio(attributIndex, valueIndex) * entropie(attributIndex, valueIndex);
	}

	/**
	 * Retourne le ratio d'une valeur d'un attribut sur les entries du noeud courant
	 *
	 * @param attributIndex
	 * @param valueIndex
	 * @return
	 */
	public double getValueRatio(int attributIndex, int valueIndex) {
		double count = 0d;

		// Pour chaque entry
		for (Entry entry : entries) {
			if (!entry.getValues().containsKey(attributIndex)) {
				return 0d;
			} else if (entry.getValues().get(attributIndex) == valueIndex) {
				count += entry.getCount();
			}
		}

		if (entriesCount() == 0) {
			return 0d;
		}

		return count / entriesCount();
	}

	public double getResultatRatio(int attributIndex) {
		double count = 0d;

		// Pour chaque entry
		for (Entry entry : entries) {
			if (entry.getDecision().equals(attributIndex)) {
				count += entry.getCount();
			}
		}

		if (entriesCount() == 0) {
			return 0d;
		}

		return count / entriesCount();
	}

	public Integer getPlusPertinent() {
		Double pertinence = 0d;
		Integer plusPertinent = -1;
		for (int i = 0; i < config.getAttributs().size(); i++) {
			Double p = pertinence(i);
			if (p > pertinence) {
				pertinence = p;
				plusPertinent = i;
			}
		}

		if (plusPertinent == -1) {
			return null;
		}

		return plusPertinent;
	}

	public Integer getPlusPertinent(List<Integer> listAttributs) {
		Double p;
		Double pertinence = 0d;
		Integer plusPertinent = -1;
		for (Integer i : listAttributs) {
			 p = pertinence(i);
			if (p > pertinence) {
				pertinence = p;
				plusPertinent = i;
			}
		}

		if (plusPertinent == -1) {
			return null;
		}

		return plusPertinent;
	}

	public int getDecision() {
		List<Integer> pDecisions = new ArrayList<>();

		for (int i = 0; i < config.getDecisions().size(); i++) {
			pDecisions.add(0);
		}

		for (Entry entry : entries) {
			pDecisions.set(entry.getDecision(), pDecisions.get(entry.getDecision()) + 1);
		}

		int max = 0;
		int maxIndex = -1;
		for (int i = 0; i < pDecisions.size(); i++) {
			if (pDecisions.get(i) > max) {
				max = pDecisions.get(i);
				maxIndex = i;
			}
		}

		return maxIndex;
	}

	public void regenerateTree(List<Integer> listAttributs) {
		setBranches(new ArrayList<>());
		generateTree(listAttributs);
	}

	public void generateTree(List<Integer> listAttributs) {
		Integer plusPertinent = getPlusPertinent(listAttributs);
		if (plusPertinent != null) {
			setAttributIndex(plusPertinent);
			for (int valueIndex = 0; valueIndex < config.getAttributByIndex(plusPertinent)
					.getValues().length; valueIndex++) {
				Branch branch = addBranch(new Branch(valueIndex));
				Node child = branch.setChild(new Node(config, valueIndex, plusPertinent, entries));
//				listAttributs.remove(plusPertinent);
				child.generateTree(listAttributs);
			}
		} else {
			setAttributIndex(getDecision());
		}
	}

	public Result decide(Entry entry) {
		if (getBranches().size() == 0) {
			if (attributIndex != -1) {
				String treeDecision = config.getDecisions().get(attributIndex);
				double ratio = getResultatRatio(attributIndex);
				return new Result(treeDecision, ratio);
			}
		} else {
			if (entry != null) {
				for (Branch branch : getBranches()) {
					if (entry.getValues().containsKey(attributIndex) && entry.getValues().get(attributIndex).equals(branch.getValueIndex())) {
						return branch.getNode().decide(entry);
					}
				}
			}
		}
		return null;
	}

	public void print() {
		print("#", true, "");
	}

	private void print(String prefix, boolean isTail, String branchValue) {
		if (attributIndex != -1) {
			boolean isFinal = branches.isEmpty();
			String txt = prefix;
			txt += !branchValue.equals("") ? isTail ? "└── " : "├── " : " ── ";
			txt += !branchValue.equals("") ? ConsoleColors.BLUE + branchValue + ConsoleColors.RESET + " => "
					: ConsoleColors.BLUE;
			txt += isFinal ? ConsoleColors.GREEN + config.getDecisions().get(attributIndex) : ConsoleColors.RED + config
					.getAttributByIndex(attributIndex)
					.getName();
			txt += " (" + attributIndex + ") " + ConsoleColors.RESET;
			System.out.println(txt);
			txt = prefix;
			txt += isTail ? "     " : "│    ";
			for (int i = 0; i < branches.size() - 1; i++) {
				branchValue = config.getValue(attributIndex, branches.get(i).getValueIndex());
				branchValue += " (" + branches.get(i).getValueIndex() + ")";
				branches.get(i).getNode().print(txt, false, branchValue);
			}
			if (branches.size() > 0) {
				branchValue = config.getValue(attributIndex, branches.get(branches.size() - 1).getValueIndex());
				branchValue += " (" + branches.get(branches.size() - 1).getValueIndex() + ")";
				branches.get(branches.size() - 1).getNode().print(txt, true, branchValue);
			}
		}
	}
}
