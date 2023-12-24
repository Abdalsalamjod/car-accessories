
Feature: Admin search for a product

  Scenario: searching for existing product by name
    When I search for a product with the name existing_product
    Then the product should be returned

  Scenario: searching for non-existing product by name
    When I search for a product with the name non_existing_product
    Then the product should not be returned


  Scenario: searching for existing product by ID
    When I search for a product with existing ID
    Then the product should be returned

  Scenario: searching for non-existing product by ID
    When I search for a product with ID non-existing_product
    Then the product should not be returned


  Scenario: searching products by categories
    When I search for all product in valid category
    Then the products in this range should be returned


  Scenario: searching products by price range
    When I search for products within a price range
    Then the products in this range should be returned



