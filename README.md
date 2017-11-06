# Contenu du projet #
	- Le code source du projet
	- L'achive rdf2rdf-1.0.1-2.3.1.jar pour la conversion de fichiers RDF
	- L'archive RDFProject.jar (executable)
# Execution du jar #

	java -jar RDFProject.jar repertoire/fichier.owl repertoir/fichier.queryset

## Remarques: ##
	- Le programme peut s'exécuter sans aucuns arguments : le fichier owl par défaut est datas/University0_0owl et queries/Q1Test.queryset pour le fichier de requetes
	- Les fichiers doivent porter l'extension owl (voir conversion de fichires ci dessous)
	- L'extension du fichier de requetes n'importe pas (tant que c'est du text)

# Convertion de fichier #
Afin de convertir vos fichier nt,ttl,n3trig... en owl il suffit d'executer la commande suivante :

	java -jar rdf2rdf-1.0.1-2.3.1.jar repertoire/fichier.nt repertoire/fichier.owl
	
## Important ##
Certains fichiers rdf sont en réalité des fichier nt (c'est le cas des fichiers 100K et 500K de ce projet)
	Pour les convertir, il faudra renommer l'extention en nt puis utiliser la commande au dessus.

