package recsys.datapreparer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import recsys.algorithms.UserProfile;

import org.apache.lucene.search.similarities.DefaultSimilarity;

public class HybirdRecommenderDataPreparer extends DataPreparer {

	Path index_directory_path;

	/**
	 * kiểm tra su ton tai cua thu muc index
	 * 
	 * @return
	 */
	public boolean hasIndexDirectory(Path path) {
		if (Files.exists(path)) {
			return true;
		}
		return false;
	}

	public boolean isEmptyIndexDirectory() {
		File f1 = new File("HybridIndex");
		return f1.list().length == 0;
	}

	public String getEducation(String resumeId) {
		String education = "education: ";
		if (this.connection.connect()) {
			ResultSet rs = this.connection.read("select * from education where ResumeId = " + resumeId);
			try {
				while (rs.next()) {
					education += rs.getString("EducationLevel") + " ";
					education += rs.getString("EducationMajor") + " ";
					education += rs.getString("EducationDescription") + " ";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.connection.close();
		}
		return education;
	}

	public ArrayList<String> getUserRating(String accountId) {
		ArrayList<String> listJobs = new ArrayList<String>();

		if (this.connection.connect()) {
			ResultSet rs = this.connection
					.read("select JobId from job_recommended where Rating > 0 and AccountId = " + accountId);
			try {
				while (rs.next()) {
					listJobs.add(rs.getString("JobId"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.connection.close();
		}
		return listJobs;
	}

	public String getLanguage(String resumeId) {
		String languages = "language: ";
		if (this.connection.connect()) {
			ResultSet rs = this.connection.read("select * from language where ResumeId = " + resumeId);
			try {
				while (rs.next()) {
					languages += rs.getString("Name") + " ";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.connection.close();
		}
		return languages;
	}

	public String getSkill(String resumeId) {
		String skill = "skill:";
		if (this.connection.connect()) {
			ResultSet rs = this.connection.read("select * from skill where ResumeId = " + resumeId);
			try {
				while (rs.next()) {
					skill += rs.getString("Name") + " ";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.connection.close();
		}
		return skill;
	}

	public List<UserProfile> buildUserProfile() throws SQLException {
		List<UserProfile> usersprofile = new ArrayList<UserProfile>();
		UserProfile dto;

		String sql = "call get_user_profile()";

		if (this.connection.connect()) {
			ResultSet rs = this.connection.read(sql);

			while (rs.next()) {
				dto = new UserProfile();
				dto.accountId = rs.getString("AccountId");
				dto.CvName = rs.getString("Title");
				dto.gender = rs.getString("Gender");
				dto.location = rs.getString("Address");
				dto.target = rs.getString("Objective");
				dto.salary = rs.getString("Salary");
				dto.location = rs.getString("Location");
				dto.catetory = rs.getString("Description");
				dto.field = rs.getString("Field");
				String resumeId = rs.getString("ResumeId");
				dto.skill = getSkill(resumeId);
				dto.languages = getLanguage(resumeId);
				dto.education = getEducation(resumeId);
				dto.userRatingList = getUserRating(dto.accountId);
				usersprofile.add(dto);
			}
			this.connection.close();
		}
		return usersprofile;
	}

	public void Init() {

		index_directory_path = FileSystems.getDefault().getPath("HybridIndex");
		try {

			File dir = new File(index_directory_path.toFile().getAbsolutePath());
			for (File f : dir.listFiles()) {
				f.delete();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!this.hasIndexDirectory(index_directory_path)) {
			File dir = new File("HybridIndex");
			dir.mkdir();
		}

	}

	public int IndexData() throws Exception {
		int document_count = 0;
		// Initialize the running environment.
		this.Init();

		Directory dir = FSDirectory.open(index_directory_path.toFile());
		IndexWriterConfig iwr_config = new IndexWriterConfig(Version.LUCENE_45,
				new StandardAnalyzer(Version.LUCENE_45));
		iwr_config.setOpenMode(OpenMode.CREATE);
		iwr_config.setSimilarity(new DefaultSimilarity());
		// check connection to database

		if (this.connection.connect()) {
			IndexWriter index_wr = new IndexWriter(dir, iwr_config);
			ResultSet rs = this.connection.read(
					"SELECT JobId,JobName,Location,Salary,job.Description,Tags, Requirement,Benifit, category.Description as Category FROM job, category WHERE job.CategoryId = category.CategoryId and job.JobId not in(select JobId from job_recommended where Rating > 0)");
			int count = 0;
			while (rs.next()) {
				count++;
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
				data = rs.getString("JobId");
				Field field = new Field("JobId", data, type);
				doc.add(field);
				data = rs.getString("JobName");
				field = new Field("JobName", data, type);
				doc.add(field);
				data = rs.getString("Location");
				field = new Field("Location", data, type);
				doc.add(field);
				data = rs.getString("Salary");
				field = new Field("Salary", data, type);
				doc.add(field);
				data = rs.getString("Description");
				field = new Field("Description", data, type);
				doc.add(field);
				data = rs.getString("Requirement");
				field = new Field("Requirement", data, type);
				doc.add(field);
				data = rs.getString("Benifit");
				field = new Field("Benifit", data, type);
				doc.add(field);
				data = rs.getString("Tags");
				field = new Field("Tags", data, type);
				doc.add(field);
				data = rs.getString("Category");
				field = new Field("Category", data, type);
				doc.add(field);
				index_wr.addDocument(doc);
			}
			document_count = index_wr.numDocs();
			index_wr.close();
			this.connection.close();
		} else {
			throw new Exception("khong ket noi duoc database");
		}
		return document_count;
	}

}
