
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
		Date d = new Date();
		String dateStrDetail= d.getDate()+"-"+d.getMonth()+"-"+d.getYear()+"-"+d.getHours()+"."+d.getMinutes(); 
		
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
					+ "\"java -jar RDFProject.jar repertoire/fichier.owl\"  repertoire/requete.txt ( l'extension n'importe pas)");
			fichierStr = "." + File.separator + "datas" + File.separator + "500K.owl";
			fichierQuery="." + File.separator + "queries" + File.separator + "Q_1_eligibleregion.queryset";
		}
		
		
		ArrayList<String> ExecutionTimeList = new ArrayList<String>();
		Long time;
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
		time=System.currentTimeMillis() - start;
		System.out.println("Import time : " + time);
		ExecutionTimeList.add("Import time : " + time);
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
			start = System.currentTimeMillis();
							
			Query query = QueryFactory.create(matcher.group(1));
			time=System.currentTimeMillis() - start;
			System.out.println   ("Query "+cpt+" -Parsing time : " + time);
			ExecutionTimeList.add("Query "+cpt+" -Parsing time : " + time);
			
			start = System.currentTimeMillis();

			QueryExecution qexec = QueryExecutionFactory.create(query, dictionnaire);			
			time=System.currentTimeMillis() - start;
			System.out.println   ("Query "+cpt+" -Pre-processing time : " + time);
			ExecutionTimeList.add("Query "+cpt+" -Pre-processing time : " + time);
			
			/**
			 * Partie exécution
			 */		
			
			start = System.currentTimeMillis();
			
			ResultSet rs = qexec.execSelect();	
			time=System.currentTimeMillis() - start;
			System.out.println("Query "+cpt+" -Exec query time : " + time);
			ExecutionTimeList.add("Query "+cpt+" -Exec query time : " + time);
			
			start = System.currentTimeMillis();
			
			String fileResultName="." + File.separator +"results"+ File.separator + dateStrDetail + File.separator +"Query" + cpt + ".csv";
			rs.toCSV(fileResultName);
			System.out.println("Résults on : "+fileResultName);
			time=System.currentTimeMillis() - start;
			System.out.println   ("Query "+cpt+" -Writing on file time : " + time +"\n");
			ExecutionTimeList.add("Query "+cpt+" -Writing on file time : " + time +"\n");
			cpt++;
		}
		time = System.currentTimeMillis() - startLoop;
		System.out.println("\n\nLoop time : " +time);
		ExecutionTimeList.add("\n\nLoop time : " +time);
		
		String fichierExecTrace= "." + File.separator + "results" + File.separator + dateStrDetail + File.separator +"trace" + File.separator + dateStrDetail+".csv" ;
		System.out.println("Les traces d'executions se trouvent dans le fichier : "+fichierExecTrace);
		writetoCSV(ExecutionTimeList,fichierExecTrace);
	}
	
	public static void writetoCSV(ArrayList<String> execList,String filename) throws IOException {
		
		File file = new File(filename);
		file.getParentFile().mkdirs();
		FileWriter writer = new FileWriter(filename);
	    String collect = execList.stream().collect(Collectors.joining("\n"));

	    writer.write(collect);
	    writer.close();
		
		
	}
}