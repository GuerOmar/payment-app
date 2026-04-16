package com.payment.api;

import com.payment.api.dto.PaymentRequest;
import com.payment.service.payment.PaymentService;
import lombok.AllArgsConstructor;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pay")
@AllArgsConstructor
public class PaymentRestController {

    private final PaymentService paymentService;
    private final RuntimeService runtimeService;

//    @PostMapping
//    public ResponseEntity<Void> pay(@RequestBody PaymentRequest paymentRequest) {
//        paymentService.processPayment(paymentRequest.paymentAmount(), paymentRequest.paymentId(), PaymentMethodType.findByName(paymentRequest.paymentMethodType()));
//        return ResponseEntity.ok().build();
//    }

    @PostMapping
    public ResponseEntity<String> pay(@RequestBody PaymentRequest paymentRequest) {
        // Initial validation can stay here
        if (paymentRequest.paymentAmount().compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().build();
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("amount", paymentRequest.paymentAmount());
        variables.put("methodId", paymentRequest.paymentId());
        variables.put("type", paymentRequest.paymentMethodType());

        // Start the process by the ID defined in your XML
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("paymentProcess", variables);

        return ResponseEntity.ok("Process Started: " + processInstance.getId());
    }
}
