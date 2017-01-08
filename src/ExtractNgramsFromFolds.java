import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class ExtractNgramsFromFolds {

	public static void main(String[] args) {
		try {
			for (int fold=1; fold<=10; ++fold) {
				BufferedReader br = new BufferedReader(new FileReader("ngrams\\fold"+fold+"\\train.txt"));
				PrintWriter uni = new PrintWriter("ngrams\\fold"+fold+"\\unigrams.txt", "UTF-8");
				PrintWriter bi = new PrintWriter("ngrams\\fold"+fold+"\\bigrams.txt", "UTF-8");
				PrintWriter tri = new PrintWriter("ngrams\\fold"+fold+"\\trigrams.txt", "UTF-8");

				HashMap<String, Integer> unigrams = new HashMap<String, Integer>();
				HashMap<String, Integer> bigrams = new HashMap<String, Integer>();
				HashMap<String, Integer> trigrams = new HashMap<String, Integer>();
				
				String line = "";
				while ((line = br.readLine()) != null && line.length()!=0) {
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
				br.close();
				
				for (String unigram : unigrams.keySet()) {
					if (unigrams.get(unigram)>=5) {
						uni.println(unigram);
					}
				}
				uni.close();
				
				for (String bigram : bigrams.keySet()) {
					if (bigrams.get(bigram)>=5) {
						bi.println(bigram);
					}
				}
				bi.close();
				
				for (String trigram : trigrams.keySet()) {
					if (trigrams.get(trigram)>=5) {
						tri.println(trigram);
					}
				}
				tri.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
