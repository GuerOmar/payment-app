package com.entretien.service.payment;

import com.entretien.model.PaymentMethod;
import com.entretien.model.enums.PaymentMethodType;
import com.entretien.persistence.PaymentMethodAdapter;
import com.entretien.service.transaction.TransactionService;
import com.entretien.service.transaction.TransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
public class PaymentServiceServiceImpl implements PaymentService {

    private final PaymentMethodAdapter paymentMethodAdapter;
    private final TransactionService transactionService;


    public PaymentServiceServiceImpl(PaymentMethodAdapter paymentMethodAdapter, TransactionServiceImpl transactionService) {
        this.paymentMethodAdapter = paymentMethodAdapter;
        this.transactionService = transactionService;
    }

    @Transactional
    @Override
    public void processPayment(BigDecimal amount, Long paymentMethodId, PaymentMethodType paymentMethodType) {
        if (BigDecimal.ZERO.compareTo(amount) > 0)
            throw new RuntimeException("Amount is negative..");
        PaymentMethod paymentMethod = paymentMethodAdapter.findById(paymentMethodId);
        if (amount.compareTo(paymentMethod.getBalance()) > 0)
            throw new RuntimeException("Not enough balance ...");
        paymentMethod.setBalance(paymentMethod.getBalance().subtract(amount));
        log.info("Pay with {} <{}> .. {}", paymentMethodType.name(), paymentMethodId,amount);
        paymentMethodAdapter.save(paymentMethod);
        transactionService.createTransaction(amount, paymentMethod);
    }
}