package query;

import java.util.ArrayList;

public class QueryExecution {
	ArrayList<ConditionInteger> conditionsInt;

	public ArrayList<ConditionInteger> getConditionsInt() {
		return conditionsInt;
	}

	public void setConditionsInt(ArrayList<ConditionInteger> conditionsInt) {
		this.conditionsInt = conditionsInt;
	}

	public ResultSet execSelect() {
		System.err.println("Opération non implémenté : execSelect");
		return new ResultSet();
	}
	
}
