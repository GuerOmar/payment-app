package com.payment.cucumber.steps;

import com.payment.model.PaymentMethod;
import com.payment.model.Transaction;
import com.payment.persistence.entity.TransactionJpa;
import com.payment.persistence.TransactionAdapter;
import com.payment.persistence.mapper.TransactionMapper;
import com.payment.persistence.repository.TransactionRepository;
import com.payment.service.transaction.TransactionServiceImpl;
import io.cucumber.java.en.*;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceSteps {

    private final TransactionRepository repository = Mockito.mock(TransactionRepository.class);
    private final TransactionMapper mapper = Mockito.mock(TransactionMapper.class);
    private final TransactionAdapter transactionAdapter = new TransactionAdapter(repository, mapper);
    private final TransactionServiceImpl transactionService = new TransactionServiceImpl(transactionAdapter);

    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private RuntimeException thrownException;

    @Given("a payment method for transaction")
    public void aPaymentMethodForTransaction() {
        paymentMethod = PaymentMethod.builder()
                .balance(BigDecimal.valueOf(500))
                .build();
    }

    @And("a transaction amount of {double}")
    public void aTransactionAmountOf(double txAmount) {
        amount = BigDecimal.valueOf(txAmount);
    }

    @And("the repository will fail to save")
    public void theRepositoryWillFailToSave() {
        when(mapper.toEntity(any(Transaction.class))).thenReturn(new TransactionJpa());
        doThrow(new RuntimeException("Failed to save transaction"))
                .when(repository).save(any());
    }

    @When("the transaction is created")
    public void theTransactionIsCreated() {
        when(mapper.toEntity(any(Transaction.class))).thenReturn(new TransactionJpa());
        try {
            transactionService.createTransaction(amount, paymentMethod);
        } catch (RuntimeException e) {
            thrownException = e;
        }
    }

    @Then("the transaction should be saved with amount {double}")
    public void theTransactionShouldBeSavedWithAmount(double expectedAmount) {
        verify(repository, times(1)).save(any());
    }

    @Then("a transaction exception should be thrown with message {string}")
    public void aRuntimeExceptionShouldBeThrownWithMessage(String message) {
        assertNotNull(thrownException);
        assertEquals(message, thrownException.getMessage());
    }
}