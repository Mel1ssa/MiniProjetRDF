package query;

import java.util.ArrayList;
import java.util.HashMap;

import query.condition_element.Variable;

public class Query {
	HashMap<String, String> prefixes;
	ArrayList<Variable> variables;
	ArrayList<Condition> conditions;
	
	public Query() {
		this.prefixes = new HashMap<>();
		this.variables = new ArrayList<>();
		this.conditions = new ArrayList<>();
	}
	
	public Query(HashMap<String, String> p, ArrayList<Variable> v, ArrayList<Condition> c) {
		this.prefixes = p;
		this.variables = v;
		this.conditions = c;
	}

	public HashMap<String, String> getPrefixes() {
		return prefixes;
	}

	public void setPrefixes(HashMap<String, String> prefixes) {
		this.prefixes = prefixes;
	}

	public ArrayList<Variable> getVariables() {
		return variables;
	}

	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}

	public ArrayList<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(ArrayList<Condition> conditions) {
		this.conditions = conditions;
	}

	@Override
	public String toString() {
		
		String str = "Liste des prefixes :\n" + this.prefixes 
				+ "\nListe des variables :\n" + this.variables 
				+ "\nListe des conditions :\n" + this.conditions;
		
		return str;
	}
	
}
