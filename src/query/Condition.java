package query;

import query.condition_element.Value;

public class Condition {
	Value subjet;
	Value predicate;
	Value object;
	
	public Condition(Value s, Value p, Value o) {
		this.subjet = s;
		this.predicate = p;
		this.object = o;
	}
	
	public String toString() {
		return this.subjet + " " + this.predicate + " " + this.object;
	}
}
