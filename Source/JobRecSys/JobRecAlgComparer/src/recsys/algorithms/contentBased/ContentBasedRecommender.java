package recsys.algorithms.contentBased;

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
import dto.JobDTO;
import recsys.algorithms.RecommendationAlgorithm;
import recsys.datapreparer.DataSetReader;
import recsys.datapreparer.DataSetType;
import recsys.datapreparer.ContentBasedDataPreparer;

public class ContentBasedRecommender extends RecommendationAlgorithm {
<<<<<<< HEAD

	
=======
	ArrayList<ScoreDTO> listScore = new ArrayList<ScoreDTO>();
	ContentBasedDataPreparer dataPreparation;
>>>>>>> af43c8104adab7c144664672949e61ae40517120
	
	public ContentBasedRecommender()
	{
		super();
		dataPreparation = null;
	}
	
	@Override
	public void init() {
		dataPreparation = new ContentBasedDataPreparer(inputDirectory);		
		super.init(); //no code
	}

	public void contentBasedFiltering() {
		if (dataPreparation == null) {
			System.out.println("Please run init method first!");
			return;
		}
		
//		try {
//			System.out.println("Create data index");
//			dataPreparation.createDataIndex();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			System.out.println("Create data index fail");
//			return;
//		}
		try
		{
			System.out.println("Create data index");
			DataSetReader reader = new DataSetReader(inputDirectory);

			//create index for each user
			reader.open(DataSetType.Cv);
			CvDTO cv = new CvDTO();
			while ((cv = reader.nextCv()) != null) {
				dataPreparation.createDataIndex(cv.getAccountId());
			}

		} catch (IOException e)
		{}
		
		
		try {			
			DataSetReader reader = new DataSetReader(inputDirectory);
			reader.open(DataSetType.Score);
			ScoreDTO score_dto = null;
			// load all score
			while ((score_dto = reader.nextScore()) != null) {
				listScore.add(score_dto);
			}
			reader.close();
			
			System.out.println("Create query builder");
			QueryBuilder queryBuilder = new QueryBuilder(new StandardAnalyzer(Version.LUCENE_45));

			
			DataSetReader readerCV = new DataSetReader(inputDirectory);
			readerCV.open(DataSetType.Cv);
			CvDTO cv = new CvDTO();
			//iterate through all users
			while ((cv = readerCV.nextCv()) != null) {
				DataSetReader readerJob = new DataSetReader(inputDirectory);
				readerJob.open(DataSetType.Job);
				JobDTO job = null;
				
				// create index reader for this user
				System.out.println("Read index data");
				File index_data = new File(inputDirectory + "INDEX_DATA" + cv.getAccountId());
				Directory dir = FSDirectory.open(index_data);
				IndexReader r = DirectoryReader.open(dir);
				IndexSearcher indexSearcher = new IndexSearcher(r);
				indexSearcher.setSimilarity(new DefaultSimilarity());
				int numdoc = r.numDocs(); //amount of jobs that the current user had rated over 3*
				int numJobDb = 0;
				//iterate through all jobs in db
				ArrayList<Integer> similarityJobId = new ArrayList<Integer>();
				ArrayList<Float> similarityValue = new ArrayList<Float>(); 
				while ((job = readerJob.nextJob()) != null)
				{
					numJobDb++;
					float simJobAndIndex = 0.0f;
					for (int i = 0; i < numdoc; i++) {
//						boolean flag = true;
//						if (job.getJobId() ==  )
//						{
//							flag = false;
//						}
//						if(!flag) continue;
						System.out.println("Calculate match score for user " + cv.getAccountId() + " and doc " + i);
						float score = 0.0f;
						try {
							Query query = queryBuilder.createBooleanQuery("JobName", job.getJobName());
							Explanation explain = indexSearcher.explain(query, i);
							score += explain.getValue();

							query = queryBuilder.createBooleanQuery("Location", job.getLocation());
							explain = indexSearcher.explain(query, i);
							score += explain.getValue();

							query = queryBuilder.createBooleanQuery("Salary", job.getSalary());
							explain = indexSearcher.explain(query, i);
							score += explain.getValue();

							query = queryBuilder.createBooleanQuery("Category", job.getCategory());
							explain = indexSearcher.explain(query, i);
							score += explain.getValue();

							query = queryBuilder.createBooleanQuery("Requirement", job.getRequirement());
							explain = indexSearcher.explain(query, i);
							score += explain.getValue();

							query = queryBuilder.createBooleanQuery("Description", job.getDescription());
							explain = indexSearcher.explain(query, i);
							score += explain.getValue();

							query = queryBuilder.createBooleanQuery("Tags", job.getTags());
							explain = indexSearcher.explain(query, i);
							score += explain.getValue();													

							score /= 7.0f;
							

							simJobAndIndex += score;
						} catch (Exception ex) {
							System.out.println(ex.getMessage());
							score = 0;
						}
					}//jobs in index
					simJobAndIndex /= numdoc; //average sim between 1 job in db versus all jobs in index
					similarityJobId.add(job.getJobId());
					similarityValue.add(simJobAndIndex);
				}//jobs in db
				
				
				//////////				

				FileWriter fwr = new FileWriter(new File(outputDirectory + "CB_REC_ITEMS.txt"), true);
				BufferedWriter wr = new BufferedWriter(fwr);
				System.out.println("start writing data");
				try {					
						for (int i = 0; i < similarityJobId.size(); i++)
						{
							wr.write(cv.getAccountId() + "\t" + similarityJobId.get(i) + "\t" + similarityValue.get(i));
							System.out.println(
									"Result: " + cv.getAccountId() + "," + similarityJobId.get(i) + "," + similarityValue.get(i));
							wr.newLine();
						}					
				} catch (Exception exx) {

				}
				wr.close();

				//next user
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
