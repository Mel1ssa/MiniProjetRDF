package query.condition_element;

public class Variable extends Value{
	boolean isShow;
	
	public Variable(String n, boolean show) {
		super(n);
		this.isShow = show;
	}

	public String toString() {
		String showed = this.isShow?"showed":"not showed" ; 
		return this.name + "(" + showed + ")";
	}
}
