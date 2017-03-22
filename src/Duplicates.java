import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

public class Duplicates {

	public static void main(String[] args) {
		cleanUp("dataset_tweets\\Egypt\\");
	}
	
	public static void cleanUp(String folder) {
		HashSet<String> tweets = new HashSet<String>();
		try {
			String tweet = "";
			BufferedReader brT = new BufferedReader(new FileReader(folder + "tweets.txt"));
			BufferedReader brL = new BufferedReader(new FileReader(folder + "locations.txt"));
			BufferedReader brU = new BufferedReader(new FileReader(folder + "users.txt"));
			BufferedReader brI = new BufferedReader(new FileReader(folder + "ids.txt"));
			PrintWriter writerT = new PrintWriter(folder + "tweets_clean.txt", "UTF-8");
			PrintWriter writerL = new PrintWriter(folder + "locations_clean.txt", "UTF-8");
			PrintWriter writerU = new PrintWriter(folder + "users_clean.txt", "UTF-8");
			PrintWriter writerI = new PrintWriter(folder + "ids_clean.txt", "UTF-8");
			while ((tweet = brT.readLine()) != null && tweet.length()!=0) {
				String location = brL.readLine();
				String user = brU.readLine();
				String id = brI.readLine();
				if (!tweets.contains(tweet) && tweet.length()>29) {
					tweets.add(tweet);
					writerT.println(tweet);
					writerU.println(user);
					writerI.println(id);
					writerL.println(location);
				}
			}
			brT.close();
			brU.close();
			brL.close();
			brI.close();
			writerT.close();
			writerU.close();
			writerL.close();
			writerI.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
