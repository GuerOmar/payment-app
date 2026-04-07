package com.entretien.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

    @GetMapping
    public String showPaymentPage() {
        return "payment-page";
    }
}
