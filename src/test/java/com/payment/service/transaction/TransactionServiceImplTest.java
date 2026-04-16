package com.payment.service.transaction;

import com.payment.model.PaymentMethod;
import com.payment.model.Transaction;
import com.payment.persistence.TransactionAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionAdapter transactionAdapter;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void shouldSaveTransaction_whenValidAmountAndPaymentMethod_callCreateTransaction() {
        PaymentMethod paymentMethod = PaymentMethod.builder().balance(BigDecimal.valueOf(500)).build();
        transactionService.createTransaction(BigDecimal.valueOf(100), paymentMethod);

        verify(transactionAdapter, times(1)).save(any(Transaction.class));
    }

    @Test
    void shouldGenerateUniqueTransactionId_whenCalledTwice_callCreateTransaction() {
        PaymentMethod paymentMethod = PaymentMethod.builder().build();
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        transactionService.createTransaction(BigDecimal.valueOf(100), paymentMethod);
        transactionService.createTransaction(BigDecimal.valueOf(100), paymentMethod);

        verify(transactionAdapter, times(2)).save(captor.capture());
        List<Transaction> savedTransactions = captor.getAllValues();
        assertNotEquals(savedTransactions.get(0).id(), savedTransactions.get(1).id());
    }

    @Test
    void shouldSaveCorrectAmount_whenValidAmountGiven_callCreateTransaction() {
        PaymentMethod paymentMethod = PaymentMethod.builder().build();
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        transactionService.createTransaction(BigDecimal.valueOf(250), paymentMethod);

        verify(transactionAdapter).save(captor.capture());
        assertEquals(BigDecimal.valueOf(250), captor.getValue().amount());
    }

    @Test
    void shouldSaveCorrectPaymentMethod_whenValidPaymentMethodGiven_callCreateTransaction() {
        PaymentMethod paymentMethod = PaymentMethod.builder().balance(BigDecimal.valueOf(500)).build();
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        transactionService.createTransaction(BigDecimal.valueOf(100), paymentMethod);

        verify(transactionAdapter).save(captor.capture());
        assertEquals(paymentMethod, captor.getValue().paymentMethod());
    }

    @Test
    void shouldThrowException_whenAdapterFailsToSave_callCreateTransaction() {
        PaymentMethod paymentMethod = PaymentMethod.builder().build();
        doThrow(new RuntimeException("Failed to save transaction"))
                .when(transactionAdapter).save(any(Transaction.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                transactionService.createTransaction(BigDecimal.valueOf(100), paymentMethod));

        assertEquals("Failed to save transaction", exception.getMessage());
    }
}