package com.payment.persistence.mapper;

import com.payment.model.PaymentMethod;
import com.payment.persistence.entity.PaymentMethodJpa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    @Mapping(target = "type", expression = "java(paymentMethod.getType().name())")
    PaymentMethodJpa toEntity(PaymentMethod paymentMethod);

    @Mapping(target = "type", expression = "java(com.payment.model.enums.PaymentMethodType.findByName(paymentMethodJpa.getType()))")
    PaymentMethod toModel(PaymentMethodJpa paymentMethodJpa);

}