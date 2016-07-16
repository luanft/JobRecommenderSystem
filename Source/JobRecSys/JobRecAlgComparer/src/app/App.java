package app;

import recsys.algorithms.collaborativeFiltering.CFAlgorithm;
import recsys.algorithms.collaborativeFiltering.CollaborativeFiltering;
import recsys.algorithms.hybird.HybirdRecommeder;

public class App {

	public static void main(String[] args) {	
		
			
		switch (args[0]) {
		case "cf":
			collaborativeFiltering(args[1], args[2]);
			break;
		case "cb":
			contentBase(args[1], args[2]);
		case "hb":
			hybrid(args[1], args[2]);
		default:
			break;
		}

	}

	private static void hybrid(String input, String output) {
		HybirdRecommeder hybirdRecommeder = new HybirdRecommeder();
		hybirdRecommeder.setInputDirectory(input);
		hybirdRecommeder.setOutputDirectory(output);
		hybirdRecommeder.init();
		hybirdRecommeder.hibridRecommend();
		
	}

	private static void contentBase(String input, String output) {
		// TODO Auto-generated method stub

	}

	private static void collaborativeFiltering(String input, String output) {
		CollaborativeFiltering cf = new CollaborativeFiltering(input, output);
		cf.recommend(CFAlgorithm.UserBase, 10);
	}
}
