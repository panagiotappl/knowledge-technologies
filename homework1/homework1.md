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
