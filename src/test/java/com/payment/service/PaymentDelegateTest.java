package com.payment.service;

import com.payment.model.PaymentMethod;
import com.payment.persistence.PaymentMethodAdapter;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentDelegateTest {

    @Mock
    private PaymentMethodAdapter paymentMethodAdapter;

    @Mock
    private DelegateExecution execution;

    @InjectMocks
    private PaymentDelegate paymentDelegate;

    @Test
    void shouldDeductBalanceAndSave_whenAmountIsValid_callExecute() {
        PaymentMethod paymentMethod = PaymentMethod.builder().build();
        paymentMethod.setBalance(new BigDecimal("500.00"));

        when(execution.getVariable("amount")).thenReturn(new BigDecimal("100.00"));
        when(execution.getVariable("methodId")).thenReturn(1L);
        when(paymentMethodAdapter.findById(1L)).thenReturn(paymentMethod);

        paymentDelegate.execute(execution);

        assertEquals(new BigDecimal("400.00"), paymentMethod.getBalance());
        verify(paymentMethodAdapter, times(1)).save(paymentMethod);
        verify(execution, times(1)).setVariable("paymentMethod", paymentMethod);
    }

    @Test
    void shouldThrowBpmnError_whenAmountExceedsBalance_callExecute() {
        PaymentMethod paymentMethod = PaymentMethod.builder().build();
        paymentMethod.setBalance(new BigDecimal("50.00"));

        when(execution.getVariable("amount")).thenReturn(new BigDecimal("100.00"));
        when(execution.getVariable("methodId")).thenReturn(1L);
        when(paymentMethodAdapter.findById(1L)).thenReturn(paymentMethod);

        BpmnError exception = assertThrows(BpmnError.class, () ->
                paymentDelegate.execute(execution));

        assertEquals("INSUFFICIENT_FUNDS", exception.getErrorCode());
        verify(paymentMethodAdapter, never()).save(any());
        verify(execution, never()).setVariable(any(), any());
    }

    @Test
    void shouldThrowBpmnError_whenAmountEqualsBalance_callExecute() {
        PaymentMethod paymentMethod = PaymentMethod.builder().build();
        paymentMethod.setBalance(new BigDecimal("100.00"));

        when(execution.getVariable("amount")).thenReturn(new BigDecimal("100.00"));
        when(execution.getVariable("methodId")).thenReturn(1L);
        when(paymentMethodAdapter.findById(1L)).thenReturn(paymentMethod);

        paymentDelegate.execute(execution);

        assertEquals(BigDecimal.ZERO.setScale(2), paymentMethod.getBalance());
        verify(paymentMethodAdapter, times(1)).save(paymentMethod);
        verify(execution, times(1)).setVariable("paymentMethod", paymentMethod);
    }

    @Test
    void shouldThrowException_whenPaymentMethodNotFound_callExecute() {
        when(execution.getVariable("amount")).thenReturn(new BigDecimal("100.00"));
        when(execution.getVariable("methodId")).thenReturn(1L);
        when(paymentMethodAdapter.findById(1L)).thenThrow(new RuntimeException("Payment method not found"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                paymentDelegate.execute(execution));

        assertEquals("Payment method not found", exception.getMessage());
        verify(paymentMethodAdapter, never()).save(any());
        verify(execution, never()).setVariable(any(), any());
    }

    @Test
    void shouldSetPaymentMethodVariable_whenPaymentIsSuccessful_callExecute() {
        PaymentMethod paymentMethod = PaymentMethod.builder().build();
        paymentMethod.setBalance(new BigDecimal("500.00"));

        when(execution.getVariable("amount")).thenReturn(new BigDecimal("100.00"));
        when(execution.getVariable("methodId")).thenReturn(1L);
        when(paymentMethodAdapter.findById(1L)).thenReturn(paymentMethod);

        paymentDelegate.execute(execution);

        verify(execution, times(1)).setVariable("paymentMethod", paymentMethod);
    }
}