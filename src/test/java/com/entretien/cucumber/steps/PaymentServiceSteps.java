package com.entretien.cucumber.steps;

import com.entretien.model.PaymentMethod;
import com.entretien.model.enums.PaymentMethodType;
import com.entretien.persistence.PaymentMethodAdapter;
import com.entretien.service.payment.PaymentServiceServiceImpl;
import com.entretien.service.transaction.TransactionServiceImpl;
import io.cucumber.java.en.*;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceSteps {

    private final PaymentMethodAdapter paymentMethodAdapter = Mockito.mock(PaymentMethodAdapter.class);
    private final TransactionServiceImpl transactionService = Mockito.mock(TransactionServiceImpl.class);
    private final PaymentServiceServiceImpl paymentService = new PaymentServiceServiceImpl(paymentMethodAdapter, transactionService);

    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private RuntimeException thrownException;

    @Given("a payment method with balance {double}")
    public void aPaymentMethodWithBalance(double balance) {
        paymentMethod = PaymentMethod.builder()
                .balance(BigDecimal.valueOf(balance))
                .build();
    }

    @And("a payment amount of {double}")
    public void aPaymentAmountOf(double paymentAmount) {
        amount = BigDecimal.valueOf(paymentAmount);
    }

    @When("the payment is processed with method id {long} and type {word}")
    public void thePaymentIsProcessed(Long methodId, String type) {
        when(paymentMethodAdapter.findById(methodId)).thenReturn(paymentMethod);
        try {
            paymentService.processPayment(amount, methodId, PaymentMethodType.findByName(type));
        } catch (RuntimeException e) {
            thrownException = e;
        }
    }

    @Then("the payment method balance should be {double}")
    public void thePaymentMethodBalanceShouldBe(double expectedBalance) {
        assertEquals(BigDecimal.valueOf(expectedBalance), paymentMethod.getBalance());
    }

    @Then("a payment service exception should be thrown with message {string}")
    public void aRuntimeExceptionShouldBeThrownWithMessage(String message) {
        assertNotNull(thrownException);
        assertEquals(message, thrownException.getMessage());
    }
}