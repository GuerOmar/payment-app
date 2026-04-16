package com.payment.persistence.repository;

import com.payment.persistence.entity.PaymentMethodJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodJpa, Long> {
}
