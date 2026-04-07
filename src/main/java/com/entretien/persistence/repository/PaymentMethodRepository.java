package com.entretien.persistence.repository;

import com.entretien.persistence.entity.PaymentMethodJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodJpa, Long> {
}
