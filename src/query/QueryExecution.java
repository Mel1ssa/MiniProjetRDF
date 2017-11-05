package query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

import Dictionnary.Dictionnaire;

public class QueryExecution {
	ArrayList<ConditionInteger> conditionsInt;
	Dictionnaire dictionnaire;

	public void setDictionnaire(Dictionnaire dictionnaire) {
		this.dictionnaire = dictionnaire;
	}

	public ArrayList<ConditionInteger> getConditionsInt() {
		return conditionsInt;
	}

	public void setConditionsInt(ArrayList<ConditionInteger> conditionsInt) {
		this.conditionsInt = conditionsInt;
	}

	public ResultSet execSelect() {
		HashMap<Integer,HashMap<Integer,HashSet<Integer>>> index = dictionnaire.getIndexOPS();
		// récupération des données de la première condition
		ConditionInteger firstCondition = conditionsInt.get(0);
		HashSet<Integer> results = index.get(firstCondition.getObject()).get(firstCondition.getPredicate());
		TreeSet<Integer> treeSetResults = new TreeSet<>();
		treeSetResults.addAll(results);
		// Initialisation des variables utiles plus tard
		ConditionInteger secondCondition;
		HashSet<Integer> resultsBis;
		TreeSet<Integer> treeSetResultsBis; 
		// parcours des conditions à partir de l'index 1
		for(int i = 1; i<conditionsInt.size();i++) {
			secondCondition = conditionsInt.get(i);
			resultsBis = index.get(secondCondition.getObject()).get(secondCondition.getPredicate());
			treeSetResultsBis = new TreeSet<>();
			treeSetResultsBis.addAll(resultsBis);
			TreeSet<Integer> actualResult = new TreeSet<>();
			Iterator<Integer> it1 = treeSetResults.iterator();
			Iterator<Integer> it2 = treeSetResultsBis.iterator();
			Integer iterator1Int = it1.next();
			Integer iterator2Int = it2.next();
			System.out.println(treeSetResults + "\nvs\n" + treeSetResultsBis);
			while(it1.hasNext() && it2.hasNext()) {
				int compare = iterator1Int.compareTo(iterator2Int);
				System.out.println(iterator1Int + " vs " + iterator2Int);
				//Scanner s = new Scanner(System.in);
				//s.nextLine();
				if(compare > 0) {
					iterator2Int = it2.next();
				}
				else if(compare < 0) {
					iterator1Int = it1.next();
				}
				else if(compare == 0) {
					actualResult.add(iterator1Int);
					iterator1Int = it1.next();
					iterator2Int = it2.next();
				}
			}
			treeSetResults = actualResult;
		}
		
		ArrayList<String> resultats = new ArrayList<>();
		for(Integer resultInt : treeSetResults) {
			resultats.add(dictionnaire.get(resultInt));
		}
		return new ResultSet(resultats);
	}
	
}
