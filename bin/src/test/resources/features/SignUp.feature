Feature: User Sign-Up

  Scenario: Successful User Registration
    Given the user accesses the sign-up command
    When the user provides "valid@example.com", "validpass"
    Then the user should be registered successfully
    And should receive a confirmation message
    And should be redirected to the user dashboardd

  Scenario Outline: Unsuccessful User Registration
    Given the user accesses the sign-up command
    When the user provides "<email>", "<password>"
    Then the system should respond with an error message '<error_message>'
    And the user should not be registered

    Examples:
      | email               | password      | error_message                                         |
      | invalid_email       | validpass     | Invalid email address\n                              |
      | valid@example.com   | shortpass     | Password must be at least 8 characters long\n        |
      | user@example.com    | validpassword | Email address is already in use\n                    |
