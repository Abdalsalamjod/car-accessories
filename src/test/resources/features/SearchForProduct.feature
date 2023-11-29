
Feature: Admin search for a product
  Background:
    Given I am an admin



  Scenario: searching for existing product by name
    When I search for a product with the name existing_product
    Then the system should display the product with name exist_product

  Scenario: searching for non-existing product by name
    When I search for a product with the name non_existing_product
    Then the system should display a message indicating that the product was not found


  Scenario: searching for existing product by ID
    When I search for a product with existing ID
    Then the system should display the product with has the existing ID

  Scenario: searching for non-existing product by ID
    When I search for a product with ID non-existing_product
    Then the system should display a message indicating that the product was not found


  Scenario: searching products by categories
    When I search for all product in valid category
    Then the system should display all products within category


  Scenario: searching products by price range
    When I search for products within a price range
    Then the system should display all products within this range



