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
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

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
	
	MysqlConnection _connection = new MysqlConnection();
	
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
		
		Directory dir = FSDirectory.open(index_directory_path);
		IndexWriterConfig iwr_config = new IndexWriterConfig(new StandardAnalyzer());
		iwr_config.setOpenMode(OpenMode.CREATE);


		//check connection to database
		if(_connection.connect())
		{
			IndexWriter index_wr = new IndexWriter(dir, iwr_config);
			ResultSet rs = _connection.read("SELECT JobName,Location,Salary,job.Description,Tags, Requirement,Benifit, category.Description as Category FROM job, category WHERE job.CategoryId = category.CategoryId");
			int count = 0;
			while(rs.next())
			{	
				count++;
				String data = "";
				FieldType type = new FieldType();
				type.setStoreTermVectors(true);			
				type.setStored(true);
				type.setTokenized(false);
				type.setOmitNorms(false);
				type.setIndexOptions(IndexOptions.DOCS_AND_FREQS);				
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
			this._connection.close();			
		}
		else
		{
			throw new Exception("khong ket noi duoc database");
		}			
	}
	
	
	
}
