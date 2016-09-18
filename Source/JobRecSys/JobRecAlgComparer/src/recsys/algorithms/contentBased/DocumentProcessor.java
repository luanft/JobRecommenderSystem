package recsys.algorithms.contentBased;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vn.hus.nlp.tokenizer.VietTokenizer;

public class DocumentProcessor {

	// This variable will hold all terms of each document in an array(Item).
	private List<String[]> termsDocsArrayItems = new ArrayList<String[]>();
	private List<String> termsDocsArrayItemKeys = new ArrayList<String>();
	// to hold all tf-idf termVector for item
	private List<double[]> tfidfDocsVectorItems = new ArrayList<double[]>();
	
	
	private List<String[]> termsDocsArrayProfiles = new ArrayList<String[]>();
	private List<String> termsDocsArrayProfileKeys = new ArrayList<String>();
	// to hold all tf-idf termVector for profile
	private List<double[]> tfidfDocsVectorProfiles = new ArrayList<double[]>();
	// to hold all terms
	private List<String> allTerms = new ArrayList<String>();


	/**
	 * Tách từ từ chuổi input
	 * 
	 * @param input
	 * @return
	 */
	private String[] extractWords(String input) {
		VietTokenizer vietTokenizer = new VietTokenizer();
		input = input.replace('.', ' ');
		input = input.replace(']', ' ');
		input = input.replace('[', ' ');
		input = input.replace(')', ' ');
		input = input.replace('(', ' ');
		input = input.replace(',', ' ');
		input = input.replace(';', ' ');
		input = input.replace('"', ' ');
		input = input.replace(':', ' ');
		input = input.replace('+', ' ');
		input = input.replace('-', ' ');
		input = input.replace('|', ' ');
		input = input.replace('!', ' ');
		input = input.replace('%', ' ');
		input = input.replace('*', ' ');
		input = input.replace('/', ' ');
		input = input.replace('\'', ' ');
		input = input.replace('?', ' ');
		input = input.replace('–', ' ');
		input = input.replace('$', ' ');

		String[] copus = vietTokenizer.tokenize(input);
		StringBuffer buf = new StringBuffer();
		StripAccent strip_accent = new StripAccent();
		for (int i = 0; i < copus.length; i++) {
			copus[i] = strip_accent.Convert(copus[i].trim().toLowerCase()).replace(',', ' ').trim();
			buf.append(copus[i] + " ");
		}
		return buf.toString().split(" ");
	}

	/**
	 * Đọc dữ liệu từ file. sau đó tiến hành tính toán term vector
	 * 
	 * @param filePath
	 *            : source file path
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void addItem(String fileName) throws FileNotFoundException, IOException {
		InputStream ins = null;
		Reader r = null;
		BufferedReader in = null;
		// lay duong dan cua file
		ins = new FileInputStream(fileName);
		r = new InputStreamReader(ins, "UTF-8");
		in = new BufferedReader(r);
		// get noi dung file		
		String s = null;
		while ((s = in.readLine()) != null) {
			Scanner scan = new Scanner(s);
			scan.useDelimiter("\t");	
			//tao document
			String[] copus = extractWords(s.toString());
			for (int i = 0; i < copus.length; i++) {
				if (!allTerms.contains(copus[i])) { // avoid duplicate entry
					if (copus[i].trim().length() > 0) {
						allTerms.add(copus[i]);
					}
				}
			}
			termsDocsArrayItems.add(copus);
		}		
		in.close();
	}

	/**
	 * Tính toán termvector theo TF-IDF Score.
	 */
	public void tfIdfCalculator() {
		double tf; // term frequency
		double idf; // inverse document frequency
		double tfidf; // term requency inverse document frequency
		for (String[] docTermsArray : termsDocsArrayItems) {
			double[] tfidfvectors = new double[allTerms.size()];
			int count = 0;
			for (String terms : allTerms) {
				tf = new TfIdf().tfCalculator(docTermsArray, terms);
				idf = new TfIdf().idfCalculator(termsDocsArrayItems, terms);
				tfidf = tf * idf;
				tfidfvectors[count] = tfidf;
				count++;
			}
			tfidfDocsVectorItems.add(tfidfvectors); // storing document vectors;
		}
	}

	/**
	 * Có thể là hàm recommend.
	 */
	public void getCosineSimilarity() {
		for (int i = 0; i < tfidfDocsVectorItems.size(); i++) {
			for (int j = 0; j < tfidfDocsVectorItems.size(); j++) {
				System.out.println("between " + i + " and " + j + "  =  " + new CosineSimilarity()
						.cosineSimilarity(tfidfDocsVectorItems.get(i), tfidfDocsVectorItems.get(j)));
			}
		}
	}

}