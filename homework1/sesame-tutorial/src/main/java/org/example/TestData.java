package org.example;

import java.net.URL;
import java.util.List;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.resultio.sparqlxml.SPARQLResultsXMLWriter;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.sail.memory.MemoryStore;

public class TestData {

    static final String inputData = "file:///home/giota/Documents/kallikratis.n3";
    static final String dataUrlString = "file:///home/giota/Documents/kallikratis.n3";

    public static void main(String[] args) {

	try {
	    // Create a new main memory repository
	    MemoryStore store = new MemoryStore();
	    Repository repo = new SailRepository(store);
	    repo.initialize();

	    // Store file
	    try {
		URL file = new URL(inputData);
		String fileBaseURI = "http://zoi.gr/culture#";
		RDFFormat fileRDFFormat = RDFFormat.N3;

		RepositoryConnection con = repo.getConnection();
		try {
		    // store the file
		    // con.add(file, fileBaseURI, fileRDFFormat);
		    // System.out.println("Repository loaded");

		    // store file from url
		    URL url = new URL(dataUrlString);
		    con.add(url, null, fileRDFFormat);
		} finally {
		    con.close();
		}
	    } catch (OpenRDFException e) {
		e.printStackTrace();
	    } catch (java.io.IOException e) {
		// handle io exception
		e.printStackTrace();
	    }

	    // Sesame supports:
	    // Tuple queries: queries that produce sets of value tuples.
	    // Graph queries: queries that produce RDF graphs
	    // Boolean queries: true/false queries

	    // Evaluate a SPARQL tuple query
	    try {
		RepositoryConnection con = repo.getConnection();
		try {
		    String queryString1 = "PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/>"
			    + " SELECT ?official_municipality_name ?population"
			    + " WHERE { ?municipality rdf:type gag:Δήμος . "
			    + "         ?municipality gag:έχει_επίσημο_όνομα ?official_municipality_name . "
			    + "         ?municipality gag:έχει_πληθυσμό ?population . }";

		    String queryString2 = "PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/>"
			    + " SELECT ?municipality ?official_municipality_name ?official_regional_unit_name ?official_municipality_unit_name"
			    + " WHERE { ?municipality rdf:type gag:Δήμος . "
			    + "         ?municipality gag:έχει_επίσημο_όνομα ?official_municipality_name . "
			    + "         ?municipality gag:ανήκει_σε ?regional_unit . "
			    + "         ?regional_unit gag:έχει_επίσημο_όνομα ?official_regional_unit_name . "
			    + "         ?municipality_unit gag:ανήκει_σε ?municipality . "
			    + "         ?municipality_unit gag:έχει_επίσημο_όνομα ?official_municipality_unit_name . }"
			    + " ORDER BY ?municipality";

		    String queryString3 = "PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/> "
			    + " SELECT ?official_municipality_name ?population"
			    + " WHERE { ?region rdf:type gag:Περιφέρεια . "
			    + "         ?region gag:έχει_επίσημο_όνομα \"ΠΕΡΙΦΕΡΕΙΑ ΚΡΗΤΗΣ\" . "
			    + "         ?regional_unit gag:ανήκει_σε ?region . "
			    + "         ?municipality gag:ανήκει_σε ?regional_unit . "
			    + "         ?municipality gag:έχει_πληθυσμό ?population . "
			    + "         FILTER(?population > 5000) "
			    + "         ?municipality gag:έχει_επίσημο_όνομα ?official_municipality_name .}";

		    String queryString4 = "PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/> "
			    + " SELECT ?official_municipality_name" + " WHERE { ?region rdf:type gag:Περιφέρεια . "
			    + "         ?region gag:έχει_επίσημο_όνομα \"ΠΕΡΙΦΕΡΕΙΑ ΚΡΗΤΗΣ\" . "
			    + "         ?regional_unit gag:ανήκει_σε ?region . "
			    + "         ?municipality gag:ανήκει_σε ?regional_unit . "
			    + "         FILTER NOT EXISTS { ?municipality gag:έχει_έδρα ?seat . } "
			    + "         ?municipality gag:έχει_επίσημο_όνομα ?official_municipality_name .}";

		    String queryString5 = "PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/> "
			    + " SELECT ?official_municipality_name ?official_unit_name"
			    + " WHERE { ?region rdf:type gag:Περιφέρεια . "
			    + "         ?region gag:έχει_επίσημο_όνομα \"ΠΕΡΙΦΕΡΕΙΑ ΚΡΗΤΗΣ\" . "
			    + "         ?regional_unit gag:ανήκει_σε ?region . "
			    + "         ?municipality gag:ανήκει_σε ?regional_unit . "
			    + "         ?municipality gag:έχει_επίσημο_όνομα ?official_municipality_name . "
			    + "         ?municipality gag:ανήκει_σε+ ?unit . "
			    + "         ?unit gag:έχει_επίσημο_όνομα ?official_unit_name . }";

		    String queryString6 = "PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/> "
			    + " SELECT ?official_region_name ?regional_unit_count ?official_regional_unit_name ?municipality_count"
			    + " WHERE { ?regional_unit gag:ανήκει_σε ?region ."
			    + "         ?region gag:έχει_επίσημο_όνομα ?official_region_name ."
			    + "         ?regional_unit gag:έχει_επίσημο_όνομα ?official_regional_unit_name ."
			    + "         {" + "           SELECT ?region (COUNT(?regional_unit) as ?regional_unit_count)"
			    + "           WHERE { ?regional_unit rdf:type gag:Περιφερειακή_Ενότητα . "
			    + "                   ?region rdf:type gag:Περιφέρεια . "
			    + "                   ?regional_unit gag:ανήκει_σε ?region . }"
			    + "           GROUP BY ?region " + "          } . " + "         {"
			    + "           SELECT ?regional_unit (COUNT(?municipality) as ?municipality_count)"
			    + "           WHERE { ?municipality rdf:type gag:Δήμος . "
			    + "                   ?regional_unit rdf:type gag:Περιφερειακή_Ενότητα . "
			    + "                   ?municipality gag:ανήκει_σε ?regional_unit . }"
			    + "           GROUP BY ?regional_unit " + "          } . }" + "ORDER BY ?region";

		    String queryString7 = "PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/> "
			    + " SELECT ?population ?sum_population ?parent_name"
			    + " WHERE { ?parent_unit gag:έχει_πληθυσμό ?population . "
			    + "         ?parent_unit gag:έχει_επίσημο_όνομα ?parent_name . " + "         {"
			    + "           SELECT ?parent_unit (SUM(?unit_population) as ?sum_population) "
			    + "           WHERE { ?unit gag:ανήκει_σε ?parent_unit . "
			    + "                   ?unit gag:έχει_πληθυσμό ?unit_population . "
			    + "           } GROUP BY ?parent_unit } . " + "}";

		    String queryString8 = "PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/>"
			    + "SELECT ?official_d_name " + "WHERE { ?d rdf:type gag:Αποκεντρωμένη_Διοίκηση ."
			    + "        ?d gag:έχει_επίσημο_όνομα ?official_d_name . "
			    + "        ?regional_unit1 rdf:type gag:Περιφερειακή_Ενότητα . "
			    + "        ?regional_unit1 gag:ανήκει_σε+ ?d . " 
			    + "        ?regional_unit2 rdf:type gag:Περιφερειακή_Ενότητα . "
			    + "        ?regional_unit2 gag:ανήκει_σε+ ?d . "
			    + "        ?regional_unit3 rdf:type gag:Περιφερειακή_Ενότητα . "
			    + "        ?regional_unit3 gag:ανήκει_σε+ ?d . " 
			    + "        FILTER(?regional_unit1 != ?regional_unit2) "
			    + "        FILTER(?regional_unit2 != ?regional_unit3) "
			    + "        FILTER(?regional_unit3 != ?regional_unit1) }"
			    + "GROUP BY ?official_d_name";

		    String queryString = queryString8;
		    TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		    TupleQueryResult result = tupleQuery.evaluate();
		    System.out.println("Query:\n" + queryString);
		    try {
			// iterate the result set
			// result = tupleQuery.evaluate();
			while (result.hasNext()) {
			    BindingSet bindingSet = result.next();
			    System.out.println(bindingSet.toString());

			    // Value valueOfX = bindingSet.getValue("x");
			    // Value valueOfY = bindingSet.getValue("y");
			    // System.out.println("?x=" + valueOfX + " ?y=" + valueOfY);

			}

			/*
			 * //iterate #2 //result = tupleQuery.evaluate(); List<String> bindingNames =
			 * result.getBindingNames(); while (result.hasNext()) { BindingSet bindingSet =
			 * result.next(); Value firstValue = bindingSet.getValue(bindingNames.get(0));
			 * Value secondValue = bindingSet.getValue(bindingNames.get(1)); Value
			 * thirdValue = bindingSet.getValue(bindingNames.get(2));
			 * 
			 * System.out.println("?x=" + firstValue + ", ?p=" + secondValue + ", ?y=" +
			 * thirdValue); }
			 */

			/*
			 * //iterate #3 SPARQLResultsXMLWriter sparqlWriter = new
			 * SPARQLResultsXMLWriter(System.out); tupleQuery.evaluate(sparqlWriter);
			 */
		    } finally {
			result.close();
		    }
		} catch (Exception e) {
		    // handle exception
		    e.printStackTrace();
		} finally {
		    con.close();
		}
	    } catch (OpenRDFException e) {
		// handle exception
		e.printStackTrace();
	    }

	    /*
	     * //Evaluate a query that produces an RDF graph try { RepositoryConnection con
	     * = repo.getConnection(); try { GraphQueryResult graphResult =
	     * con.prepareGraphQuery(QueryLanguage.SPARQL,
	     * "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
	     * "CONSTRUCT {_:v rdf:type rdf:Statement; rdf:Subject ?x ; rdf:Predicate ?p; rdf:Object ?y} "
	     * + "WHERE {?x ?p ?y . }").evaluate();
	     * 
	     * while (graphResult.hasNext()) { Statement st = graphResult.next();
	     * System.out.println(st.toString()); } } finally { con.close(); } } catch
	     * (OpenRDFException e) { // handle exception e.printStackTrace(); }
	     */

	} catch (RepositoryException e) {
	    // handle exception
	    e.printStackTrace();
	}
    }
}
