package recsys.datapreparer;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import recsys.algorithms.cf.CollaborativeFiltering;
import recsys.algorithms.cf.SimilarityMeasure;
import recsys.algorithms.cf.TypeOfNeighborhood;

public class DataPreparer {

	public static void main(String[] args) {

		try {
			CollaborativeFiltering cf = new CollaborativeFiltering();
			List<RecommendedItem> recommendedItems = cf.UserBase(SimilarityMeasure.LOGLIKELIHOOD_SIMILARITY,
					TypeOfNeighborhood.NEARESTNUSER, 5, 1, 10);
			for (RecommendedItem recommendedItem : recommendedItems) {
				System.out.println("item: " + recommendedItem.getItemID() + " - value: " + recommendedItem.getValue());
			}
//			System.out.println("------------------------");
//			List<RecommendedItem> recs = cf.ItemBase(SimilarityMeasure.LOGLIKELIHOOD_SIMILARITY, 1, 10);
//			for (RecommendedItem recommendedItem : recs) {
//				System.out.println("item: " + recommendedItem.getItemID() + " - value: " + recommendedItem.getValue());
//			}
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
