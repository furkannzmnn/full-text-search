package com.example.fulltextsearch.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account")
public final class Account {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    private final String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private final User user;

    public Account(Long id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Account() {
        this.id = null;
        this.name = null;
        this.user = null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Account) obj;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public String toString() {
        return "Account[" +
                "id=" + id + ']';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, user);
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private Long id;
        private String name;
        private User user;

        private Builder() {
        }


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Account build() {
            return new Account(id, name, user);
        }
    }
}
