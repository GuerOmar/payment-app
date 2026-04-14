Feature: Payment Method Adapter

  Scenario: Successfully find payment method by id
    Given a payment method JPA entity exists with id 1
    When the adapter finds payment method by id 1
    Then the payment method should be returned

  Scenario: Throw exception when payment method not found
    Given no payment method exists with id 99
    When the adapter finds payment method by id 99
    Then a runtime exception should be thrown

  Scenario: Successfully save a payment method
    Given a valid payment method model
    When the adapter saves the payment method
    Then the repository should have saved the entity