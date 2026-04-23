package com.payment.persistence;

import com.payment.model.PaymentMethod;
import com.payment.persistence.entity.PaymentMethodJpa;
import com.payment.persistence.mapper.PaymentMethodMapper;
import com.payment.persistence.repository.PaymentMethodRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<PaymentMethod> findAllByUsername(String username) {
        List<PaymentMethodJpa> paymentMethods = paymentMethodRepository.findAllByUsername(username);
        return paymentMethods.stream().map(paymentMethodMapper::toModel).toList();
    }
}
