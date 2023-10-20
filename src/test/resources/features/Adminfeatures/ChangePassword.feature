Feature: Admin User Account Management

  Scenario: Admin changes a user's password
    Given the admin is logged in
    When the admin selects the option to change a user's password
    And enters a new password for the user
    And confirms the password change
    Then the user's password should be updated
    And the admin should receive a confirmation message

