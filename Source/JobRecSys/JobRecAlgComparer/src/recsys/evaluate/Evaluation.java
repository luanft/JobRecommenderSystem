package recsys.evaluate;

import recsys.algorithms.collaborativeFiltering.CollaborativeFiltering;
import recsys.datapreparer.CollaborativeFilteringDataPreparer;
import recsys.datapreparer.ContentBasedDataPreparer;
import uit.se.evaluation.dtos.ScoreDTO;
import uit.se.evaluation.metrics.*;
import uit.se.evaluation.utils.DatasetUtil;
import utils.DbConfig;
import utils.MysqlDBConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA. User: tuynguye Date: 8/12/16 Time: 9:47 AM To
 * change this template use File | Settings | File Templates.
 */
public class Evaluation {
	String evaluationType;
	int evaluationParam;
	String algorithm;
	int topN;
	String inputDir;
	String evaluationDir;
	String taskId;
	Properties config;

	public Evaluation(String evalType, int evalParam, String algorithm, String input, String evalDir, String taskId) {
		this.algorithm = algorithm;
		this.evaluationParam = evalParam;
		this.evaluationType = evalType;
		this.inputDir = input;
		this.evaluationDir = evalDir;
		this.taskId = taskId;
		this.config = new Properties();
		try {
			config.load(new FileInputStream(evalDir + "config.properties"));
			topN = Integer.valueOf(config.getProperty("cf.recommendItems"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void percentageSplit() {
		/**
		 * First step: preparing data, split training and testing data set
		 */
		CollaborativeFilteringDataPreparer dataPreparer = new CollaborativeFilteringDataPreparer(inputDir);
		// dataPreparer.splitDataSet(evaluationParam, evaluationDir);
		// if (!algorithm.equals("cf")) {
		// ContentBasedDataPreparer cbDataPreparer = new
		// ContentBasedDataPreparer(inputDir);
		// cbDataPreparer.splitDataSet(evaluationDir);
		// }

		/**
		 * Second step: call Algorithm execute on training data set
		 */
		trainAlgorithm();

		/**
		 * Third step: convert result to boolean type
		 */
		HashMap<Integer, List<ScoreDTO>> groundTruth = DatasetUtil.getScores(evaluationDir + "testing\\Score.txt");
		HashMap<Integer, List<ScoreDTO>> rankList = DatasetUtil.getScores(evaluationDir + "result\\Score.txt");
		/**
		 * Four step: evaluation
		 */

		computeEvaluation(rankList, groundTruth);
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
		trainAlgorithm();

		/**
		 * Third step: convert result to boolean type
		 */
		HashMap<Integer, List<ScoreDTO>> groundTruth = DatasetUtil.getScores(evaluationDir + "testing\\Score.txt");
		HashMap<Integer, List<ScoreDTO>> rankList = DatasetUtil.getScores(evaluationDir + "result\\Score.txt");

		/**
		 * Four step: evaluation
		 */
		computeEvaluation(rankList, groundTruth);
	}

	private void crossValidation() {

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
			trainAlgorithm();

			/**
			 * Third step: convert result to boolean type
			 */
			HashMap<Integer, List<ScoreDTO>> groundTruth = DatasetUtil.getScores(evaluationDir + "testing\\Score.txt");
			HashMap<Integer, List<ScoreDTO>> rankList = DatasetUtil.getScores(evaluationDir + "result\\Score.txt");

			/**
			 * Four step: compute evaluation
			 */
			computeEvaluation(rankList, groundTruth);
		}
	}

	public void evaluate() {

		switch (evaluationType) {
		case "cross":
			crossValidation();
			break;
		case "partitioning":
			percentageSplit();
			break;
		default:
			customValidation();
			break;
		}

	}

	private void computeEvaluation(HashMap<Integer, List<ScoreDTO>> rankList,
			HashMap<Integer, List<ScoreDTO>> groundTruth) {
		double preTopN = 0;
		double precision = 0;
		double recall = 0;
		double recTopN = 0;
		double f = 0;
		double ndcgTopN = 0;
		double rmse = 0;
		double mrr = 0;
		double map = 0;
		for (Integer userId : rankList.keySet()) {
			preTopN += Precision.computePrecisionTopN(rankList.get(userId), groundTruth.get(userId), topN);
			precision += Precision.computePrecision(rankList.get(userId), groundTruth.get(userId));
			recall += Recall.computeRecall(rankList.get(userId), groundTruth.get(userId));
			recall += Recall.computeRecallTopN(rankList.get(userId), groundTruth.get(userId), topN);
			f += FMeasure.computeF1(rankList.get(userId), groundTruth.get(userId));
			try {
				ndcgTopN += NDCG.computeNDCG(rankList.get(userId), groundTruth.get(userId), topN);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rmse += RMSE.computeRMSE(rankList.get(userId), groundTruth.get(userId));
			try {
				mrr += ReciprocalRank.computeRR(rankList.get(userId), groundTruth.get(userId));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map += AveragePrecision.computeAP(rankList.get(userId), groundTruth.get(userId));
		}
		int n = rankList.size();
		preTopN /= n;
		precision /= n;
		recall /= n;
		recTopN /= n;
		f /= n;
		ndcgTopN /= n;
		rmse /= n;
		mrr /= n;
		map /= n;
		System.out.println("P@" + topN + ": " + preTopN);
		System.out.println("P:" + precision);
		System.out.println("R:" + recall);
		System.out.println("R@" + topN + ": " + recTopN);
		System.out.println("F:" + f);
		System.out.println("NDCG@" + topN + ": " + ndcgTopN);
		System.out.println("RMSE:" + rmse);
		System.out.println("MRR:" + mrr);
		System.out.println("MAP:" + map);

		System.out.println("-----------");

		HashMap<String, Double> evaluationResult = new HashMap<>(evaluationParam);
		evaluationResult.put("Precision", precision);
		evaluationResult.put("Recall", recall);
		evaluationResult.put("P@" + topN, preTopN);
		evaluationResult.put("R@" + topN, recTopN);
		evaluationResult.put("F1", f);
		evaluationResult.put("RMSE", rmse);
		evaluationResult.put("NDCG@" + topN, ndcgTopN);
		evaluationResult.put("MRR", mrr);
		evaluationResult.put("MAP", map);

		// write output to db
		writeResult(taskId, evaluationResult);
	}

	private void writeResult(String taskId, HashMap<String, Double> evaluationResult) {
		try {
			MysqlDBConnection con = new MysqlDBConnection(DbConfig.load("config.properties"));
			if (con.connect()) {
				String sql = "INSERT INTO `evaluation`(`TaskId`, `Score`, `Metric`) VALUES ";
				for (String key : evaluationResult.keySet()) {
					sql += "(" + taskId + "," + evaluationResult.get(key) + ", '" + key + "'),";
				}
				con.write(sql.substring(0, sql.length() - 1));
				con.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void trainAlgorithm() {
		switch (algorithm) {
		case "cf":
			trainCF();
			break;
		case "cb":
			trainCB();
			break;
		case "hb":
			trainHB();
			break;
		default:
			break;
		}

	}

	private void trainHB() {

	}

	private void trainCB() {

	}

	private void trainCF() {
		CollaborativeFiltering cf = new CollaborativeFiltering(evaluationDir, config);
		cf.recommend();
	}
}
