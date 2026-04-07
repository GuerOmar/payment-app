package com.entretien.service.payment;

import com.entretien.model.PaymentMethod;
import com.entretien.model.enums.PaymentMethodType;
import com.entretien.persistence.PaymentMethodAdapter;
import com.entretien.service.transaction.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentMethodAdapter paymentMethodAdapter;

    @Mock
    private TransactionServiceImpl transactionService;

    @InjectMocks
    private PaymentServiceServiceImpl paymentService;

    private final PaymentMethod paymentMethod = PaymentMethod.builder().balance(BigDecimal.valueOf(500)).build();

    @Test
    void shouldDeductBalance_whenAmountIsValid_callProcessPayment() {
        when(paymentMethodAdapter.findById(1L)).thenReturn(paymentMethod);

        paymentService.processPayment(BigDecimal.valueOf(100), 1L, PaymentMethodType.CREDIT_CARD);

        assertEquals(BigDecimal.valueOf(400), paymentMethod.getBalance());
        verify(paymentMethodAdapter, times(1)).save(paymentMethod);
        verify(transactionService, times(1)).createTransaction(BigDecimal.valueOf(100), paymentMethod);
    }

    @Test
    void shouldThrowException_whenAmountIsNegative_callProcessPayment() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                paymentService.processPayment(BigDecimal.valueOf(-50), 1L, PaymentMethodType.CREDIT_CARD));

        assertEquals("Amount is negative..", exception.getMessage());
        verifyNoInteractions(paymentMethodAdapter);
        verifyNoInteractions(transactionService);
    }

    @Test
    void shouldThrowException_whenAmountExceedsBalance_callProcessPayment() {
        when(paymentMethodAdapter.findById(1L)).thenReturn(paymentMethod);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                paymentService.processPayment(new BigDecimal("600.00"), 1L, PaymentMethodType.CREDIT_CARD));

        assertEquals("Not enough balance ...", exception.getMessage());
        verify(paymentMethodAdapter, never()).save(any());
        verify(transactionService, never()).createTransaction(any(), any());
    }

    @Test
    void shouldThrowException_whenAmountEqualsZero_callProcessPayment() {
        when(paymentMethodAdapter.findById(1L)).thenReturn(paymentMethod);

        paymentService.processPayment(BigDecimal.ZERO, 1L, PaymentMethodType.CREDIT_CARD);

        assertEquals(BigDecimal.valueOf(500), paymentMethod.getBalance());
        verify(paymentMethodAdapter, times(1)).save(paymentMethod);
        verify(transactionService, times(1)).createTransaction(BigDecimal.ZERO, paymentMethod);
    }

    @Test
    void shouldDeductExactBalance_whenAmountEqualsBalance_callProcessPayment() {
        when(paymentMethodAdapter.findById(1L)).thenReturn(paymentMethod);

        paymentService.processPayment(BigDecimal.valueOf(500), 1L, PaymentMethodType.CREDIT_CARD);

        assertEquals(BigDecimal.ZERO, paymentMethod.getBalance());
        verify(paymentMethodAdapter, times(1)).save(paymentMethod);
        verify(transactionService, times(1)).createTransaction(BigDecimal.valueOf(500), paymentMethod);
    }

    @Test
    void shouldNotSave_whenAdapterThrowsException_callProcessPayment() {
        when(paymentMethodAdapter.findById(1L)).thenThrow(new RuntimeException("Payment method not found"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                paymentService.processPayment(BigDecimal.valueOf(100), 1L, PaymentMethodType.CREDIT_CARD));

        assertEquals("Payment method not found", exception.getMessage());
        verify(paymentMethodAdapter, never()).save(any());
        verify(transactionService, never()).createTransaction(any(), any());
    }
}