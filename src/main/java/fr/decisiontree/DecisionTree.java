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

	public Node getTree() {
		return tree;
	}

	public Config getConfig() {
		return config;
	}

	public static String getDataFileName() {
		return dataFileName;
	}

	private void initData() {
		tree = new Node(config);
//		creataFileDataManouche();
		readDataFromFile();
	}

	/**
	 * Créé le dossier si besoin
	 */
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
        
        public void save(Entry decision) {
		writeDataToFile(decision);
	}

	private String getFilePath(String fileName) {
		return config.getDirectory() + "/" + fileName;
	}

	/**
	 * Récupère les données depuis le fichier
	 */
	public void readDataFromFile() {
		try {
			File f = new File(getFilePath(dataFileName));
			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
			String line;
			// Pour chaque ligne
			while ((line = bufferedReader.readLine()) != null) {
				// On ajoute à l'arbre l'entry de la ligne courante
				tree.getEntries().add(Entry.fromText(config, line));
			}
		} catch (IOException e) {
			System.out.println("Aucune données");
		}
	}
        
        public void creataFileDataManouche() {
            PrintWriter writer = null;
		try {
			File f = new File(getFilePath(dataFileName));
			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
			String line;
                        
			writer = new PrintWriter(getFilePath("dataMesCouilles.txt"), "UTF-8");
			
			// Pour chaque ligne
			while ((line = bufferedReader.readLine()) != null) {
                            String[] params = line.split(",");
                            String s = "";
                            for(int i = 0; i < params.length; i++){
                                if( i < params.length - 2){
                                    s += String.valueOf(i) + ":" + params[i] + ",";
                                } else {
                                    s += params[i];
                                    if (i == params.length - 2){
                                        s += ",";
                                    }
                                }
                            }
                            writer.println(s);
			}
		} catch (IOException e) {
			System.out.println("Aucune données");
		}finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * Sauvegarde les données dans le fichier
	 */
	public void writeDataToFile() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(getFilePath(dataFileName), "UTF-8");
			for (Entry entry : tree.getEntries()) {
//                                writer.append(entry.toText() + "\r\n");
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
        
        public void writeDataToFile(Entry decision) {
		PrintWriter writer = null;
                boolean exist = false;
		try {
			writer = new PrintWriter(getFilePath(dataFileName), "UTF-8");
			for (Entry entry : tree.getEntries()) {
                            if(entry.isEquals(decision)){
                                exist = true;
                                entry.addOccurence();
                            }
                            writer.println(entry.toText());
			}
                        if(exist == false){
                            writer.printf(decision.toText());
                        }
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * Decide sur une partie des attributs
	 *
	 * @param params
	 * @return
	 */
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

	/**
	 * Récupére une entry correspondant aux paramètres donnés
	 * @param params
	 * @param decision
	 * @return
	 */
	public Entry entryFromParams(HashMap<String, String> params, Integer decision) {
		Entry entry = null;
		boolean valid = true;
		int attributIndex;
		int valueIndex;
		Map.Entry<String, String> param;
		Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
		HashMap<Integer, Integer> values = new HashMap<>();
		// Pour chaque paramètre
		while (iterator.hasNext() && valid) {
			param = iterator.next();
			// On récupère l'index de l'attribut correspondant
			attributIndex = config.getIndexOfAttribut(param.getKey());
			// On récupère l'index de la valeur correspondante
			valueIndex = config.getIndexOfValue(attributIndex, param.getValue());
			// Si l'attribut ou la valeur n'est pas trouvé, le paramètre n'est pas valide
			if (attributIndex == -1 || valueIndex == -1) {
				valid = false;
			} else {
				// Sinon, on ajout le paramètre aux valeurs
				values.put(attributIndex, valueIndex);
			}
		}
		// Si l'entry est valide
		if (valid) {
			entry = new Entry(values, decision, 1l);
		}
		return entry;
	}
}