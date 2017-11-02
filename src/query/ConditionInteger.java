package query;

public class ConditionInteger {
	Condition conditionOrigine;
	int predicate;
	int object;
	
	public ConditionInteger(Condition origine, int predicate, int object){
		this.predicate = predicate;
		this.object = object;
		this.conditionOrigine = origine;
	}

	public int getPredicate() {
		return predicate;
	}

	public void setPredicate(int predicate) {
		this.predicate = predicate;
	}

	public int getObject() {
		return object;
	}

	public void setObject(int object) {
		this.object = object;
	}
	
	@Override
	public String toString() {
		return "predicate : " + this.predicate + ", object : " + this.object; 
	}
	
}
