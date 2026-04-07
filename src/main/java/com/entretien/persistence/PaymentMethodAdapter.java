package com.entretien.persistence;

import com.entretien.model.PaymentMethod;
import com.entretien.persistence.entity.PaymentMethodJpa;
import com.entretien.persistence.mapper.PaymentMethodMapper;
import com.entretien.persistence.repository.PaymentMethodRepository;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodAdapter {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    public PaymentMethodAdapter(PaymentMethodRepository paymentMethodRepository, PaymentMethodMapper paymentMethodMapper) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodMapper = paymentMethodMapper;
    }

    public PaymentMethod findById(Long id) {
        PaymentMethodJpa paymentMethodJpa =  paymentMethodRepository.findById(id).orElseThrow(RuntimeException::new);
        return paymentMethodMapper.toModel(paymentMethodJpa);
    }

    public void save(PaymentMethod paymentMethod) {
        paymentMethodRepository.save(paymentMethodMapper.toEntity(paymentMethod));
    }
}
