package com.payment.service.payment;


import com.payment.model.enums.PaymentMethodType;

import java.math.BigDecimal;

public interface PaymentService {
    void processPayment(BigDecimal amount, Long paymentMethodId, PaymentMethodType paymentMethodType);
}
