Feature: User Logout

  Scenario: User logs out successfully
    Given the user is logged in
    When the user selects the option to log out
    Then the user should be successfully logged out
    And the system should display a logout confirmation message

  Scenario: User logs out failed
    Given the user is logged in
    When the user selects the option to log out and something wrong happened
    Then the system should display a logout warning message