import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

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
	public static void main(String args[]) throws IOException {

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
		//System.out.println(dictionnaire.toString());
		
		HashMap<Integer,HashMap<Integer,HashSet<Integer>>> IndexPOS = getIndex(dictionnaire.getTripletList(),"POS");
		//System.out.println(IndexPOS);
		
		/**
		 * Le code commenté en dessous permet l'affichage du dictionnaire et de l'index dans un fichier log.txt
		 */
		/*
		BufferedWriter out = null;
		try  
		{
		    FileWriter fstream = new FileWriter("log.txt", false); //true tells to append data.
		    out = new BufferedWriter(fstream);
		    out.write(dictionnaire.toString() + "\n\n\n" + IndexPOS.toString());
		}
		catch (IOException e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
		finally
		{
		    if(out != null) {
		        out.close();
		    }
		}
		*/
		
		System.out.println("Import time : " + (System.currentTimeMillis() - start));
		Long parsingTime = System.currentTimeMillis();;
		/**
		 * Creation d'une requete
		 */
		int choix = 1;
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
		}
		
		//System.out.println(q);
		Query query = QueryFactory.create(q);
		System.out.println("parsing time : " + (System.currentTimeMillis() - parsingTime));
		System.out.println(query);
			
	}

	
	public static HashMap<Integer,HashMap<Integer,HashSet<Integer>>> getIndex(ArrayList<Triplet> triplets, String type){
		HashMap<Integer,HashMap<Integer,HashSet<Integer>>> indexPOS = new HashMap<Integer,HashMap<Integer,HashSet<Integer>>>();
		boolean trv=false;
		int T1,T2;
		
		for(Triplet t : triplets) {			
			if(type.equals("POS")) {
				T1=t.getPredicate();
				T2=t.getObject();				
			}
			else {
				T1=t.getObject();
				T2=t.getPredicate();
			}
			
			HashMap<Integer,HashSet<Integer>>  indexOS = new HashMap<Integer,HashSet<Integer>>();
			HashSet<Integer> indexSub= new HashSet<Integer>(); ;
			
			if(!indexPOS.containsKey(T1)) { 	// si l'index ne contient pas le premier élement (predicat/objet) alors on lui ajoute 		
				indexSub.add(t.getSubject());
				indexOS.put(T2, indexSub);
				indexPOS.put(T1, indexOS);
				
			}
			else { 
				for(Map.Entry<Integer,HashSet<Integer>> val : indexPOS.get(T1).entrySet()) {
					if(val.getKey().equals(T2)) {
						val.getValue().add(t.getSubject());
						trv = true;
						break;
					}
				}
				if(!trv) {
					indexSub.add(t.getSubject());
					indexPOS.get(T1).put(T2, indexSub);
					
				}
				trv=false;
			}
				
		}	
		return indexPOS;
	}
	

}