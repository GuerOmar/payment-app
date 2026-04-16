package com.payment.model;

import com.payment.model.enums.PaymentMethodType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PaymentMethod implements Serializable {
    @Serial
    private static final long serialVersionUID = -8805126033654232310L;

    private Long id;
    private BigDecimal balance;
    private PaymentMethodType type;
}
