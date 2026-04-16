package com.payment.cucumber.steps;

import com.payment.model.PaymentMethod;
import com.payment.persistence.entity.PaymentMethodJpa;
import com.payment.persistence.PaymentMethodAdapter;
import com.payment.persistence.mapper.PaymentMethodMapper;
import com.payment.persistence.repository.PaymentMethodRepository;
import io.cucumber.java.en.*;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentMethodAdapterSteps {

    private final PaymentMethodRepository repository = Mockito.mock(PaymentMethodRepository.class);
    private final PaymentMethodMapper mapper = Mockito.mock(PaymentMethodMapper.class);
    private final PaymentMethodAdapter adapter = new PaymentMethodAdapter(repository, mapper);

    private PaymentMethod result;
    private RuntimeException thrownException;

    @Given("a payment method JPA entity exists with id {long}")
    public void aPaymentMethodJpaEntityExistsWithId(Long id) {
        PaymentMethodJpa jpa = new PaymentMethodJpa();
        PaymentMethod model = PaymentMethod.builder().build();
        when(repository.findById(id)).thenReturn(Optional.of(jpa));
        when(mapper.toModel(jpa)).thenReturn(model);
    }

    @Given("no payment method exists with id {long}")
    public void noPaymentMethodExistsWithId(Long id) {
        when(repository.findById(id)).thenReturn(Optional.empty());
    }

    @When("the adapter finds payment method by id {long}")
    public void theAdapterFindsPaymentMethodById(Long id) {
        try {
            result = adapter.findById(id);
        } catch (RuntimeException e) {
            thrownException = e;
        }
    }

    @Then("the payment method should be returned")
    public void thePaymentMethodShouldBeReturned() {
        assertNotNull(result);
    }

    @Then("a runtime exception should be thrown")
    public void aRuntimeExceptionShouldBeThrown() {
        assertNotNull(thrownException);
    }

    @Given("a valid payment method model")
    public void aValidPaymentMethodModel() {
        PaymentMethod model = PaymentMethod.builder().build();
        PaymentMethodJpa jpa = new PaymentMethodJpa();
        when(mapper.toEntity(model)).thenReturn(jpa);
    }

    @When("the adapter saves the payment method")
    public void theAdapterSavesThePaymentMethod() {
        adapter.save(PaymentMethod.builder().build());
    }

    @Then("the repository should have saved the entity")
    public void theRepositoryShouldHaveSavedTheEntity() {
        verify(repository, times(1)).save(any());
    }
}