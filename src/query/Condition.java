package query;

import query.condition_element.*;

public class Condition {
	Variable subjet;
	Uri predicate;
	Value object;
	
	public Condition(Variable s, Uri p, Value o) {
		this.subjet = s;
		this.predicate = p;
		this.object = o;
	}
	
	public String toString() {
		return this.subjet + " " + this.predicate + " " + this.object;
	}
}
