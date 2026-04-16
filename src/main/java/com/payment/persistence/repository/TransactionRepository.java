package com.payment.persistence.repository;

import com.payment.persistence.entity.TransactionJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionJpa, Long> {
}
