package org.example;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
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

public class TestNamedGraphs {

	static final String inputURL1  = "file:///Users/gstam/Documents/eclipse-workspace/sesame-tutorial/aliceFoaf.rdf";
	static final String inputURL2  = "file:///Users/gstam/Documents/eclipse-workspace/sesame-tutorial/bobFoaf.rdf";
	
	//static final String inputURL3 = "file:///Users/George/Desktop/aliceFoaf.rdf";
	//static final String inputURL4 = "file:///Users/George/Desktop/bobFoaf.rdf";
	
	public static void main(String[] args) {

		try {
			//Create a new main memory repository 
			MemoryStore store = new MemoryStore();
			Repository repo = new SailRepository(store);
			repo.initialize();

			//Store file
			try {
				URL file1 = new URL(inputURL1);
				URL file2 = new URL(inputURL2);
				RDFFormat fileRDFFormat = RDFFormat.N3; 
				
				ValueFactory f = repo.getValueFactory();
				URI context1 = f.createURI("http://example.org/foaf/aliceFoaf");
				URI context2 = f.createURI("http://example.org/foaf/bobFoaf");
				
				RepositoryConnection con = repo.getConnection();
				try {
					//load files
					con.add(file1, null, fileRDFFormat, context1);
					con.add(file2, null, fileRDFFormat, context2);
				}
				finally {
					con.close();
				}
			}
			catch (OpenRDFException e) {
				e.printStackTrace();
			}
			catch (java.io.IOException e) {
				// handle io exception
				e.printStackTrace();
			}


			//Sesame supports:
			//Tuple queries: queries that produce sets of value tuples.
			//Graph queries: queries that produce RDF graphs
			//Boolean queries: true/false queries			
			
			//Evaluate a SPARQL tuple query
			try {
				RepositoryConnection con = repo.getConnection();
				try {
					//Give me the IRIs of all the graphs where Bob has 
					//a nickname and the value of that nickname.
			        String queryString1 = 
			        	" PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
			        	" SELECT ?src ?bobNick " +
			        	//" FROM NAMED <http://example.org/foaf/aliceFoaf> " +
			        	//" FROM NAMED <http://example.org/foaf/bobFoaf> " +
			        	" WHERE { " +
			        	" GRAPH ?src " +
			        	"{ ?x foaf:mbox <mailto:bob@work.example> . " +
			        	" ?x foaf:nick ?bobNick " +
			        	"} " +
			        	"}";
			        
			        
			        String queryString2 = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
					"SELECT ?x ?g " +
					" FROM NAMED  <http://example.org/foaf/aliceFoaf> " +
					//" FROM NAMED <http://example.org/foaf/bobFoaf> " +
					"WHERE {" +
					"	GRAPH ?g {" +
					"    ?me foaf:name ?x }" +
					"  }";
			        
			       //Use Alice's FOAF file to find the
			       //personal profile document of everybody Alice
			       //knows. Use that document to find this 
			       //person's e-mail and nickname. 
			        String queryString3 = " PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
			        		" PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
			        		" SELECT ?mbox ?nick ?ppd " +
			        		" FROM NAMED <http://example.org/foaf/aliceFoaf> " +
			        		" FROM NAMED <http://example.org/foaf/bobFoaf> " +
			        		" WHERE { " +
			        		" GRAPH <http://example.org/foaf/aliceFoaf> { " +
			        		"?alice foaf:mbox <mailto:alice@work.example> ; " +
			        		" foaf:knows ?whom . " +
			        		" ?whom foaf:mbox ?mbox;" +
			        		" rdfs:seeAlso ?ppd. " +
			        		"?ppd a foaf:PersonalProfileDocument . " +
			        		"}  " +
			        		" GRAPH ?ppd { " +
			        		" ?w foaf:mbox ?mbox; " +
			        		" foaf:nick ?nick " +
			        		"} " +
			        		"}";

					
			        String queryString = queryString3;
					TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
					TupleQueryResult result = tupleQuery.evaluate();
					System.out.println("Query:\n" + queryString);
					try {
						//iterate the result set
						//result = tupleQuery.evaluate();
						while (result.hasNext()) {
							BindingSet bindingSet = result.next();
							System.out.println(bindingSet.toString());
						}

						/*//iterate #2
						result = tupleQuery.evaluate();
						List<String> bindingNames = result.getBindingNames();
						while (result.hasNext()) {
							BindingSet bindingSet = result.next();
							Value firstValue = bindingSet.getValue(bindingNames.get(0));
							Value secondValue = bindingSet.getValue(bindingNames.get(1));
							Value thirdValue = bindingSet.getValue(bindingNames.get(2));

							System.out.println("?x=" + firstValue + ", ?p=" + secondValue + ", ?y=" + thirdValue);
						}*/

						/*//iterate #3
						SPARQLResultsXMLWriter sparqlWriter = new SPARQLResultsXMLWriter(System.out);
						tupleQuery.evaluate(sparqlWriter);*/
					}
					finally {
						result.close();
					}
				}
				catch (Exception e) {
					//handle exception
					e.printStackTrace();
				} finally {
					con.close();
				}
			}
			catch (OpenRDFException e) {
				// handle exception
				e.printStackTrace();
			}

			/*//Evaluate a query that produces an RDF graph
			try {
				RepositoryConnection con = repo.getConnection();
				try {
					GraphQueryResult graphResult = con.prepareGraphQuery(QueryLanguage.SPARQL, 
							"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
							"CONSTRUCT {_:v rdf:type rdf:Statement; rdf:Subject ?x ; rdf:Predicate ?p; rdf:Object ?y} " +
							"WHERE {?x ?p ?y . }").evaluate();
				
					while (graphResult.hasNext()) {
						Statement st = graphResult.next();
						System.out.println(st.toString());
					}
				}
				finally {
					con.close();
				}
			}
			catch (OpenRDFException e) {
				// handle exception
				e.printStackTrace();
			}*/
		} catch (RepositoryException e) {
			// handle exception
			e.printStackTrace();
		}
	}
}
