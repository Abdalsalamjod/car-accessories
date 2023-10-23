
Feature: Admin adds a new product
  Background:
    Given I am an admin

  Scenario: admin adds product with all valid information
    When  I add a new product with ID 12011111, name "Valid Product", category "Interior", price 149.99, and quantity 10
    Then  the product should be added to the database

  Scenario: admin tries to add a product with duplicated ID
    And   a product with ID 12011111
    When  I add a duplicated product with ID 12011111, name "Valid Product", category "Interior", price 149.99, and quantity 10
    Then  the system should display an error message indicating the ID is already in use

  Scenario Outline: admin adds a product with invalid information
    When I add a new product with ID <ID>, name "<Name>", category "<Category>", price <Price>, and quantity <Quantity>
    Then the user should see "<errorMessage>"

    Examples:
      | ID        | Name            | Category      | Price   | Quantity | errorMessage
      | 12011111  | InvalidName     | exterior      | 20.0    | 10       | Invalid Name!
      | 12011111  | Spoiler         | Watches       | 20.0    | 10       | Invalid Category!
      | 12011111  | Spoiler         | exterior      | -10     | 10       | Invalid Price!
      | 12011111  | Spoiler         | exterior      | 20.0    | -10      | Invalid Quantity!




