import java.util.ArrayList;
import java.util.List;

public class NGramExtractor {
	public static void main(String[] args) {
		System.out.println(charNgrams(2,"hi kifak cava"));
	}
	public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }

    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }
	public static List<String> charNgrams(int n, String str) {
        List<String> charNgrams = new ArrayList<String>();
        for (int i = 0; i <= str.length() - n; ++i) {
            charNgrams.add(str.substring(i, i+n));
        }
        return charNgrams;
    }
}
