package com.payment.persistence.mapper;

import com.payment.model.Transaction;
import com.payment.persistence.entity.TransactionJpa;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private final PaymentMethodMapper paymentMethodMapper;

    public TransactionMapper(PaymentMethodMapper paymentMethodMapper) {
        this.paymentMethodMapper = paymentMethodMapper;
    }

    public TransactionJpa toEntity(Transaction tx) {
        return TransactionJpa.builder()
                .id(tx.id())
                .amount(tx.amount())
                .paymentMethod(paymentMethodMapper.toEntity(tx.paymentMethod()))
                .build();
    }
}
