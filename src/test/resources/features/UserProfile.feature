Feature: User Profile
  Background:
    Given the customer is logged in
    When the customer accesses their profile settings

  Scenario: Customer edits their contact information
    And updates their contact information
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

  Scenario: Customer views their order history
    And selects the order history section
    Then they should see a list of their past orders

  Scenario: Customer views their installation requests
    And selects the installation requests section
    Then they should see a list of their installation requests