package recsys.evaluate;

import dto.ScoreDTO;
import recsys.algorithms.collaborativeFiltering.CollaborativeFiltering;
import recsys.datapreparer.CollaborativeFilteringDataPreparer;
import recsys.datapreparer.ContentBasedDataPreparer;
import utils.DbConfig;
import utils.MysqlDBConnection;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: tuynguye Date: 8/12/16 Time: 9:47 AM To
 * change this template use File | Settings | File Templates.
 */
public class Evaluation {
	String evaluationType;
	int evaluationParam;
	String algorithm;
	boolean useConfig;
	String inputDir;
	String evaluationDir;
	String taskId;

	public Evaluation(String evalType, int evalParam, String algorithm, boolean useConfig, String input, String evalDir,
			String taskId) {
		this.algorithm = algorithm;
		this.evaluationParam = evalParam;
		this.evaluationType = evalType;
		this.useConfig = useConfig;
		this.inputDir = input;
		this.evaluationDir = evalDir;
		this.taskId = taskId;
	}

	private void partitioningValidation() {
		/**
		 * First step: preparing data, split training and testing data set
		 */
		CollaborativeFilteringDataPreparer dataPreparer = new CollaborativeFilteringDataPreparer(inputDir);
		dataPreparer.splitDataSet(evaluationParam, evaluationDir);
		if (!algorithm.equals("cf")) {
			ContentBasedDataPreparer cbDataPreparer = new ContentBasedDataPreparer(inputDir);
			cbDataPreparer.splitDataSet(evaluationDir);
		}

		/**
		 * Second step: call Algorithm execute on training data set
		 */
		training();

		/**
		 * Third step: convert result to boolean type
		 */
		List<ScoreDTO> testingList = dataPreparer.getAllEvaluateScores(evaluationDir + "testing\\");
		List<ScoreDTO> resultList = dataPreparer.getAllEvaluateScores(evaluationDir + "result\\");
		/**
		 * Four step: evaluation
		 */
		HashMap<String, Float> evaluationResult = new HashMap<>(evaluationParam);
		EvaluationMetrics evalMetrics = new EvaluationMetrics(testingList, resultList);
		float precision = evalMetrics.calculatePrecision();
		float recall = evalMetrics.calculateRecall();
		evaluationResult.put("precision", precision);
		evaluationResult.put("recall", recall);
		evaluationResult.put("f1", evalMetrics.calculateF1(precision, recall));
		evaluationResult.put("mae", evalMetrics.calculateMAE());
		evaluationResult.put("rmse", evalMetrics.calculateRMSE());
		writeResult(taskId, evaluationResult);
	}

	private void customValidation() {
		/**
		 * First step: preparing data, split training and testing data set
		 */
		CollaborativeFilteringDataPreparer dataPreparer = new CollaborativeFilteringDataPreparer(inputDir);
		dataPreparer.copyFileTo(inputDir + "Score.txt", evaluationDir + "training\\Score.txt");
		if (!algorithm.equals("cf")) {
			ContentBasedDataPreparer cbDataPreparer = new ContentBasedDataPreparer(inputDir);
			cbDataPreparer.copyFileTo(inputDir, evaluationDir + "training\\");
		}

		/**
		 * Second step: call Algorithm execute on training data set
		 */
		training();

		/**
		 * Third step: convert result to boolean type
		 */
		List<ScoreDTO> testingList = dataPreparer.getAllEvaluateScores(evaluationDir + "testing\\");
		List<ScoreDTO> resultList = dataPreparer.getAllEvaluateScores(evaluationDir + "result\\");
		/**
		 * Four step: evaluation
		 */
		HashMap<String, Float> evaluationResult = new HashMap<>(evaluationParam);
		EvaluationMetrics evalMetrics = new EvaluationMetrics(testingList, resultList);
		float precision = evalMetrics.calculatePrecision();
		float recall = evalMetrics.calculateRecall();
		evaluationResult.put("precision", precision);
		evaluationResult.put("recall", recall);
		evaluationResult.put("f1", evalMetrics.calculateF1(precision, recall));
		evaluationResult.put("mae", evalMetrics.calculateMAE());
		evaluationResult.put("rmse", evalMetrics.calculateRMSE());
		writeResult(taskId, evaluationResult);
	}

	private void crossValidation() {
		HashMap<String, Float> evaluationResult = new HashMap<>(evaluationParam);
		evaluationResult.put("precision", 0.0f);
		evaluationResult.put("recall", 0.0f);
		evaluationResult.put("f1", 0.0f);
		evaluationResult.put("mae", 0.0f);
		evaluationResult.put("rmse", 0.0f);
		CollaborativeFilteringDataPreparer dataPreparer = new CollaborativeFilteringDataPreparer(inputDir);
		for (int i = 0; i < evaluationParam; i++) {
			/**
			 * First step: preparing data, split training and testing data set
			 */
			dataPreparer.splitDataSet(i, evaluationParam, inputDir, evaluationDir);
			if (!algorithm.equals("cf")) {
				ContentBasedDataPreparer cbDataPreparer = new ContentBasedDataPreparer(inputDir);
				cbDataPreparer.splitDataSet(evaluationDir);
			}

			/**
			 * Second step: call Algorithm execute on training data set
			 */
			training();

			/**
			 * Third step: convert result to boolean type
			 */
			List<ScoreDTO> testingList = dataPreparer.getAllEvaluateScores(evaluationDir + "testing\\");
			List<ScoreDTO> resultList = dataPreparer.getAllEvaluateScores(evaluationDir + "result\\");

			/**
			 * Four step: evaluation
			 */
			EvaluationMetrics evalMetrics = new EvaluationMetrics(testingList, resultList);
			float precision = evalMetrics.calculatePrecision();
			float recall = evalMetrics.calculateRecall();
			evaluationResult.put("precision", evaluationResult.get("precision") + precision);
			evaluationResult.put("recall", evaluationResult.get("recall") + recall);
			evaluationResult.put("f1", evaluationResult.get("f1") + evalMetrics.calculateF1(precision, recall));
			evaluationResult.put("mae", evaluationResult.get("mae") + evalMetrics.calculateMAE());
			evaluationResult.put("rmse", evaluationResult.get("rmse") + evalMetrics.calculateRMSE());

		}

		evaluationResult.put("precision", evaluationResult.get("precision") / evaluationParam);
		evaluationResult.put("recall", evaluationResult.get("recall") / evaluationParam);
		evaluationResult.put("f1", evaluationResult.get("f1") / evaluationParam);
		evaluationResult.put("mae", evaluationResult.get("mae") / evaluationParam);
		evaluationResult.put("rmse", evaluationResult.get("rmse") / evaluationParam);
		writeResult(taskId, evaluationResult);
	}

	public void evaluate() {

		switch (evaluationType) {
		case "cross":
			crossValidation();
			break;
		case "partitioning":
			partitioningValidation();
			break;
		default:
			customValidation();
			break;
		}

	}

	private static void writeResult(String taskId, HashMap<String, Float> eval) {

		MysqlDBConnection con = new MysqlDBConnection(DbConfig.load("config.txt"));
		if (con.connect()) {
			String sql = "INSERT INTO `evaluation`(`MetricId`, `TaskId`, `Score`) VALUES (1," + taskId + ","
					+ eval.get("precision") + "),";
			sql += "(2," + taskId + "," + eval.get("recall") + "),";
			sql += "(3," + taskId + "," + eval.get("f1") + "),";
			sql += "(4," + taskId + "," + eval.get("mae") + "),";
			sql += "(5," + taskId + "," + eval.get("rmse") + ")";
			con.write(sql);
			con.close();
		}
	}

	private void training() {
		switch (algorithm) {
		case "cf":
			trainingCF();
			break;
		case "cb":
			trainingCB();
			break;
		case "hb":
			trainingHB();
			break;
		default:
			break;
		}

	}

	private void trainingHB() {

	}

	private void trainingCB() {

	}

	private void trainingCF() {
		CollaborativeFiltering cf = new CollaborativeFiltering(evaluationDir, useConfig);
		cf.recommend();
	}
}
