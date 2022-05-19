package com.example.fulltextsearch.controller;

import com.example.fulltextsearch.service.BankAccountCreateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/account")
public class BankAccountCreateController {

    private final BankAccountCreateService createService;

    public BankAccountCreateController(BankAccountCreateService createService) {
        this.createService = createService;
    }

    @PostMapping("/create/{user}")
    public ResponseEntity<?> createAccount(@PathVariable Long user) throws IOException {
        return createService.createBankAccount(user);
    }
}
