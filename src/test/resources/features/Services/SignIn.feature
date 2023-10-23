Feature: User Login

  Scenario: User logs in successfully with valid credentials
    Given the user accesses the sign-in command
    When the user enters "valid@example.com" , "validpass"
#    And confirms the login
    Then the user should be successfully logged in
    And should be redirected to the user dashboard

Feature: User Login

  Scenario Outline: User login with invalid credentials
    Given the user accesses the sign-in command
    When the user enters "<username>" , "<password>"
    #    And confirms the login
    Then the system should display an error message "<error_message>"
    And the user should not be logged in

    Examples:
      | username       | password  | error_message       |
      | invaliduser    | invalid   | Invalid username    |
      | validuser      | wrongpass | Invalid password    |
      | nonexisting    | secret    | User does not exist |
      |                |           | error_message       |
