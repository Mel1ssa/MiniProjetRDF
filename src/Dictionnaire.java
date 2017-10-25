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
	
	public String toString(){
		String str1 = "Dictionnaire :\n{";
		for(int i = 0; i<100; i++){
			str1 += i + " : " + indexList.get(i) + "\n";
		}
		String str = str1 + "}\nListe de triplet :\n{\n";
		for(int i = 0; i<100; i++){
			str += "   {"+ i + " : " + tripletList.get(i).toString() + "\n";
		}
		str += "}\nNombre de mots unique dans le dictionnaire : " + indexList.size();
		return str;
	}
}
