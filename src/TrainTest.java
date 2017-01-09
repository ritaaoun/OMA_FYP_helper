import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Vector;

public class TrainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("resources\\all tweets.txt"));
			PrintWriter train = new PrintWriter("resources\\train tweets.txt", "UTF-8");
			PrintWriter trainLabels = new PrintWriter("resources\\train tweet labels.txt", "UTF-8");
			PrintWriter dev = new PrintWriter("resources\\dev tweets.txt","UTF-8");
			PrintWriter devLabels = new PrintWriter("resources\\dev tweet labels.txt","UTF-8");
			PrintWriter test = new PrintWriter("resources\\test tweets.txt", "UTF-8");
			PrintWriter testLabels = new PrintWriter("resources\\test tweet labels.txt", "UTF-8");
			String line = "";
			
			Vector<String> allTweets = new Vector<String>();
			Vector<Integer> allLabels = new Vector<Integer>();
			
			while ((line = br.readLine()) != null && line.length()!=0) {
				if (line.contains("POS")) {
					allLabels.add(2);
					line = line.replace("POS", "");
				}
				else if (line.contains("NEG")) {
					allLabels.add(0);
					line = line.replace("NEG", "");
				}
				else {
					allLabels.add(1);
					line = line.replace("NEUTRAL", "");
				}
				allTweets.addElement(line);
			}
			br.close();
			int nbOfTweets = allTweets.size();
			int nbOfTrainTweets = (int)Math.ceil(nbOfTweets*0.7);
			int nbOfDevTweets = nbOfTrainTweets + (int) Math.ceil(nbOfTweets*0.1);
			
			for (int i=0; i<nbOfTrainTweets; ++i) {
				train.println(allTweets.elementAt(i));
				trainLabels.println(allLabels.elementAt(i));
			}
			train.close();
			trainLabels.close();
			
			for (int i = nbOfTrainTweets; i < nbOfDevTweets; ++i) {
				dev.println(allTweets.elementAt(i));
				devLabels.println(allLabels.elementAt(i));
			}
			dev.close();
			devLabels.close();
			
			for (int i = nbOfDevTweets; i<nbOfTweets; ++i) {
				test.println(allTweets.elementAt(i));
				testLabels.println(allLabels.elementAt(i));
			}
			test.close();
			testLabels.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
