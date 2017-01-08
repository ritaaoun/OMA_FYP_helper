import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Vector;

public class Folds {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("resources\\train tweets.txt"));
			
			String line = "";
			
			Vector<String> all = new Vector<String>();
			
			while ((line = br.readLine()) != null && line.length()!=0) {
				all.addElement(line);
			}
			br.close();
			
			int totalSize = all.size();
			int sizeOfFold = totalSize/10;
			
			Vector<PrintWriter> foldTrainSets = new Vector<PrintWriter>();
			Vector<PrintWriter> foldTestSets = new Vector<PrintWriter>();
			
			for (int fold=1; fold<=10; ++fold) {
				foldTrainSets.addElement(new PrintWriter("ngrams\\fold"+fold+"\\train.txt","UTF-8"));
				foldTestSets.addElement(new PrintWriter("ngrams\\fold"+fold+"\\test.txt","UTF-8"));
			}
			
			for (int i=0; i<totalSize; ++i) {
				int belongsToFold = i/sizeOfFold;
				String tweet = all.elementAt(i);
				for (int fold=0; fold<10; ++fold) {
					if (belongsToFold==fold || (belongsToFold>9 && fold==9)) {
						foldTestSets.elementAt(fold).println(tweet);
					}
					else {
						foldTrainSets.elementAt(fold).println(tweet);
					}
				}
			}
			
			for (int fold=0; fold<10; ++fold) {
				foldTrainSets.elementAt(fold).close();
				foldTestSets.elementAt(fold).close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
