package app;

import java.nio.file.FileSystems;
import java.nio.file.Path;
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

public class App {

	public static void main(String[] args) {

		try {
			Path index_directory_path = FileSystems.getDefault().getPath("HybridIndex");

			Directory dir = FSDirectory.open(index_directory_path);

			IndexReader r = DirectoryReader.open(dir);

			IndexSearcher iSeach = new IndexSearcher(r);
			Term t = new Term("JobName", "Lập trình java");
			Query query = new TermQuery(t);

			int n = r.maxDoc();

			TopDocs rs = iSeach.search(query, n);
			for (int i = 0; i < rs.scoreDocs.length; i++) {
				int d = rs.scoreDocs[i].doc;
				Document doc = iSeach.doc(d);
				System.out.println(doc.get("JobName") + rs.scoreDocs[i].score);
				Thread.sleep(1000);
			}
			for (int i = 0; i < n; i++) {
				Document d = r.document(i);
				System.out.println(d.get("JobName"));

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
