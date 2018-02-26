package fr.decisiontree;

import fr.decisiontree.model.Entry;
import fr.decisiontree.model.Node;
import fr.decisiontree.model.Result;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecisionTree {

	private static final String dataFileName = "data.txt";

	private static Node tree;

	private static Config config;

	public static void init(Config config) {
		DecisionTree.config = config;
		initDirectory();
		initData();
	}

	private static void initData() {
		tree = new Node(config);
		readDataFromFile();
		tree.generateTree();
	}

	private static void initDirectory() {
		File dir = new File(config.getDirectory());
		try {
			Files.createDirectories(dir.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void print() {
		tree.print();
	}

	public static void regenerateTree() {
		tree.regenerateTree();
	}

	public static void save() {
		writeDataToFile();
	}

	private static String getFilePath(String fileName) {
		return config.getDirectory() + "/" + fileName;
	}

	public static void readDataFromFile() {
		try {
			File f = new File(getFilePath(dataFileName));
			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				tree.getEntries().add(Entry.fromText(line));
			}
		} catch (IOException e) {
			System.out.println("Aucune données");
		}
	}

	public static void writeDataToFile() {
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
        
        public static Result decide(HashMap<String, String> params) {
		Entry entry = entryFromParams(params, null);
                List<Integer> listAttributs = new ArrayList<>();
                for (Map.Entry<String, String> param : params.entrySet()) {
			listAttributs.add(config.getIndexOfAttribut(param.getKey()));
                }
                tree.regenerateTree(listAttributs);
                tree.print();
		return tree.decide(entry);
	}

	public static void addData(HashMap<String, String> params, Integer decision) {
		Entry entry = entryFromParams(params, decision);
		tree.addEntry(entry);
	}

	private static Entry entryFromParams(HashMap<String, String> params, Integer decision) {
		HashMap<Integer, Integer> values = new HashMap<>();
		int attributIndex;
		int valueIndex;
		for (Map.Entry<String, String> param : params.entrySet()) {
			attributIndex = config.getIndexOfAttribut(param.getKey());
			valueIndex = config.getIndexOfValue(attributIndex, param.getValue());
			values.put(attributIndex, valueIndex);
		}
		return new Entry(values, decision);
	}
}