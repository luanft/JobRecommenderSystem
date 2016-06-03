package recsys.datapreparer;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;

import org.apache.lucene.index.FieldInfo.DocValuesType;

import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity ;
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
	

		
	
	public void Init()
	{
		index_directory_path = FileSystems.getDefault().getPath("HybridIndex");		
		if(!this.hasIndexDirectory(index_directory_path))
		{
			File dir = new File("HybridIndex");
			dir.mkdir();
		}
		
					
	}
	
	public void IndexData() throws Exception
	{
		//Initialize the running environment.
		this.Init();
		
		Directory dir = FSDirectory.open(index_directory_path.toFile());
		IndexWriterConfig iwr_config = new IndexWriterConfig(Version.LUCENE_45, new StandardAnalyzer(Version.LUCENE_45));
		iwr_config.setOpenMode(OpenMode.CREATE);
		iwr_config.setSimilarity(new DefaultSimilarity());
		//check connection to database

		if(this.connection.connect())
		{
			IndexWriter index_wr = new IndexWriter(dir, iwr_config);
			ResultSet rs = this.connection.read("SELECT JobName,Location,Salary,job.Description,Tags, Requirement,Benifit, category.Description as Category FROM job, category WHERE job.CategoryId = category.CategoryId");
			int count = 0;
			while(rs.next())
			{	
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
				data = rs.getString("JobName");
				Field field = new Field("JobName",data , type);				
				doc.add(field);
				data = rs.getString("Location");
				field = new Field("Location",data , type);
				doc.add(field);
				data = rs.getString("Salary");
				field = new Field("Salary",data , type);
				doc.add(field);
				data = rs.getString("Description");
				field = new Field("Description",data , type);
				doc.add(field);
				data = rs.getString("Requirement");
				field = new Field("Requirement",data , type);
				doc.add(field);
				data = rs.getString("Benifit");
				field = new Field("Benifit",data , type);
				doc.add(field);
				data = rs.getString("Tags");
				field = new Field("Tags",data , type);
				doc.add(field);
				data = rs.getString("Category");
				field = new Field("Category",data , type);
				doc.add(field);				
				index_wr.addDocument(doc);
			}
			index_wr.close();
			this.connection.close();			
		}
		else
		{
			throw new Exception("khong ket noi duoc database");
		}			
	}
	
	
	
}
