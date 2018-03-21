package fr.decisiontree;

import fr.decisiontree.model.Entry;
import fr.decisiontree.model.Node;
import fr.decisiontree.model.Result;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class DecisionTree {

	private static final String dataFileName = "data.txt";

	private Node tree;

	private Config config;

	public DecisionTree(Config config) {
		this.config = config;
		initDirectory();
		initData();
	}

	private void initData() {
		tree = new Node(config);
		readDataFromFile();
	}

	private void initDirectory() {
		File dir = new File(config.getDirectory());
		try {
			Files.createDirectories(dir.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void print() {
		tree.print();
	}

	public void save() {
		writeDataToFile();
	}

	private String getFilePath(String fileName) {
		return config.getDirectory() + "/" + fileName;
	}

	public void readDataFromFile() {
		try {
			File f = new File(getFilePath(dataFileName));
			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				tree.getEntries().add(Entry.fromText(config, line));
			}
		} catch (IOException e) {
			System.out.println("Aucune données");
		}
	}

	public void writeDataToFile() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(getFilePath(dataFileName), "UTF-8");
			for (Entry entry : tree.getEntries()) {
				writer.println(entry.toText());
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

//	public static Result decide(HashMap<String, String> params) {
//		Entry entry = entryFromParams(params, null);
//		return tree.decide(entry);
//	}

	public Result decide(HashMap<String, String> params) {
		Result result = null;
		Entry entry = entryFromParams(params, null);
		List<Integer> listAttributs = new ArrayList<>();
		for (Map.Entry<String, String> param : params.entrySet()) {
			listAttributs.add(config.getIndexOfAttribut(param.getKey()));
		}
		tree.regenerateTree(listAttributs);
		if (entry != null) {
			result = tree.decide(entry);
		}
		return result;
	}

	public void addData(HashMap<String, String> params, Integer decision) {
		Entry entry = entryFromParams(params, decision);
		if (entry != null) {
			tree.addEntry(entry);
		}
	}

	private Entry entryFromParams(HashMap<String, String> params, Integer decision) {
		Entry entry = null;
		boolean valid = true;
		HashMap<Integer, Integer> values = new HashMap<>();
		int attributIndex;
		int valueIndex;
		Map.Entry<String, String> param;
		Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext() && valid) {
			param = iterator.next();
			attributIndex = config.getIndexOfAttribut(param.getKey());
			valueIndex = config.getIndexOfValue(attributIndex, param.getValue());
			if (attributIndex == -1 || valueIndex == -1) {
				valid = false;
			} else {
				values.put(attributIndex, valueIndex);
			}
		}
		if (valid) {
			entry = new Entry(values, decision);
		}
		return entry;
	}
}