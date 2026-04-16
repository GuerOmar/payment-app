package com.payment.api.dto;

import java.math.BigDecimal;

public record PaymentRequest(Long paymentId, String paymentMethodType, BigDecimal paymentAmount) {
}
