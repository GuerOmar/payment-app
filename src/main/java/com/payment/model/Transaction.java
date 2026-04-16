package com.payment.model;

import java.math.BigDecimal;

public record Transaction(String id, BigDecimal amount, PaymentMethod paymentMethod) {
}
