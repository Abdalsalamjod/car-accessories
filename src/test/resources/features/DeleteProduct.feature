

Feature: Admin deletes a product

  Scenario: successful delete process
    When the admin deletes a product with id=11
    Then the product with this id should removed from database

  Scenario: deleting non-existing product
    When the admin deletes a product with id=1111
    Then nothing will happen



