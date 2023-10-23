Feature: Admin User Account Management


  Scenario: Admin edits user account information
    Given the admin is logged in
    When the admin selects the option to edit a user's account
    And makes changes to the user's account information
    And confirms the changes
    Then the user's account information should be updated
    And the admin should receive a confirmation message
