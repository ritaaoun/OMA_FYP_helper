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
			PrintWriter test = new PrintWriter("resources\\test tweets.txt", "UTF-8");
			String line = "";
			
			Vector<String> allTweets = new Vector<String>();
			
			while ((line = br.readLine()) != null && line.length()!=0) {
				allTweets.addElement(line);
			}
			br.close();
			int nbOfTweets = allTweets.size();
			int nbOfTrainTweets = (int)Math.ceil(nbOfTweets*0.8);
			
			for (int i=0; i<nbOfTrainTweets; ++i) {
				train.println(allTweets.elementAt(i));
			}
			train.close();
			for (int i = nbOfTrainTweets; i<nbOfTweets; ++i) {
				test.println(allTweets.elementAt(i));
			}
			test.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
