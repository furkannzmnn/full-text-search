package com.example.fulltextsearch.elastic;

import com.example.fulltextsearch.common.QueryUtil;
import com.example.fulltextsearch.dto.SourceBuilderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public record ElasticFullSearch(RestHighLevelClient restHighLevelClient, ObjectMapper mapper) {

    public <S> List<?> search(String indexName, Class<S> clazz) throws IOException {

        String[] terms = QueryUtil.getTerms("", new String[]{""});
        SearchSourceBuilder sourceBuilder = QueryUtil.buildQuery(
                SourceBuilderRequest.builder()
                        .from(0)
                        .size(100)
                        .sourceBuilder(QueryBuilders.matchAllQuery())
                        .sort(Map.of("id", SortOrder.DESC))
                        .build()
        );


        SearchRequest request = new SearchRequest(indexName);
        request.source(sourceBuilder);

        SearchResponse response = this.restHighLevelClient.search(request, RequestOptions.DEFAULT);

        return Stream.of(response.getHits().getHits())
                .map(hit -> read(hit, clazz))
                .map(QueryResponse::new)
                .collect(Collectors.toList());
    }



    private <S> S read(SearchHit hit, Class<S> clazz) {
        try {
            return mapper.readValue(hit.getSourceAsString(), clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }


    record QueryResponse<S>(S hits) {
        public List<S> getHits() {
            return List.of(hits);
        }
    }

    record RecommendationResponse<R>(List<R> recommendations) {
    }
}
