package com.example.backend.storage;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStorage extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
