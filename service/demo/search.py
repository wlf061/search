baseQuery = {
  "query": {
      "multi_match": {
          "query": "test",
          "fields": ["prod_name","prod_desc","prod_merchant"]
       }
   },
  "rescore": {
      "query": {
        "rescore_query": {
            "sltr": {
                "params": {
                    "keywords": ""
                },
                "model": "",
            }
         }
      }
   }
}

def ltrQuery(keywords, modelName):
    import json
    baseQuery['rescore']['query']['rescore_query']['sltr']['model'] = model
    baseQuery['query']['multi_match']['query'] = keywords
    baseQuery['rescore']['query']['rescore_query']['sltr']['params']['keywords'] = keywords
    print("%s" % json.dumps(baseQuery))
    return baseQuery


if __name__ == "__main__":
    import configparser
    from sys import argv
    from utils import Elasticsearch

    es = Elasticsearch(timeout=1000)
    model = "test_6"
    if len(argv) > 2:
        model = argv[2]
    results = es.search(index='search_data', doc_type='doc', body=ltrQuery(argv[1], model))
    for result in results['hits']['hits']:
        print('%s %s %s'% (result['_source']['prod_name'], result['_source']['prod_desc'],result['_source']['prod_merchant']))

