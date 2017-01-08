import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PunctuationExtractor {

	public static HashMap<Character,Integer> getPunctuation(String tweet) {
		HashMap<Character,Integer> punc = new HashMap<Character,Integer>();
		punc.put('?', 0);
		punc.put('!', 0);
		
		Pattern pattern = Pattern.compile("[^\u0621-\u064A\u0660-\u0669 0-9a-zA-Z]");
		Matcher matcher = pattern.matcher(tweet);
		while(matcher.find()) {
		   String s = matcher.group();
		   if (s.equals("!")) {
			   punc.put('!', punc.get('!')+1);
		   }
		   else if (s.equals("?") || s.equals("؟")) {
			   punc.put('?', punc.get('?')+1);
		   }
		}
		return punc;
	}
	
	public static String replaceAllPunctuationsWithSpace(String tweet, boolean skipSome) {
		Pattern pattern;
		if (skipSome)
		{
			pattern = Pattern.compile("[\u0621-\u064A\u0660-\u0669 0-9a-zA-Z;:.,?!؟(]+");
			Matcher matcher = pattern.matcher(tweet);
			String fixedTweet = "";
			while(matcher.find()) {
				String word = matcher.group();
				Pattern pattern2 = Pattern.compile("[;:.,?!؟(]");
				Matcher matcher2 = pattern2.matcher(word);
				int start = 0;
				while (matcher2.find()) {
					int end = matcher2.start()+1;
					fixedTweet = fixedTweet + word.substring(start, end) + " ";
					start = end;
				}
				if (start < word.length()) {
					fixedTweet = fixedTweet + word.substring(start) + " ";
				}
			}
			return fixedTweet;
		}
		else
		{
			pattern = Pattern.compile("[\u0621-\u064A\u0660-\u0669 0-9a-zA-Z]+");
			Matcher matcher = pattern.matcher(tweet);
			String fixedTweet = "";
			while(matcher.find()) {
				fixedTweet = fixedTweet + matcher.group() + " ";
			}
			return fixedTweet;
		}
		
	}
}
