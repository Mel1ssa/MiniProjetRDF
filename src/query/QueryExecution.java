package query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
		ConditionInteger firstCondition = conditionsInt.get(0);
		if(firstCondition.getSizeRequest() == 0){
			return new ResultSet(new ArrayList<>());
		}
		HashSet<Integer> results = new HashSet<>(index.get(firstCondition.getObject()).get(firstCondition.getPredicate()));
		ConditionInteger secondCondition;
		HashSet<Integer> resultsBis;
		for(int i = 1; i<conditionsInt.size();i++) {
			secondCondition = conditionsInt.get(i);
			resultsBis = index.get(secondCondition.getObject()).get(secondCondition.getPredicate());
			results.retainAll(resultsBis);
		}
		ArrayList<String> resultats = new ArrayList<>();
		for(Integer resultInt : results) {
			resultats.add(dictionnaire.get(resultInt));
		}
		return new ResultSet(resultats);
	}
	
}
