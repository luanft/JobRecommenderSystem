package app;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import recsys.datapreparer.HybirdRecommenderDataPreparer;

public class App {

	public static void main(String[] args) throws IOException {

		Path index_directory_path = FileSystems.getDefault().getPath("HybridIndex");
		
		Directory dir = FSDirectory.open(index_directory_path);
		
		DirectoryReader r = DirectoryReader.open(dir);
		
		
		
		IndexSearcher iSeach = new IndexSearcher(r);
			    
		int n = r.maxDoc();
		for(int i = 0; i < n; i++)
		{
			Document d = r.document(i);
			System.out.println(d.get("JobName"));
			
		}
	    	    
	    
		
//		HybirdRecommenderDataPreparer tesst = new HybirdRecommenderDataPreparer();
//		try {
//			tesst.IndexData();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
		
		
		
		//IndexSearcher iSearch = new IndexSearcher(r)
	}

}
