
Feature: Admin updates a product
  Background:
    Given I am an admin


  Scenario Outline: successful update process
    When I update the product with ID <ExistingID> to have a new name "<NewName>", category "<NewCategory>", price <NewPrice>, and quantity <NewQuantity>
    Then the product information should be updated in the database

    Examples:
      | ExistingID | NewName    | NewCategory  | NewPrice  | NewQuantity |
      | 1          | u-spoiler  | exterior     |  199.99   | 10          |
      | 2          | u-steering | interior     |  29.25    | 20          |
      | 3          | u-reverse  | electronics  |  50.0     | 8           |



  Scenario Outline: update existing product with invalid price or quantity
    When I update the product with ID <ExistingID>, name "<NewName>", category "<NewCategory>", price <NewPrice>, and quantity <NewQuantity> to have invalid data
    Then the system should display error message

    Examples:
      | ExistingID | NewName         | NewCategory | NewPrice | NewQuantity |
      | 1          | Spoiler         | exterior    | -20.0    | 10          |
      | 2          | steering-cover  | interior    |  19.5    | -5          |



  Scenario: update a non-existing product
    When I update a non existing product
    Then the system should display that the product does not exist








