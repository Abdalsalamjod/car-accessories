Feature: User Sign-Up

  Scenario: Successful User Registration
    Given the user accesses the sign-up command
    When the user provides valid registration information
    Then the user should be registered successfully
    And should receive a confirmation message
    And should be redirected to the user dashboard

  Scenario Outline: Unsuccessful User Registration
    Given the user accesses the sign-up command
    When the user provides "<email>", "<password>"
    Then the system should respond with an error message
    And the user should not be registered

    Examples:
      | email               | password      |
      | invalid_email       | validpass     |
      | valid@example.com   | shortpass     |
      | user@example.com    | validpassword |
      |                     |               |

