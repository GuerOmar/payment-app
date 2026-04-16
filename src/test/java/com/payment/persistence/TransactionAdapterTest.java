package com.payment.persistence;

import com.payment.model.PaymentMethod;
import com.payment.model.Transaction;
import com.payment.persistence.entity.TransactionJpa;
import com.payment.persistence.mapper.TransactionMapper;
import com.payment.persistence.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionAdapterTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionAdapter transactionAdapter;

    private final Transaction transaction = new Transaction("TX-001", BigDecimal.valueOf(100), PaymentMethod.builder().build());

    @Test
    void shouldSaveTransaction_whenValidTransactionGiven_callSave() {
        TransactionJpa transactionJpa = new TransactionJpa();

        when(transactionMapper.toEntity(transaction)).thenReturn(transactionJpa);

        transactionAdapter.save(transaction);

        verify(transactionMapper, times(1)).toEntity(transaction);
        verify(transactionRepository, times(1)).save(transactionJpa);
    }

    @Test
    void shouldThrowException_whenMapperFails_callSave() {
        when(transactionMapper.toEntity(transaction))
                .thenThrow(new RuntimeException("Mapping failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                transactionAdapter.save(transaction));

        assertEquals("Mapping failed", exception.getMessage());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldThrowException_whenRepositoryFailsToSave_callSave() {
        TransactionJpa transactionJpa = new TransactionJpa();

        when(transactionMapper.toEntity(transaction)).thenReturn(transactionJpa);
        doThrow(new RuntimeException("Failed to save"))
                .when(transactionRepository).save(transactionJpa);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                transactionAdapter.save(transaction));

        assertEquals("Failed to save", exception.getMessage());
        verify(transactionMapper, times(1)).toEntity(transaction);
    }
}