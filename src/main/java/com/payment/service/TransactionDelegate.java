package com.payment.service;

import com.payment.model.PaymentMethod;
import com.payment.model.Transaction;
import com.payment.persistence.TransactionAdapter;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component("transactionDelegate")
public class TransactionDelegate implements JavaDelegate {
    private final TransactionAdapter transactionAdapter;

    public TransactionDelegate(TransactionAdapter transactionAdapter) {
        this.transactionAdapter = transactionAdapter;
    }

    @Override
    public void execute(DelegateExecution execution) {
        BigDecimal amount = (BigDecimal) execution.getVariable("amount");
        PaymentMethod method = (PaymentMethod) execution.getVariable("paymentMethod");

        Transaction tx = new Transaction(UUID.randomUUID().toString(), amount, method);
        transactionAdapter.save(tx);
    }
}