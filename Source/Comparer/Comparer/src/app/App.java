package app;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import recsys.datapreparer.HybirdRecommenderDataPreparer;

public class App {

	public static void main(String[] args) {

		try
		{
			Path index_directory_path = FileSystems.getDefault().getPath("HybridIndex");

			Directory dir = FSDirectory.open(index_directory_path);

			DirectoryReader r = DirectoryReader.open(dir);

			IndexSearcher iSeach = new IndexSearcher(r);
			iSeach.setSimilarity(new DefaultSimilarity());
			Term term = new Term("JobName", "Software");
			
			Analyzer analyzer = new StandardAnalyzer();
			QueryParser queryParser = new QueryParser("JobName", analyzer);

			queryParser.setDefaultOperator(QueryParser.Operator.AND);

			Query query = queryParser.parse("lập trình java");
			//DFISimilarity simi = new DFISimilarity(null);
			//Query query = new TermQuery(term);
			//MultiPhraseQuery multi = new MultiPhraseQuery(null, null, null, 0);
			
			int n = r.maxDoc();
			TopDocs rs = iSeach.search(query, 1);
			
			
			for(int i = 0 ; i < n; i++)
			{
				float job_name = 0.0f;
				float requirement = 0.0f;
				float location = 0.0f;
				float salary = 0;
				
				
				
				Explanation ex = iSeach.explain(query, i);
				if(ex.getValue() > 0)
				{
						
				}
				else
				{
					
				}
			}		
		} catch (Exception ex) {
			ex.printStackTrace();
		}

//		 HybirdRecommenderDataPreparer tesst = new
//		 HybirdRecommenderDataPreparer();
//		 try {
//		 tesst.IndexData();
//		 System.out.println("Done!");
//		 } catch (Exception ex) {
//		 ex.printStackTrace();
//		 }

		// IndexSearcher iSearch = new IndexSearcher(r)
	}

}
