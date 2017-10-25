package query;

import java.util.HashMap;

public class Query {
	HashMap<String, String> prefixes;
	public Query(String query){
		parse(query);
		System.out.println("Liste Prefixes : " + prefixes);
	}
	
	private void parse(String query){
		String[] selectSplit = query.split("SELECT|select");
		String prefixeString = selectSplit[0];
		if(!prefixeString.equals("")){
			this.prefixes = extractPrefix(query);
		}
		
		String selectString = selectSplit[1];
		
		String[] whereSplit = selectString.split("WHERE|where");
		String selectVariables = whereSplit[0];
		String conditionString = whereSplit[1];
		
		System.out.println(selectVariables);
			
		conditionString = conditionString.replace("{", "");
		conditionString = conditionString.replace("}", "");
		
		System.out.println(conditionString);
		
	}
	
	private HashMap<String, String> extractPrefix(String prefixeString){
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
}
