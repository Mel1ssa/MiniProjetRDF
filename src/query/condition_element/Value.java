package query.condition_element;

public abstract class Value {
	String name;
	
	public Value(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
