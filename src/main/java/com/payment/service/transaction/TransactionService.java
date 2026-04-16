package com.payment.service.transaction;

import com.payment.model.PaymentMethod;

import java.math.BigDecimal;

public interface TransactionService {
    void createTransaction(BigDecimal amount, PaymentMethod paymentMethod);
}
