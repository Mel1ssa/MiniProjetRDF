package Dictionnary;
public class Triplet {
		private int predicate;
		private int subject;
		private int object;
		
		public Triplet(int s, int p, int o) {
			this.predicate = p;
			this.subject = s;
			this.object = o;
		}
		
		public String toString(){
			return "[" + subject + " , " + predicate + " , " + object + "]";
		}
		
		public String toStringPOS(){
			return "[" + predicate + " , " + object + " , " + subject + "]";
		}
		
		public String toStringOPS(){
			return "[" + object + " , " + predicate + " , " + subject + "]";
		}


		public int getPredicate() {
			return predicate;
		}


		public int getSubject() {
			return subject;
		}


		public int getObject() {
			return object;
		}
	}