package Dictionnary;
public class Triplet {
		public int predicate;
		public int subject;
		public int object;
		
		
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