package app;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import recsys.datapreparer.HybirdRecommenderDataPreparer;

public class App {

	public static void main(String[] args) {

//		HybirdRecommenderDataPreparer tesst = new HybirdRecommenderDataPreparer();
//		try {
//			tesst.IndexData();
//			System.out.println("Done!");
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

}
