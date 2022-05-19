package com.example.fulltextsearch.service;

import com.example.fulltextsearch.elastic.ElasticFullSearch;
import com.example.fulltextsearch.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.fulltextsearch.service.UserService.USER_INDEX_NAME;

@Component
public record UserSearchService(ElasticFullSearch elasticFullSearch) implements SearchStrategy {

    public ResponseEntity<Object> listSearch() throws IOException {
        return ResponseEntity.ok(
                elasticFullSearch.search(USER_INDEX_NAME, User.class)
        );
    }

}
