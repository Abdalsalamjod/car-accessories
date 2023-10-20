Feature: Admin User Account Management

  Scenario: Admin creates a new user account
    Given the admin is logged in
    When the admin selects the option to create a new user account
    And provides valid registration information for the new user
    And confirms the account creation
    Then a new user account should be created
    And the admin should receive a confirmation message
