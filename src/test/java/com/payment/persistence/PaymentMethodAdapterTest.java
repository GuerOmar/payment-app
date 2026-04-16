package com.payment.persistence;

import com.payment.model.PaymentMethod;
import com.payment.persistence.entity.PaymentMethodJpa;
import com.payment.persistence.mapper.PaymentMethodMapper;
import com.payment.persistence.repository.PaymentMethodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentMethodAdapterTest {

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Mock
    private PaymentMethodMapper paymentMethodMapper;

    @InjectMocks
    private PaymentMethodAdapter paymentMethodAdapter;

    @Test
    void shouldReturnPaymentMethod_whenIdExists_callFindById() {
        PaymentMethodJpa paymentMethodJpa = new PaymentMethodJpa();
        PaymentMethod paymentMethod = PaymentMethod.builder().build();

        when(paymentMethodRepository.findById(1L)).thenReturn(Optional.of(paymentMethodJpa));
        when(paymentMethodMapper.toModel(paymentMethodJpa)).thenReturn(paymentMethod);

        PaymentMethod result = paymentMethodAdapter.findById(1L);

        assertEquals(paymentMethod, result);
        verify(paymentMethodRepository, times(1)).findById(1L);
        verify(paymentMethodMapper, times(1)).toModel(paymentMethodJpa);
    }

    @Test
    void shouldThrowException_whenIdDoesNotExist_callFindById() {
        when(paymentMethodRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paymentMethodAdapter.findById(1L));

        verify(paymentMethodRepository, times(1)).findById(1L);
        verify(paymentMethodMapper, never()).toModel(any());
    }

    @Test
    void shouldSavePaymentMethod_whenValidPaymentMethodGiven_callSave() {
        PaymentMethod paymentMethod = PaymentMethod.builder().build();
        PaymentMethodJpa paymentMethodJpa = new PaymentMethodJpa();

        when(paymentMethodMapper.toEntity(paymentMethod)).thenReturn(paymentMethodJpa);

        paymentMethodAdapter.save(paymentMethod);

        verify(paymentMethodMapper, times(1)).toEntity(paymentMethod);
        verify(paymentMethodRepository, times(1)).save(paymentMethodJpa);
    }

    @Test
    void shouldThrowException_whenRepositoryFailsToSave_callSave() {
        PaymentMethod paymentMethod = PaymentMethod.builder().build();
        PaymentMethodJpa paymentMethodJpa = new PaymentMethodJpa();

        when(paymentMethodMapper.toEntity(paymentMethod)).thenReturn(paymentMethodJpa);
        doThrow(new RuntimeException("Failed to save"))
                .when(paymentMethodRepository).save(paymentMethodJpa);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                paymentMethodAdapter.save(paymentMethod));

        assertEquals("Failed to save", exception.getMessage());
    }
}