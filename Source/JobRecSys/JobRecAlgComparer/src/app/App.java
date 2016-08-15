package app;

import recsys.algorithms.collaborativeFiltering.CFAlgorithm;
import recsys.algorithms.collaborativeFiltering.CollaborativeFiltering;
import recsys.algorithms.collaborativeFiltering.SimilarityMeasure;
import recsys.algorithms.contentBased.ContentBasedRecommender;
import recsys.algorithms.hybird.HybirdRecommeder;
import recsys.evaluate.CFEvaluation;

public class App {

  public static void main(String[] args) {

    CFEvaluation cFEvaluate = new CFEvaluation(30);
    
    
    cFEvaluate.init("E:\\SoftwareLocation\\apache-tomcat-8.0.36\\data\\1\\Test\\input\\", "E:\\SoftwareLocation\\apache-tomcat-8.0.36\\data\\1\\Test\\output\\");
    System.out.println("precision: "+cFEvaluate.precision());
    System.out.println("recall: "+cFEvaluate.recall());
    System.out.println("MAE: "+cFEvaluate.mAE());
    System.out.println("RMSE: "+cFEvaluate.rMSE());

//    if (args[0].equals("cf")) {
//      collaborativeFiltering(args[1], args[2]);
//    } else {
//      if (args[0].equals("cb")) {
//        contentBase(args[1], args[2]);
//      } else {
//        if (args[0].equals("hb")) {
//          hybrid(args[1], args[2]);
//        }
//      }
//    }
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
    cf.recommend(CFAlgorithm.UserBase, SimilarityMeasure.LOGLIKELIHOOD_SIMILARITY, 10, 10);
  }
}
