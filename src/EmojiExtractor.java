import java.util.List;
import java.util.Vector;

import com.vdurmont.emoji.EmojiParser;

public class EmojiExtractor extends EmojiParser {

	public static Vector<String> getEmojis(String input) {
		List<UnicodeCandidate> em = EmojiParser.getUnicodeCandidates(input);
		Vector<String> emojis = new Vector<String>();
		for (UnicodeCandidate u : em) {
			emojis.addElement(u.getEmoji().getUnicode());
		}
		return emojis;
	}
	
	public static String normalizeAllEmojis(String str) {
	    EmojiTransformer emojiTransformer = new EmojiTransformer() {
	      public String transform(UnicodeCandidate unicodeCandidate) {
	        return " «Ì„Ê ÌﬂÊ‰ Êﬂ‰ ";
	      }
	    };

	    return parseFromUnicode(str, emojiTransformer);
	}

}
