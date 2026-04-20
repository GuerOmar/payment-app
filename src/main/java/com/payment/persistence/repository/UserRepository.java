package com.payment.persistence.repository;

import com.payment.persistence.entity.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserJpa, Long> {

    @Query("from UserJpa u where u.username = :username")
    Optional<UserJpa> findByUsername(String username);
}
