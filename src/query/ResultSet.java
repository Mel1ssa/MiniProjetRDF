package query;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ResultSet {
	ArrayList<String> resultats;
	
	public ResultSet(ArrayList<String> r) {
		resultats = r;
	}
	
	public String toString() {
		
		List<String> subList = resultats.subList(0, resultats.size());
		Collections.sort(subList);
		
		StringBuilder str = new StringBuilder("Nb results :" + resultats.size());
		for(String strResult : subList) {
			str.append("\n");
			str.append(strResult);
		}
		return str.toString();
	}
	
	public void toCSV(String filename) throws IOException {
		if(resultats.size()==0)
			return;
		File file = new File(filename);
		file.getParentFile().mkdirs();
		FileWriter writer = new FileWriter(filename);
	    String collect = resultats.stream().collect(Collectors.joining("\n"));

	    writer.write(collect);
	    writer.close();
		
		
	}
}
