package com.payment.persistence.mapper;

import com.payment.model.Transaction;
import com.payment.persistence.entity.TransactionJpa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PaymentMethodMapper.class)
public interface TransactionMapper {

    @Mapping(target = "paymentMethod", source = "paymentMethod")
    TransactionJpa toEntity(Transaction tx);

}
