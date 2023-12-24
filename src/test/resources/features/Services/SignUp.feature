Feature: User Sign-Up

  Scenario: Successful User Registration
    Given the user accesses the sign-up command
    When the user provides valid registration information
    Then the user should be registered successfully
    And should receive a confirmation message
    And should be redirected to the user dashboard


  Scenario: falid User Registration
    Given the user accesses the sign-up command
    When the user provides valid registration information
    And error in validation hapneed
    Then the system should respond with an error message
    And the user should not be registered

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
  Scenario:Unsuccessful User Registration2
    Given the user accesses the sign-up command
    When the user provides valid registration information and sth went weong
    Then the system should respond with an error message
    And the user should not be registered

  Scenario: Successful User Registration using other constucotr
    Given the user accesses the sign-up command
    When the user provides valid registration information new constucotr
    Then the user should be registered successfully
    And should receive a confirmation message
    And should be redirected to the user dashboard

