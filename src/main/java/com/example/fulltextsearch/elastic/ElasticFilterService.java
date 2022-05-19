package com.example.fulltextsearch.elastic;

import org.elasticsearch.client.RestHighLevelClient;

public record ElasticFilterService(RestHighLevelClient restHighLevelClient) {

}
