
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openrdf.model.Statement;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.RDFHandlerBase;

import Dictionnary.Dictionnaire;
import query.Query;
import query.QueryExecution;
import query.QueryExecutionFactory;
import query.QueryFactory;
import query.ResultSet;

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
		/**
		 * Check si un nom de fichié a été ajouté en argument
		 */

		String fichierStr;
		String fichierQuery;
		if(args.length == 2 ) {
			fichierStr = args[0];
			if(!fichierStr.contains(".owl")) {
				System.err.println("Seuls les fichiers owl sont acceptés.\n"
						+ "Voir la section Convertion de fichier dans Readme.md pour convertir vos fichiers en owl");
				System.exit(0);
			}
			fichierQuery=args[1];
		}
		else {
			System.out.println("Pour utiliser un autre fichier, utilisez la commande\n\t "
					+ "\"java -jar RDFProject.jar repertoire/fichier.owl\"  repertoire/requete.txt ( l'extention n'importe pas)");
			fichierStr = "." + File.separator + "datas" + File.separator + "500K.owl";
			fichierQuery="." + File.separator + "queries" + File.separator + "Q1Test.queryset";
		}
		Reader reader = new FileReader(fichierStr);

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
		
		/**
		 * Le code commenté en dessous permet l'affichage du dictionnaire et de l'index dans un fichier log.txt
		 */
		/*
		BufferedWriter out = null;
		try  
		{
		    FileWriter fstream = new FileWriter("log.txt", false); //true tells to append data.
		    out = new BufferedWriter(fstream);
		    out.write(dictionnaire.toString() + "\n\n\n" + dictionnaire.getIndexPOS().toString());
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
		
		/*
		 * Lecture des requetes
		 */

		Scanner scanner = new Scanner( new File(fichierQuery) );
		String text = scanner.useDelimiter("\\A").next();
		scanner.close();
		
		Pattern pattern = Pattern.compile("(SELECT.*?})",Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(text);
		int cpt=1;
		Long startLoop = System.currentTimeMillis();
		
		if(!matcher.find()) {
			System.err.println("Aucune requete reconnue");
			System.exit(1);
		}
		while (matcher.find()) {
			System.out.println("\nQuery "+cpt+" :");
			start = System.currentTimeMillis();
			
							
			Query query = QueryFactory.create(matcher.group(1));
			System.out.println("parsing time : " + (System.currentTimeMillis() - start));
			
			start = System.currentTimeMillis();

			QueryExecution qexec = QueryExecutionFactory.create(query, dictionnaire);

			System.out.println("Query pre-processing time : " + (System.currentTimeMillis() - start));
		    
			/**
			 * Partie exécution
			 */		
			
			start = System.currentTimeMillis();
			ResultSet rs = qexec.execSelect();		
			System.out.println("Query time : " + (System.currentTimeMillis() - start));
			
			start = System.currentTimeMillis();
			
			String fileResultName="./results/"+startLoop+"/Query"+cpt+".csv";
			rs.toCSV(fileResultName);
			System.out.println("Résults on : "+fileResultName);
			System.out.println("Writing on file time : " + (System.currentTimeMillis() - start));
			cpt++;
		}
		
		System.out.println("\n\nLoop time : " + (System.currentTimeMillis() - startLoop));
		
		
		
	}
}