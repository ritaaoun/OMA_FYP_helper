import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Vector;

public class Folds {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("resources\\train tweets lemmatized.txt"));
			BufferedReader br2 = new BufferedReader(new FileReader("resources\\train tweet labels.txt"));
			
			String line = "";
			
			Vector<String> allTweets = new Vector<String>();
			Vector<String> allLabels = new Vector<String>();
			
			while ((line = br.readLine()) != null && line.length()!=0) {
				allTweets.addElement(line);
				allLabels.addElement(br2.readLine());
			}
			br.close();
			br2.close();
			
			int totalSize = allTweets.size();
			int sizeOfFold = totalSize/10;
			
			Vector<PrintWriter> foldTrainSets = new Vector<PrintWriter>();
			Vector<PrintWriter> foldTrainLabels = new Vector<PrintWriter>();
			Vector<PrintWriter> foldTestSets = new Vector<PrintWriter>();
			Vector<PrintWriter> foldTestLabels = new Vector<PrintWriter>();
			
			for (int fold=1; fold<=10; ++fold) {
				foldTrainSets.addElement(new PrintWriter("ngrams\\fold"+fold+"\\train.txt","UTF-8"));
				foldTrainLabels.addElement(new PrintWriter("ngrams\\fold"+fold+"\\train labels.txt","UTF-8"));
				foldTestSets.addElement(new PrintWriter("ngrams\\fold"+fold+"\\test.txt","UTF-8"));
				foldTestLabels.addElement(new PrintWriter("ngrams\\fold"+fold+"\\test labels.txt","UTF-8"));
			}
			
			for (int i=0; i<totalSize; ++i) {
				int belongsToFold = i/sizeOfFold;
				String tweet = allTweets.elementAt(i);
				String label = allLabels.elementAt(i);
				for (int fold=0; fold<10; ++fold) {
					if (belongsToFold==fold || (belongsToFold>9 && fold==9)) {
						foldTestSets.elementAt(fold).println(tweet);
						foldTestLabels.elementAt(fold).println(label);
					}
					else {
						foldTrainSets.elementAt(fold).println(tweet);
						foldTrainLabels.elementAt(fold).println(label);
					}
				}
			}
			
			for (int fold=0; fold<10; ++fold) {
				foldTrainSets.elementAt(fold).close();
				foldTrainLabels.elementAt(fold).close();
				foldTestSets.elementAt(fold).close();
				foldTestLabels.elementAt(fold).close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
