Feature: Admin User Account Management

  Scenario: Admin creates a new user account
    Given the admin is logged in
    When the admin selects the option to create a new user account
    And provides valid registration information for the new user
    And confirms the account creation
    Then a new user account should be created
    And the admin should receive a confirmation message

  Scenario: Admin edits user account information
    Given the admin is logged in
    When the admin selects the option to edit a user's account
    And makes changes to the user's account information
    And confirms the changes
    Then the user's account information should be updated
    And the admin should receive a confirmation message

  Scenario: Admin changes a user's password
    Given the admin is logged in
    When the admin selects the option to change a user's password
    And enters a new password for the user
    And confirms the password change
    Then the user's password should be updated
    And the admin should receive a confirmation message

  Scenario: Admin deletes a user account
    Given the admin is logged in
    When the admin selects the option to delete a user's account
    And confirms the account deletion
    Then the user's account should be deleted
    And the admin should receive a confirmation message

  Scenario: Admin views a user's account information
    Given the admin is logged in
    When the admin selects the option to view a user's account information
    Then the user's account details should be displayed
