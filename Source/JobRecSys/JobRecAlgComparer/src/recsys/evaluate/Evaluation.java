package recsys.evaluate;

import dto.ScoreDTO;
import recsys.algorithms.collaborativeFiltering.CFAlgorithm;
import recsys.algorithms.collaborativeFiltering.CollaborativeFiltering;
import recsys.algorithms.collaborativeFiltering.SimilarityMeasure;
import recsys.datapreparer.CollaborativeFilteringDataPreparer;
import recsys.datapreparer.ContentBasedDataPreparer;
import utils.DbConfig;
import utils.MysqlDBConnection;

import java.util.List;

/**
 * Created with IntelliJ IDEA. User: tuynguye Date: 8/12/16 Time: 9:47 AM To
 * change this template use File | Settings | File Templates.
 */
public class Evaluation {

	public static void evaluate(int proportionOfTest, String algorithm,
			String input, String output, String taskId) {

		/**
		 * First step: preparing data, split training and testing data set
		 */
		CollaborativeFilteringDataPreparer dataPreparer = new CollaborativeFilteringDataPreparer(
				input);
		dataPreparer.splitDataSet(proportionOfTest, output + "cf\\");
		ContentBasedDataPreparer cbDataPreparer = new ContentBasedDataPreparer(
				input);
		cbDataPreparer.splitDataSet(output + "cf\\testing\\", output + "cb\\");

		/**
		 * Second step: call to CF Algorithm execute on training data set
		 */
		training(algorithm, output);

		/**
		 * Third step: convert result to boolean type
		 */
		List<ScoreDTO> testingList = dataPreparer.getAllEvaluateScores(output
				+ "cf\\testing\\");
		List<ScoreDTO> resultList = dataPreparer.getAllEvaluateScores(output
				+ "cf\\result\\");
		/**
		 * Four step: evaluation
		 */
		writeResult(taskId, new EvaluationMetrics(testingList, resultList));
	}

	private static void writeResult(String taskId, EvaluationMetrics eval) {
		MysqlDBConnection con = new MysqlDBConnection(
				DbConfig.load("config.txt"));
		if (con.connect()) {
			float recall = eval.calculateRecall();
			float precision = eval.calculatePrecision();
			String sql = "INSERT INTO `evaluation`(`MetricId`, `TaskId`, `Score`) VALUES (1,"
					+ taskId + "," + precision + "),";
			sql += "(2," + taskId + "," + recall + "),";
			sql += "(3," + taskId + "," + eval.calculateF1(precision, recall)
					+ "),";
			sql += "(4," + taskId + "," + eval.calculateMAE() + "),";
			sql += "(5," + taskId + "," + eval.calculateRMSE() + ")";
			con.write(sql);
			con.close();
		}
	}

	private static void training(String algorithm, String output) {
		switch (algorithm) {
		case "cf":
			cf(output);
			break;
		case "cb":
			cb(output);
			break;
		case "hb":
			hb(output);
			break;
		default:
			break;
		}

	}

	private static void hb(String output) {

	}

	private static void cb(String output) {

	}

	private static void cf(String output) {
		CollaborativeFiltering cf = new CollaborativeFiltering(output
				+ "cf\\training\\", output + "cf\\result\\", output
				+ "cf\\testing\\");
		cf.recommend(CFAlgorithm.UserBase,
				SimilarityMeasure.LOGLIKELIHOOD_SIMILARITY, 5, 10);
	}
}
