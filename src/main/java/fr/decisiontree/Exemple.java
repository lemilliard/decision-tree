package fr.decisiontree;

import fr.decisiontree.model.Entry;
import fr.decisiontree.model.Result;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

public class Exemple {

	public static void main(String... args) {
//		exemple1();
//		exemple2();
//                exempleBanque();
                exempleChatBot();
	}

	private static void exemple1() {
		Config config = new Config("./exemple");

		config.addAttribut("Ciel", "Soleil", "Couvert", "Pluie");
		config.addAttribut("Température", "Basse", "Haute", "Pole Nord");
		config.addAttribut("Humidité", "Normale", "Elevée");
		config.addAttribut("Vent", "Faible", "Fort");

		config.addDecision("Jouer");
		config.addDecision("Rester");

		DecisionTree decisionTree = new DecisionTree(config);
                decisionTree.getTree().generateTree(config.getAttributIndexes());
		HashMap<String, String> values = new HashMap<>();
		values.put("Ciel", "Soleil");
		values.put("Humidité", "Normale");
		Result decision;
		decision = decisionTree.decide(values);
		System.out.println(decision.getValue());
		System.out.println(decision.getRatio());

		values = new HashMap<>();
		values.put("Température", "Basse");
		values.put("Humidité", "Normale");
		values.put("Vent", "Fort");
		decision = decisionTree.decide(values);
		System.out.println(decision.getValue());
		System.out.println(decision.getRatio());
//
		values = new HashMap<>();
		values.put("Température", "Basse");
		decision = decisionTree.decide(values);
		System.out.println(decision.getValue());
		System.out.println(decision.getRatio());
                Entry entry = decisionTree.entryFromParams(values, config.getIndexOfDecision(decision.getValue()));

		decisionTree.print();
		decisionTree.save(entry);
	}

	private static void exemple2() {
		Config config = new Config("./exemple2");

		config.addAttribut("Outlook", "Rainy", "Overcast", "Sunny");
		config.addAttribut("Temperature", "Hot", "Mild", "Cool");
		config.addAttribut("Humidity", "High", "Normal");
		config.addAttribut("Windy", "False", "True");

		config.addDecision("Yes");
		config.addDecision("No");
                
//                config.addAttribut("Couleur", "normale", "colorimétré");
//		config.addAttribut("Noyaux", "0", "1", "2");
//		config.addAttribut("Flagelles", "0", "1", "2");
//
//		config.addDecision("fine");
//		config.addDecision("epaisse");
                
                List<List<String>> params = new ArrayList<>();

                try {
			File f = new File(config.getDirectory() + "/" + "data.txt");
			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
			String line;
			// Pour chaque ligne
			while ((line = bufferedReader.readLine()) != null) {
                                List<String> s = new ArrayList<>();
				// On ajoute à l'arbre l'entry de la ligne courante
				String[] lineParams = line.split(",");
                                for(int i = 0; i < lineParams.length - 2; i++){
                                    String test = config.getValue(i, Integer.valueOf(lineParams[i].split(":")[1]));
                                    s.add(test);
                                }
                                s.add(config.getDecisions().get(Integer.valueOf(lineParams[lineParams.length - 2])));
                                params.add(s);
			}
		} catch (IOException e) {
			System.out.println("Aucune données");
		}

		DecisionTree decisionTree = new DecisionTree(config, params);

		decisionTree.getTree().generateTree(config.getAttributIndexes());

		decisionTree.print();
                
//                HashMap<String, String> values = new HashMap<>();
//		values.put("Couleur", "normale");
//		values.put("Noyaux", "1");
//		values.put("Flagelles", "1");
//		Result decision;
//		decision = decisionTree.decide(values);
//		System.out.println(decision.getValue());
//		System.out.println(decision.getRatio());
	}
        
        private static void exempleBanque() {
		Config config = new Config("./exemple3");

		config.addAttribut("Age", "18-30", "30-50", "50+");
		config.addAttribut("Salaire", "1000-1500", "1500-2500", "2500-5000", "5000+");
		config.addAttribut("MontantCompte", "0", "500", "1500", "5000+");
		config.addAttribut("Credit", "Oui", "Non");
		config.addAttribut("NbEnfant", "0", "1", "2", "3", "3+");

		config.addDecision("ContratA");
		config.addDecision("ContratB");
		config.addDecision("ContratC");
		config.addDecision("ContratD");

		DecisionTree decisionTree = new DecisionTree(config);

		decisionTree.getTree().generateTree(config.getAttributIndexes());

		decisionTree.print();
	}
        
        private static void exempleChatBot() {
		Config config = new Config("./exempleChat");

		config.addAttribut("Action", "acceder", "recherche", "effectuer");
		config.addAttribut("Cible", "compte", "virement", "contrat", "suivante", "precedente");

		config.addDecision("acces compte");
		config.addDecision("acces virement");
		config.addDecision("recherche contrat");
		config.addDecision("effectuer virement");
		config.addDecision("acces contrat");
		config.addDecision("acces page suivante");
		config.addDecision("acces page precedente");

		DecisionTree decisionTree = new DecisionTree(config);
                decisionTree.initFirstData();

		decisionTree.getTree().generateTree(config.getAttributIndexes());

		decisionTree.print();
                
                decisionTree.save();
	}
}
