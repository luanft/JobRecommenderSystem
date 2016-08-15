package recsys.evaluate;

import dto.ScoreDTO;
import recsys.algorithms.collaborativeFiltering.CFAlgorithm;
import recsys.algorithms.collaborativeFiltering.CollaborativeFiltering;
import recsys.algorithms.collaborativeFiltering.SimilarityMeasure;
import recsys.datapreparer.CollaborativeFilteringDataPreparer;

import java.util.List;

/**
 * Created with IntelliJ IDEA. User: tuynguye Date: 8/12/16 Time: 9:47 AM To
 * change this template use File | Settings | File Templates.
 */
public class CFEvaluation {
	int splitProportion = 0;
	Evaluation evaluation;

	public CFEvaluation(int proportionOfTest) {
		this.splitProportion = proportionOfTest;
	}

	public void init(String input, String output) {

		/**
		 * First step: preparing data, split training and testing data set
		 */
		CollaborativeFilteringDataPreparer dataPreparer = new CollaborativeFilteringDataPreparer(input);
		dataPreparer.splitDataSet(splitProportion, output);

		/**
		 * Second step: call to CF Algorithm execute
		 */
		CollaborativeFiltering cf = new CollaborativeFiltering(output + "\\training\\", output + "\\result\\",
				output + "\\testing\\");
		cf.recommend(CFAlgorithm.UserBase, SimilarityMeasure.LOGLIKELIHOOD_SIMILARITY, 0.7f, 10);

		/**
		 * Third step: convert result to boolean type
		 */
		List<ScoreDTO> testingList = dataPreparer.getAllEvaluateScores(output + "\\testing\\");
		List<ScoreDTO> resultList = dataPreparer.getAllEvaluateScores(output + "\\result\\");
		/**
		 * Four step: evaluation
		 */
		evaluation = new Evaluation(testingList, resultList);
	}

	public float precision() {
		return evaluation.calculatePrecision();
	}

	public float recall() {
		return evaluation.calculateRecall();
	}

	public float f1(float precision, float recall) {
		return evaluation.calculateF1(precision, recall);
	}

	public float mAE(){
		return evaluation.calculateMAE();
	}
	
	public float rMSE(){
		return evaluation.calculateRMSE();
	}
}
