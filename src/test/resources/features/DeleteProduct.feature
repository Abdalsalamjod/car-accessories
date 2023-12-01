

Feature: Admin deletes a product
  Background:
    Given I am an admin

  Scenario: successful delete process
    And  a product with id 1, name "name", category "category", price 10.0, quantity 7
    When I delete the product with ID 1
    Then the product with ID 1 should be removed from the database

  Scenario: deleting non-existing product
    When I delete a product with non-existing id
    Then the system should display an error message indicating the product does not exist



