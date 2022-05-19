package com.example.fulltextsearch.service;

import com.example.fulltextsearch.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public record BankAccountCreateService(UserService userService, AccountService accountService) {
    public ResponseEntity<?> createBankAccount(Long userId) throws IOException {
        User user = userService.findBy(userId).orElseThrow(() -> new RuntimeException("user.not.found"));
        return accountService.createAccount(user);
    }

}
