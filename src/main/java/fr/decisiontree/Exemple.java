package fr.decisiontree;

import fr.decisiontree.model.Result;

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

		DecisionTree.print();

		Result decision = DecisionTree.decide("Soleil", "Basse", "Normale", "Fort");
		System.out.println(decision.getValue());
		System.out.println(decision.getRatio());

		decision = DecisionTree.decide("Couvert", "Basse", "Normale", "Fort");
		System.out.println(decision.getValue());
		System.out.println(decision.getRatio());

		DecisionTree.save();
	}
}
