package com.payment.model.enums;

public enum PaymentMethodType {
    CREDIT_CARD,
    PAYPAL,
    CRYPTO;

    public static PaymentMethodType findByName(String name) {
        for (PaymentMethodType paymentMethodType : PaymentMethodType.values()) {
            if (paymentMethodType.name().equals(name))
                return paymentMethodType;
        }
        throw new IllegalArgumentException("Unknown type");
    }
}
