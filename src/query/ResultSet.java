package query;

import java.util.ArrayList;

public class ResultSet {
	ArrayList<String> resultats;
	
	public ResultSet(ArrayList<String> r) {
		resultats = r;
	}
	
	public String toString() {
		String str = resultats.size() + " résultats obtenu :";
		for(String strResult : resultats) {
			str += "\n" + strResult;
		}
		return str;
	}
}
