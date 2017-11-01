package query.condition_element;

public class Uri extends Variable{
	String prefix;
	
	public Uri(String pref, String n) {
		super(n,false);
		this.prefix = pref;
	}
	
	@Override
	public String toString() {
		return this.prefix + this.name; 
	}
	
	
	/*
	// Ce constructeur ne marche pas car il faut que super soit en premier, il n'est donc pas possible de faire du traitement sur n avant le super...
	public Uri(String n) {
		super(separatePrefix(n));
	}
	
	private static Uri separatePrefix(String n) {
		String[] stringTab = n.split(":");
		return new Uri(stringTab[0], stringTab[1]);
	}
	*/
}
