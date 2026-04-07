package com.entretien.service.payment;


import com.entretien.model.enums.PaymentMethodType;

import java.math.BigDecimal;

public interface PaymentService {
    void processPayment(BigDecimal amount, Long paymentMethodId, PaymentMethodType paymentMethodType);
}
