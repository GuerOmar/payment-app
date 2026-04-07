package com.entretien.service;

import com.entretien.model.PaymentMethod;
import com.entretien.persistence.PaymentMethodAdapter;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("paymentDelegate")
public class PaymentDelegate implements JavaDelegate {
    private final PaymentMethodAdapter paymentMethodAdapter;

    public PaymentDelegate(PaymentMethodAdapter paymentMethodAdapter) {
        this.paymentMethodAdapter = paymentMethodAdapter;
    }

    @Override
    public void execute(DelegateExecution execution) {
        BigDecimal amount = (BigDecimal) execution.getVariable("amount");
        Long methodId = (Long) execution.getVariable("methodId");

        PaymentMethod paymentMethod = paymentMethodAdapter.findById(methodId);

        if (amount.compareTo(paymentMethod.getBalance()) > 0) {
            throw new BpmnError("INSUFFICIENT_FUNDS");
        }

        paymentMethod.setBalance(paymentMethod.getBalance().subtract(amount));
        paymentMethodAdapter.save(paymentMethod);

        // Pass the payment object (or ID) to the next step
        execution.setVariable("paymentMethod", paymentMethod);
    }
}