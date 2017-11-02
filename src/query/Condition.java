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
	
	
	
	public Variable getSubjet() {
		return subjet;
	}



	public void setSubjet(Variable subjet) {
		this.subjet = subjet;
	}



	public Uri getPredicate() {
		return predicate;
	}



	public void setPredicate(Uri predicate) {
		this.predicate = predicate;
	}



	public Value getObject() {
		return object;
	}



	public void setObject(Value object) {
		this.object = object;
	}



	public String toString() {
		return this.subjet + " " + this.predicate + " " + this.object;
	}
}
