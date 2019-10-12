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
