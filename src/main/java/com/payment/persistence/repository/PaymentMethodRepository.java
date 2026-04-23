package com.payment.persistence.repository;

import com.payment.persistence.entity.PaymentMethodJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodJpa, Long> {

    @Query("from PaymentMethodJpa p where p.user.username = :username")
    List<PaymentMethodJpa> findAllByUsername(String username);
}
