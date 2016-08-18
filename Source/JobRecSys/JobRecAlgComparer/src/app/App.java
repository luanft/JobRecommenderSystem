package app;

import recsys.algorithms.collaborativeFiltering.CFAlgorithm;
import recsys.algorithms.collaborativeFiltering.CollaborativeFiltering;
import recsys.algorithms.collaborativeFiltering.SimilarityMeasure;
import recsys.algorithms.contentBased.ContentBasedRecommender;
import recsys.algorithms.hybird.HybirdRecommeder;
import recsys.evaluate.Evaluation;

public class App {

	public static void main(String[] args) {

		if (args[0].equals("rec")) {
			if (args[1].equals("cf")) {
				collaborativeFiltering(args[2], args[3]);
			} else {
				if (args[0].equals("cb")) {
					contentBase(args[2], args[3]);
				} else {
					if (args[0].equals("hb")) {
						hybrid(args[2], args[3]);
					}
				}
			}
		} else {
			if (args[1].equals("cf")) {
				Evaluation.evaluate(Integer.parseInt(args[2]), args[1], args[3], args[4], args[5]);
			} else {
			}
		}

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

	private static void collaborativeFiltering(String input, String output) {
		CollaborativeFiltering cf = new CollaborativeFiltering(input, output);
		cf.recommend(CFAlgorithm.UserBase,
				SimilarityMeasure.LOGLIKELIHOOD_SIMILARITY, 10, 10);
	}
}
