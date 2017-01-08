
public class ElongationExtractor {

	public static String removeElongation(String word){
		String cleaned = "";
		if(word.length() > 3){
			char prev = word.charAt(0);
			cleaned+=prev;
			int count = 0;
			char curr;
			for(int i = 1; i < word.length(); i++){
				count++;
				curr = word.charAt(i);
				if(curr==prev){
					count++;
				}else{
					count = 0;
				}
				if(count <= 2 ){
					cleaned+=curr;
				}
				prev = curr;
			}
		}else{
			cleaned = word;
		}
		
		return cleaned;
	}

}
