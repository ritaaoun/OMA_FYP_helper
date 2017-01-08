import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
public class LexiconMapping {
	public static final HashMap<String, Vector<Double>> arsenl = arSenLMapping("Resources\\ArSenL.txt");
	public static final HashMap<String, Double> arasenti = araSenTiMapping("Resources\\AraSenTi.txt");
	public static final HashMap<String, Vector<Double>> emojisLexicon = emojiMapping("Resources\\Emojis.csv");
	public static final HashMap<String, Double> dahl = dahlMapping("Resources\\DAHL.txt");
	public static final Vector<String> negationWords = negation("Resources\\Negation.txt");
	
	public static HashMap<String, Vector<Double>> arSenLMapping(String filename){
		HashMap<String, Vector<Double>> map = new HashMap<String, Vector<Double>>();
		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			while ((line = br.readLine()) != null) {
				if(line.length() > 0){
					if(line.charAt(0) != '/'){
						//current row is a word with its scores
						int counter = 0;
						String word = "";
						String pos = "";
						String neg = "";
						String con = "";
						for(int i = 0; i < line.length(); i++){
							char curr = line.charAt(i);
							if(curr != ';'){
								if(counter == 0){
									word+=curr;
								}else if(counter == 1){
									pos+=curr;
								}else if(counter == 2){
									neg+=curr;
								}else if(counter == 3){
									con+=curr;
								}
							}else{
								counter++;
							}
						}
						Vector<Double> v = new Vector<Double>();
						v.add(Double.parseDouble(pos));
						v.add(Double.parseDouble(neg));
						v.add(Double.parseDouble(con));
						
						boolean contains = map.containsKey(word);
						if((contains && map.get(word).get(2) < v.get(2)) || !contains){
								map.put(word, v);
						}
					}
				}
			}
			
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static HashMap<String, Double> araSenTiMapping(String filename){
		HashMap<String, Double> map = new HashMap<String, Double>();
		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(filename));

			while ((line = br.readLine()) != null) {
				
				if(line.length()>0 && line.charAt(0) != '/'){
					
					int counter = 0;
					String word = "";
					String score = "";
					for(int i = 0; i < line.length(); i++){
						char curr = line.charAt(i);
						if(curr != ' '){
							if(counter == 0){
								word+=curr;
							}else{
								score+=curr;
							}
						}else{
							counter++;
						}
					}
					map.put(word, Double.parseDouble(score));
				}
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static HashMap<String, Vector<Double>> emojiMapping(String filename){
		HashMap<String, Vector<Double>> map = new HashMap<String, Vector<Double>>();
		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] s = line.split(",");
				Vector<Double> v = new Vector<Double>();
				v.addElement(Double.parseDouble(s[2]));
				v.addElement(Double.parseDouble(s[1]));
				map.put(s[0], v);
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static HashMap<String, Double> dahlMapping(String filename){
		HashMap<String, Double> map = new HashMap<String, Double>();
		try{
			String line;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			while ((line = br.readLine()) != null) {
				if(line.length()>0 && line.charAt(0) != '/'){
					int pos = 0;
					String word = "";
					String score = "";
					for(int i = 0; i < line.length(); i++){
						char curr = line.charAt(i);
						if(curr != '\t'){
							if(pos == 0){
								word += curr;
							}
							else if(pos == 2){
								score += curr;
							}
						}
						else{
							pos++;
						}
						
					}
					map.put(word, Double.parseDouble(score));
				}
			}
			
			br.close();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
		public static Vector<String> negation(String filename){
		Vector<String> neg = new Vector<String>();
		try{
			String line;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String word = "";
				if(line.length()>0 && line.charAt(0) != '/'){
					for(int i = 0; i < line.length(); i++){
						char curr = line.charAt(i);
						if(curr != ' '){
								word+=curr;
						}
					}
					neg.addElement(word);
				}
				
			}
			br.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return neg;
	}
	
	
}






