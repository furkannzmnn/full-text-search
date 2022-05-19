package com.example.fulltextsearch.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.Version;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public record ElasticCreateIndexService(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {

    public <C> void createIndex(String indexName, String id, C source) throws IOException {
        String json = objectMapper.writeValueAsString(source);

        IndexRequest indexRequest = new IndexRequest();
        indexRequest.id(id);
        indexRequest.source(json, XContentType.JSON);
        indexRequest.index(indexName);

        this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

}
