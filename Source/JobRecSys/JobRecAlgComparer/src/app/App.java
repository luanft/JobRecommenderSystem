package app;

import recsys.algorithms.collaborativeFiltering.CFAlgorithm;
import recsys.algorithms.collaborativeFiltering.CollaborativeFiltering;
import recsys.algorithms.hybird.HybirdRecommeder;
public class App {


	public static void main(String[] args) {

//		HybirdRecommeder hybirdRecommeder = new HybirdRecommeder();
//		hybirdRecommeder.setInputDirectory("F:/JOB_REC/Input/");
//		hybirdRecommeder.setOutputDirectory("F:/JOB_REC/Output/");	
//		hybirdRecommeder.init();
//		hybirdRecommeder.contentBasedFiltering();
		
		CollaborativeFiltering cf = new CollaborativeFiltering("C:/Users/TUYEN/Desktop/garbage/algorithm/", "C:/Users/TUYEN/Desktop/garbage/algorithm/");
		cf.recommend(CFAlgorithm.UserBase, 100);		
	}

}
