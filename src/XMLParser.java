import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;


public class XMLParser {
	
	   //takes a hashmap of processed tweets and places them in the 
	   //input configuration file to be given to madamira
	   public static void inputXML(Vector<HashMap<String,Object>> map, String fileName, boolean withHashtags){
		   try {
				DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance(); 
				DocumentBuilder builder;
				builder = domFactory.newDocumentBuilder();
				Document doc = builder.parse(new File(fileName)); 

				Node node = doc.getElementsByTagName("in_doc").item(0);
				// Removing previous tweets
				while (node.hasChildNodes()) {
					node.removeChild(node.getFirstChild());
				}
				
				int nbOfTweets = map.size();

				// Adding new tweets
				for (int i=0; i<nbOfTweets; i++) {
					HashMap<String, Object> preprocessingMap = map.get(i);
					@SuppressWarnings("unchecked")
					Vector<String> tweet = (Vector<String>)(preprocessingMap.get("normalized"));
					String tweetText = "";
					
					for (String word : tweet) {
						tweetText += word + " ";
					}
					Text a = doc.createTextNode(tweetText); 
					Element p = doc.createElement("in_seg"); 
					p.setAttribute("id", "tweet" + i);
					p.appendChild(a);
					node.insertBefore(p, null);
					
					if (withHashtags) {
						@SuppressWarnings("unchecked")
						Vector<Vector<String>> hashtags = (Vector<Vector<String>>) (preprocessingMap.get("hashtags"));
						
						for (int j=0; j<hashtags.size(); ++j) {
							Vector<String> hashtag = hashtags.elementAt(j);
							String hashtagText = "";
							for (String word : hashtag) {
								hashtagText += word + " ";
							}
							Text a2 = doc.createTextNode(hashtagText); 
							Element p2 = doc.createElement("in_seg"); 
							p2.setAttribute("id", "hashtag" + j);
							p2.appendChild(a2);
							node.insertBefore(p2, null);
						}
					}
				}
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(fileName));
				transformer.transform(source, result);
				
				fixNamespace(fileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
	   }
	   
	   //parses the output file produced by madamira and extracts from it
	   //the lemmas for every sentence
	   @SuppressWarnings("unchecked")
	public static HashMap<String, Object> outputXML(String filename, Vector<HashMap<String,Object>> preprocessed, boolean withHashtags ){
		   Vector<Vector<String>> lemmas = new Vector<Vector<String>>();
		   Vector<HashMap<String, Integer>> pos = new Vector<HashMap<String, Integer>>();
		   Vector<Vector<Vector<String>>> hashtags = new Vector<Vector<Vector<String>>>();
		   
		   HashMap<String, Object> output = new HashMap<String, Object>();
		   try {	
		         File inputFile = new File(filename);
		         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		         Document doc = dBuilder.parse(inputFile);
		         doc.getDocumentElement().normalize();
		         
		         NodeList segments = doc.getElementsByTagName("out_seg");
		         
		         int k = 0;
		         
		         for(int m = 0; m < preprocessed.size(); m++){
			         NodeList words = ((Element)segments.item(k)).getElementsByTagName("word");
			         String lemma="";
			         String p = "";
			         Vector<String> v = new Vector<String>();
			         HashMap<String, Integer> pV = new HashMap<String, Integer>();
			         Vector<Vector<String>> hV = new Vector<Vector<String>>();
			         
			         for(int i = 0; i < words.getLength(); i++){
			        	 Node word = words.item(i);
			        	 NodeList features = ((Element)word).getElementsByTagName("morph_feature_set");
		
		        		 Element morph = (Element)features.item(1);
		        		 String cleaned = "";
		        		 if (morph==null){//meaning that the word was not lemmatized
			        		 cleaned = ((Element)word).getAttribute("word");
			        		 
			        	 }else{
			        		 //get lemma
				        	 lemma = morph.getAttribute("lemma");
				        	 
				        	 for(int j = 0; j < lemma.length(); j++){
				        		 char c = lemma.charAt(j);
				        		 if(c!='_'){
				        			 cleaned+=lemma.charAt(j);	 
				        		 }else{
				        			 break;
				        		 }
				        	 }
				        	 
				        	 //get pos
				        	 p = morph.getAttribute("pos");
				        	 	 
			        	 }
			        	 
			        	 //lemmas vector
			        	 v.add(cleaned);
			        	 //pos vector
			        	 if(pV.containsKey(p)){
			        		 Integer count = pV.get(p);
			        		 pV.put(p, count+1);
			        	 }else{
			        		 pV.put(p, 1);
			        	 }
			        	 
			         }
			         lemmas.addElement(v);
			         pos.addElement(pV);
			         k++;
			         if (withHashtags) {
				         for(int n = 0; n < ((Vector<String>)preprocessed.elementAt(m).get("hashtags")).size(); n++){
				        	 Vector<String> h = new Vector<String>();
				        	 NodeList hwords = ((Element)segments.item(k)).getElementsByTagName("word");
				        	 String hlemma="";
				        	 /****** start *****/
				        	 
				        	 for(int i = 0; i < hwords.getLength(); i++){
					        	 Node hword = hwords.item(i);
					        	 NodeList hfeatures = ((Element)hword).getElementsByTagName("morph_feature_set");
				
				        		 Element hmorph = (Element)hfeatures.item(1);
				        		 String hcleaned = "";
				        		 if (hmorph==null){//meaning that the word was not lemmatized
					        		 hcleaned = ((Element)hword).getAttribute("word");
					        		 
					        	 }else{
					        		 //get lemma
						        	 hlemma = hmorph.getAttribute("lemma");
						        	 
						        	 for(int j = 0; j < hlemma.length(); j++){
						        		 char hc = hlemma.charAt(j);
						        		 if(hc!='_'){
						        			 hcleaned+=hlemma.charAt(j);	 
						        		 }else{
						        			 break;
						        		 }
						        	 }	 
					        	 }
					        	 
					        	 //lemmas vector
					        	 h.add(hcleaned);
					        	 
					         }
				        	 
				        	 
				        	 /***** end ******/
				        	 hV.add(h);
				        	 k++;
				         }
				         hashtags.add(hV); 
			         }
		      }
		          
		  } catch (Exception e) {
		     e.printStackTrace();
		  }   
		  output.put("lemmas", lemmas);
		  output.put("pos", pos);
		  output.put("hashtags", hashtags);
		  return output;
	   }
	   
		private static void fixNamespace(String fileName) {
			File f = new File(fileName);
			try {
				Scanner s = new Scanner(f);
				String content = s.useDelimiter("\\Z").next();
				int ind = content.indexOf("<madamira_input");
				int ind2 = content.indexOf(">",ind);
				String fixed = content.substring(0, ind2) + " xmlns=\"urn:edu.columbia.ccls.madamira.configuration:0.1\"" +
						content.substring(ind2);
				s.close();
				
				PrintStream out = new PrintStream(new FileOutputStream(fileName));
				out.print(fixed);
				out.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

}
