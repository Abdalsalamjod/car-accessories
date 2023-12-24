
Feature: Admin search for a product

  Scenario: searching for existing product by name
    When I search for a product with name = "Spoiler"
    Then the product should be returned

  Scenario: searching for non-existing product by name
    When I search for a product with the name "noneExisting"
    Then the product should not be returned


  Scenario: searching for existing product by ID
    When I search for a product with id=2
    Then the product should be returned

  Scenario: searching for non-existing product by ID
    When I search for a product with id=9999
    Then the product should not be returned


  Scenario: searching products by categories
    When I search for all products within "exterior" category
    Then the products in "exterior" category should be returned


  Scenario: searching products by price range
    When I search for all products within a lower price = 10 and upper price = 60
    Then the products in range lower = 10 and upper = 60 should be returned

  Scenario: showing all products
    When I need to see all product
    Then all products should be returned

  Scenario: showing all products names
    When I need to see all products names
    Then all products names should be returned



