package query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import Dictionnary.Dictionnaire;

public class QueryExecutionFactory {

	/**
	 *  Fonction à revoir dans le cas de requête qui ne seront pas étoile
	 * @param query
	 * @param dico
	 * @return
	 */
	public static QueryExecution create(Query query,Dictionnaire dico) {
		/**
		 * Premièrement, il faut associer les ids aux éléments de la requête
		 */
		HashMap<Integer,HashMap<Integer,HashSet<Integer>>> index = dico.getIndexOPS();
		
		ArrayList<ConditionInteger> conditionsInt = new ArrayList<>();
		
		ArrayList<Condition> conditions = query.getConditions();
		for(Condition c : conditions) {
			String predicate = c.getPredicate().getQueryName();
			String object = c.getObject().getQueryName();
			int predicateInt = dico.get(predicate);
			int objectInt = dico.get(object);
			if(predicateInt==-1) {
				System.err.println("Le prédicat : " + predicate + " n'existe pas dans l'index");
				System.exit(0);
			}
			if(objectInt==-1) {
				System.err.println("L'objet : " + object + " n'existe pas dans l'index");
				System.exit(0);
			}
			
			int size = 0;
			if(index.get(objectInt).containsKey(predicateInt)){
				size = index.get(objectInt).get(predicateInt).size();
			}
			ConditionInteger condition = new ConditionInteger(c, predicateInt, objectInt, size);
			conditionsInt.add(condition);
		}
		
		Collections.sort(conditionsInt, new Comparator<ConditionInteger>() {
	        @Override
	        public int compare(ConditionInteger condition1, ConditionInteger condition2)
	        {

	            return  Integer.compare(condition1.sizeRequest, condition2.sizeRequest);
	        }
	    });
				
		QueryExecution queryExecutionObject = new QueryExecution();
		queryExecutionObject.setConditionsInt(conditionsInt);
		queryExecutionObject.setDictionnaire(dico);
		
		return queryExecutionObject;
	}
}
