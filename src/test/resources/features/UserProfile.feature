Feature: User Profile
  Background:
    Given the customer is logged in
    When the customer accesses their profile settings

  Scenario: Customer edits their contact information
    And updates their contact information "123456789"
    Then the changes should be saved successfully

  Scenario: Customer edits their location
    And updates their location
    Then the changes should be saved successfully

  Scenario: Customer edits their name
    And updates their name
    Then the changes should be saved successfully

  Scenario: Customer edits their email
    And updates their email
    Then the changes should be saved successfully

  Scenario: Customer edits their password
    And updates their password
    Then the changes should be saved successfully

  Scenario: Customer edits ... and sth went wrong
    Then the changes should not be saved and message apear

  Scenario: Customer views their order history
    And selects the order history section
    Then they should see a list of their past orders

  Scenario: Customer cant views their order history
    And selects the order history section but no requsets
    Then they should not see a list of their installation requests

  Scenario: Customer views their installation requests
    And selects the installation requests section
    Then they should see a list of their installation requests

  Scenario: Customer cant views their installation requests
    And selects the installation requests section but no requetss exist
    Then they should not see a list of their installation requests

  Scenario: Customer enter invalid Data
    And enter invalid Data
    Then the changes should be not saved
  Scenario: Customer edits their email with exist email
    And updates their email with exist email
    Then the changes should be not saved
