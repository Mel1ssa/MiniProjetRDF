package query;

public class ConditionInteger {
	Condition conditionOrigine;
	int predicate;
	int object;
	int sizeRequest;
	

	public ConditionInteger(Condition origine, int predicate, int object, int size){
		this.predicate = predicate;
		this.object = object;
		this.conditionOrigine = origine;
		this.sizeRequest = size;
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
	
	public int getSizeRequest() {
		return sizeRequest;
	}
	
	@Override
	public String toString() {
		return "predicate : " + this.predicate + ", object : " + this.object + ", size :" + sizeRequest; 
	}
	
}
