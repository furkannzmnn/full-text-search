package com.example.fulltextsearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id = null;

    private final String userName = null;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private final Set<Account> accounts = Set.of();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }
}
