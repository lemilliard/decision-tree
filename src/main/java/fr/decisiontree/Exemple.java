package fr.decisiontree;

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

		String decision = DecisionTree.decide("Soleil", null, "Normale", null);
		System.out.println(decision);

		decision = DecisionTree.decide("Soleil", null, "Elevée", null);
		System.out.println(decision);

		DecisionTree.save();
	}
}
