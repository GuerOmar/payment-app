package com.payment.persistence.mapper;

import com.payment.model.PaymentMethod;
import com.payment.model.enums.PaymentMethodType;
import com.payment.persistence.entity.PaymentMethodJpa;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodMapper {

    public PaymentMethodJpa toEntity(PaymentMethod paymentMethod) {
        return PaymentMethodJpa.builder()
                .id(paymentMethod.getId())
                .balance(paymentMethod.getBalance())
                .type(paymentMethod.getType().name())
                .build();
    }

    public PaymentMethod toModel(PaymentMethodJpa paymentMethodJpa) {
        return PaymentMethod.builder()
                .id(paymentMethodJpa.getId())
                .balance(paymentMethodJpa.getBalance())
                .type(PaymentMethodType.findByName(paymentMethodJpa.getType()))
                .build();
    }


}
