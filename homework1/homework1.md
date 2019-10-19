# M164 CS2 Knowledge Technologies

## Homework 1

### Exercise 1 (Wikidata)

* For the purposes of the first exercise the greek entity Gregory's Micromeals was chosen. There was already an entry made in Wikidata but there was very little (and semi-inaccurate) information added. The title was missing an apostrophe and the labels, descriptions and aliases were inaccurate/missing. For example, the Greek description was just the world "restaurant" in English. Also, there were additional statements added regarding the creation and history of the chain. All the information that was added was based on their [official website](https://www.gregorys.gr/en). The pointer to the wikidata page is [Q62273834](https://www.wikidata.org/wiki/Q62273834).

* Below you can find the SPARQL queries that were used:
  * Find all the prime ministers of Greece known to Wikidata. Output their name, the party or parties they have been members of and the university (-ies) that they have graduated from.
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

  * Find all the Greek universities known to Wikidata. Output their name, the city that they are located in and the number of Greek authors that have graduated from them (order answers by this number).
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
Results:
```
[official_municipality_name="ΔΗΜΟΣ ΑΒΔΗΡΩΝ";population="18573"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΣΙΘΩΝΙΑΣ";population="12927"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΝΕΑΣ ΣΜΥΡΝΗΣ";population="73986"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΗΛΙΟΥΠΟΛΕΩΣ";population="75904"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΚΟΡΔΕΛΙΟΥ-ΕΥΟΣΜΟΥ";population="54787"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΘΑΣΟΥ";population="10939"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΚΗΦΙΣΙΑΣ";population="59887"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΕΡΕΤΡΙΑΣ";population="13325"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΚΥΜΗΣ-ΑΛΙΒΕΡΙΟΥ";population="32339"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΑΜΟΡΓΟΥ";population="1859"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΧΕΡΣΟΝΗΣΟΥ";population="22708"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΔΥΤΙΚΗΣ ΑΧΑΙΑΣ";population="28422"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΖΩΓΡΑΦΟΥ";population="76115"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΚΑΣΣΑΝΔΡΑΣ";population="16153"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΘΕΡΜΗΣ";population="22659"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΑΝΑΦΗΣ";population="273"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΑΜΦΙΚΛΕΙΑΣ - ΕΛΑΤΕΙΑΣ";population="9679"^^<http://www.w3.org/2001/XMLSchema#integer>]
```
* For each municipality (δήμος) of Greece, give its official name, the official name of the regional unit (περιφερειακή ενότητα) it belongs to, and the official name of each municipal unit (δημοτική ενότητα) in it. Organize your answer by municipality.

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
Results:
```
[municipality=http://geo.linkedopendata.gr/gag/id/9193;official_municipality_name="ΔΗΜΟΣ ΦΙΛΑΔΕΛΦΕΙΑΣ - ΧΑΛΚΗΔΟΝΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΕΝΤΡΙΚΟΥ ΤΟΜΕΑ ΑΘΗΝΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΑΣ ΦΙΛΑΔΕΛΦΕΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9194;official_municipality_name="ΔΗΜΟΣ ΑΓΙΟΥ ΔΗΜΗΤΡΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΟΤΙΟΥ ΤΟΜΕΑ ΑΘΗΝΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΟΥ ΔΗΜΗΤΡΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9195;official_municipality_name="ΔΗΜΟΣ ΑΛΙΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΟΤΙΟΥ ΤΟΜΕΑ ΑΘΗΝΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΛΙΜΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9196;official_municipality_name="ΔΗΜΟΣ ΓΛΥΦΑΔΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΟΤΙΟΥ ΤΟΜΕΑ ΑΘΗΝΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΛΥΦΑΔΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9197;official_municipality_name="ΔΗΜΟΣ ΕΛΛΗΝΙΚΟΥ - ΑΡΓΥΡΟΥΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΟΤΙΟΥ ΤΟΜΕΑ ΑΘΗΝΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΛΛΗΝΙΚΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9197;official_municipality_name="ΔΗΜΟΣ ΕΛΛΗΝΙΚΟΥ - ΑΡΓΥΡΟΥΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΟΤΙΟΥ ΤΟΜΕΑ ΑΘΗΝΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΡΓΥΡΟΥΠΟΛΕΩΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9198;official_municipality_name="ΔΗΜΟΣ ΚΑΛΛΙΘΕΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΟΤΙΟΥ ΤΟΜΕΑ ΑΘΗΝΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΛΛΙΘΕΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9199;official_municipality_name="ΔΗΜΟΣ ΜΟΣΧΑΤΟΥ - ΤΑΥΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΟΤΙΟΥ ΤΟΜΕΑ ΑΘΗΝΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΑΥΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9199;official_municipality_name="ΔΗΜΟΣ ΜΟΣΧΑΤΟΥ - ΤΑΥΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΟΤΙΟΥ ΤΟΜΕΑ ΑΘΗΝΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΟΣΧΑΤΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9200;official_municipality_name="ΔΗΜΟΣ ΝΕΑΣ ΣΜΥΡΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΟΤΙΟΥ ΤΟΜΕΑ ΑΘΗΝΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΑΣ ΣΜΥΡΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9201;official_municipality_name="ΔΗΜΟΣ ΠΑΛΑΙΟΥ ΦΑΛΗΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΟΤΙΟΥ ΤΟΜΕΑ ΑΘΗΝΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΑΛΑΙΟΥ ΦΑΛΗΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9202;official_municipality_name="ΔΗΜΟΣ ΚΕΡΑΤΣΙΝΙΟΥ - ΔΡΑΠΕΤΣΩΝΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΠΕΙΡΑΙΩΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΕΡΑΤΣΙΝΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9202;official_municipality_name="ΔΗΜΟΣ ΚΕΡΑΤΣΙΝΙΟΥ - ΔΡΑΠΕΤΣΩΝΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΠΕΙΡΑΙΩΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΔΡΑΠΕΤΣΩΝΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9203;official_municipality_name="ΔΗΜΟΣ ΚΟΡΥΔΑΛΛΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΠΕΙΡΑΙΩΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΡΥΔΑΛΛΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9204;official_municipality_name="ΔΗΜΟΣ ΝΙΚΑΙΑΣ - ΑΓΙΟΥ Ι. ΡΕΝΤΗ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΠΕΙΡΑΙΩΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΙΚΑΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9204;official_municipality_name="ΔΗΜΟΣ ΝΙΚΑΙΑΣ - ΑΓΙΟΥ Ι. ΡΕΝΤΗ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΠΕΙΡΑΙΩΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΟΥ ΙΩΑΝΝΟΥ ΡΕΝΤΗ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9205;official_municipality_name="ΔΗΜΟΣ ΠΕΙΡΑΙΩΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΠΕΙΡΑΙΩΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΕΙΡΑΙΩΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9206;official_municipality_name="ΔΗΜΟΣ ΠΕΡΑΜΑΤΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΠΕΙΡΑΙΩΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΕΡΑΜΑΤΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9207;official_municipality_name="ΔΗΜΟΣ ΑΓΚΙΣΤΡΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΗΣΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΚΙΣΤΡΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9208;official_municipality_name="ΔΗΜΟΣ ΑΙΓΙΝΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΗΣΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΙΓΙΝΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9209;official_municipality_name="ΔΗΜΟΣ ΚΥΘΗΡΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΗΣΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΥΘΗΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9209;official_municipality_name="ΔΗΜΟΣ ΚΥΘΗΡΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΗΣΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΤΙΚΥΘΗΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9210;official_municipality_name="ΔΗΜΟΣ ΠΟΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΗΣΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΟΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9211;official_municipality_name="ΔΗΜΟΣ ΣΑΛΑΜΙΝΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΗΣΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΑΛΑΜΙΝΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9211;official_municipality_name="ΔΗΜΟΣ ΣΑΛΑΜΙΝΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΗΣΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΜΠΕΛΑΚΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9212;official_municipality_name="ΔΗΜΟΣ ΣΠΕΤΣΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΗΣΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΠΕΤΣΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9213;official_municipality_name="ΔΗΜΟΣ ΤΡΟΙΖΗΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΗΣΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΕΘΑΝΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9213;official_municipality_name="ΔΗΜΟΣ ΤΡΟΙΖΗΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΗΣΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΡΟΙΖΗΝΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9214;official_municipality_name="ΔΗΜΟΣ ΥΔΡΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΗΣΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΥΔΡΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9215;official_municipality_name="ΔΗΜΟΣ ΑΧΑΡΝΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΘΡΑΚΟΜΑΚΕΔΟΝΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9215;official_municipality_name="ΔΗΜΟΣ ΑΧΑΡΝΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΧΑΡΝΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9216;official_municipality_name="ΔΗΜΟΣ ΒΑΡΗΣ - ΒΟΥΛΑΣ - ΒΟΥΛΙΑΓΜΕΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΟΥΛΙΑΓΜΕΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9216;official_municipality_name="ΔΗΜΟΣ ΒΑΡΗΣ - ΒΟΥΛΑΣ - ΒΟΥΛΙΑΓΜΕΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΟΥΛΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9216;official_municipality_name="ΔΗΜΟΣ ΒΑΡΗΣ - ΒΟΥΛΑΣ - ΒΟΥΛΙΑΓΜΕΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΑΡΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9217;official_municipality_name="ΔΗΜΟΣ ΔΙΟΝΥΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΔΙΟΝΥΣΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9217;official_municipality_name="ΔΗΜΟΣ ΔΙΟΝΥΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΡΥΟΝΕΡΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9217;official_municipality_name="ΔΗΜΟΣ ΔΙΟΝΥΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΟΥ ΣΤΕΦΑΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9217;official_municipality_name="ΔΗΜΟΣ ΔΙΟΝΥΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΡΟΔΟΠΟΛΕΩΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9217;official_municipality_name="ΔΗΜΟΣ ΔΙΟΝΥΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΔΡΟΣΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9217;official_municipality_name="ΔΗΜΟΣ ΔΙΟΝΥΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΤΑΜΑΤΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9217;official_municipality_name="ΔΗΜΟΣ ΔΙΟΝΥΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΟΙΞΕΩΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9218;official_municipality_name="ΔΗΜΟΣ ΚΡΩΠΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΡΩΠΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9219;official_municipality_name="ΔΗΜΟΣ ΛΑΥΡΕΩΤΙΚΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΕΡΑΤΕΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9219;official_municipality_name="ΔΗΜΟΣ ΛΑΥΡΕΩΤΙΚΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΑΥΡΕΩΤΙΚΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9219;official_municipality_name="ΔΗΜΟΣ ΛΑΥΡΕΩΤΙΚΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΟΥ ΚΩΝΣΤΑΝΤΙΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9220;official_municipality_name="ΔΗΜΟΣ ΜΑΡΑΘΩΝΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΑΣ ΜΑΚΡΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9220;official_municipality_name="ΔΗΜΟΣ ΜΑΡΑΘΩΝΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΡΑΜΜΑΤΙΚΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9220;official_municipality_name="ΔΗΜΟΣ ΜΑΡΑΘΩΝΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΑΡΝΑΒΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9220;official_municipality_name="ΔΗΜΟΣ ΜΑΡΑΘΩΝΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΡΑΘΩΝΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9221;official_municipality_name="ΔΗΜΟΣ ΜΑΡΚΟΠΟΥΛΟΥ ΜΕΣΟΓΑΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΡΚΟΠΟΥΛΟΥ ΜΕΣΟΓΑΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9222;official_municipality_name="ΔΗΜΟΣ ΠΑΙΑΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΛΥΚΩΝ ΝΕΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9222;official_municipality_name="ΔΗΜΟΣ ΠΑΙΑΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΑΙΑΝΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9223;official_municipality_name="ΔΗΜΟΣ ΠΑΛΛΗΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΕΡΑΚΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9223;official_municipality_name="ΔΗΜΟΣ ΠΑΛΛΗΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΑΛΛΗΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9223;official_municipality_name="ΔΗΜΟΣ ΠΑΛΛΗΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΘΟΥΣΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9224;official_municipality_name="ΔΗΜΟΣ ΡΑΦΗΝΑΣ - ΠΙΚΕΡΜΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΙΚΕΡΜΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9224;official_municipality_name="ΔΗΜΟΣ ΡΑΦΗΝΑΣ - ΠΙΚΕΡΜΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΡΑΦΗΝΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9225;official_municipality_name="ΔΗΜΟΣ ΣΑΡΩΝΙΚΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΑΛΑΙΑΣ ΦΩΚΑΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9225;official_municipality_name="ΔΗΜΟΣ ΣΑΡΩΝΙΚΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΥΒΑΡΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9225;official_municipality_name="ΔΗΜΟΣ ΣΑΡΩΝΙΚΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΛΥΒΙΩΝ ΘΟΡΙΚΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9225;official_municipality_name="ΔΗΜΟΣ ΣΑΡΩΝΙΚΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΑΡΩΝΙΔΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9225;official_municipality_name="ΔΗΜΟΣ ΣΑΡΩΝΙΚΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΑΒΥΣΣΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9226;official_municipality_name="ΔΗΜΟΣ ΣΠΑΤΩΝ - ΑΡΤΕΜΙΔΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΠΑΤΩΝ-ΛΟΥΤΣΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9226;official_municipality_name="ΔΗΜΟΣ ΣΠΑΤΩΝ - ΑΡΤΕΜΙΔΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΡΤΕΜΙΔΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9227;official_municipality_name="ΔΗΜΟΣ ΩΡΩΠΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΩΡΩΠΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9227;official_municipality_name="ΔΗΜΟΣ ΩΡΩΠΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΛΑΜΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9227;official_municipality_name="ΔΗΜΟΣ ΩΡΩΠΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΦΙΔΝΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9227;official_municipality_name="ΔΗΜΟΣ ΩΡΩΠΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΛΑΚΑΣΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9227;official_municipality_name="ΔΗΜΟΣ ΩΡΩΠΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΟΛΥΔΕΝΔΡΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9227;official_municipality_name="ΔΗΜΟΣ ΩΡΩΠΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΠΑΝΔΡΙΤΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9227;official_municipality_name="ΔΗΜΟΣ ΩΡΩΠΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΥΚΑΜΙΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9227;official_municipality_name="ΔΗΜΟΣ ΩΡΩΠΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΥΛΩΝΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9227;official_municipality_name="ΔΗΜΟΣ ΩΡΩΠΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΑΤΟΛΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΡΚΟΠΟΥΛΟΥ ΩΡΩΠΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9228;official_municipality_name="ΔΗΜΟΣ ΑΣΠΡΟΠΥΡΓΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΣΠΡΟΠΥΡΓΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9229;official_municipality_name="ΔΗΜΟΣ ΕΛΕΥΣΙΝΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΛΕΥΣΙΝΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9229;official_municipality_name="ΔΗΜΟΣ ΕΛΕΥΣΙΝΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΓΟΥΛΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9230;official_municipality_name="ΔΗΜΟΣ ΜΑΝΔΡΑΣ - ΕΙΔΥΛΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΙΛΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9230;official_municipality_name="ΔΗΜΟΣ ΜΑΝΔΡΑΣ - ΕΙΔΥΛΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΟΙΝΟΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9230;official_municipality_name="ΔΗΜΟΣ ΜΑΝΔΡΑΣ - ΕΙΔΥΛΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΡΥΘΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9230;official_municipality_name="ΔΗΜΟΣ ΜΑΝΔΡΑΣ - ΕΙΔΥΛΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΝΔΡΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9231;official_municipality_name="ΔΗΜΟΣ ΜΕΓΑΡΕΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΕΓΑΡΕΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9231;official_municipality_name="ΔΗΜΟΣ ΜΕΓΑΡΕΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΑΣ ΠΕΡΑΜΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9232;official_municipality_name="ΔΗΜΟΣ ΦΥΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΖΕΦΥΡΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9232;official_municipality_name="ΔΗΜΟΣ ΦΥΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΦΥΛΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9232;official_municipality_name="ΔΗΜΟΣ ΦΥΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΔΥΤΙΚΗΣ ΑΤΤΙΚΗΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΩ ΛΙΟΣΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9233;official_municipality_name="ΔΗΜΟΣ ΑΡΓΟΥΣ-ΜΥΚΗΝΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΥΡΚΕΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9233;official_municipality_name="ΔΗΜΟΣ ΑΡΓΟΥΣ-ΜΥΚΗΝΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΥΚΗΝΑΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9233;official_municipality_name="ΔΗΜΟΣ ΑΡΓΟΥΣ-ΜΥΚΗΝΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΥΤΣΟΠΟΔΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9233;official_municipality_name="ΔΗΜΟΣ ΑΡΓΟΥΣ-ΜΥΚΗΝΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΛΕΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9233;official_municipality_name="ΔΗΜΟΣ ΑΡΓΟΥΣ-ΜΥΚΗΝΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΧΛΑΔΟΚΑΜΠΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9233;official_municipality_name="ΔΗΜΟΣ ΑΡΓΟΥΣ-ΜΥΚΗΝΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΕΡΝΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9233;official_municipality_name="ΔΗΜΟΣ ΑΡΓΟΥΣ-ΜΥΚΗΝΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΑΣ ΚΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9233;official_municipality_name="ΔΗΜΟΣ ΑΡΓΟΥΣ-ΜΥΚΗΝΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΡΓΟΥΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9234;official_municipality_name="ΔΗΜΟΣ ΕΠΙΔΑΥΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΣΚΛΗΠΙΕΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9234;official_municipality_name="ΔΗΜΟΣ ΕΠΙΔΑΥΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΠΙΔΑΥΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9235;official_municipality_name="ΔΗΜΟΣ ΕΡΜΙΟΝΙΔΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΡΜΙΟΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9235;official_municipality_name="ΔΗΜΟΣ ΕΡΜΙΟΝΙΔΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΡΑΝΙΔΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9236;official_municipality_name="ΔΗΜΟΣ ΝΑΥΠΛΙΕΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΙΔΕΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9236;official_municipality_name="ΔΗΜΟΣ ΝΑΥΠΛΙΕΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΑΥΠΛΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9236;official_municipality_name="ΔΗΜΟΣ ΝΑΥΠΛΙΕΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΣΙΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9236;official_municipality_name="ΔΗΜΟΣ ΝΑΥΠΛΙΕΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΓΟΛΙΔΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΑΣ ΤΙΡΥΝΘΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9237;official_municipality_name="ΔΗΜΟΣ ΒΟΡΕΙΑΣ ΚΥΝΟΥΡΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΟΡΕΙΑΣ ΚΥΝΟΥΡΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9238;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΗΡΑΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9238;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΑΓΚΑΔΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9238;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΛΕΙΤΟΡΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9238;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΥΤΙΝΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9238;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΡΟΠΑΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9238;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΔΗΜΗΤΣΑΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9238;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΝΤΟΒΑΖΑΙΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9238;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΡΙΚΟΛΩΝΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9239;official_municipality_name="ΔΗΜΟΣ ΜΕΓΑΛΟΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΟΡΤΥΝΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9239;official_municipality_name="ΔΗΜΟΣ ΜΕΓΑΛΟΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΦΑΛΑΙΣΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9239;official_municipality_name="ΔΗΜΟΣ ΜΕΓΑΛΟΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΕΓΑΛΟΠΟΛΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9240;official_municipality_name="ΔΗΜΟΣ ΝΟΤΙΑΣ ΚΥΝΟΥΡΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΣΜΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9240;official_municipality_name="ΔΗΜΟΣ ΝΟΤΙΑΣ ΚΥΝΟΥΡΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΥΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9240;official_municipality_name="ΔΗΜΟΣ ΝΟΤΙΑΣ ΚΥΝΟΥΡΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΕΩΝΙΔΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9241;official_municipality_name="ΔΗΜΟΣ ΤΡΙΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΦΑΛΑΝΘΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9241;official_municipality_name="ΔΗΜΟΣ ΤΡΙΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΕΓΕΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9241;official_municipality_name="ΔΗΜΟΣ ΤΡΙΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΚΙΡΙΤΙΔΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9241;official_municipality_name="ΔΗΜΟΣ ΤΡΙΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΑΛΤΕΤΣΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9241;official_municipality_name="ΔΗΜΟΣ ΤΡΙΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΡΙΠΟΛΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9241;official_municipality_name="ΔΗΜΟΣ ΤΡΙΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΕΒΙΔΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9241;official_municipality_name="ΔΗΜΟΣ ΤΡΙΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΝΤΙΝΕΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9241;official_municipality_name="ΔΗΜΟΣ ΤΡΙΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΡΚΑΔΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΡΥΘΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9242;official_municipality_name="ΔΗΜΟΣ ΒΕΛΟΥ-ΒΟΧΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΟΧΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9242;official_municipality_name="ΔΗΜΟΣ ΒΕΛΟΥ-ΒΟΧΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΕΛΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9243;official_municipality_name="ΔΗΜΟΣ ΚΟΡΙΝΘΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΕΝΕΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9243;official_municipality_name="ΔΗΜΟΣ ΚΟΡΙΝΘΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΣΣΟΥ-ΛΕΧΑΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9243;official_municipality_name="ΔΗΜΟΣ ΚΟΡΙΝΘΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΟΛΥΓΕΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9243;official_municipality_name="ΔΗΜΟΣ ΚΟΡΙΝΘΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΑΡΩΝΙΚΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9243;official_municipality_name="ΔΗΜΟΣ ΚΟΡΙΝΘΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΡΙΝΘΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9244;official_municipality_name="ΔΗΜΟΣ ΛΟΥΤΡΑΚΙΟΥ-ΑΓ.ΘΕΟΔΩΡΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΟΥΤΡΑΚΙΟΥ-ΠΕΡΑΧΩΡΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9244;official_municipality_name="ΔΗΜΟΣ ΛΟΥΤΡΑΚΙΟΥ-ΑΓ.ΘΕΟΔΩΡΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΩΝ ΘΕΟΔΩΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9245;official_municipality_name="ΔΗΜΟΣ ΝΕΜΕΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΜΕΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9246;official_municipality_name="ΔΗΜΟΣ ΞΥΛΟΚΑΣΤΡΟΥ-ΕΥΡΩΣΤΙΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΞΥΛΟΚΑΣΤΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9246;official_municipality_name="ΔΗΜΟΣ ΞΥΛΟΚΑΣΤΡΟΥ-ΕΥΡΩΣΤΙΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΥΡΩΣΤΙΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9247;official_municipality_name="ΔΗΜΟΣ ΣΙΚΥΩΝΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΦΕΝΕΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9247;official_municipality_name="ΔΗΜΟΣ ΣΙΚΥΩΝΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΙΚΥΩΝΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9247;official_municipality_name="ΔΗΜΟΣ ΣΙΚΥΩΝΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΟΡΙΝΘΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΤΥΜΦΑΛΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9248;official_municipality_name="ΔΗΜΟΣ ΑΝΑΤΟΛΙΚΗΣ ΜΑΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΜΥΝΟΥΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9248;official_municipality_name="ΔΗΜΟΣ ΑΝΑΤΟΛΙΚΗΣ ΜΑΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΟΙΤΥΛΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9248;official_municipality_name="ΔΗΜΟΣ ΑΝΑΤΟΛΙΚΗΣ ΜΑΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΑΤΟΛΙΚΗΣ ΜΑΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9248;official_municipality_name="ΔΗΜΟΣ ΑΝΑΤΟΛΙΚΗΣ ΜΑΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΥΘΕΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9249;official_municipality_name="ΔΗΜΟΣ ΕΛΑΦΟΝΗΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΛΑΦΟΝΗΣΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9250;official_municipality_name="ΔΗΜΟΣ ΕΥΡΩΤΑ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΛΟΥΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9250;official_municipality_name="ΔΗΜΟΣ ΕΥΡΩΤΑ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΚΑΛΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9250;official_municipality_name="ΔΗΜΟΣ ΕΥΡΩΤΑ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΕΡΟΝΘΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9250;official_municipality_name="ΔΗΜΟΣ ΕΥΡΩΤΑ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΙΑΤΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9250;official_municipality_name="ΔΗΜΟΣ ΕΥΡΩΤΑ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΡΟΚΕΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9251;official_municipality_name="ΔΗΜΟΣ ΜΟΝΕΜΒΑΣΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΟΝΕΜΒΑΣΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9251;official_municipality_name="ΔΗΜΟΣ ΜΟΝΕΜΒΑΣΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΣΩΠΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9251;official_municipality_name="ΔΗΜΟΣ ΜΟΝΕΜΒΑΣΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΟΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9251;official_municipality_name="ΔΗΜΟΣ ΜΟΝΕΜΒΑΣΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΟΛΑΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9251;official_municipality_name="ΔΗΜΟΣ ΜΟΝΕΜΒΑΣΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΖΑΡΑΚΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9252;official_municipality_name="ΔΗΜΟΣ ΣΠΑΡΤΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΕΛΛΑΝΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9252;official_municipality_name="ΔΗΜΟΣ ΣΠΑΡΤΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΠΑΡΤΙΑΤΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9252;official_municipality_name="ΔΗΜΟΣ ΣΠΑΡΤΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΦΑΡΙΔΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9252;official_municipality_name="ΔΗΜΟΣ ΣΠΑΡΤΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΡΥΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9252;official_municipality_name="ΔΗΜΟΣ ΣΠΑΡΤΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΥΣΤΡΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9252;official_municipality_name="ΔΗΜΟΣ ΣΠΑΡΤΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΘΕΡΑΠΝΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9252;official_municipality_name="ΔΗΜΟΣ ΣΠΑΡΤΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΚΩΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΟΙΝΟΥΝΤΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9253;official_municipality_name="ΔΗΜΟΣ ΔΥΤΙΚΗΣ ΜΑΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΕΥΚΤΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9253;official_municipality_name="ΔΗΜΟΣ ΔΥΤΙΚΗΣ ΜΑΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΒΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9254;official_municipality_name="ΔΗΜΟΣ ΚΑΛΑΜΑΤΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΡΦΑΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9254;official_municipality_name="ΔΗΜΟΣ ΚΑΛΑΜΑΤΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΛΑΜΑΤΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9254;official_municipality_name="ΔΗΜΟΣ ΚΑΛΑΜΑΤΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΡΙΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9254;official_municipality_name="ΔΗΜΟΣ ΚΑΛΑΜΑΤΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΘΟΥΡΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9255;official_municipality_name="ΔΗΜΟΣ ΜΕΣΣΗΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΡΙΣΤΟΜΕΝΟΥΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9255;official_municipality_name="ΔΗΜΟΣ ΜΕΣΣΗΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΟΥΦΡΑΔΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9255;official_municipality_name="ΔΗΜΟΣ ΜΕΣΣΗΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΙΠΕΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9255;official_municipality_name="ΔΗΜΟΣ ΜΕΣΣΗΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΙΘΩΜΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9255;official_municipality_name="ΔΗΜΟΣ ΜΕΣΣΗΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΡΙΚΟΡΦΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9255;official_municipality_name="ΔΗΜΟΣ ΜΕΣΣΗΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΕΣΣΗΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9255;official_municipality_name="ΔΗΜΟΣ ΜΕΣΣΗΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΔΡΟΥΣΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9255;official_municipality_name="ΔΗΜΟΣ ΜΕΣΣΗΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΕΤΑΛΙΔΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9256;official_municipality_name="ΔΗΜΟΣ ΟΙΧΑΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΟΙΧΑΛΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9256;official_municipality_name="ΔΗΜΟΣ ΟΙΧΑΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΕΛΙΓΑΛΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9256;official_municipality_name="ΔΗΜΟΣ ΟΙΧΑΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΔΩΡΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9256;official_municipality_name="ΔΗΜΟΣ ΟΙΧΑΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΔΑΝΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9256;official_municipality_name="ΔΗΜΟΣ ΟΙΧΑΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΙΡΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9257;official_municipality_name="ΔΗΜΟΣ ΠΥΛΟΥ-ΝΕΣΤΟΡΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΣΤΟΡΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9257;official_municipality_name="ΔΗΜΟΣ ΠΥΛΟΥ-ΝΕΣΤΟΡΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΥΛΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9257;official_municipality_name="ΔΗΜΟΣ ΠΥΛΟΥ-ΝΕΣΤΟΡΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΕΘΩΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9257;official_municipality_name="ΔΗΜΟΣ ΠΥΛΟΥ-ΝΕΣΤΟΡΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΑΠΑΦΛΕΣΣΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9257;official_municipality_name="ΔΗΜΟΣ ΠΥΛΟΥ-ΝΕΣΤΟΡΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΡΩΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9257;official_municipality_name="ΔΗΜΟΣ ΠΥΛΟΥ-ΝΕΣΤΟΡΟΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΧΙΛΙΟΧΩΡΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9258;official_municipality_name="ΔΗΜΟΣ ΤΡΙΦΥΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΥΛΩΝΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9258;official_municipality_name="ΔΗΜΟΣ ΤΡΙΦΥΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΑΡΓΑΛΙΑΝΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9258;official_municipality_name="ΔΗΜΟΣ ΤΡΙΦΥΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΦΙΛΙΑΤΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9258;official_municipality_name="ΔΗΜΟΣ ΤΡΙΦΥΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΕΤΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9258;official_municipality_name="ΔΗΜΟΣ ΤΡΙΦΥΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΥΠΑΡΙΣΣΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9258;official_municipality_name="ΔΗΜΟΣ ΤΡΙΦΥΛΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΕΣΣΗΝΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΡΙΠΥΛΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9259;official_municipality_name="ΔΗΜΟΣ ΙΚΑΡΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΙΚΑΡΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΡΑΧΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9259;official_municipality_name="ΔΗΜΟΣ ΙΚΑΡΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΙΚΑΡΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΟΥ ΚΗΡΥΚΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9259;official_municipality_name="ΔΗΜΟΣ ΙΚΑΡΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΙΚΑΡΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΥΔΗΛΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9260;official_municipality_name="ΔΗΜΟΣ ΦΟΥΡΝΩΝ ΚΟΡΣΕΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΙΚΑΡΙΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΦΟΥΡΝΩΝ ΚΟΡΣΕΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΥΕΡΓΕΤΟΥΛΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΑΣΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΥΤΙΛΗΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΡΕΣΟΥ - ΑΝΤΙΣΣΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΝΤΑΜΑΔΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΗΘΥΜΝΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΕΡΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΛΛΟΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΟΛΙΧΝΙΤΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΟΥΤΡΟΠΟΛΕΩΣ ΘΕΡΜΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΛΩΜΑΡΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΕΤΡΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9261;official_municipality_name="ΔΗΜΟΣ ΛΕΣΒΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΕΣΒΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΑΣ ΠΑΡΑΣΚΕΥΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9262;official_municipality_name="ΔΗΜΟΣ ΑΓΙΟΥ ΕΥΣΤΡΑΤΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΗΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΟΥ ΕΥΣΤΡΑΤΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9263;official_municipality_name="ΔΗΜΟΣ ΛΗΜΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΗΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΤΣΙΚΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9263;official_municipality_name="ΔΗΜΟΣ ΛΗΜΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΗΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΥΡΙΝΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9263;official_municipality_name="ΔΗΜΟΣ ΛΗΜΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΗΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΑΣ ΚΟΥΤΑΛΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9263;official_municipality_name="ΔΗΜΟΣ ΛΗΜΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΗΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΟΥΔΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9264;official_municipality_name="ΔΗΜΟΣ ΣΑΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΣΑΜΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΑΘΕΟΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9264;official_municipality_name="ΔΗΜΟΣ ΣΑΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΣΑΜΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΡΑΘΟΚΑΜΠΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9264;official_municipality_name="ΔΗΜΟΣ ΣΑΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΣΑΜΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΡΛΟΒΑΣΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9264;official_municipality_name="ΔΗΜΟΣ ΣΑΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΣΑΜΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΥΘΑΓΟΡΕΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9265;official_municipality_name="ΔΗΜΟΣ ΟΙΝΟΥΣΣΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΟΙΝΟΥΣΣΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9266;official_municipality_name="ΔΗΜΟΣ ΧΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΙΩΝΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9266;official_municipality_name="ΔΗΜΟΣ ΧΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΟΜΗΡΟΥΠΟΛΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9266;official_municipality_name="ΔΗΜΟΣ ΧΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΧΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9266;official_municipality_name="ΔΗΜΟΣ ΧΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΣΤΙΧΟΧΩΡΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9266;official_municipality_name="ΔΗΜΟΣ ΧΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΜΠΟΧΩΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9266;official_municipality_name="ΔΗΜΟΣ ΧΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΡΔΑΜΥΛΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9266;official_municipality_name="ΔΗΜΟΣ ΧΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΜΑΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9266;official_municipality_name="ΔΗΜΟΣ ΧΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΟΥ ΜΗΝΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9267;official_municipality_name="ΔΗΜΟΣ ΨΑΡΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΨΑΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9268;official_municipality_name="ΔΗΜΟΣ ΑΝΔΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΔΡΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΡΘΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9268;official_municipality_name="ΔΗΜΟΣ ΑΝΔΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΔΡΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΔΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9268;official_municipality_name="ΔΗΜΟΣ ΑΝΔΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΑΝΔΡΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΥΔΡΟΥΣΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9269;official_municipality_name="ΔΗΜΟΣ ΑΝΑΦΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΘΗΡΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΑΦΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9270;official_municipality_name="ΔΗΜΟΣ ΘΗΡΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΘΗΡΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΟΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9270;official_municipality_name="ΔΗΜΟΣ ΘΗΡΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΘΗΡΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΘΗΡΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9271;official_municipality_name="ΔΗΜΟΣ ΙΗΤΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΘΗΡΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΙΗΤΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9272;official_municipality_name="ΔΗΜΟΣ ΣΙΚΙΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΘΗΡΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΙΚΙΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9273;official_municipality_name="ΔΗΜΟΣ ΦΟΛΕΓΑΝΔΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΘΗΡΑΣ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΦΟΛΕΓΑΝΔΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9274;official_municipality_name="ΔΗΜΟΣ ΑΓΑΘΟΝΗΣΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΑΛΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΑΘΟΝΗΣΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9275;official_municipality_name="ΔΗΜΟΣ ΑΣΤΥΠΑΛΑΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΑΛΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΣΤΥΠΑΛΑΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9276;official_municipality_name="ΔΗΜΟΣ ΚΑΛΥΜΝΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΑΛΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΛΥΜΝΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9277;official_municipality_name="ΔΗΜΟΣ ΛΕΙΨΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΑΛΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΕΙΨΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9278;official_municipality_name="ΔΗΜΟΣ ΛΕΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΑΛΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΕΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9279;official_municipality_name="ΔΗΜΟΣ ΠΑΤΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΑΛΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΑΤΜΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9280;official_municipality_name="ΔΗΜΟΣ ΚΑΡΠΑΘΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΑΡΠΑΘΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΡΠΑΘΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9280;official_municipality_name="ΔΗΜΟΣ ΚΑΡΠΑΘΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΑΡΠΑΘΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΟΛΥΜΠΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9281;official_municipality_name="ΔΗΜΟΣ ΚΑΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΑΡΠΑΘΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΣΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9282;official_municipality_name="ΔΗΜΟΣ ΚΕΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΕΑΣ-ΚΥΘΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΕΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9283;official_municipality_name="ΔΗΜΟΣ ΚΥΘΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΕΑΣ-ΚΥΘΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΥΘΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9284;official_municipality_name="ΔΗΜΟΣ ΚΩ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΩ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΔΙΚΑΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9284;official_municipality_name="ΔΗΜΟΣ ΚΩ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΩ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΩ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9284;official_municipality_name="ΔΗΜΟΣ ΚΩ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΩ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΗΡΑΚΛΕΙΔΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9285;official_municipality_name="ΔΗΜΟΣ ΝΙΣΥΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΚΩ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΙΣΥΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9286;official_municipality_name="ΔΗΜΟΣ ΚΙΜΩΛΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΗΛΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΙΜΩΛΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9287;official_municipality_name="ΔΗΜΟΣ ΜΗΛΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΗΛΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΗΛΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9288;official_municipality_name="ΔΗΜΟΣ ΣΕΡΙΦΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΗΛΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΕΡΙΦΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9289;official_municipality_name="ΔΗΜΟΣ ΣΙΦΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΗΛΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΙΦΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9290;official_municipality_name="ΔΗΜΟΣ ΜΥΚΟΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΜΥΚΟΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΥΚΟΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9291;official_municipality_name="ΔΗΜΟΣ ΑΜΟΡΓΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΑΞΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΜΟΡΓΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9292;official_municipality_name="ΔΗΜΟΣ ΝΑΞΟΥ ΜΙΚΡΩΝ ΚΥΚΛΑΔΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΑΞΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΔΟΝΟΥΣΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9292;official_municipality_name="ΔΗΜΟΣ ΝΑΞΟΥ ΜΙΚΡΩΝ ΚΥΚΛΑΔΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΑΞΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΧΟΙΝΟΥΣΣΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9292;official_municipality_name="ΔΗΜΟΣ ΝΑΞΟΥ ΜΙΚΡΩΝ ΚΥΚΛΑΔΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΑΞΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΗΡΑΚΛΕΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9292;official_municipality_name="ΔΗΜΟΣ ΝΑΞΟΥ ΜΙΚΡΩΝ ΚΥΚΛΑΔΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΑΞΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΥΦΟΝΗΣΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9292;official_municipality_name="ΔΗΜΟΣ ΝΑΞΟΥ ΜΙΚΡΩΝ ΚΥΚΛΑΔΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΑΞΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΑΞΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9292;official_municipality_name="ΔΗΜΟΣ ΝΑΞΟΥ ΜΙΚΡΩΝ ΚΥΚΛΑΔΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΝΑΞΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΔΡΥΜΑΛΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9293;official_municipality_name="ΔΗΜΟΣ ΑΝΤΙΠΑΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΠΑΡΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΤΙΠΑΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9294;official_municipality_name="ΔΗΜΟΣ ΠΑΡΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΠΑΡΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΑΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9295;official_municipality_name="ΔΗΜΟΣ ΜΕΓΙΣΤΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΕΓΙΣΤΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9296;official_municipality_name="ΔΗΜΟΣ ΡΟΔΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΟΤΙΑΣ ΡΟΔΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9296;official_municipality_name="ΔΗΜΟΣ ΡΟΔΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΡΟΔΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9296;official_municipality_name="ΔΗΜΟΣ ΡΟΔΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΙΑΛΥΣΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9296;official_municipality_name="ΔΗΜΟΣ ΡΟΔΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΕΤΑΛΟΥΔΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9296;official_municipality_name="ΔΗΜΟΣ ΡΟΔΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΜΕΙΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9296;official_municipality_name="ΔΗΜΟΣ ΡΟΔΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΙΝΔΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9296;official_municipality_name="ΔΗΜΟΣ ΡΟΔΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΡΧΑΓΓΕΛΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9296;official_municipality_name="ΔΗΜΟΣ ΡΟΔΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΛΛΙΘΕΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9296;official_municipality_name="ΔΗΜΟΣ ΡΟΔΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΦΑΝΤΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9296;official_municipality_name="ΔΗΜΟΣ ΡΟΔΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΤΑΒΥΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9297;official_municipality_name="ΔΗΜΟΣ ΣΥΜΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΥΜΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9298;official_municipality_name="ΔΗΜΟΣ ΤΗΛΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΗΛΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9299;official_municipality_name="ΔΗΜΟΣ ΧΑΛΚΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΟΔΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΧΑΛΚΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9300;official_municipality_name="ΔΗΜΟΣ ΣΥΡΟΥ-ΕΡΜΟΥΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΣΥΡΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΡΜΟΥΠΟΛΕΩΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9300;official_municipality_name="ΔΗΜΟΣ ΣΥΡΟΥ-ΕΡΜΟΥΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΣΥΡΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΩ ΣΥΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9300;official_municipality_name="ΔΗΜΟΣ ΣΥΡΟΥ-ΕΡΜΟΥΠΟΛΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΣΥΡΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΟΣΕΙΔΩΝΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9301;official_municipality_name="ΔΗΜΟΣ ΤΗΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΤΗΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΗΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9301;official_municipality_name="ΔΗΜΟΣ ΤΗΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΤΗΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΑΝΟΡΜΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9301;official_municipality_name="ΔΗΜΟΣ ΤΗΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΤΗΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΞΩΜΒΟΥΡΓΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9302;official_municipality_name="ΔΗΜΟΣ ΑΡΧΑΝΩΝ - ΑΣΤΕΡΟΥΣΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΡΧΑΝΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9302;official_municipality_name="ΔΗΜΟΣ ΑΡΧΑΝΩΝ - ΑΣΤΕΡΟΥΣΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΙΚΟΥ ΚΑΖΑΝΤΖΑΚΗ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9302;official_municipality_name="ΔΗΜΟΣ ΑΡΧΑΝΩΝ - ΑΣΤΕΡΟΥΣΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΣΤΕΡΟΥΣΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9303;official_municipality_name="ΔΗΜΟΣ ΒΙΑΝΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΙΑΝΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9304;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΑΣ ΒΑΡΒΑΡΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9304;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΟΡΤΥΝΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9304;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΦΙΝΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9304;official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΡΟΥΒΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9305;official_municipality_name="ΔΗΜΟΣ ΗΡΑΚΛΕΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΗΡΑΚΛΕΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9305;official_municipality_name="ΔΗΜΟΣ ΗΡΑΚΛΕΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΕΜΕΝΟΥΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9305;official_municipality_name="ΔΗΜΟΣ ΗΡΑΚΛΕΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΑΛΙΑΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9305;official_municipality_name="ΔΗΜΟΣ ΗΡΑΚΛΕΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΑΣ ΑΛΙΚΑΡΝΑΣΣΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9305;official_municipality_name="ΔΗΜΟΣ ΗΡΑΚΛΕΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΟΡΓΟΛΑΪΝΗ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9306;official_municipality_name="ΔΗΜΟΣ ΜΑΛΕΒΙΖΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΑΖΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9306;official_municipality_name="ΔΗΜΟΣ ΜΑΛΕΒΙΖΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΥΛΙΣΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9306;official_municipality_name="ΔΗΜΟΣ ΜΑΛΕΒΙΖΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΡΟΥΣΩΝΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9307;official_municipality_name="ΔΗΜΟΣ ΜΙΝΩΑ ΠΕΔΙΑΔΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΣΤΕΛΛΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9307;official_municipality_name="ΔΗΜΟΣ ΜΙΝΩΑ ΠΕΔΙΑΔΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΘΡΑΨΑΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9307;official_municipality_name="ΔΗΜΟΣ ΜΙΝΩΑ ΠΕΔΙΑΔΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΡΚΑΛΟΧΩΡΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9308;official_municipality_name="ΔΗΜΟΣ ΦΑΙΣΤΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΖΑΡΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9308;official_municipality_name="ΔΗΜΟΣ ΦΑΙΣΤΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΟΙΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9308;official_municipality_name="ΔΗΜΟΣ ΦΑΙΣΤΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΤΥΜΠΑΚΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9309;official_municipality_name="ΔΗΜΟΣ ΧΕΡΣΟΝΗΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΧΕΡΣΟΝΗΣΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9309;official_municipality_name="ΔΗΜΟΣ ΧΕΡΣΟΝΗΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΛΛΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9309;official_municipality_name="ΔΗΜΟΣ ΧΕΡΣΟΝΗΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΠΙΣΚΟΠΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9309;official_municipality_name="ΔΗΜΟΣ ΧΕΡΣΟΝΗΣΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΗΡΑΚΛΕΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΟΥΒΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9310;official_municipality_name="ΔΗΜΟΣ ΑΓΙΟΥ ΝΙΚΟΛΑΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΣΙΘΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΓΙΟΥ ΝΙΚΟΛΑΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9310;official_municipality_name="ΔΗΜΟΣ ΑΓΙΟΥ ΝΙΚΟΛΑΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΣΙΘΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΑΠΟΛΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9310;official_municipality_name="ΔΗΜΟΣ ΑΓΙΟΥ ΝΙΚΟΛΑΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΣΙΘΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΡΑΧΑΣΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9311;official_municipality_name="ΔΗΜΟΣ ΙΕΡΑΠΕΤΡΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΣΙΘΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΙΕΡΑΠΕΤΡΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9311;official_municipality_name="ΔΗΜΟΣ ΙΕΡΑΠΕΤΡΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΣΙΘΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΑΚΡΥ ΓΙΑΛΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9312;official_municipality_name="ΔΗΜΟΣ ΟΡΟΠΕΔΙΟΥ ΛΑΣΙΘΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΣΙΘΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΟΡΟΠΕΔΙΟΥ ΛΑΣΙΘΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9313;official_municipality_name="ΔΗΜΟΣ ΣΗΤΕΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΣΙΘΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΕΥΚΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9313;official_municipality_name="ΔΗΜΟΣ ΣΗΤΕΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΣΙΘΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΙΤΑΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9313;official_municipality_name="ΔΗΜΟΣ ΣΗΤΕΙΑΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΛΑΣΙΘΙΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΗΤΕΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9314;official_municipality_name="ΔΗΜΟΣ ΑΓΙΟΥ ΒΑΣΙΛΕΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΑΜΠΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9314;official_municipality_name="ΔΗΜΟΣ ΑΓΙΟΥ ΒΑΣΙΛΕΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΦΟΙΝΙΚΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9315;official_municipality_name="ΔΗΜΟΣ ΑΜΑΡΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΥΒΡΙΤΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9315;official_municipality_name="ΔΗΜΟΣ ΑΜΑΡΙΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΥΡΗΤΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9316;official_municipality_name="ΔΗΜΟΣ ΑΝΩΓΕΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΩΓΕΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9317;official_municipality_name="ΔΗΜΟΣ ΜΥΛΟΠΟΤΑΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΖΩΝΙΑΝΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9317;official_municipality_name="ΔΗΜΟΣ ΜΥΛΟΠΟΤΑΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΕΡΟΠΟΤΑΜΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9317;official_municipality_name="ΔΗΜΟΣ ΜΥΛΟΠΟΤΑΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΥΛΟΥΚΩΝΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9318;official_municipality_name="ΔΗΜΟΣ ΡΕΘΥΜΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΛΑΠΠΑΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9318;official_municipality_name="ΔΗΜΟΣ ΡΕΘΥΜΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΡΚΑΔΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9318;official_municipality_name="ΔΗΜΟΣ ΡΕΘΥΜΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΡΕΘΥΜΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9318;official_municipality_name="ΔΗΜΟΣ ΡΕΘΥΜΝΗΣ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΡΕΘΥΜΝΟΥ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΙΚΗΦΟΡΟΥ ΦΩΚΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9319;official_municipality_name="ΔΗΜΟΣ ΑΠΟΚΟΡΩΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΡΥΟΝΕΡΙΔΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9319;official_municipality_name="ΔΗΜΟΣ ΑΠΟΚΟΡΩΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΡΜΕΝΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9319;official_municipality_name="ΔΗΜΟΣ ΑΠΟΚΟΡΩΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΣΗ ΓΩΝΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9319;official_municipality_name="ΔΗΜΟΣ ΑΠΟΚΟΡΩΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΑΜΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9319;official_municipality_name="ΔΗΜΟΣ ΑΠΟΚΟΡΩΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΦΡΕ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9319;official_municipality_name="ΔΗΜΟΣ ΑΠΟΚΟΡΩΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΕΩΡΓΙΟΥΠΟΛΕΩΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9320;official_municipality_name="ΔΗΜΟΣ ΓΑΥΔΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΓΑΥΔΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9321;official_municipality_name="ΔΗΜΟΣ ΚΑΝΤΑΝΟΥ - ΣΕΛΙΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΕΛΕΚΑΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9321;official_municipality_name="ΔΗΜΟΣ ΚΑΝΤΑΝΟΥ - ΣΕΛΙΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΑΝΤΑΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9321;official_municipality_name="ΔΗΜΟΣ ΚΑΝΤΑΝΟΥ - ΣΕΛΙΝΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΝΑΤΟΛΙΚΟΥ ΣΕΛΙΝΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9322;official_municipality_name="ΔΗΜΟΣ ΚΙΣΣΑΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΥΘΗΜΝΗΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9322;official_municipality_name="ΔΗΜΟΣ ΚΙΣΣΑΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΙΝΑΧΩΡΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9322;official_municipality_name="ΔΗΜΟΣ ΚΙΣΣΑΜΟΥ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΙΣΣΑΜΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9323;official_municipality_name="ΔΗΜΟΣ ΠΛΑΤΑΝΙΑ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΒΟΥΚΟΛΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9323;official_municipality_name="ΔΗΜΟΣ ΠΛΑΤΑΝΙΑ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΜΟΥΣΟΥΡΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9323;official_municipality_name="ΔΗΜΟΣ ΠΛΑΤΑΝΙΑ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΠΛΑΤΑΝΙΑ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9323;official_municipality_name="ΔΗΜΟΣ ΠΛΑΤΑΝΙΑ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΟΛΥΜΒΑΡΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9324;official_municipality_name="ΔΗΜΟΣ ΣΦΑΚΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΦΑΚΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9325;official_municipality_name="ΔΗΜΟΣ ΧΑΝΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΝΕΑΣ ΚΥΔΩΝΙΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9325;official_municipality_name="ΔΗΜΟΣ ΧΑΝΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΑΚΡΩΤΗΡΙΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9325;official_municipality_name="ΔΗΜΟΣ ΧΑΝΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΕΛΕΥΘΕΡΙΟΥ ΒΕΝΙΖΕΛΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9325;official_municipality_name="ΔΗΜΟΣ ΧΑΝΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΚΕΡΑΜΙΩΝ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9325;official_municipality_name="ΔΗΜΟΣ ΧΑΝΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΣΟΥΔΑΣ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9325;official_municipality_name="ΔΗΜΟΣ ΧΑΝΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΘΕΡΙΣΟΥ"]
[municipality=http://geo.linkedopendata.gr/gag/id/9325;official_municipality_name="ΔΗΜΟΣ ΧΑΝΙΩΝ";official_regional_unit_name="ΠΕΡΙΦΕΡΕΙΑΚΗ ΕΝΟΤΗΤΑ ΧΑΝΙΩΝ";official_municipality_unit_name="ΔHMOTIKH ΕNOTHTA ΧΑΝΙΩΝ"]

```
* For each municipality of the region Crete with population more than 5,000 people, give its official name and its population.

```SQL
PREFIX gag: <http://geo.linkedopendata.gr/gag/ontology/>
SELECT ?official_municipality_name ?population
WHERE { ?region rdf:type gag:Περιφέρεια .
        ?region gag:έχει_επίσημο_όνομα \"ΠΕΡΙΦΕΡΕΙΑ ΚΡΗΤΗΣ\" .
        ?regional_unit gag:ανήκει_σε ?region .
        ?municipality gag:ανήκει_σε ?regional_unit .
        ?municipality gag:έχει_πληθυσμό ?population .
        FILTER(?population < 5000)
        ?municipality gag:έχει_επίσημο_όνομα ?official_municipality_name .}
```
Results:
```
[official_municipality_name="ΔΗΜΟΣ ΟΡΟΠΕΔΙΟΥ ΛΑΣΙΘΙΟΥ";population="3152"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΣΦΑΚΙΩΝ";population="2446"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΓΑΥΔΟΥ";population="98"^^<http://www.w3.org/2001/XMLSchema#integer>]
[official_municipality_name="ΔΗΜΟΣ ΑΝΩΓΕΙΩΝ";population="2507"^^<http://www.w3.org/2001/XMLSchema#integer>]
```
* For each municipality of Crete for which we have no seat (έδρα) information in the dataset, give its official name.
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
Results:
```
[official_municipality_name="ΔΗΜΟΣ ΑΡΧΑΝΩΝ - ΑΣΤΕΡΟΥΣΙΩΝ"]
[official_municipality_name="ΔΗΜΟΣ ΜΑΛΕΒΙΖΙΟΥ"]
[official_municipality_name="ΔΗΜΟΣ ΦΑΙΣΤΟΥ"]
[official_municipality_name="ΔΗΜΟΣ ΜΙΝΩΑ ΠΕΔΙΑΔΑΣ"]
[official_municipality_name="ΔΗΜΟΣ ΒΙΑΝΝΟΥ"]
[official_municipality_name="ΔΗΜΟΣ ΓΟΡΤΥΝΑΣ"]
[official_municipality_name="ΔΗΜΟΣ ΧΕΡΣΟΝΗΣΟΥ"]
[official_municipality_name="ΔΗΜΟΣ ΠΛΑΤΑΝΙΑ"]
[official_municipality_name="ΔΗΜΟΣ ΣΦΑΚΙΩΝ"]
[official_municipality_name="ΔΗΜΟΣ ΚΙΣΣΑΜΟΥ"]
[official_municipality_name="ΔΗΜΟΣ ΓΑΥΔΟΥ"]
[official_municipality_name="ΔΗΜΟΣ ΑΠΟΚΟΡΩΝΟΥ"]
[official_municipality_name="ΔΗΜΟΣ ΚΑΝΤΑΝΟΥ - ΣΕΛΙΝΟΥ"]
[official_municipality_name="ΔΗΜΟΣ ΑΜΑΡΙΟΥ"]
[official_municipality_name="ΔΗΜΟΣ ΑΝΩΓΕΙΩΝ"]
[official_municipality_name="ΔΗΜΟΣ ΑΓΙΟΥ ΒΑΣΙΛΕΙΟΥ"]
[official_municipality_name="ΔΗΜΟΣ ΜΥΛΟΠΟΤΑΜΟΥ"]
```
