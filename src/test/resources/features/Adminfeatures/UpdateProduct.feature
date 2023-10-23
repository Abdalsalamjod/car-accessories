
Feature: Admin updates a product
  Background:
    Given I am an admin


  Scenario Outline: successful update process
    And  a product with ID <ExistingID>, name "Existing Product", category "Interior", price 99.99, and quantity 5 exists
    When I update the product with ID <ExistingID> to have a new price <NewPrice>, and quantity <NewQuantity>
    Then the product information should be updated in the database

    Examples:
      | ExistingID |  NewPrice | NewQuantity |
      | 12011111   |  199.99   | 10          |
      | 12022222   |  29.99    | 20          |
      | 12033333   |  50.0     | 8           |



  Scenario Outline: update existing product with invalid price and quantity
    And a product with ID 12011111 exists
    When I update the product with ID "<ExistingID>" to have a "<NewPrice>" and a "<NewQuantity>"
    Then the system should display error "<message>"

    Examples:
      | ExistingID |  NewPrice | NewQuantity | message
      | 12011111   |  -20.0    | 10          | Invalid Price!
      | 12022222   |  19.5     | -5          | Invalid Quantity!



  Scenario: update a non-existing product
    When I update a product with ID 9999999 that does not exist with a new price 120.0 and new quantity 7
    Then the system should display error message says that the product does not exist








