
Feature: Admin search for a product
  Background:
    Given I am an admin



  Scenario: searching for existing product by name
    When I search for a product with the name "Searched Product"
    Then the system should display the product with name "Searched Product"

  Scenario: searching for non-existing product by name
    When I search for a product with the name "Non-Existent Product"
    Then the system should display a message indicating that the product was not found


  Scenario: searching for existing product by ID
    When I search for a product with ID 12011111
    Then the system should display the product with ID 1201111

  Scenario: searching for non-existing product by ID
    When I search for a product with ID 9999999
    Then the system should display a message indicating that the product was not found


  Scenario: searching products by categories
    When I search for products in the category "ExistingCategory"
    Then the system should display all products within category ExistingCategory and with their respective details


  Scenario: searching products by price range
    When I search for products within the price range $50.0 to $200.0
    Then the system should display all products within this price range and with their respective details



