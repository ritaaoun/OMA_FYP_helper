import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class ExtractNgramsMain {
	public static void main(String[] args) {
		extractNgramsFromTrainSet();
	}
	public static void extractNgramsFromTrainSet() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("resources\\train tweets lemmatized.txt"));
			PrintWriter uni = new PrintWriter("resources\\unigrams.txt", "UTF-8");
			PrintWriter bi = new PrintWriter("resources\\bigrams.txt", "UTF-8");
			PrintWriter tri = new PrintWriter("resources\\trigrams.txt", "UTF-8");
			
			extractNgramsFromFile(br,uni,bi,tri);
			
			br.close();
			uni.close();
			bi.close();
			tri.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void extractNgramsFromFolds() {
		try {
			for (int fold=1; fold<=10; ++fold) {
				BufferedReader br = new BufferedReader(new FileReader("ngrams\\fold"+fold+"\\train.txt"));
				PrintWriter uni = new PrintWriter("ngrams\\fold"+fold+"\\unigrams.txt", "UTF-8");
				PrintWriter bi = new PrintWriter("ngrams\\fold"+fold+"\\bigrams.txt", "UTF-8");
				PrintWriter tri = new PrintWriter("ngrams\\fold"+fold+"\\trigrams.txt", "UTF-8");

				extractNgramsFromFile(br,uni,bi,tri);
				
				br.close();
				uni.close();
				bi.close();
				tri.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void extractNgramsFromFile(BufferedReader input, PrintWriter uni, PrintWriter bi, PrintWriter tri) {
		try {
			HashMap<String, Integer> unigrams = new HashMap<String, Integer>();
			HashMap<String, Integer> bigrams = new HashMap<String, Integer>();
			HashMap<String, Integer> trigrams = new HashMap<String, Integer>();
			String line = "";
			while ((line = input.readLine()) != null && line.length()!=0) {
				for (String ngram : NGramExtractor.ngrams(1, line)) {
					if (unigrams.containsKey(ngram)) {
						unigrams.put(ngram, unigrams.get(ngram)+1);
					}
					else {
						unigrams.put(ngram,1);
					}
				}
				for (String ngram : NGramExtractor.ngrams(2, line)) {
					if (bigrams.containsKey(ngram)) {
						bigrams.put(ngram, bigrams.get(ngram)+1);
					}
					else {
						bigrams.put(ngram,1);
					}
				}
				for (String ngram : NGramExtractor.ngrams(3, line)) {
					if (trigrams.containsKey(ngram)) {
						trigrams.put(ngram, trigrams.get(ngram)+1);
					}
					else {
						trigrams.put(ngram,1);
					}
				}
			}				
			
			for (String unigram : unigrams.keySet()) {
				if (unigrams.get(unigram)>=5) {
					uni.println(unigram);
				}
			}
			
			for (String bigram : bigrams.keySet()) {
				if (bigrams.get(bigram)>=5) {
					bi.println(bigram);
				}
			}
			
			for (String trigram : trigrams.keySet()) {
				if (trigrams.get(trigram)>=5) {
					tri.println(trigram);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
