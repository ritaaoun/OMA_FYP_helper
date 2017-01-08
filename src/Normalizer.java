import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Normalizer {
	public static boolean hasURL(String tweet) {
		return tweet.contains("http://") || tweet.contains("https://");
	}
	public static String normalizeURLs(String tweet) {
		String normalized = "";
		String[] patterns = {"http://", "https://"};
		
		for (int i=0; i<2; ++i) {
			int ind = tweet.indexOf(patterns[i]);
			int start = 0;
			while (ind != -1) {
				normalized = normalized + tweet.substring(start,ind) + "يوارالتوكن";
				int indSpace = tweet.indexOf(' ', ind);
				int indEnter = tweet.indexOf('\n', ind);
				if (indSpace==indEnter) { // if they're both == -1 -> end of tweet
					start = tweet.length();
					break;
				}
				else if	(indSpace < indEnter || indEnter==-1) {
					start = indSpace;
				}
				else {
					start = indEnter;
				}
				ind = tweet.indexOf(patterns[i], start);
			}

			normalized = normalized + tweet.substring(start);
			
			tweet = normalized;
			normalized = "";
		}
		return tweet;
	}

	public static boolean hasMention(String tweet) {
		return tweet.contains("@");
	}
	
	public static String normalizeMentions(String tweet) {
		String normalized = "";
		
		int ind = tweet.indexOf("@");
		int start = 0;
		while (ind != -1) {
			if (ind==0 || tweet.charAt(ind-1) == ' ' || tweet.charAt(ind-1) == '\n') {
				normalized = normalized + tweet.substring(start,ind) + "منشنتوكن";
				int indSpace = tweet.indexOf(' ', ind);
				int indEnter = tweet.indexOf('\n', ind);
				if (indSpace==indEnter) { // if they're both == -1 -> end of tweet
					return normalized;
				}
				else if	(indSpace < indEnter || indEnter==-1) {
					start = indSpace;
				}
				else {
					start = indEnter;
				}
				ind = tweet.indexOf("@", start);
			}
			else {
				if (ind != -1) {
					normalized = normalized + tweet.substring(start,ind);
				}
				start = ind;
				ind = tweet.indexOf("@", start+1);
			}
		}
		normalized = normalized + tweet.substring(start);
	
		return normalized;
	}
	
	public static String normalizeNumbers(String tweet) {
	    String normalized = "";

		Pattern pattern = Pattern.compile("[0-9\u0660-\u0669]+");
        Matcher matcher = pattern.matcher(tweet);
		
		int start = 0;
        while(matcher.find()) {
			int end = matcher.start();
            normalized = normalized + tweet.substring(start, end) + " " + matcher.group() + " ";
			start = matcher.end()+1;
        }
		normalized += tweet.substring(start);
        return normalized;
	}
	

}
