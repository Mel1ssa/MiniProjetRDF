package query.condition_element;

public class Constant extends Value{

	public Constant(String n) {
		super(n);
	}
	
	public String getQueryName() {
		return "\"" + this.name + "\"";
	}
	
	@Override
	public String toString() {
		return "\"" + this.name + "\"";
	}
}
