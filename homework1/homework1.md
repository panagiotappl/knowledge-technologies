# M164 CS2 Knowledge Technologies

## Homework 1

### Exercise 1 (Wikidata)

* For the purposes of the first exercise the greek entity Gregory's Micromeals was chosen. There was already an entry made in Wikidata but there was very little (and semi-inaccurate) information added. The title was missing an apostrophe and the labels, descriptions and aliases were inaccurate/missing. For example, the Greek description was just the world "restaurant" in English. Also, there were additional statements added regarding the creation and history of the chain. All the information that was added was based on their [official website](https://www.gregorys.gr/en). The pointer to the wikidata page is [Q62273834](https://www.wikidata.org/wiki/Q62273834).

* Below you can find the SPARQL queries that were used:
  * Find all the prime ministers of Greece known to Wikidata. Output their name,
  the party or parties they have been members of and the university (-ies) that they have graduated from.
  ```SQL
    SELECT ?item ?itemLabel ?politicalPartyLabel ?educatedAtLabel
    WHERE {
      ?item wdt:P27 wd:Q41 .
      ?item wdt:P39 wd:Q4377230 .
      OPTIONAL{?item wdt:P102 ?politicalParty .}
      OPTIONAL{
        ?item wdt:P69 ?educatedAt .
        ?educatedAt wdt:P31 ?type FILTER(?type = wd:Q3918).
      }
      SERVICE wikibase:label { bd:serviceParam wikibase:language "el,en". }
    }
    ```
    [Link to wikidata query](https://query.wikidata.org/#SELECT%20DISTINCT%20%3Fitem%20%3FitemLabel%20%28COUNT%28%3Fperson%29%20AS%20%3Fcount%29%0AWHERE%20%7B%0A%20%20%3Fitem%20wdt%3AP31%20wd%3AQ3918%20.%20%23%20instance%20of%20university%0A%20%20%3Fitem%20wdt%3AP17%20wd%3AQ41%20.%20%23%20country%20Greece%0A%20%20OPTIONAL%7B%3Fitem%20wdt%3AP159%20%3FheadquartersLocation%20.%7D%20%23%20optional%20headquarters%20location%0A%20%20OPTIONAL%7B%0A%20%20%20%20%3Fperson%20wdt%3AP69%20%3Fitem%20.%20%23%20person%20educated%20at%20university%0A%20%20%20%20%3Fperson%20wdt%3AP27%20wd%3AQ41%20.%20%23%20person%20is%20greek%0A%20%20%20%20%3Fperson%20wdt%3AP106%20wd%3AQ36180%20%23%20person%20is%20writter%20%0A%20%20%7D%0A%20%20SERVICE%20wikibase%3Alabel%20%7B%20bd%3AserviceParam%20wikibase%3Alanguage%20%22el%2Cen%22.%20%7D%0A%7D%20GROUP%20BY%20%3Fitem%20%3FitemLabel%20ORDER%20BY%20DESC%28%3Fcount%29)

  * Find all the Greek universities known to Wikidata. Output their name, the
  city that they are located in and the number of Greek authors that have
  graduated from them (order answers by this number).
  ```SQL  
  SELECT DISTINCT ?item ?itemLabel (COUNT(?person) AS ?count)
  WHERE {
    ?item wdt:P31 wd:Q3918 . # instance of university
    ?item wdt:P17 wd:Q41 . # country Greece
    OPTIONAL{?item wdt:P159 ?headquartersLocation .} # optional headquarters location
    OPTIONAL{
      ?person wdt:P69 ?item . # person educated at university
      ?person wdt:P27 wd:Q41 . # person is greek
      ?person wdt:P106 wd:Q36180 # person is writter
    }
    SERVICE wikibase:label { bd:serviceParam wikibase:language "el,en". }
  } GROUP BY ?item ?itemLabel ORDER BY DESC(?count)
  ```
  [Link to wikidata query](https://query.wikidata.org/#SELECT%20%3Fitem%20%3FitemLabel%20%3FpoliticalPartyLabel%20%3FeducatedAtLabel%0AWHERE%20%7B%0A%20%20%3Fitem%20wdt%3AP27%20wd%3AQ41%20.%0A%20%20%3Fitem%20wdt%3AP39%20wd%3AQ4377230%20.%0A%20%20OPTIONAL%7B%3Fitem%20wdt%3AP102%20%3FpoliticalParty%20.%7D%0A%20%20OPTIONAL%7B%0A%20%20%20%20%3Fitem%20wdt%3AP69%20%3FeducatedAt%20.%0A%20%20%20%20%3FeducatedAt%20wdt%3AP31%20%3Ftype%20FILTER%28%3Ftype%20%3D%20wd%3AQ3918%29.%0A%20%20%7D%0A%20%20SERVICE%20wikibase%3Alabel%20%7B%20bd%3AserviceParam%20wikibase%3Alanguage%20%22el%2Cen%22.%20%7D%0A%7D)

### Exercise 2
* Give the official name and population of each municipality (δήμος) of Greece.

```SQL
PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/>
SELECT ?official_municipality_name ?population
WHERE { ?municipality rdf:type gag:Δήμος .
        ?municipality gag:έχει_επίσημο_όνομα ?official_municipality_name .
        ?municipality gag:έχει_πληθυσμό ?population . }
```

* For each municipality (δήμος) of Greece, give its official name, the official
name of the regional unit (περιφερειακή ενότητα) it belongs to, and the official
name of each municipal unit (δημοτική ενότητα) in it. Organize your answer by
municipality.

```SQL
PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/>
SELECT ?municipality ?official_municipality_name ?official_regional_unit_name ?official_municipality_unit_name
WHERE { ?municipality rdf:type gag:Δήμος .
        ?municipality gag:έχει_επίσημο_όνομα ?official_municipality_name .
        ?municipality gag:ανήκει_σε ?regional_unit .
        ?regional_unit gag:έχει_επίσημο_όνομα ?official_regional_unit_name .
        ?municipality_unit gag:ανήκει_σε ?municipality .
        ?municipality_unit gag:έχει_επίσημο_όνομα ?official_municipality_unit_name . }
        ORDER BY ?municipality
```

* For each municipality of the region Crete with population more than 5,000
people, give its official name and its population.

```SQL
PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/>
SELECT ?official_municipality_name ?population
WHERE { ?region rdf:type gag:Περιφέρεια .
        ?region gag:έχει_επίσημο_όνομα \"ΠΕΡΙΦΕΡΕΙΑ ΚΡΗΤΗΣ\" .
        ?regional_unit gag:ανήκει_σε ?region .
        ?municipality gag:ανήκει_σε ?regional_unit .
        ?municipality gag:έχει_πληθυσμό ?population .
        FILTER(?population > 5000)
        ?municipality gag:έχει_επίσημο_όνομα ?official_municipality_name .}
```

* For each municipality of Crete for which we have no seat (έδρα) information in
the dataset, give its official name.
```SQL
PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/>
SELECT ?official_municipality_name
WHERE { ?region rdf:type gag:Περιφέρεια .
        ?region gag:έχει_επίσημο_όνομα \"ΠΕΡΙΦΕΡΕΙΑ ΚΡΗΤΗΣ\" .
        ?regional_unit gag:ανήκει_σε ?region .
        ?municipality gag:ανήκει_σε ?regional_unit .
        FILTER NOT EXISTS { ?municipality gag:έχει_έδρα ?seat . }
        ?municipality gag:έχει_επίσημο_όνομα ?official_municipality_name .}
```

* For each region of Greece, give its official name, how many regional units
belong to it, the official name of each regional unit (περιφερειακή ενότητα)
that belongs to it, and how many municipalities belong to that regional unit.

```SQL
PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/>
SELECT ?official_region_name ?regional_unit_count ?official_regional_unit_name ?municipality_count
WHERE { ?regional_unit gag:ανήκει_σε ?region .
        ?region gag:έχει_επίσημο_όνομα ?official_region_name .
        ?regional_unit gag:έχει_επίσημο_όνομα ?official_regional_unit_name .
        {
          SELECT ?region (COUNT(?regional_unit) as ?regional_unit_count)
          WHERE { ?regional_unit rdf:type gag:Περιφερειακή_Ενότητα .
                  ?region rdf:type gag:Περιφέρεια .
                  ?regional_unit gag:ανήκει_σε ?region . }
         GROUP BY ?region
       } .
      {
        SELECT ?regional_unit (COUNT(?municipality) as ?municipality_count)
        WHERE { ?municipality rdf:type gag:Δήμος .
                ?regional_unit rdf:type gag:Περιφερειακή_Ενότητα .
                ?municipality gag:ανήκει_σε ?regional_unit . }
       GROUP BY ?regional_unit
     } . }
ORDER BY ?region;
```

* Check the consistency of the dataset regarding stated populations: the sum of
the populations of all administrative units A of level L must be equal to the
population of the administrative unit B of level L+1 to which all administrative
units A belong to. (You have to write one query only.)

```SQL
PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/>
SELECT ?population ?sum_population ?parent_name
WHERE { ?parent_unit gag:έχει_πληθυσμό ?population .
        ?parent_unit gag:έχει_επίσημο_όνομα ?parent_name .
        {
          SELECT ?parent_unit (SUM(?unit_population) as ?sum_population)
          WHERE { ?unit gag:ανήκει_σε ?parent_unit .
                  ?unit gag:έχει_πληθυσμό ?unit_population .
        } GROUP BY ?parent_unit } .
}
```

* Give the decentralized administrations (αποκεντρωμένες διοικήςεις) of Greece
that consist of more than two regional units. (You cannot use SPARQL 1.1
aggregate operators to express this query.)

```SQL
PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/>
SELECT ?official_d_name
WHERE { ?d rdf:type gag:Αποκεντρωμένη_Διοίκηση .
        ?d gag:έχει_επίσημο_όνομα ?official_d_name .
        ?regional_unit1 rdf:type gag:Περιφερειακή_Ενότητα .
        ?regional_unit1 gag:ανήκει_σε+ ?d .
        ?regional_unit2 rdf:type gag:Περιφερειακή_Ενότητα .
        ?regional_unit2 gag:ανήκει_σε+ ?d .
        ?regional_unit3 rdf:type gag:Περιφερειακή_Ενότητα .
        ?regional_unit3 gag:ανήκει_σε+ ?d .
        FILTER(?regional_unit1 != ?regional_unit2)
        FILTER(?regional_unit2 != ?regional_unit3) }
GROUP BY ?official_d_name
```
Results:
All the decentralized

### Exercise 3 (linkedopendata & geonames)
* Find all information that Geonames has for “Dimos Chania” (you have to use
only Geonames here, not the Kallikratis dataset).
```SQL
PREFIX gn:<http://www.geonames.org/ontology#>
SELECT ?property ?value
WHERE { ?x ?property ?value .
        ?x geonames:name "Dimos Chania".
}
```
### Exercise 4 (Schema.org)

#### With Inferencing

* Find all subclasses of class Place (note that http://schema.org/ prefers to
use the equivalent term “type” for “class”).

```SQL
PREFIX ns:  <http://schema.org/>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT ?x
WHERE { ?x  rdfs:subClassOf  ns:Place  }
```
* Find all the superclasses of class Place.
```SQL
PREFIX ns:  <http://schema.org/>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT ?x
WHERE { ns:Place  rdfs:subClassOf  ?x  }  
```
* Find all properties defined for the class Place together with all the
properties inherited from its superclasses.
```SQL
PREFIX ns: <http://schema.org/> "
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
SELECT DISTINCT ?property "
WHERE { ?property  rdf:type  rdf:Property .
        ?place rdf:type rdfs:Class .
        ?property ns:domainIncludes ?place . }
```
* Find all classes that are subclasses of class Thing and are found in at most 2
levels of subclass relationships away from Thing.
```SQL  
PREFIX ns:  <http://schema.org/>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT DISTINCT ?x
WHERE {
        { ?x  rdfs:subClassOf ns:Thing }
        UNION
        { ?subClassOfThing rdfs:subClassOf ns:Thing .
          ?x  rdfs:subClassOf ?subClassOfThing. }
      }

```
### Without Inferencing

* Find all subclasses of class Place (note that http://schema.org/ prefers to
use the equivalent term “type” for “class”).

```SQL
PREFIX ns:  <http://schema.org/>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT ?x
WHERE { ?x  rdfs:subClassOf*  ns:Place  }
