import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.openrdf.model.Statement;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.RDFHandlerBase;

public final class RDFRawParser {
	
	public static Map<Object, Object> SPOmapPub = new HashMap<Object, Object>();
	
	private static class RDFListener extends RDFHandlerBase {
		public HashMap<Object, Object> SPOmap = new HashMap<Object, Object>();
		int i=0;
		@Override
		public void handleStatement(Statement st) {
		
			SPOmapPub.put(i,st.getSubject().toString());
			i++;
			SPOmapPub.put(i, st.getPredicate().toString());
			i++;
			SPOmapPub.put(i, st.getObject().toString()); 
			i++;
			
		}
	};
	
	
	public static HashMap<Object, Object> getsortedMap() {
		Set<String> existing = new HashSet<>();
		SPOmapPub = SPOmapPub.entrySet()
			.stream() 
		    .filter(entry -> existing.add((String) entry.getValue()))
		    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		 
		return (HashMap<Object, Object>) SPOmapPub;
	}
	

	public static void main(String args[]) throws FileNotFoundException {

		Reader reader = new FileReader(
				".\\datas\\University0_0.owl");

		org.openrdf.rio.RDFParser rdfParser = Rio
				.createParser(RDFFormat.RDFXML);
		rdfParser.setRDFHandler(new RDFListener());
		
		try {
			rdfParser.parse(reader, "");
		} catch (Exception e) {

		}

		try {
			reader.close();
		} catch (IOException e) {
		}
		System.out.println(SPOmapPub);
		//System.out.println(getsortedMap());

		
	}

}