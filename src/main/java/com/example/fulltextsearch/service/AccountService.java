package com.example.fulltextsearch.service;

import com.example.fulltextsearch.elastic.ElasticCreateIndexService;
import com.example.fulltextsearch.model.Account;
import com.example.fulltextsearch.model.User;
import com.example.fulltextsearch.repository.AccountRepository;
import org.apache.http.entity.ContentType;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Random;

@Service
public record AccountService(AccountRepository accountRepository, ElasticCreateIndexService elasticCreateIndexService) {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private static final String ACCOUNT_INDEX_NAME = "account";

    public ResponseEntity<?> createAccount(User user) throws IOException {
        Account to = Account.builder()
                .user(user)
                .name(user.getUserName() + new Random().nextDouble())
                .build();

        Account account = accountRepository.save(to);

        try {
            elasticCreateIndexService.createIndex(ACCOUNT_INDEX_NAME, account.getId().toString(), account);
        }catch (Exception e) {
            LOGGER.error("ELASTIC ERROR WHEN CREATE INDEX");
            accountRepository.deleteById(account.getId());
            elasticCreateIndexService.restHighLevelClient().delete(new DeleteRequest(ACCOUNT_INDEX_NAME, account.getId().toString()), RequestOptions.DEFAULT);
        }
        return ResponseEntity.accepted()
                .contentType(MediaType.ALL)
                .body(account);

    }

}
