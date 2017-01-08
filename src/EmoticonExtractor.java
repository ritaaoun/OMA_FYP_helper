// =================================================================================================
// Copyright 2011 Twitter, Inc.
// -------------------------------------------------------------------------------------------------
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this work except in compliance with the License.
// You may obtain a copy of the License in the LICENSE file, or at:
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// =================================================================================================

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmoticonExtractor {
	private static String [] positive = {":-)", ":)", ":-]", ":]", ":-3", ":3", ":->", ":>", "8-)", "8)", ":-}",
			":}", ":o)", ":c)", ":^)", "=]", "=)", ":-D", ":D", "8-D", "8D", "x-D", "X-D", "xD", "X-D", "XD", "=D",
			"=3", "B^D", ":'-)", ":')", ":-*", ":*", ":√ó", ";-)", ";)", ";-]", ";]", ";^)", ";D", ":-P", ":P",
			"X-P", "XP", "x-p", "xp", ":-p", ":p", ":-b", ":b", "d:", "=p", "<3", "^_^", "^.^", "^^"};
	
	private static String [] neutral = {":-O", ":O", ":-o", ":o", ":-0", "8-0"};
	
	private static String[] negative = {":-(", ":(", ":-c", ":c", ":-<", ":<", ":-[", ":[", ":{", ":@", ":'-(", ";'(",
			"D-':", "D:", "D8", "D;", "D=", "DX", ":-/", ":\\", "=/", "=\\", ":S", ":-|", ":|", "</3", "<\3", "-_-",
			"-.-", ">_<", ">.<", "~_~", "_|_", "-|-", "=_="};
	
	public static Set<String> positiveEmoticons = new HashSet<String>(Arrays.asList(positive));
	public static Set<String> negativeEmoticons = new HashSet<String>(Arrays.asList(negative));
	public static Set<String> neutralEmoticons = new HashSet<String>(Arrays.asList(neutral));
	public static Set<String> allEmoticons = getAllEmoticons();
			
	public static Polarity polarity(String emoticon) {
		if (positiveEmoticons.contains(emoticon)) {
			return Polarity.Positive;
		}
		else if (neutralEmoticons.contains(emoticon)){
			return Polarity.Neutral;
		}
		else {
			return Polarity.Negative;
		}
	}
	
	public static Vector<String> getEmoticons(String tweet) {		
		Vector<String> emoticons = new Vector<String>();
		StringBuilder regex = new StringBuilder();
		regex.append("(");
		Iterator<String> iterator = allEmoticons.iterator();
		regex.append(Pattern.quote(iterator.next()));
		while (iterator.hasNext())
		{
		    regex.append('|');
		    regex.append(Pattern.quote(iterator.next()));
		}
		regex.append(")");
		Pattern pattern = Pattern.compile(regex.toString());
		Matcher matcher = pattern.matcher(tweet);
		while(matcher.find()) {
		   emoticons.addElement(matcher.group());  
		}
		
		return emoticons;
	}

	public static String normalizeAllEmoticons(String tweet) {
		String fixedTweet = "";
		StringBuilder regex = new StringBuilder();
		regex.append("(");
		Iterator<String> iterator = allEmoticons.iterator();
		regex.append(Pattern.quote(iterator.next()));
		while (iterator.hasNext())
		{
		    regex.append('|');
		    regex.append(Pattern.quote(iterator.next()));
		}
		regex.append(")");
		Pattern pattern = Pattern.compile(regex.toString());
		Matcher matcher = pattern.matcher(tweet);
		int end = 0;

		while(matcher.find()) {
		    fixedTweet = fixedTweet + tweet.substring(end, matcher.start()) + " «Ì„Ê ÌﬂÊ‰ Êﬂ‰ ";
		    end = matcher.end();
		}
		fixedTweet = fixedTweet + tweet.substring(end);

		return fixedTweet;
	}
	
	private static Set<String> getAllEmoticons() {
		Set<String> emoticons = new HashSet<String>();
		emoticons.addAll(positiveEmoticons);
		emoticons.addAll(negativeEmoticons);
		emoticons.addAll(neutralEmoticons);
		return emoticons;
	}
}