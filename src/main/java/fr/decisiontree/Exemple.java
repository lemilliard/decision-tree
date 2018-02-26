package fr.decisiontree;

import fr.decisiontree.model.Result;

import java.util.HashMap;

public class Exemple {

	public static void main(String... args) {
		Config config = new Config("./exemple");

		config.addAttribut("Ciel", "Soleil", "Couvert", "Pluie");
		config.addAttribut("Température", "Basse", "Haute", "Pole Nord");
		config.addAttribut("Humidité", "Normale", "Elevée");
		config.addAttribut("Vent", "Faible", "Fort");

		config.addDecision("Jouer");
		config.addDecision("Rester");

		DecisionTree.init(config);

//		DecisionTree.print();

		HashMap<String, String> values = new HashMap<>();
		values.put("Ciel", "Soleil");
		values.put("Humidité", "Normale");
                Result decision;
		decision = DecisionTree.decide(values);
		System.out.println(decision.getValue());
		System.out.println(decision.getRatio());

		values = new HashMap<>();
		values.put("Température", "Basse");
		values.put("Humidité", "Normale");
		values.put("Vent", "Fort");
		decision = DecisionTree.decide(values);
		System.out.println(decision.getValue());
		System.out.println(decision.getRatio());
                
                values = new HashMap<>();
		values.put("Température", "Basse");
		decision = DecisionTree.decide(values);
		System.out.println(decision.getValue());
		System.out.println(decision.getRatio());

		DecisionTree.save();
	}
}
