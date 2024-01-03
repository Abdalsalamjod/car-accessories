Feature: Manage User Accounts in Admin Module

  Background:
    Given the admin is logged in to the system

  Scenario: Show user details
    When the admin selects the option to show user details for "asd@gmail.com"
    Then the user's details are displayed

  Scenario Outline: Edit user details
    When the admin selects the option <option> to edit "<field>" to "<newValue>" for "asd@gmail.com"
    Then the "<field>" for the user "user@example.com" is updated to "<newValue>"

    Examples:
      | option | field         | newValue       |
      | 2      | Name          | abd       |
      | 3      | Email         | asd@gmail.com |
      | 4      | Password      | 132 |
      | 5      | location          | nablus           |
      | 6      | phone number | 08765         |

  Scenario: Delete a user account
    When the admin selects the option to delete the user with email "asd@gmail.com"
    Then the user with email "asd@gmail.com" is deleted from the system

  Scenario: Handle invalid option input
    When the admin enters an invalid option
    Then a log entry is made stating "Please enter a valid input."



  Scenario: View all users
    When the admin selects the option to view all users
    Then a list of all users with their details is displayed in the log

#  Scenario: View users when no users exist
#    When the admin selects the option to view all users
#    Then a message is displayed indicating that no users are available

  Scenario: Handle database errors during user retrieval
    When the admin selects the option to view all users and problem hapend
    Then an error log entry is made stating "Error: in viewUsers"

  Scenario: Exit the system
    When the admin selects the option to exit the system
    Then the system is terminated
