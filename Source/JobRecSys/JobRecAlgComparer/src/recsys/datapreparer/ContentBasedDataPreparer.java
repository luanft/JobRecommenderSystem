package recsys.datapreparer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import dto.CvDTO;
import dto.JobDTO;
import dto.ScoreDTO;

public class ContentBasedDataPreparer extends DataPreparer {
	ArrayList<ScoreDTO> listScore = new ArrayList<ScoreDTO>();

	public ContentBasedDataPreparer(String dir) {
		super(dir);
	}

	public ArrayList<ScoreDTO> getListScore() {
		return listScore;
	}

	public void init() {
		System.out.println("Preparing to create index data!");
		String dir = this.dataReader.getSource();
		File file = new File(dir + "INDEX_DATA");
		if (file.exists()) {
			file.delete();
		}
		file.mkdir();

		this.dataReader.open(DataSetType.Score); // <~ check data type
		ScoreDTO score_dto = null;
		while ((score_dto = dataReader.nextScore()) != null) {
			listScore.add(score_dto);
		}
		dataReader.close();

		System.out.println("Done!");
	}

	public void createDataIndex(int accountId) throws IOException {
		init();
		System.out.println("Starting index data!");
		this.dataReader.open(DataSetType.Job);
		JobDTO job = null;
		String str_dir = this.dataReader.getSource();
		File file = new File(str_dir + "INDEX_DATA" + accountId);
		Directory dir = FSDirectory.open(file);

		@SuppressWarnings("deprecation")
		IndexWriterConfig iwr_config = new IndexWriterConfig(Version.LUCENE_45,
				new StandardAnalyzer(Version.LUCENE_45));
		iwr_config.setOpenMode(OpenMode.CREATE);
		iwr_config.setSimilarity(new DefaultSimilarity());
		IndexWriter index_wr = new IndexWriter(dir, iwr_config);

		// add rated jobs to this user's index
		while ((job = dataReader.nextJob()) != null) {
			for (ScoreDTO sc : listScore) {
				if (sc.getUserId() == accountId && sc.getJobId() == job.getJobId() && sc.getScore() >= 3) {
					String data = "";
					FieldType type = new FieldType();
					type.setStoreTermVectors(true);
					type.setStored(true);
					type.setTokenized(true);
					type.setOmitNorms(false);
					type.setStoreTermVectorPositions(true);
					type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
					type.setIndexed(true);
					Document doc = new Document();
					data = job.getJobId() + "";
					Field field = new Field("JobId", data, type);
					doc.add(field);
					data = job.getJobName();
					System.out.println("Index job name " + job.getJobName());
					field = new Field("JobName", data, type);
					doc.add(field);
					data = job.getLocation();
					field = new Field("Location", data, type);
					doc.add(field);
					data = job.getSalary();
					field = new Field("Salary", data, type);
					doc.add(field);
					data = job.getDescription();
					field = new Field("Description", data, type);
					doc.add(field);
					data = job.getRequirement();
					field = new Field("Requirement", data, type);
					doc.add(field);
					data = job.getTags();
					field = new Field("Tags", data, type);
					doc.add(field);
					data = job.getCategory();
					field = new Field("Category", data, type);
					doc.add(field);
					index_wr.addDocument(doc);
				}
			}
		}
		index_wr.close();

		this.dataReader.close();
	}

	public void splitDataSet(float proportionOfTest) {

		dataReader.open(DataSetType.Job);
		List<JobDTO> fullSet = getAllJobs();
		List<JobDTO> testingSet = new ArrayList<JobDTO>();
		List<JobDTO> trainingSet = new ArrayList<JobDTO>();
		int fullSize = fullSet.size();
		int testingSize = (int) (proportionOfTest * fullSize);

		for (int i = 0; i < testingSize; i++) {
			JobDTO dto = getAnyJob(fullSize, fullSet);
			while (testingSet.contains(dto)) {
				dto = getAnyJob(fullSize, fullSet);
			}
			testingSet.add(dto);
		}
		if (fullSet.removeAll(testingSet)) {
			trainingSet = fullSet;
		}
		writeJob("C:\\Users\\tuyen\\Desktop\\garbage\\nc\\", "CF_TRAINING_ITEMS.txt", trainingSet);
		writeJob("C:\\Users\\tuyen\\Desktop\\garbage\\nc\\", "CF_TESTING_ITEMS.txt", testingSet);
	}
	
//	public void splitCv(float proportionOfTest) {
//
//		dataReader.open(DataSetType.Cv);
//		List<CvDTO> fullSet = getAllCVs();
//		List<CvDTO> testingSet = new ArrayList<CvDTO>();
//		List<CvDTO> trainingSet = new ArrayList<CvDTO>();
//		int fullSize = fullSet.size();
//		int testingSize = (int) (proportionOfTest * fullSize);
//
//		for (int i = 0; i < testingSize; i++) {
//			CvDTO dto = getAnyCv(fullSize, fullSet);
//			while (testingSet.contains(dto)) {
//				dto = getAnyCv(fullSize, fullSet);
//			}
//			testingSet.add(dto);
//		}
//		if (fullSet.removeAll(testingSet)) {
//			trainingSet = fullSet;
//		}
//		writeCv("C:\\Users\\tuyen\\Desktop\\garbage\\nc\\", "CF_TRAINING_ITEMS.txt", trainingSet);
//		writeCv("C:\\Users\\tuyen\\Desktop\\garbage\\nc\\", "CF_TESTING_ITEMS.txt", testingSet);
//	}

	private JobDTO getAnyJob(int maxRange, List<JobDTO> fullSet) {
		int index = new Random().nextInt(maxRange);
		return fullSet.get(index);
	}

	private void writeJob(String destination, String fileName, List<JobDTO> dataSet) {
		FileWriter fwr;
		try {
			fwr = new FileWriter(new File(destination + fileName), true);
			BufferedWriter wr = new BufferedWriter(fwr);

			for (JobDTO dto : dataSet) {
				wr.write(dto.getJobId() + "\t" + dto.getJobName() + "\t" + dto.getLocation() + "\t" + dto.getSalary()
						+ "\t" + dto.getCategory() + "\t" + dto.getRequirement() + "\t" + dto.getTags() + "\t"
						+ dto.getDescription());
				wr.newLine();
			}

			wr.close();
			fwr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	private CvDTO getAnyCv(int maxRange, List<CvDTO> fullSet) {
//		int index = new Random().nextInt(maxRange);
//		return fullSet.get(index);
//	}

//	private void writeCv(String destination, String fileName, List<CvDTO> dataSet) {
//		FileWriter fwr;
//		try {
//			fwr = new FileWriter(new File(destination + fileName), true);
//			BufferedWriter wr = new BufferedWriter(fwr);
//
//			for (CvDTO dto : dataSet) {
//				wr.write(dto.getAccountId() + "\t" + dto.getResumeId() + "\t" + dto.getCvName() + "\t" + dto.get
//						+ "\t" + dto.getCategory() + "\t" + dto.getRequirement() + "\t" + dto.getTags() + "\t"
//						+ dto.getDescription());
//				wr.newLine();
//			}
//
//			wr.close();
//			fwr.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
