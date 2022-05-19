package com.example.fulltextsearch.service;

import com.example.fulltextsearch.elastic.ElasticCreateIndexService;
import com.example.fulltextsearch.model.User;
import com.example.fulltextsearch.repository.UserRepository;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public record UserService(UserRepository userRepository, ElasticCreateIndexService elasticCreateIndexService) {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    static final String USER_INDEX_NAME = "user-info-ok";

    public ResponseEntity<Object> createUser(User user) throws IOException {
        User fromDB = userRepository.save(user);

        try {
            elasticCreateIndexService.createIndex(USER_INDEX_NAME, fromDB.getId().toString(), user);
        }catch (Exception e) {
            LOGGER.error("ELASTIC ERROR WHEN CREATE INDEX");
            LOGGER.info(e.getMessage());
            userRepository.deleteById(fromDB.getId());
            elasticCreateIndexService.restHighLevelClient().delete(new DeleteRequest(USER_INDEX_NAME, fromDB.getId().toString()), RequestOptions.DEFAULT);
        }

        return ResponseEntity.accepted().build();
    }

    public Optional<User> findBy(Long userId) {
        return userRepository.findById(userId);
    }

}
