package app;

import recsys.algorithms.cf.CFAlgorithm;
import recsys.algorithms.cf.CollaborativeFiltering;
import recsys.algorithms.hybird.HybirdRecommeder;
public class App {


	public static void main(String[] args) {

//		HybirdRecommeder hybirdRecommeder = new HybirdRecommeder();
//		hybirdRecommeder.setInputDirectory("F:/JOB_REC/Input/");
//		hybirdRecommeder.setOutputDirectory("F:/JOB_REC/Output/");	
//		hybirdRecommeder.init();
//		hybirdRecommeder.contentBasedFiltering();
		
		CollaborativeFiltering cf = new CollaborativeFiltering("data/", "data/");
		cf.recommend(CFAlgorithm.UserBase, 10);
		cf.recommend(CFAlgorithm.ItemBase, 10);
	}

}
