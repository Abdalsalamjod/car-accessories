

Feature: Admin deletes a product
  Background:
    Given I am an admin

  Scenario: successful delete process
    When I delete existing product
    Then the existing product should be deleted

  Scenario: deleting non-existing product
    When I delete non-existing product
    Then the system should show that the product is not exist



