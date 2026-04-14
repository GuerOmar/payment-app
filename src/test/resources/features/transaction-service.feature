Feature: Transaction Service

  Scenario: Successfully create a transaction
    Given a payment method for transaction
    And a transaction amount of 100.00
    When the transaction is created
    Then the transaction should be saved with amount 100.00

  Scenario: Transaction fails when repository throws exception
    Given a payment method for transaction
    And a transaction amount of 100.00
    And the repository will fail to save
    When the transaction is created
    Then a transaction exception should be thrown with message "Failed to save transaction"