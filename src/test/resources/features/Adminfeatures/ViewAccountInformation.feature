Feature: Admin User Account Management



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
