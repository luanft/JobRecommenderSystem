package recsys.algorithms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;

import com.mysql.jdbc.Buffer;

import recsys.datapreparer.HybirdRecommenderDataPreparer;

public class HybirdRecommeder extends RecommendationAlgorithm {

	public List<UserProfile> users = new ArrayList();
	HybirdRecommenderDataPreparer dataPreparation = new HybirdRecommenderDataPreparer();

	public void prepareUserProfile() {
		try {
			users = dataPreparation.buildUserProfile();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	}

	public void contentBasedFiltering() {
		try {
			// index data
			int numdoc = dataPreparation.IndexData();
			System.out.println("Indexed " + numdoc + " documents");
			// prepare user's profile
			prepareUserProfile();
			System.out.println("Load all user profile");
			// create index reader
			System.out.println("Read index data");
			Path index_directory_path = FileSystems.getDefault().getPath("HybridIndex");
			Directory dir = FSDirectory.open((index_directory_path.toFile()));
			IndexReader r = DirectoryReader.open(dir);
			IndexSearcher iSeach = new IndexSearcher(r);
			iSeach.setSimilarity(new DefaultSimilarity());
			System.out.println("Create query builder");
			// TF-IDF calculate
			QueryBuilder queryBuilder = new QueryBuilder(new StandardAnalyzer(Version.LUCENE_45));
			System.out.println("Start recommending");
			for (UserProfile user : users) {
				System.out.println("Start recommeding for user " + user.accountId);
				float min = Float.MAX_VALUE;
				float max = Float.MIN_VALUE;
				float[] tf_idf = new float[numdoc];
				for (int i = 0; i < numdoc; i++) {
					if(user.userRatingList.contains(iSeach.doc(i).get("JobId").trim()))
					{
						continue;
					}
					System.out.println("Calculate match score for user " + user.accountId + " and doc " + i);
					float score = 0.0f;
					try {
						Query query = queryBuilder.createBooleanQuery("JobName", user.CvName);
						Explanation explain = iSeach.explain(query, i);
						score += explain.getValue();
						
						
						query = queryBuilder.createBooleanQuery("Location", user.location);
						explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Salary", user.salary);
						explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Category", user.field);
						explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Requirement",
								user.skill + " " + user.education + "" + user.languages);
						explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Description",
								user.skill + " " + user.education + "" + user.languages);
						explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Tags", user.skill + "" + user.languages);
						explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Benifit", user.target);
						explain = iSeach.explain(query, i);
						score += explain.getValue();
						
						score /= 8.0f;
						System.out.println("score for user " + user.accountId + " and doc " + i + " is " + score);
						if (score > max) {
							max = score;
						}
						if (score < min) {
							min = score;
						}
						tf_idf[i] = score;
					} catch (Exception ex) {							
						System.out.println(ex.getMessage());
						score = 0;
					}
				}
				FileWriter fwr = new FileWriter(new File("CB_REC.txt"), true);
				BufferedWriter wr = new BufferedWriter(fwr);
				System.out.println("start writing data");
				try {
					float based = max - min;
					if(based == 0.0f) continue;
					for (int i = 0; i < numdoc; i++) {
						//normalize to 0 - 1
						tf_idf[i] = ((tf_idf[i] - min)) / based;
						//normalize to 1 - 5						
						tf_idf[i] = (tf_idf[i] * 4) + 1; 
						wr.write(user.accountId + "," + iSeach.doc(i).get("JobId") + "," + tf_idf[i]);
						System.out.println(
								"Result: " + user.accountId + "," + iSeach.doc(i).get("JobId") + "," + tf_idf[i]);
						wr.newLine();
					}
				} catch (Exception exx) {

				}
				wr.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
