import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class PerformanceEvaluator {
	public static void main(String[] args) {
		String folder = "results\\not_lemmatized_ngrams_threshold_5\\";
//		String costs[] = {/*"-2", "-1",*/ "-0", "+1", "+2"};
//		String gammas[] = {"-5", "-4", "-3", "-2", "-1", "-0", "+1", "+2"};
//		
//		for (String cost : costs) {
//			evaluateModel("resources\\dev tweet labels.txt",folder+"models\\linearc"+cost+"P.txt",folder+"results_linearc"+cost+".txt");
//			for (String gamma : gammas) {
//				evaluateModel("resources\\dev tweet labels.txt",folder+"models\\rbfc"+cost+"g"+gamma+"P.txt",folder+"results_rbfc"+cost+"g"+gamma+".txt");
//			}
//		}
//		evaluateModel("resources\\dev tweet labels.txt",folder+"models\\linearc-2P.txt",folder+"results_linearc-2.txt");
//		evaluateModel("resources\\dev tweet labels.txt",folder+"models\\linearc-1P.txt",folder+"results_linearc-1.txt");
		
		String filename = "best_test_rbfc+1g-2";
		evaluateModel("resources\\test tweet labels.txt",folder+filename+"P.txt",folder+filename+"_results.txt");
	}
	public static void evaluateModel(String locationOfActualLabels, String locationOfPredictedLabels, String locationOfOutput) {
		try {
			BufferedReader actual = new BufferedReader(new FileReader(locationOfActualLabels));
			BufferedReader predicted = new BufferedReader(new FileReader(locationOfPredictedLabels));
			String line = "";
			
			int truePol[] = {0,0,0};
			int falsePol[] = {0,0,0};
			int total[] = {0,0,0};
			
			while ((line = actual.readLine()) != null && line.length()!=0) {
				int actualLabel = (int) Double.parseDouble(line);
				line = predicted.readLine();
				int predictedLabel = (int) Double.parseDouble(line);
				++total[actualLabel];
				if (predictedLabel == actualLabel) {
					++truePol[predictedLabel];
				}
				else {
					++falsePol[predictedLabel];
				}
			}
			actual.close();
			predicted.close();
			
			PrintWriter performance = new PrintWriter(locationOfOutput, "UTF-8");
			double avgFmeasure = 0;
			
			for (int pol = 0; pol<=2; ++pol) {
				int totalPredictedPol = truePol[pol] + falsePol[pol];
				double precision;
				double recall;
				double fmeasure;
				if (totalPredictedPol == 0) {
					precision = 0;
					recall = 0;
					fmeasure = 0;
					performance.println("Error: There were no tweets predicted to be in class " + pol);
				}
				else if (truePol[pol] == 0) {
					precision = 0;
					recall = 0;
					fmeasure = 0;
				}
				else {
					precision = (double)truePol[pol]/totalPredictedPol;
					recall = (double)truePol[pol]/total[pol];
					fmeasure = 2*precision*recall/(precision+recall);
					avgFmeasure += fmeasure;
				}
				performance.println("Class " + pol + " precision: " + precision);
				performance.println("Class " + pol + " recall: " + recall);
				performance.println("Class " + pol + " f-measure: " + fmeasure);
			}
			avgFmeasure /= 3;
			double accuracy = (double)(truePol[0] + truePol[1] + truePol[2])/(total[0] + total[1] + total[2]);
			performance.println("Overall average f-measure: " + avgFmeasure);
			performance.println("Overall accuracy: " + accuracy);
			
			performance.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
