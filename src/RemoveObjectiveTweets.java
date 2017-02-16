import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RemoveObjectiveTweets {
	public static void main(String[] args) {
		try {
			String filename = "Resources\\All tweets (including objective).txt";
			
			String line = "";
			BufferedReader br = new BufferedReader(new FileReader(filename));
			PrintWriter writerPos = new PrintWriter("Resources\\positive tweets.txt", "UTF-8");
			PrintWriter writerNeg = new PrintWriter("Resources\\negative tweets.txt", "UTF-8");
			PrintWriter writerNeu = new PrintWriter("Resources\\neutral tweets.txt", "UTF-8");
			PrintWriter writerAll = new PrintWriter("Resources\\all tweets (excluding objective).txt", "UTF-8");
			
			while ((line = br.readLine()) != null && line.length()!=0) {
				String originalLine = line;
				if(line.contains("NEG")){
					line = line.replace("NEG", "");
					writerNeg.println(line);
				}
				else if(line.contains("POS")){
					line = line.replace("POS", "");
					writerPos.println(line);
				}
				else if(line.contains("NEUTRAL")){
					line = line.replace("NEUTRAL", "");
					writerNeu.println(line);
				}
				else {
					continue;
				}
				writerAll.println(originalLine);
			}
			br.close();
			writerPos.close();
			writerNeg.close();
			writerNeu.close();
			writerAll.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
