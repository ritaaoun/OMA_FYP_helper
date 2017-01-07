import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("ngrams\\all\\lemmatized.txt"));
			String line;
			Vector<String> tweets = new Vector<String>();
			while ((line = br.readLine()) != null && line.length()!=0) {
				tweets.addElement(line);
			}
			br.close();
			
			for (int n=1; n<4; n++) {
				HashMap<String,Integer> ngrams = new HashMap<String,Integer>();
				for (String tweet : tweets) {
					List<String> ngramsInTweet = NGramExtractor.ngrams(n, tweet);
					for (String ngram : ngramsInTweet) {
						if (ngrams.containsKey(ngram)) {
							ngrams.put(ngram, ngrams.get(ngram)+1);
						}
						else {
							ngrams.put(ngram,1);
						}
					}
				}
													
				Set<String> keys = ngrams.keySet();
				
				Vector<String> toRemove = new Vector<String>();
				for (String ngram : keys) {
					if (ngrams.get(ngram) < 5) {
						toRemove.addElement(ngram);
					}
				}
				keys.removeAll(toRemove);
				
				PrintWriter writer = new PrintWriter("ngrams\\all\\" + n + "grams.txt", "UTF-8");
				for (String ngram : keys) {
					writer.println(ngram);
				}
				writer.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
//		try {
//			for (int j=1; j<11; j++) {
//				BufferedReader br = new BufferedReader(new FileReader("ngrams\\fold"+j+"\\train.txt"));
//				String line;
//				Vector<String> trainTweets = new Vector<String>();
//				while ((line = br.readLine()) != null && line.length()!=0) {
//					trainTweets.addElement(line);
//				}		
//				br.close();
//				
//				br = new BufferedReader(new FileReader("ngrams\\fold"+j+"\\test labels.txt"));
//				Vector<Integer> labels = new Vector<Integer>();
//				while ((line = br.readLine()) != null && line.length()!=0) {
//					labels.addElement(Integer.decode(line));
//				}
//				br.close();
//				
//				for (int n=1; n<4; n++) {
//					HashMap<String,Integer> ngrams = new HashMap<String,Integer>();
//					for (String tweet : trainTweets) {
//						List<String> ngramsInTweet = NGramExtractor.ngrams(n, tweet);
//						for (String ngram : ngramsInTweet) {
//							if (ngrams.containsKey(ngram)) {
//								ngrams.put(ngram, ngrams.get(ngram)+1);
//							}
//							else {
//								ngrams.put(ngram,1);
//							}
//						}
//					}
//										
//					PrintWriter writer = new PrintWriter("ngrams\\fold"+j+"\\features " + n + "grams test.txt", "UTF-8");
//					
////					int nbOfTweets = trainTweets.size();
//					Set<String> keys = ngrams.keySet();
//					
//					Vector<String> toRemove = new Vector<String>();
//					
//					for (String ngram : keys) {
//						if (ngrams.get(ngram) < 5) {
//							toRemove.addElement(ngram);
//						}
//					}
//
//					keys.removeAll(toRemove);
//
//					br = new BufferedReader(new FileReader("ngrams\\fold"+j+"\\test.txt"));
//					Vector<String> testTweets = new Vector<String>();
//					while ((line = br.readLine()) != null && line.length()!=0) {
//						testTweets.addElement(line);
//					}
//					br.close();
//					int size = testTweets.size();
//					for (int i=0; i<size; ++i) {
//						String tweet = testTweets.elementAt(i);
//						List<String> ngramsInTweet = NGramExtractor.ngrams(n, tweet);
//						
//						for (String ngram : keys) {
//							if (ngramsInTweet.contains(ngram)) {
//								writer.print(1 + ",");
//							}
//							else {
//								writer.print(0 + ",");
//							}
//						}
//						writer.println(labels.elementAt(i));
//					}
//					writer.close();
//					
////					writer = new PrintWriter("ngrams\\fold"+j+"\\" + n + "grams.txt", "UTF-8");
////					for (String ngram : keys) {
////						writer.println(ngram);
////					}
////					writer.close();
//				}
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//}
