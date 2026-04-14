Feature: Payment Service

  Scenario: Successful payment with sufficient balance
    Given a payment method with balance 500.00
    And a payment amount of 100.00
    When the payment is processed with method id 1 and type CREDIT_CARD
    Then the payment method balance should be 400.00

  Scenario: Payment fails when amount is negative
    Given a payment method with balance 500.00
    And a payment amount of -50.00
    When the payment is processed with method id 1 and type CREDIT_CARD
    Then a payment service exception should be thrown with message "Amount is negative.."

  Scenario: Payment fails when balance is insufficient
    Given a payment method with balance 50.00
    And a payment amount of 100.00
    When the payment is processed with method id 1 and type CREDIT_CARD
    Then a payment service exception should be thrown with message "Not enough balance ..."

  Scenario: Payment succeeds when amount equals balance
    Given a payment method with balance 100.00
    And a payment amount of 100.00
    When the payment is processed with method id 1 and type CREDIT_CARD
    Then the payment method balance should be 0.00