package com.entretien.service.transaction;

import com.entretien.model.PaymentMethod;

import java.math.BigDecimal;

public interface TransactionService {
    void createTransaction(BigDecimal amount, PaymentMethod paymentMethod);
}
