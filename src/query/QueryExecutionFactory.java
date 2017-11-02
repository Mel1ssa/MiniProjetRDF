package query;

import java.util.ArrayList;

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
		
		int[] tabCondition = new int[2];
		
		ArrayList<ConditionInteger> conditionsInt = new ArrayList<>();
		
		ArrayList<Condition> conditions = query.getConditions();
		for(Condition c : conditions) {
			String predicate = c.getPredicate().getQueryName();
			String object = c.getObject().getQueryName();
			
			int predicateInt = dico.get(predicate);
			int objectInt = dico.get(object);
			
			ConditionInteger condition = new ConditionInteger(c, predicateInt, objectInt);
			conditionsInt.add(condition);
		}
		
		System.out.println(conditionsInt);
		
		QueryExecution queryExecutionObject = new QueryExecution();
		queryExecutionObject.setConditionsInt(conditionsInt);
		
		return queryExecutionObject;
	}
}
