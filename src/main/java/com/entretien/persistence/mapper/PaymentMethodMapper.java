package com.entretien.persistence.mapper;

import com.entretien.model.PaymentMethod;
import com.entretien.model.enums.PaymentMethodType;
import com.entretien.persistence.entity.PaymentMethodJpa;
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
