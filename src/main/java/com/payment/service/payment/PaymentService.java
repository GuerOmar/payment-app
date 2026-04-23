package com.payment.service.payment;


import com.payment.model.PaymentMethod;
import com.payment.model.enums.PaymentMethodType;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    void processPayment(BigDecimal amount, Long paymentMethodId, PaymentMethodType paymentMethodType);
    List<PaymentMethod> findAllByUsername(String username);
}
