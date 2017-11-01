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

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QDecoderStream;

import query.Query;
import query.QueryFactory;

public final class RDFRawParser {

	private static class RDFListener extends RDFHandlerBase {
		@Override
		public void handleStatement(Statement st) {

			Dictionnaire dictionnaire = Dictionnaire.getInstance();
			dictionnaire.add(st.getSubject().toString(), st.getPredicate().toString(), st.getObject().toString());

		}
	};

	//main !
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
		
		/**
		 * Creation d'une requete
		 */
		int choix = 0;
		String q = null;
		switch(choix){
		case 0:
			q = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
					 + " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					 + " PREFIX owl: <http://www.w3.org/2002/07/owl#>"
					 + " PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>"
					 + " SELECT ?x"
					 + " WHERE {?x rdf:type ub:Subj18Student .  ?x rdf:type ub:GraduateStudent . ?x rdf:type ub:ResearchAssistant }";
			break;
		case 1:
			q = " PREFIX rdf:\n" 
					 + " <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" 
					 + " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					 + " PREFIX owl: <http://www.w3.org/2002/07/owl#>"
					 + " PREFIX ub: <http://swat.cse.lehigh.edu/onto/univ-bench.owl#>"
					 + " select ?x"
					 + " WHERE {?x rdf:type ub:Subj18Student .  ?x rdf:type ub:GraduateStudent . ?x rdf:type ub:TeachingAssistant }";
			break;
		case 2:
			q = "SELECT ?x"
			  + " WHERE {?x rdf:type ub:Subj18Student .  ?x rdf:type ub:GraduateStudent . ?x rdf:type ub:ResearchAssistant }";
			break;
		case 3:
			q = "SELECT ?x"
			  + "WHERE {?x rdf:type ?y . ?y rdf:isAge \"18\" }";
		}
		
		//System.out.println(q);
		Query query = QueryFactory.create(q);
		System.out.println(query);
			
	}

}