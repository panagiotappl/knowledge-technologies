package org.example;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.Query;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.QueryResult;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.resultio.sparqlxml.SPARQLResultsXMLWriter;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.openrdf.sail.memory.MemoryStore;

public class TestWithRDFS {

	static final String inputDataURL  = "file:///Users/gstam/Documents/eclipse-workspace/sesame-tutorial/culture_data.rdf";
	static final String inputSchemaURL = "file:///Users/gstam/Documents/eclipse-workspace/sesame-tutorial/culture.rdfs";
	
	
	public static void main(String[] args) {

		try {
			//Create a new main memory repository 
			MemoryStore store = new MemoryStore();
			ForwardChainingRDFSInferencer inferencer = new ForwardChainingRDFSInferencer(store);
			
			Repository repo = new SailRepository(store);
			//Repository repo = new SailRepository(inferencer);
			repo.initialize();

			//Store files (one local and one available through http)	
			URL data = new URL(inputDataURL);
			URL schema = new URL(inputSchemaURL);
			String fileBaseURI = "http://zoi.gr/culture#";
			RDFFormat fileRDFFormat = RDFFormat.N3;
			RepositoryConnection con = repo.getConnection();
			
			//store the files
			con.add(data, null, fileRDFFormat);
			con.add(schema, fileBaseURI, fileRDFFormat);
	
			System.out.println("Repository loaded");

			//Sesame supports:
			//Tuple queries: queries that produce sets of value tuples.
			//Graph queries: queries that produce RDF graphs
			//Boolean queries: true/false queries			
			
			//Evaluate a SPARQL tuple query
			String queryString1 = 
				"PREFIX ns:   <http://zoi.gr/culture#> \n" +
				" PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
				" SELECT ?x \n" +
				" WHERE { ?x  rdfs:subClassOf*  ns:Artist  }";
					
			String queryString2 = "prefix ns:   <http://zoi.gr/culture#>" +
				" PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			 	" SELECT ?x " +
			 	" WHERE { ?x  rdf:type  ns:Artist }" ;
				        
			String queryString3 = "prefix ns:   <http://zoi.gr/culture#>" +
							" prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
							" prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
							"SELECT ?x WHERE { ?x  rdf:type  rdfs:Class }";
				        
			String queryString4 = "prefix ns:   <http://zoi.gr/culture#>" +
							" prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
							" SELECT ?x " +
							" WHERE { ?x  rdf:type  rdf:Property }";
				        

			String queryString5 = "prefix ns:   <http://zoi.gr/culture#>" +
				        " SELECT ?x ?y " +
				        " WHERE { ?x  ns:creates  ?y } ";
				        
			String queryString6 = "prefix ns:   <http://zoi.gr/culture#>" +
				        " SELECT ?x ?y " +
				        " WHERE { ?x  ns:creates  ?y . " +
				        " ?y ns:exhibited <http://www.louvre.fr/>} ";
				                
			String queryString7 = "prefix ns:   <http://zoi.gr/culture#>" +
				        		" prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
				        " SELECT ?x WHERE { ?x  ns:created  ?y " +
				        " FILTER (?y < 1990) . " +
				        " ?x rdf:type ns:Artifact} ";
				        
			String queryString8 = "prefix ns:   <http://zoi.gr/culture#>" +
						" prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
						" SELECT ?x ?y " +
						" WHERE { ?x  ns:created  ?y " +
						" FILTER (?y < 1990) . " +
						" ?x rdf:type ns:Sculpture} ";
					
				      
			String queryString = queryString2;
			TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = tupleQuery.evaluate();
			System.out.println("Query:\n" + queryString);
					
			try {
				//iterate the result set
				while (result.hasNext()) {
					BindingSet bindingSet = result.next();
					System.out.println(bindingSet.toString());
				}

				
			} finally {
						result.close();
			}
				
		}
		catch (Exception e) {
			// handle exception
			e.printStackTrace();
		}
	}
}
