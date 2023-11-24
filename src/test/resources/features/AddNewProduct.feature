
Feature: Admin adds a new product
  Background:
    Given I am an admin

  Scenario: admin adds product with all valid information
    When  I add a new product with valid ID, name, category, price, quantity
    Then  the product should be added to the database

  Scenario: admin tries to add a product with duplicated ID
    When  I add a product with duplicated ID
    Then  the system should show that the ID is already exist

  Scenario Outline: admin adds a product with invalid information
    When I add a new product with invalid ID <ID>, name "<Name>", category "<Category>", price <Price>, quantity <Quantity>
    Then the system should show invalid information

    Examples:
      | ID | Name           | Category      | Price   | Quantity |
      | 5  | Spoiler         | Watches       | 20.0    | 10       |
      | 6  | Spoiler         | exterior      | -10.0   | 10       |
      | 7  | Spoiler         | exterior      | 20.0    | -10      |




