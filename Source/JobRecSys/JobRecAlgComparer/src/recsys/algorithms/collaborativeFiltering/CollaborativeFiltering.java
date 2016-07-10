package recsys.algorithms.collaborativeFiltering;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import recsys.datapreparer.CollaborativeFilteringDataPreparer;

public class CollaborativeFiltering {

	DataSource dataSource;
	DataModel dataModel;
	Recommender recommender;
	UserSimilarity userSimilarity;
	ItemSimilarity itemSimilarity;
	UserNeighborhood userNeightborhood;
	List<Integer> listUserIds;
	String outputDirectory;

	public CollaborativeFiltering(String inputDir, String outputDir){
		try {
			dataModel = new FileDataModel(new File(inputDir + "Score.txt"));		
		} catch (IOException e) {
			e.printStackTrace();
		}
		outputDirectory = outputDir;
		listUserIds = new CollaborativeFilteringDataPreparer(inputDir).getListUserId();
	}

	public void recommend(CFAlgorithm algorithm, int numberOfRecItems) {
		switch (algorithm) {
		case UserBase:
			for (Integer userId : listUserIds) {
				try {
					UserBase(SimilarityMeasure.LOGLIKELIHOOD_SIMILARITY, TypeOfNeighborhood.THRESHOLDSUSER, 0.7f, userId,
							numberOfRecItems);
				} catch (TasteException e) {
					e.printStackTrace();
				}
			}
			break;
		case ItemBase:
			for (Integer userId : listUserIds) {
				try {
					ItemBase(SimilarityMeasure.LOGLIKELIHOOD_SIMILARITY, userId, numberOfRecItems);
				} catch (TasteException e) {
					e.printStackTrace();
				}
			}
			break;

		default:
			break;
		}
	}

	/**
	 * Recommendation using user-base method
	 * 
	 * @param sm
	 *            {@link SimilarityMeasure}
	 * @param type
	 *            {@link TypeOfNeighborhood}
	 * @param numberOfNeighbor
	 *            {@link Integer}
	 * @param userIDToRecommend
	 *            {@link Integer} {@code UserID to receive recommender}
	 * @param numberOfRecItems
	 *            {@link Integer} {@code Number of recommender items}
	 * @throws TasteException
	 */
	public void UserBase(SimilarityMeasure sm, TypeOfNeighborhood type, float numberOfNeighbor, int userIDToRecommend,
			int numberOfRecItems) throws TasteException {
		// initialize user'similarity
		InitUserSimilaritymeasure(sm);
		// initialize user neighborhood
		InitUserNeighborhood(type, numberOfNeighbor);
		// initialize recommender
		recommender = new GenericUserBasedRecommender(dataModel, userNeightborhood, userSimilarity);
		writeOutput(userIDToRecommend, recommender.recommend(userIDToRecommend, numberOfRecItems));
	}

	/**
	 * Recommendation using item-base method
	 * 
	 * @param sm
	 *            {@link SimilarityMeasure}
	 * @param userIDToRecommend
	 *            {@link Integer}
	 * @param numberOfRecItems
	 *            {@link Integer}
	 * @throws TasteException
	 */
	public void ItemBase(SimilarityMeasure sm, int userIDToRecommend, int numberOfRecItems) throws TasteException {
		// initialize item's similarity
		InitItemSimilarityMeasure(sm);
		// initialize recommender
		recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
		writeOutput(userIDToRecommend, recommender.recommend(userIDToRecommend, numberOfRecItems));
	}

	/**
	 * Initialize the user similarity measure
	 * 
	 * @param sm
	 *            {@link SimilarityMeasure}
	 * @throws TasteException
	 */
	private void InitUserSimilaritymeasure(SimilarityMeasure sm) throws TasteException {
		switch (sm) {
		case LOGLIKELIHOOD_SIMILARITY:
			userSimilarity = new LogLikelihoodSimilarity(dataModel);
			break;
		case EUCLIDEAN_DISTANCE:
			userSimilarity = new EuclideanDistanceSimilarity(dataModel);
			break;
		case PEARSON_CORRELATION:
			userSimilarity = new PearsonCorrelationSimilarity(dataModel);
			break;
		case SPEARMAN_CORRELATION:
			userSimilarity = new SpearmanCorrelationSimilarity(dataModel);
			break;
		case TANIMOTO_COOFFICIENT:
			userSimilarity = new TanimotoCoefficientSimilarity(dataModel);
			break;
		default:
			break;
		}
	}

	/**
	 * Initialize item's similarity
	 * 
	 * @param sm
	 *            {@link SimilarityMeasure}
	 * @throws TasteException
	 */
	private void InitItemSimilarityMeasure(SimilarityMeasure sm) throws TasteException {
		switch (sm) {
		case LOGLIKELIHOOD_SIMILARITY:
			itemSimilarity = new LogLikelihoodSimilarity(dataModel);
			break;
		case EUCLIDEAN_DISTANCE:
			itemSimilarity = new EuclideanDistanceSimilarity(dataModel);
			break;
		case PEARSON_CORRELATION:
			itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
			break;
		case TANIMOTO_COOFFICIENT:
			itemSimilarity = new TanimotoCoefficientSimilarity(dataModel);
			break;
		default:
			break;
		}
	}

	/**
	 * Initialize the user neighborhood
	 * 
	 * @param type
	 *            {@link TypeOfNeighborhood}
	 * @param numberOfNeighbor
	 *            {@link Integer}
	 * @throws TasteException
	 */
	private void InitUserNeighborhood(TypeOfNeighborhood type, float numberOfNeighbor) throws TasteException {
		switch (type) {
		case NEARESTNUSER:
			userNeightborhood = new NearestNUserNeighborhood((int) numberOfNeighbor, userSimilarity, dataModel);
			break;
		case THRESHOLDSUSER:
			userNeightborhood = new ThresholdUserNeighborhood(numberOfNeighbor, userSimilarity, dataModel);
			break;
		default:
			break;
		}
	}

	private void writeOutput(int userId, List<RecommendedItem> recommendedItems) {
		FileWriter fwr;
		try {
			fwr = new FileWriter(new File(outputDirectory + "CF_REC_ITEMS.txt"), true);
			BufferedWriter wr = new BufferedWriter(fwr);
			System.out.println("start writing data");
			for (RecommendedItem rec : recommendedItems) {
				wr.write(userId + "\t" + rec.getItemID() + "\t" + rec.getValue());
				System.out.println("Result: " + userId + "\t" + rec.getItemID() + "\t" + rec.getValue());
				wr.newLine();
			}
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
