package com.payment.web;

import com.payment.model.PaymentMethod;
import com.payment.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public String showPaymentPage(Model model, @AuthenticationPrincipal User user) {
        List<PaymentMethod> paymentMethods = paymentService.findAllByUsername(user.getUsername());
        model.addAttribute("paymentMethods", paymentMethods);
        return "payment-page";
    }
}
