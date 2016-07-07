package app;

import recsys.algorithms.collaborativeFiltering.CFAlgorithm;
import recsys.algorithms.collaborativeFiltering.CollaborativeFiltering;
import recsys.algorithms.hybird.HybirdRecommeder;
public class App {


	public static void main(String[] args) {

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
	}

}
