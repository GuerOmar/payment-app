package com.payment.service.transaction;

import com.payment.model.PaymentMethod;
import com.payment.model.Transaction;
import com.payment.persistence.TransactionAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;


@Service
public class TransactionServiceImpl implements TransactionService{

    TransactionAdapter transactionAdapter;

    public TransactionServiceImpl(TransactionAdapter transactionAdapter) {
        this.transactionAdapter = transactionAdapter;
    }

    @Transactional
    public void createTransaction(BigDecimal amount, PaymentMethod paymentMethod) {
        Transaction tx = new Transaction(UUID.randomUUID().toString(), amount, paymentMethod);
        transactionAdapter.save(tx);
    }
}