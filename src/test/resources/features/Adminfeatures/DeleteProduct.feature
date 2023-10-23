

Feature: Admin deletes a product
  Background:
    Given I am an admin

  Scenario: successful delete process
    And  a product with ID "ExistingID"
    When I delete the product with ID "ExistingID"
    Then the product with ID "ExistingID" should be removed from the database

  Scenario: deleting non-existing product
    When I delete a product with ID 9999999 that does not exist
    Then the system should display an error message indicating the product does not exist



