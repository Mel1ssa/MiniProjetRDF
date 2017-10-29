package query;

import java.util.ArrayList;
import java.util.HashMap;

import query.condition_element.*;

public class QueryFactory {

	public static Query create(String queryString) {
		System.out.println("\n------------ Parsing de la requête -----------------");
		HashMap<String, String> prefixes = new HashMap<>();
		ArrayList<Variable> variables = new ArrayList<>();
		ArrayList<Condition> conditions = new ArrayList<>();
		
		
		String[] selectSplit = queryString.split("SELECT|select");
		String prefixeString = selectSplit[0];
		if(!prefixeString.equals("")){
			prefixes = extractPrefix(prefixeString);
		}
		
		String selectString = selectSplit[1];
		
		String[] whereSplit = selectString.split("WHERE|where");
		String selectVariables = whereSplit[0];
		String conditionString = whereSplit[1];
		
		variables = extractSelectVar(selectVariables);
		
			
		Query queryObject = new Query();
		conditions = extractConditions(variables ,conditionString);
				
		System.out.println("Liste des prefixes :\n" + prefixes);
		System.out.println("\nListe des variables :\n" + variables);
		//System.out.println("\nListe des conditions :\n" + conditions);
	
		return queryObject;
		
	}
	
	private static HashMap<String, String> extractPrefix(String prefixeString){
		HashMap<String, String> result = new HashMap<>();
		String[] listPrefixes = prefixeString.split("PREFIX|prefix");
		for(String str : listPrefixes){
			if(!str.trim().equals("")){
				String [] tabPrefix = str.split(":",2);
				String uri = tabPrefix[1].trim();
				String prefix = tabPrefix[0].trim();
				
				uri = uri.replace("<", "");
				uri = uri.replace(">", "");
				
				result.put(prefix, uri);
			}
		}
		return result;
	}
	
	private static ArrayList<Variable> extractSelectVar(String str){
		ArrayList<Variable> variables = new ArrayList<>();
		String[] variablesString = str.split(" ");
		if(variablesString.length == 0) {
			System.err.println("Erreur, aucune variables à afficher ...");
			return null;
		}
			
		for(String strVariable : variablesString) {
			if(!strVariable.equals("")) {
				Variable v = new Variable(strVariable.trim(), true);
				variables.add(v);	
			}
		}
		if(variables.size() == 0) {
			System.err.println("Erreur, aucune variables à afficher ...");
			return null;
		}
		return variables;
	}
	
	private static ArrayList<Condition> extractConditions(ArrayList<Variable> variables, String conditionString){
		
		ArrayList<Condition> conditions = new ArrayList<>();
		conditionString = conditionString.replace("{", "");
		conditionString = conditionString.replace("}", "");
		
		String[] tabConditions = conditionString.split("\\.");
		for(String condition : tabConditions) {
			condition = condition.trim();
			String[] elements = condition.split(" ");
			if(elements.length != 3) {
				System.err.println("Erreur : la condition n'a pas 3 elements");
				return null;
			}
			
			
			
			//Condition conditionObject = new Condition(elements[0].trim(), elements[1].trim(), elements[2].trim());
			//conditions.add(conditionObject);
		}
		
		return conditions;
	}
	
	private Value detectValue(String str) {
		
		
		return new Constant(str);
	}
}
