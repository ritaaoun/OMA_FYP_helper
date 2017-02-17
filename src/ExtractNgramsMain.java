import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class ExtractNgramsMain {
	public static void main(String[] args) {
		for (int threshold = 3; threshold<=5; ++threshold) {
			//extractNgramsFromTrainSet(true, threshold);
			extractNgramsFromTrainSet(false, threshold);
			//extractCharNgramsFromTrainSet(true, threshold);
		}
	}
	public static void extractNgramsFromTrainSet(boolean lemmatized, int frequencyThreshold) {
		try {
			BufferedReader br;
			PrintWriter uni, bi, tri, four;
			if (lemmatized) {
				br = new BufferedReader(new FileReader("resources\\train tweets lemmatized.txt"));
				uni = new PrintWriter("resources\\unigrams"+frequencyThreshold+".txt", "UTF-8");
				bi = new PrintWriter("resources\\bigrams"+frequencyThreshold+".txt", "UTF-8");
				tri = new PrintWriter("resources\\trigrams"+frequencyThreshold+".txt", "UTF-8");
				four = new PrintWriter("resources\\fourgrams"+frequencyThreshold+".txt", "UTF-8");
			}
			else {
				br = new BufferedReader(new FileReader("resources\\train tweets.txt"));
				uni = new PrintWriter("resources\\unigrams_not_lemmatized"+frequencyThreshold+".txt", "UTF-8");
				bi = new PrintWriter("resources\\bigrams_not_lemmatized"+frequencyThreshold+".txt", "UTF-8");
				tri = new PrintWriter("resources\\trigrams_not_lemmatized"+frequencyThreshold+".txt", "UTF-8");
				four = new PrintWriter("resources\\fourgrams_not_lemmatized"+frequencyThreshold+".txt", "UTF-8");
			}
			extractNgramsFromFile(br,uni,bi,tri,four,frequencyThreshold);
			
			br.close();
			uni.close();
			bi.close();
			tri.close();
			four.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void extractCharNgramsFromTrainSet(boolean lemmatized, int frequencyThreshold) {
		try {
			BufferedReader br;
			PrintWriter three, four, five;
			if (lemmatized) {
				br = new BufferedReader(new FileReader("resources\\train tweets lemmatized.txt"));
				three = new PrintWriter("resources\\char_trigrams"+frequencyThreshold+".txt", "UTF-8");
				four = new PrintWriter("resources\\char_fourgrams"+frequencyThreshold+".txt", "UTF-8");
				five = new PrintWriter("resources\\char_fivegrams"+frequencyThreshold+".txt", "UTF-8");
			}
			else {
				br = new BufferedReader(new FileReader("resources\\train tweets.txt"));
				three = new PrintWriter("resources\\char_trigrams_not_lemmatized"+frequencyThreshold+".txt", "UTF-8");
				four = new PrintWriter("resources\\char_fourgrams_not_lemmatized"+frequencyThreshold+".txt", "UTF-8");
				five = new PrintWriter("resources\\char_fivegrams_not_lemmatized"+frequencyThreshold+".txt", "UTF-8");
			}
			extractCharNgramsFromFile(br,three,four,five,frequencyThreshold);
			
			br.close();
			three.close();
			four.close();
			five.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void extractNgramsFromFile(BufferedReader input, PrintWriter uni, PrintWriter bi, PrintWriter tri, PrintWriter four, int frequencyThreshold) {
		try {
			HashMap<String, Integer> unigrams = new HashMap<String, Integer>();
			HashMap<String, Integer> bigrams = new HashMap<String, Integer>();
			HashMap<String, Integer> trigrams = new HashMap<String, Integer>();
			HashMap<String, Integer> fourgrams = new HashMap<String, Integer>();
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
				for (String ngram : NGramExtractor.ngrams(4, line)) {
					if (fourgrams.containsKey(ngram)) {
						fourgrams.put(ngram, fourgrams.get(ngram)+1);
					}
					else {
						fourgrams.put(ngram,1);
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
			for (String fourgram : fourgrams.keySet()) {
				if (fourgrams.get(fourgram)>=frequencyThreshold) {
					four.println(fourgram);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void extractCharNgramsFromFile(BufferedReader input, PrintWriter three, PrintWriter four, PrintWriter five, int frequencyThreshold) {
		try {
			HashMap<String, Integer> trigrams = new HashMap<String, Integer>();
			HashMap<String, Integer> fourgrams = new HashMap<String, Integer>();
			HashMap<String, Integer> fivegrams = new HashMap<String, Integer>();
			String line = "";
			while ((line = input.readLine()) != null && line.length()!=0) {
				for (String ngram : NGramExtractor.charNgrams(3, line)) {
					if (trigrams.containsKey(ngram)) {
						trigrams.put(ngram, trigrams.get(ngram)+1);
					}
					else {
						trigrams.put(ngram,1);
					}
				}
				for (String ngram : NGramExtractor.charNgrams(4, line)) {
					if (trigrams.containsKey(ngram)) {
						fourgrams.put(ngram, fourgrams.get(ngram)+1);
					}
					else {
						fourgrams.put(ngram,1);
					}
				}
				for (String ngram : NGramExtractor.charNgrams(5, line)) {
					if (fivegrams.containsKey(ngram)) {
						fivegrams.put(ngram, fivegrams.get(ngram)+1);
					}
					else {
						fivegrams.put(ngram,1);
					}
				}
			}	
			for (String trigram : trigrams.keySet()) {
				if (trigrams.get(trigram)>=frequencyThreshold) {
					three.println(trigram);
				}
			}	
			for (String fourgram : fourgrams.keySet()) {
				if (fourgrams.get(fourgram)>=frequencyThreshold) {
					four.println(fourgram);
				}
			}	
			for (String fivegram : fivegrams.keySet()) {
				if (fivegrams.get(fivegram)>=frequencyThreshold) {
					five.println(fivegram);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
