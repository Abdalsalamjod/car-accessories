
Feature: Admin adds a new product


  Scenario: admin adds product with all valid information
    When  I add a new valid product with id=11 name = "testProduct" category = "interior" price = 10.0 quantity = 10
    Then  the product with id=11 name = "testProduct" category = "interior" price = 10.0 quantity = 10 should be added to the database

  Scenario: admin tries to add a product with duplicated id
    When  I add a product with duplicated id = 1 name = "testProduct" category = "interior" price = 10.0 quantity = 10
    Then  the product with this id should not be added to the database

  Scenario Outline: admin adds a product with invalid information
    When I add a new product with invalid  ID <ID>, name "<Name>", category "<Category>", price <Price>, quantity <Quantity>
    Then the product should not be added to the database and the error message "<Message>"

    Examples:
      | ID  | Name            | Category      | Price   | Quantity | Message            |
      | 11  | Spoiler         | Watches       | 20.0    | 10       | Invalid Category!  |
      | 12  | Spoiler         | exterior      | -10.0   | 10       | Invalid Price!     |
      | 13  | Spoiler         | exterior      | 20.0    | -10      | Invalid Quantity!  |




