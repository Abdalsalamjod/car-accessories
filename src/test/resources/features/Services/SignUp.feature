#Feature: User Login
#
#  Scenario Outline: User logs in successfully with valid credentials
#    Given the user accesses the sign-in command
#    When the user provides valid information '<username>' , '<password>' , '<role>'
#    Then the user should be successfully logged in
#    Examples:
#      | username                | password  | role |
#      | asd@gmail.com           | 132       | u    |
#      | s12027747@stu.najah.edu | 12345     | a    |
#      | s12027670@stu.najah.edu | 12345     | i    |
#      | asd@gmail.com           | 132       | x    |
#
#  Scenario: User logs in failed with valid credentials
#    Given the user accesses the sign-in command
#    When the user provides valid information "asd@gmail.com" , "132" and sth went wrong
#    Then the system should display an error message "<error_message>"
#    And the user should not be logged in
#
#  Scenario: User logs in failed with valid credentials with sql error
#    Given the user accesses the sign-in command
#    When the user provides valid information "asd@gmail.com" , "132" and sth went wrong in sql
#    Then the system should display an error message "<error_message>"
#    And the user should not be logged in
#
#  Scenario Outline: User login with invalid credentials
#    Given the user accesses the sign-in command
#    When the user provides information '<username>' , '<password>'
#    Then the system should display an error message "<error_message>"
#    And the user should not be logged in
#
#    Examples:
#      | username       | password  | error_message       |
#      | invaliduser    | invalid   | Invalid username    |
#      | asd@gmail.com  | 111       | Invalid password    |
#      | non@gmail.com  | 111       | User does not exist |
#      |                |           | error_message       |
