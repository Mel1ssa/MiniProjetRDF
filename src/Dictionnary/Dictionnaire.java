package Dictionnary;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Dictionnaire {

	private static Dictionnaire instance;
	private ArrayList<String> indexList;
	private HashMap<String, Integer> wordList;
	private ArrayList<Triplet> tripletList;

	private Dictionnaire() {
		wordList = new HashMap<String, Integer>();
		indexList = new ArrayList<String>();
		tripletList = new ArrayList<>();
	}

	public static Dictionnaire getInstance() {
		if (instance == null) {
			instance = new Dictionnaire();
		}
		return instance;
	}

	public Integer has(String word) {
		return wordList.get(word);
	}

	public int get(String word) {
		Integer mot = wordList.get(word);
		if(mot == null)
			return -1;
		return wordList.get(word);
	}

	public String get(int index) {
		return indexList.get(index);
	}

	private int add(String word) {
		Integer wordIndex;
		if ((wordIndex = has(word)) == null) {
			wordIndex = indexList.size();
			wordList.put(word, wordIndex);
			indexList.add(word);
			return wordIndex;
		}
		return wordIndex;
	}

	
	
	public void add(String subject, String predicate, String object) {
		Triplet triplet = new Triplet();
		triplet.subject = add(subject);
		triplet.predicate = add(predicate);
		triplet.object = add(object);

		tripletList.add(triplet);
	}
	
	public ArrayList<Triplet> getTripletList() {
		return tripletList;
	}

	
	
	public String toString(){
		String str ="";
				
		String strD = "Dictionnaire :\n{";
		for(int i = 0; i<indexList.size(); i++){
			strD += i + " : " + indexList.get(i) + "\n";
		}
		 str = strD + "}\nListe de triplet :\n{\n";
		for(int i = 0; i<tripletList.size(); i++){
			str += "   {"+ i + " : " + tripletList.get(i).toStringPOS() + "\n";
		}
		
		str += "}\nNombre de mots unique dans le dictionnaire : " + indexList.size();
		
		return str;
	}
		
}
