package com.testing.springsecuritytest.repository;

import com.testing.springsecuritytest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String Username);
}
