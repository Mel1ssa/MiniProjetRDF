import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
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

	private static class RDFListener extends RDFHandlerBase {
		@Override
		public void handleStatement(Statement st) {

			Dictionnaire dictionnaire = Dictionnaire.getInstance();
			dictionnaire.add(st.getSubject().toString(), st.getPredicate().toString(), st.getObject().toString());

		}
	};

	public static void main(String args[]) throws FileNotFoundException {

		Reader reader = new FileReader("." + File.separator + "datas" + File.separator + "University0_0.owl");

		org.openrdf.rio.RDFParser rdfParser = Rio.createParser(RDFFormat.RDFXML);
		rdfParser.setRDFHandler(new RDFListener());
		Long start = null;
		try {
			start = System.currentTimeMillis();
			rdfParser.parse(reader, "");
		} catch (Exception e) {

		}

		try {
			reader.close();
		} catch (IOException e) {
		}
		
		Dictionnaire dictionnaire = Dictionnaire.getInstance();
		System.out.println(dictionnaire.toString());
		
		System.out.println("Import time : " + (System.currentTimeMillis() - start));
	}

}