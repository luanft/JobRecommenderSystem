package app;

import recsys.algorithms.collaborativeFiltering.CFAlgorithm;
import recsys.algorithms.collaborativeFiltering.CollaborativeFiltering;
import recsys.algorithms.hybird.HybirdRecommeder;

public class App {

	public static void main(String[] args) {

<<<<<<< HEAD
		HybirdRecommeder hybirdRecommeder = new HybirdRecommeder();
		hybirdRecommeder.setInputDirectory("F:/JOB_REC/Input/");
		hybirdRecommeder.setOutputDirectory("F:/JOB_REC/Output/");	
		hybirdRecommeder.init();
		hybirdRecommeder.contentBasedFiltering();
		
<<<<<<< HEAD:Source/Comparer/Comparer/src/app/App.java
		//CollaborativeFiltering cf = new CollaborativeFiltering("C:/Users/TUYEN/Desktop/garbage/algorithm/", "C:/Users/TUYEN/Desktop/garbage/algorithm/");
		//cf.recommend(CFAlgorithm.UserBase, 10);		
=======
		CollaborativeFiltering cf = new CollaborativeFiltering("C:/Users/TUYEN/Desktop/garbage/algorithm/", "C:/Users/TUYEN/Desktop/garbage/algorithm/");
		cf.recommend(CFAlgorithm.UserBase, 100);		
>>>>>>> 0f0f68a1f1339c7f0a14232b336a7ed3f0007cee:Source/JobRecSys/JobRecAlgComparer/src/app/App.java
=======
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
		hybirdRecommeder.contentBasedFiltering();
>>>>>>> 813aaa73d6a4d64fb5057dbbf8f3b1266efab41d
	}

	private static void contentBase(String input, String output) {
		// TODO Auto-generated method stub

	}

	private static void collaborativeFiltering(String input, String output) {
		CollaborativeFiltering cf = new CollaborativeFiltering(input, output);
		cf.recommend(CFAlgorithm.UserBase, 10);
	}
}
