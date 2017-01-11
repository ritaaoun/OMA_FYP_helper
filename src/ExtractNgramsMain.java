import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class ExtractNgramsMain {
	public static void main(String[] args) {
		int threshold = 3;
		extractNgramsFromTrainSet(true, threshold);
		extractNgramsFromTrainSet(false, threshold);
	}
	public static void extractNgramsFromTrainSet(boolean lemmatized, int frequencyThreshold) {
		try {
			BufferedReader br;
			PrintWriter uni, bi, tri;
			if (lemmatized) {
				br = new BufferedReader(new FileReader("resources\\train tweets lemmatized.txt"));
				uni = new PrintWriter("resources\\unigrams.txt", "UTF-8");
				bi = new PrintWriter("resources\\bigrams.txt", "UTF-8");
				tri = new PrintWriter("resources\\trigrams.txt", "UTF-8");
			}
			else {
				br = new BufferedReader(new FileReader("resources\\train tweets.txt"));
				uni = new PrintWriter("resources\\unigrams_not_lemmatized.txt", "UTF-8");
				bi = new PrintWriter("resources\\bigrams_not_lemmatized.txt", "UTF-8");
				tri = new PrintWriter("resources\\trigrams_not_lemmatized.txt", "UTF-8");
			}
			extractNgramsFromFile(br,uni,bi,tri,frequencyThreshold);
			
			br.close();
			uni.close();
			bi.close();
			tri.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void extractNgramsFromFolds(int frequencyThreshold) {
		try {
			for (int fold=1; fold<=10; ++fold) {
				BufferedReader br = new BufferedReader(new FileReader("ngrams\\fold"+fold+"\\train.txt"));
				PrintWriter uni = new PrintWriter("ngrams\\fold"+fold+"\\unigrams.txt", "UTF-8");
				PrintWriter bi = new PrintWriter("ngrams\\fold"+fold+"\\bigrams.txt", "UTF-8");
				PrintWriter tri = new PrintWriter("ngrams\\fold"+fold+"\\trigrams.txt", "UTF-8");

				extractNgramsFromFile(br,uni,bi,tri,frequencyThreshold);
				
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
	
	private static void extractNgramsFromFile(BufferedReader input, PrintWriter uni, PrintWriter bi, PrintWriter tri, int frequencyThreshold) {
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
				if (unigrams.get(unigram)>=frequencyThreshold) {
					uni.println(unigram);
				}
			}
			
			for (String bigram : bigrams.keySet()) {
				if (bigrams.get(bigram)>=frequencyThreshold) {
					bi.println(bigram);
				}
			}
			
			for (String trigram : trigrams.keySet()) {
				if (trigrams.get(trigram)>=frequencyThreshold) {
					tri.println(trigram);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
