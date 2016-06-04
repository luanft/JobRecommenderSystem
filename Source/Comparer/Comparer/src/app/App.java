package app;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.SQLException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;

import recsys.datapreparer.HybirdRecommenderDataPreparer;

public class App {

	public static void index() {
		 HybirdRecommenderDataPreparer tesst = new
		 HybirdRecommenderDataPreparer();
		 try {
		 tesst.IndexData();
		 System.out.println("Done!");
		 } catch (Exception ex) {
		 ex.printStackTrace();
		 }
	}
	
	public static void test() {

		try {
			Path index_directory_path = FileSystems.getDefault().getPath("HybridIndex");
			Directory dir = FSDirectory.open((index_directory_path.toFile()));
			IndexReader r = DirectoryReader.open(dir);
			IndexSearcher iSeach = new IndexSearcher(r);			
			int n = r.maxDoc();
			
			PhraseQuery parser = new PhraseQuery();
			QueryBuilder q = new QueryBuilder(new StandardAnalyzer(Version.LUCENE_45));
			//Query qwer = q.createPhraseQuery("JobName", "Việc làm java");
			Query qwer = q.createBooleanQuery("JobName", "Việc làm java");
			
			for (int i = 0; i < n; i++) {
			
				Explanation explain = iSeach.explain(qwer, i);
				if(explain.getValue() > 0)
					System.out.println(iSeach.doc(i).get("JobName")+ "--------" + explain.getValue() +"--------" + explain.getDescription());
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {


		HybirdRecommenderDataPreparer tt = new HybirdRecommenderDataPreparer();
		try {
			tt.buildUserProfile();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//index();
		//test();


	}

}
