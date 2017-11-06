package query;

import java.util.ArrayList;
import java.util.HashMap;

import query.condition_element.*;

public class QueryFactory {

	public static Query create(String queryString) {
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
		conditions = extractConditions(prefixes, variables ,conditionString);

		Query queryObject = new Query(prefixes, variables, conditions);
		
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
	
	private static ArrayList<Condition> extractConditions(HashMap<String, String> prefixes, ArrayList<Variable> variables, String conditionString){
		
		ArrayList<Condition> conditions = new ArrayList<>();
		
		conditionString = conditionString.replace("{", "");
		conditionString = conditionString.replace("}", "");
		
		String[] tabConditions = conditionString.split(" \\. ");

		for(String condition : tabConditions) {
			
			if(condition.equals(" ") || condition.equals(""))
				break; // cas ou il n ya pas de condition après le point
			condition = condition.trim();
			if(!condition.equals("")) {
				String[] elements = condition.split(" ");
				if(elements.length != 3) {
					System.err.println("Erreur : la condition n'a pas 3 elements");
					return null;
				}
				
				Value sujet = extractValue(prefixes, variables, elements[0]);
				Value predicat = extractValue(prefixes, variables, elements[1]);
				Value objet = extractValue(prefixes, variables, elements[2]);
				
							
				
				Condition conditionObject = new Condition( (Variable) sujet, (Uri) predicat, objet);
				conditions.add(conditionObject);
			}
}
		
		return conditions;
	}
	
	private static Value extractValue(HashMap<String,String> prefixes, ArrayList<Variable> variables, String str) {
		String strTrim = str.trim();
		String returnStr;
		switch(strTrim.charAt(0)) {
		case '"':
			// Cas Constante
			returnStr = str.substring(1, str.length() - 1); 
			return new Constant(returnStr);
		case '?':
			// Cas variable
			for(Variable var : variables) {
				if(var.getName().equals(str)) {
					return var;
				}
			}
			Variable  result = new Variable(str, false);
			variables.add(result);
			return result;
		case '<':
			//cas Uri  sans prefix
			String[] stringTabU = str.split(">:", 2);
			if(stringTabU.length==1) {
				String constUri = stringTabU[0].trim().replace("<", "").replace(">", "");
				return new Uri(constUri, "");
			}
			String prefixeU = stringTabU[0].trim().replace("<", "").replace(">", "");
			return new Uri(prefixeU, stringTabU[1].trim());
		
		default:
			// Cas Uri avec prefix			
			String[] stringTab = str.split(":", 2);
			String prefixeUri = stringTab[0].trim();
			
			String prefixe = null;
			if(prefixes.containsKey(prefixeUri)) {
				prefixe = prefixes.get(prefixeUri);
			}
			else {
				System.err.println("Erreur : le préfixe n'existe pas : " + prefixeUri);
				return null;
			}
			return new Uri(prefixe, stringTab[1].trim());
		}
		
	}
}
