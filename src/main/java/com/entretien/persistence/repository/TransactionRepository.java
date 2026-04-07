package com.entretien.persistence.repository;

import com.entretien.persistence.entity.TransactionJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionJpa, Long> {
}
