Feature: Payment Delegate

  Scenario: Successful delegation with sufficient balance
    Given a payment method with balance 500.00 for delegation
    And an execution variable amount of 100.00 and methodId 1
    When the delegate executes
    Then the payment method balance should be updated to 400.00
    And the execution variable paymentMethod should be set

  Scenario: Delegation fails when balance is insufficient
    Given a payment method with balance 50.00 for delegation
    And an execution variable amount of 100.00 and methodId 1
    When the delegate executes
    Then a BpmnError should be thrown with error code "INSUFFICIENT_FUNDS"