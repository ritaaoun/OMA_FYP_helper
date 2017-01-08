import java.util.HashMap;
import java.util.Vector;

public class Preprocessor {

	public static HashMap<String,Object> preprocess(String tweet)
	{
		HashMap<String,Object> preprocessed = new HashMap<String,Object>();

		Vector<String> original = new Vector<String>();
		Vector<Vector<String>> hashtags = new Vector<Vector<String>>();
		Vector<String> normalized = new Vector<String>();
		
		Vector<String> emojis = EmojiExtractor.getEmojis(tweet);
		tweet = EmojiExtractor.normalizeAllEmojis(tweet);

		Vector<String> emoticons = EmoticonExtractor.getEmoticons(tweet);
		tweet = EmoticonExtractor.normalizeAllEmoticons(tweet);

		HashMap<Character,Integer> punctuation = PunctuationExtractor.getPunctuation(tweet);
		
		boolean hasURLs = Normalizer.hasURL(tweet);
		
		if (hasURLs) {	
			tweet = Normalizer.normalizeURLs(tweet);
			hasURLs = true;
		}
		
		boolean hasMentions = Normalizer.hasMention(tweet);
		
		if (hasMentions) {
			tweet = Normalizer.normalizeMentions(tweet);
			hasMentions = true;
		}
		
		tweet = Normalizer.normalizeNumbers(tweet);
		
		int numberOfElongatedWords = 0;
		
		int i = 0;
		int tweetLength = tweet.length();
		
		Vector<String> normalizedWithPunctuation = new Vector<String>();
		
		while (i < tweetLength)
		{
			String word = "";
			char currentChar = tweet.charAt(i);
			while(currentChar == ' ' || currentChar == '\n' || currentChar == '\t')
			{
				++i;
				if (i < tweetLength) {
					currentChar = tweet.charAt(i);
				}
				else {
					break;
				}
			}
			while(currentChar != ' ' && currentChar != '\n' && currentChar != '\t')
			{
				word = word + currentChar;
				++i;
				if (i < tweetLength) {
					currentChar = tweet.charAt(i);
				}
				else {
					break;
				}
			}
			
			if (word.length()!=0) {
				original.addElement(word);

				String normalizedWord = ElongationExtractor.removeElongation(word);
				
				if (!normalizedWord.equals(word)) {
					numberOfElongatedWords++;
					word = normalizedWord;
				}
				
				if (word.charAt(0) == '#')
				{
					word = PunctuationExtractor.replaceAllPunctuationsWithSpace(word, false);
					Vector <String> hashtag = extractWords(word);
					hashtags.addElement(hashtag);
					normalized.addElement("هاشتاغتوكن");
					normalizedWithPunctuation.addElement("هاشتاغتوكن");
				}
				else
				{
					String wordWithSomePunc = PunctuationExtractor.replaceAllPunctuationsWithSpace(word, true);
					Vector<String> words = extractWords(wordWithSomePunc);
					normalizedWithPunctuation.addAll(words);
					
					word = PunctuationExtractor.replaceAllPunctuationsWithSpace(word, false);
					words = extractWords(word);
					normalized.addAll(words);
				}
				
			}
		}
		

		
		preprocessed.put("negated", Negation.isNegated(normalizedWithPunctuation));
		preprocessed.put("emojis", emojis);
		preprocessed.put("original", original);
		preprocessed.put("hashtags", hashtags);
		preprocessed.put("normalized", normalized);
		preprocessed.put("urls", hasURLs);
		preprocessed.put("mentions", hasMentions);
		preprocessed.put("emoticons", emoticons);
		preprocessed.put("?", punctuation.get('?'));
		preprocessed.put("!", punctuation.get('!'));
		preprocessed.put("elongated", numberOfElongatedWords);
		return preprocessed;
	}
	
	private static Vector<String> extractWords(String word)
	{
		int ind = word.indexOf(' ');
		int start = 0;
		
		Vector<String> out = new Vector<String>();

		while (ind != -1) {
			String sub = word.substring(start,ind);
			if (sub.length() > 0) {
				out.addElement(sub);
			}
			start = ind + 1;
			ind = word.indexOf(' ', start);
		}
		if (start < word.length()) {
			out.addElement(word.substring(start));
		}
		return out;
	}
}
