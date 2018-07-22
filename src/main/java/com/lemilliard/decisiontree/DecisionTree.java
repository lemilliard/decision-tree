package com.lemilliard.decisiontree;

import com.lemilliard.decisiontree.model.Entry;
import com.lemilliard.decisiontree.model.Node;
import com.lemilliard.decisiontree.model.Result;

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
        
        public DecisionTree(Config config, Set<String> params) {
		this.config = config;
		initDirectory();
		initData(params);
	}
        
        public DecisionTree(Config config, List<List<String>> params) {
		this.config = config;
		initDirectory();
		initDataFromList(params);
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
        
        public void readDataFromFile(Set<String> params) {
		try {
			File f = new File(getFilePath(dataFileName));
			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
			String line;
                        Entry entry = null;
                        boolean ajout = true;
                        List<Integer> listParamEntry = new ArrayList<>();
			// Pour chaque ligne
			while ((line = bufferedReader.readLine()) != null) {
                            ajout = true;
                            entry = Entry.fromText(config, line);
                            for(Map.Entry<Integer, Integer> param : entry.getValues().entrySet()) {
                                listParamEntry.add(param.getKey());
                                if(!params.contains(config.getAttributByIndex(param.getKey()).getName())){
                                    ajout = false;
                                }
                            }
                            for(String s : params){
                                if(!listParamEntry.contains(config.getIndexOfAttribut(s))){
                                    ajout = false;
                                }
                            }
                            
                            if(ajout){
				// On ajoute à l'arbre l'entry de la ligne courante
				tree.getEntries().add(Entry.fromText(config, line));
                            }
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
        
        private void initData(Set<String> params) {
		tree = new Node(config);
		readDataFromFile(params);
	}
        
        public void initDataFromList(List<List<String>> params){
            tree = new Node(config);
            for(List<String> param : params){
                HashMap<Integer, Integer> values = new HashMap<>();
                for (int i = 0; i < param.size() - 1; i++){
                    values.put(i, config.getIndexOfValue(i, param.get(i)));
                }
                tree.getEntries().add(new Entry(values, config.getIndexOfDecision(param.get(param.size() - 1)), 1l));
            }
        }
        
        public void initFirstData(){
            for(int i = 0; i < config.getAttributs().size(); i++){
                for(int j = 0; j < config.getAttributs().get(i).getValues().length; j++){
                    HashMap<Integer, Integer> values = new HashMap<>();
                    values.put(i, j);
                    for(int k = 0; k < config.getDecisions().size(); k++){
                        tree.getEntries().add(new Entry(values, k, 1l));
                    }
                }
            }
        }
        
        public void initAllData(){
            Stack<Integer> pile = new Stack<>();
            pile.push(0);
            HashMap<Integer, Integer> valInit = new HashMap<>();
            for(int j = 0; j < pile.size(); j++){
                valInit.put(j, pile.get(j));
            }
            if(!valInit.isEmpty()){
                for(int k = 0; k < config.getDecisions().size(); k++){
                    Entry entry = new Entry(valInit, k, 1l);
                    if(tree.getEntries().isEmpty()){
                        tree.getEntries().add(entry);
                    }

                    if(!containsEntry(entry)){
                        tree.getEntries().add(entry);
                    }
                }
            }
            Integer i = 0;
            boolean popWithoutPush = false;
            boolean popWithPush = false;
            while(!pile.isEmpty()){
                if(!popWithPush && pile.lastElement() == config.getAttributs().get(pile.size() - 1).getValues().length - 1){
                    i = pile.lastElement();
                    pile.pop();
                    popWithoutPush = true;
                    if(!pile.isEmpty()){
                        i = pile.lastElement();
                        if(i < config.getAttributs().get(pile.size() - 1).getValues().length - 1){
                            pile.pop();
                            pile.push(i+1);
                            popWithoutPush = false;
                            popWithPush = true;
                        }
                    }
                } else if(pile.size() < config.getAttributs().size() && !popWithoutPush){
                    pile.push(0);  
                    popWithoutPush = false;
                    popWithPush = false;
                } else {
                   i = pile.lastElement();
                   pile.pop();
                   pile.push(i+1);
                   popWithoutPush = false;
                   popWithPush = false;
                }
                HashMap<Integer, Integer> values = new HashMap<>();
                for(int j = 0; j < pile.size(); j++){
                    values.put(j, pile.get(j));
                }
                if(!values.isEmpty()){
                    for(int k = 0; k < config.getDecisions().size(); k++){
                        Entry entry = new Entry(values, k, 1l);
                        if(tree.getEntries().isEmpty()){
                            tree.getEntries().add(entry);
                        }
                        
                        if(!containsEntry(entry)){
                            tree.getEntries().add(entry);
                        }
                        
//                            tree.getEntries().add(entry);
                    }
                }
            }
        }
        
        public boolean containsEntry(Entry e){
            boolean exist = false;
            Iterator<Entry> it = tree.getEntries().iterator();
            while(it.hasNext() && !exist){
                if(it.next().isEquals(e)){
                    exist = true;
                }
            }
            return exist;
        }
}