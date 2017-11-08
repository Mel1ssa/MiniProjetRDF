package Dictionnary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Dictionnaire {

	//private static Dictionnaire instance;
	
	/**
	 * Dictionnaire de mot
	 *  indexList : id  => mot
	 *  wordList  : mot => id   
	 */
	private ArrayList<String> indexList;
	private HashMap<String, Integer> wordList;
	
	/**
	 * base de donnée des Triplets
	 */
	private ArrayList<Triplet> tripletList;
	
	/**
	 * Index
	 */
	private HashMap<Integer,HashMap<Integer,HashSet<Integer>>> indexPOS;
	private HashMap<Integer,HashMap<Integer,HashSet<Integer>>> indexOPS;

	public Dictionnaire() {
		wordList = new HashMap<String, Integer>();
		indexList = new ArrayList<String>();
		tripletList = new ArrayList<>();
		indexPOS = new HashMap<Integer,HashMap<Integer,HashSet<Integer>>>();
		indexOPS = new HashMap<Integer,HashMap<Integer,HashSet<Integer>>>();
	}
	/*
	public static Dictionnaire getInstance() {
		if (instance == null) {
			instance = new Dictionnaire();
		}
		return instance;
	}
	*/
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
		
		int subjectIndex = add(subject);
		int predicateIndex = add(predicate);
		int objectIndex = add(object);
		Triplet triplet = new Triplet(subjectIndex, predicateIndex, objectIndex);
		tripletList.add(triplet);
		
		// Ici on s'occupe des index
		//addIndex(indexPOS, triplet, predicateIndex, objectIndex);
		//addIndex(indexOPS, triplet, objectIndex, predicateIndex);
		indexOPS.computeIfAbsent(triplet.getObject(), k->new HashMap<Integer, HashSet<Integer>>()).computeIfAbsent(triplet.getPredicate(), k->new HashSet<Integer>()).add(triplet.getSubject());
	}
	
	public ArrayList<Triplet> getTripletList() {
		return tripletList;
	}
	
	/**
	 * Marche que pour OPS et POS -> n'est plus utilisée !
	 * @param index
	 * @param t
	 * @param T1
	 * @param T2
	 */
	@SuppressWarnings("unused")
	private void addIndex(HashMap<Integer ,HashMap<Integer, HashSet<Integer>>> index,Triplet t, int T1, int T2) {
		
		HashMap<Integer, HashSet<Integer>>  indexOS = new HashMap<Integer,HashSet<Integer>>();
		HashSet<Integer> indexSub= new HashSet<Integer>(); ;
		boolean trv=false;
		if(!index.containsKey(T1)) { 	// si l'index ne contient pas le premier élement (predicat/objet) alors on lui ajoute 		
			indexSub.add(t.getSubject());
			indexOS.put(T2, indexSub);
			index.put(T1, indexOS);
			
		}
		else { 
			for(Map.Entry<Integer,HashSet<Integer>> val : index.get(T1).entrySet()) {
				if(val.getKey().equals(T2)) {
					val.getValue().add(t.getSubject());
					trv = true;
					break;
				}
			}
			if(!trv) {
				indexSub.add(t.getSubject());
				index.get(T1).put(T2, indexSub);
				
			}
		}
	}
	
		
	public HashMap<Integer, HashMap<Integer, HashSet<Integer>>> getIndexPOS() {
		return indexPOS;
	}

	public HashMap<Integer, HashMap<Integer, HashSet<Integer>>> getIndexOPS() {
		return indexOPS;
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
