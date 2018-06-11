package fr.decisiontree;

import fr.decisiontree.model.Result;

import java.util.HashMap;

public class Exemple {

	public static void main(String... args) {
//		exemple1();
		exemple2();
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
//		HashMap<String, String> values = new HashMap<>();
//		values.put("Ciel", "Soleil");
//		values.put("Humidité", "Normale");
//		Result decision;
//		decision = decisionTree.decide(values);
//		System.out.println(decision.getValue());
//		System.out.println(decision.getRatio());
//
//		values = new HashMap<>();
//		values.put("Température", "Basse");
//		values.put("Humidité", "Normale");
//		values.put("Vent", "Fort");
//		decision = decisionTree.decide(values);
//		System.out.println(decision.getValue());
//		System.out.println(decision.getRatio());
//
//		values = new HashMap<>();
//		values.put("Température", "Basse");
//		decision = decisionTree.decide(values);
//		System.out.println(decision.getValue());
//		System.out.println(decision.getRatio());

		decisionTree.print();
		decisionTree.save();
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

		DecisionTree decisionTree = new DecisionTree(config);

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
}
