package app;

import recsys.algorithms.collaborativeFiltering.CollaborativeFiltering;
import recsys.algorithms.contentBased.ContentBasedRecommender;
import recsys.algorithms.hybird.HybirdRecommeder;
import recsys.evaluate.Evaluation;

public class App {

	public static void main(String[] args) {

		switch (args[0]) {
		case "rec":
			recommend(args);
			break;
		case "eval":
			evaluate(args);
			break;
		default:
			break;
		}			
	}
	
	private static void recommend(String[] args){
		switch (args[1]) {
		case "cf":
			collaborativeFiltering(args[3], args[4], Boolean.valueOf(args[2]));
			break;
		case "cb":
			contentBase(args[3], args[4]);
			break;
		case "hb":
			hybrid(args[3], args[4]);
			break;			
		default:
			break;
		}
	}
	
	private static void evaluate(String[] args){
		Evaluation.evaluate(Integer.parseInt(args[3]), args[1], Boolean.valueOf(args[2]), args[4], args[5], args[6]);
	}
		
	private static void hybrid(String input, String output) {
		HybirdRecommeder hybridRecommender = new HybirdRecommeder();
		hybridRecommender.setInputDirectory(input);
		hybridRecommender.setOutputDirectory(output);
		hybridRecommender.init();
		hybridRecommender.hibridRecommend();

	}

	private static void contentBase(String input, String output) {
		ContentBasedRecommender CBRec = new ContentBasedRecommender();
		CBRec.setInputDirectory(input);
		CBRec.setOutputDirectory(output);
		CBRec.init();
		CBRec.contentBasedFiltering();

	}

	private static void collaborativeFiltering(String input, String output, boolean useConfig) {
		CollaborativeFiltering cf = new CollaborativeFiltering(input, output, useConfig);
		cf.recommend();
	}
}
