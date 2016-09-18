import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MRecommend {
	UserSimilarity userSim;
	NearestNUserNeighborhood nUserNeighbor;
	DataModel dm;
	
public void	init(String src){
		try {
			dm = new FileDataModel(new File(src));
			userSim = new PearsonCorrelationSimilarity(dm);
			nUserNeighbor = new NearestNUserNeighborhood(10, userSim, dm);
		} catch (IOException | TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void recommend(int user){
		Recommender rec = new GenericUserBasedRecommender(dm, nUserNeighbor, userSim);
		try {
			List<RecommendedItem> recs = rec.recommend(4, 10);
			System.out.println("So item: " + recs.size());
			for(RecommendedItem r : recs){
				System.out.println(r);
			}
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
