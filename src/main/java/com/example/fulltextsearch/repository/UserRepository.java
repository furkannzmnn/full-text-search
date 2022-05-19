package com.example.fulltextsearch.repository;

import com.example.fulltextsearch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
