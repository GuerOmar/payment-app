package com.entretien.cucumber.steps;

import com.entretien.model.PaymentMethod;
import com.entretien.persistence.PaymentMethodAdapter;
import com.entretien.service.PaymentDelegate;
import io.cucumber.java.en.*;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentDelegateSteps {

    private final PaymentMethodAdapter paymentMethodAdapter = Mockito.mock(PaymentMethodAdapter.class);
    private final DelegateExecution execution = Mockito.mock(DelegateExecution.class);
    private final PaymentDelegate paymentDelegate = new PaymentDelegate(paymentMethodAdapter);

    private PaymentMethod paymentMethod;
    private BpmnError thrownBpmnError;

    @Given("a payment method with balance {double} for delegation")
    public void aPaymentMethodWithBalanceForDelegation(double balance) {
        paymentMethod = PaymentMethod.builder()
                .balance(BigDecimal.valueOf(balance))
                .build();
    }

    @And("an execution variable amount of {double} and methodId {long}")
    public void anExecutionVariableAmountAndMethodId(double amount, Long methodId) {
        when(execution.getVariable("amount")).thenReturn(BigDecimal.valueOf(amount));
        when(execution.getVariable("methodId")).thenReturn(methodId);
        when(paymentMethodAdapter.findById(methodId)).thenReturn(paymentMethod);
    }

    @When("the delegate executes")
    public void theDelegateExecutes() {
        try {
            paymentDelegate.execute(execution);
        } catch (BpmnError e) {
            thrownBpmnError = e;
        }
    }

    @Then("the payment method balance should be updated to {double}")
    public void thePaymentMethodBalanceShouldBeUpdatedTo(double expectedBalance) {
        assertEquals(BigDecimal.valueOf(expectedBalance), paymentMethod.getBalance());
    }

    @And("the execution variable paymentMethod should be set")
    public void theExecutionVariablePaymentMethodShouldBeSet() {
        verify(execution, times(1)).setVariable("paymentMethod", paymentMethod);
    }

    @Then("a BpmnError should be thrown with error code {string}")
    public void aBpmnErrorShouldBeThrownWithErrorCode(String errorCode) {
        assertNotNull(thrownBpmnError);
        assertEquals(errorCode, thrownBpmnError.getErrorCode());
    }
}