

Feature: Admin deletes a product

  Scenario: successful delete process
    When the admin deletes an existing product
    Then the product should be removed from the database

  Scenario: deleting non-existing product
    When the admin deletes a non-existing product
    Then nothing will happen



