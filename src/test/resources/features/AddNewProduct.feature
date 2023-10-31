
Feature: Admin adds a new product
  Background:
    Given I am an admin

  Scenario: admin adds product with all valid information
    When  I add a new product with ID 5, name "TestProduct", category "Interior", price 149.99, quantity 10
    Then  the product with ID 5, name "TestProduct", category "Interior", price 149.99, quantity 10 should be added to the database

  Scenario: admin tries to add a product with duplicated ID
    And   a product with ID duplicated ID
    When  I add a duplicated product with ID 5, name "duplicatedID", category "Interior", price 149.99, and quantity 10
    Then  the system should display an error message indicating the ID is already in use

  Scenario Outline: admin adds a product with invalid information
    When I add a new product with invalid ID <ID>, name "<Name>", category "<Category>", price <Price>, quantity <Quantity>
    Then the user should see <errorMessage>

    Examples:
      | ID  | Name            | Category      | Price   | Quantity | errorMessage
      | 11  | Spoiler         | Watches       | 20.0    | 10       | Invalid Category!
      | 12  | Spoiler         | exterior      | -10.0   | 10       | Invalid Price!
      | 13  | Spoiler         | exterior      | 20.0    | -10      | Invalid Quantity!




