package org.example;

import java.net.URL;

import org.openrdf.OpenRDFException;
import org.openrdf.query.GraphQuery;
import org.openrdf.query.QueryLanguage;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.turtle.TurtleWriter;
import org.openrdf.sail.memory.MemoryStore;

public class TestConstruct {

	static final String inputURL = "file:///home/giota/Documents/kallikratis.n3";
	
	public static void main(String[] args) {

		try {
			//Create a new main memory repository 
			MemoryStore store = new MemoryStore();
			Repository repo = new SailRepository(store);
			repo.initialize();

			//Store file
			try {
				URL url = new URL(inputURL);
				RDFFormat fileRDFFormat = RDFFormat.N3;
				
				RepositoryConnection con = repo.getConnection();
				try {
					//store the file
					con.add(url, null, fileRDFFormat);
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

			try {
				RepositoryConnection con = repo.getConnection();
					try {

						String queryString = 
							" PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
							" PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
							" PREFIX ns: <http://example/ns#> " +
							" CONSTRUCT {?x ns:legetai ?y } " +
							" WHERE {?x foaf:name ?y . }";
						
						TurtleWriter turtleWriter = new TurtleWriter(System.out);
						//RDFXMLWriter rdfxmlWriter = new RDFXMLWriter(System.out);	
						GraphQuery gQuery = con.prepareGraphQuery(QueryLanguage.SPARQL, queryString);						
						gQuery.evaluate(turtleWriter);
	
						//GraphQueryResult graphResult = gQuery.evaluate();
						//while (graphResult.hasNext()) {
						//	Statement st = graphResult.next();
						//	System.out.println(st.toString());
						//}
					}
					finally {
						con.close();
					}
				}
				catch (Exception e) {
					//handle exception
					e.printStackTrace();
			}
		} catch (RepositoryException e) {
			// handle exception
			e.printStackTrace();
		}
	}
}
