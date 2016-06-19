package uit.se.recsys.Algorithm.CF;

import java.io.File;
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

import uit.se.recsys.Algorithm.RecommendationAlgorithm;

public class CollaborativeFiltering extends RecommendationAlgorithm {

	DataSource dataSource;
	DataModel dataModel;
	Recommender recommender;
	UserSimilarity userSimilarity;
	ItemSimilarity itemSimilarity;
	UserNeighborhood userNeightborhood;

	public CollaborativeFiltering() {
	}

	public CollaborativeFiltering(String dataset) throws IOException {
		dataModel = new FileDataModel(new File(dataset));
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
	 * @param UserIDToRecommend
	 *            {@link Integer} {@code UserID to receive recommender}
	 * @param numberOfRecItems
	 *            {@link Integer} {@code Number of recommender items}
	 * @return {@code List<RecommendedItem>: List recommended items for the UserID}
	 * @throws TasteException
	 */
	public List<RecommendedItem> UserBase(SimilarityMeasure sm, TypeOfNeighborhood type, int numberOfNeighbor,
			int UserIDToRecommend, int numberOfRecItems) throws TasteException {
		// initialize user'similarity
		InitUserSimilaritymeasure(sm);
		// initialize user neighborhood
		InitUserNeighborhood(type, numberOfNeighbor);
		// initialize recommender
		recommender = new GenericUserBasedRecommender(dataModel, userNeightborhood, userSimilarity);
		return recommender.recommend(UserIDToRecommend, numberOfRecItems);
	}

	/**
	 * Recommendation using item-base method
	 * 
	 * @param sm
	 *            {@link SimilarityMeasure}
	 * @param UserIDToRecommend
	 *            {@link Integer}
	 * @param numberOfRecItems
	 *            {@link Integer}
	 * @return {@code List<RecommendedItem>: List recommended items for UserID}
	 * @throws TasteException
	 */
	public List<RecommendedItem> ItemBase(SimilarityMeasure sm, int UserIDToRecommend, int numberOfRecItems)
			throws TasteException {
		// initialize item's similarity
		InitItemSimilarityMeasure(sm);
		// initialize recommender
		recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
		return recommender.recommend(UserIDToRecommend, numberOfRecItems);
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
	private void InitUserNeighborhood(TypeOfNeighborhood type, int numberOfNeighbor) throws TasteException {
		switch (type) {
		case NEARESTNUSER:
			userNeightborhood = new NearestNUserNeighborhood(numberOfNeighbor, userSimilarity, dataModel);
			break;
		case THRESHOLDSUSER:
			userNeightborhood = new ThresholdUserNeighborhood(numberOfNeighbor, userSimilarity, dataModel);
			break;
		default:
			break;
		}
	}
}
