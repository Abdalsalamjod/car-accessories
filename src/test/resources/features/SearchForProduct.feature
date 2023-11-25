
Feature: Admin search for a product
  Background:
    Given I am an admin



  Scenario: searching for existing product by name
    When I search for a product with name
    Then the system should display the product with this name

  Scenario: searching for non-existing product by name
    When I search for a product with non existing name
    Then the system should display that product does not exist


  Scenario: searching for existing product by ID
    When I search for a product with ID
    Then the system should display the product with this ID

  Scenario: searching for non-existing product by ID
    When I search for a product with non existing ID
    Then the system should display that the product with this id does not exist


  Scenario: searching products by categories
    When I search for all product in a category
    Then the system should display all products within this category


  Scenario: searching products by price range
    When I search for products within a price range
    Then the system should display all products within this range



