import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;


public class Lemmatizer {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("resources\\train tweets.txt"));
			Vector<String> tweets = new Vector<String>();
			String line = "";
			while ((line = br.readLine()) != null && line.length()!=0)
			{
				tweets.addElement(line);
			}
			br.close();
			Vector<HashMap<String, Object>> preprocessed = new Vector<HashMap<String, Object>>();
			
			for (String tweet : tweets)
			{
				preprocessed.add(Preprocessor.preprocess(tweet));
			}

			String input = "in.xml";
			String output = "out.xml";
				
			XMLParser.inputXML(preprocessed,input,false);
			Madamira.lemmatize(input, output);
			HashMap<String, Object> outputXML = XMLParser.outputXML(output, preprocessed, false);
				
			Vector<Vector<String>> lemmatizedTweets = (Vector<Vector<String>>)(outputXML.get("lemmas"));
				
			PrintWriter writer = new PrintWriter("resources\\train tweets lemmatized.txt", "UTF-8");
				
			for(Vector<String> tweet : lemmatizedTweets){
				for (String word : tweet) {
					writer.print(word + " ");
				}
				writer.println();
			}
			writer.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
