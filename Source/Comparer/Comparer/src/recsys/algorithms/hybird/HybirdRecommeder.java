package recsys.algorithms.hybird;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;

import dto.CvDTO;
import dto.ScoreDTO;
import recsys.algorithms.RecommendationAlgorithm;
import recsys.datapreparer.DataSetReader;
import recsys.datapreparer.DataSetType;
import recsys.datapreparer.HybirdRecommenderDataPreparer;

public class HybirdRecommeder extends RecommendationAlgorithm {

	ArrayList<ScoreDTO> listScore = new ArrayList<ScoreDTO>();

	public HybirdRecommeder() {
		super();
		dataPreparation = null;
	}

	@Override
	public void init() {
		dataPreparation = new HybirdRecommenderDataPreparer(inputDirectory);
		
		super.init();
	}

	HybirdRecommenderDataPreparer dataPreparation;

	public void contentBasedFiltering() {
		if (dataPreparation == null) {
			System.out.println("Please run init method first!");
			return;
		}
		
		try {
			System.out.println("Create data index");
			dataPreparation.createDataIndex();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Create data index fail");
			return;
		}
		try {			
			// create index reader
			System.out.println("Read index data");
			File index_data = new File(inputDirectory + "INDEX_DATA");
			Directory dir = FSDirectory.open(index_data);
			IndexReader r = DirectoryReader.open(dir);
			IndexSearcher iSeach = new IndexSearcher(r);
			iSeach.setSimilarity(new DefaultSimilarity());
			System.out.println("Create query builder");
			// TF-IDF calculate
			QueryBuilder queryBuilder = new QueryBuilder(new StandardAnalyzer(Version.LUCENE_45));
			System.out.println("Start recommending");

			DataSetReader reader = new DataSetReader(inputDirectory);
			reader.open(DataSetType.Cv);
			ScoreDTO score_dto = null;
			// load all score
			while ((score_dto = reader.nextScore()) != null) {
				listScore.add(score_dto);
			}
			reader.close();
			reader.open(DataSetType.Cv);
			CvDTO cv = new CvDTO();

			int numdoc = r.numDocs();

			while ((cv = reader.nextCv()) != null) {
				/////////////////////////////
				System.out.println("Start recommeding for user " + cv.getAccountId());
				float min = Float.MAX_VALUE;
				float max = Float.MIN_VALUE;
				float[] tf_idf = new float[numdoc];
				for (int i = 0; i < numdoc; i++) {
					boolean flag = true;
					for (ScoreDTO sc : listScore) {
						if (sc.getUserId() == cv.getAccountId()
								&& (iSeach.doc(i).get("JobId").trim() == (sc.getJobId() + ""))) {
							flag = false;
							break;
						}
					}
					if(!flag) continue;
					System.out.println("Calculate match score for user " + cv.getAccountId() + " and doc " + i);
					float score = 0.0f;
					try {
						Query query = queryBuilder.createBooleanQuery("JobName", cv.getCvName());
						Explanation explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Location", cv.getAddress());
						explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Salary", cv.getExpectedSalary());
						explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Category", cv.getCategory());
						explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Requirement",
								cv.getSkill() + " " + cv.getEducation() + "" + cv.getLanguage());
						explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Description",
								cv.getSkill() + " " + cv.getEducation() + "" + cv.getLanguage());
						explain = iSeach.explain(query, i);
						score += explain.getValue();

						query = queryBuilder.createBooleanQuery("Tags", cv.getSkill() + "" + cv.getObjective());
						explain = iSeach.explain(query, i);
						score += explain.getValue();
												

						score /= 7.0f;
						System.out.println("score for user " + cv.getAccountId() + " and doc " + i + " is " + score);
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
				FileWriter fwr = new FileWriter(new File(outputDirectory + "CB_REC.txt"), true);
				BufferedWriter wr = new BufferedWriter(fwr);
				System.out.println("start writing data");
				try {
					float based = max - min;
					if (based == 0.0f)
						continue;
					for (int i = 0; i < numdoc; i++) {
						// normalize to 0 - 1
						tf_idf[i] = ((tf_idf[i] - min)) / based;
						// normalize to 1 - 5
						tf_idf[i] = (tf_idf[i] * 4) + 1;
						wr.write(cv.getAccountId() + "," + iSeach.doc(i).get("JobId") + "," + tf_idf[i]);
						System.out.println(
								"Result: " + cv.getAccountId() + "," + iSeach.doc(i).get("JobId") + "," + tf_idf[i]);
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
