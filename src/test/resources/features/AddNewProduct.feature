
Feature: Admin adds a new product


  Scenario: admin adds product with all valid information
    When  I add a new valid product
    Then  the product should be added to the database

  Scenario: admin tries to add a product with duplicated ID
    When  I add a duplicated-Id product
    Then  the product should not be added to the database

  Scenario Outline: admin adds a product with invalid information
    When I add a new product with invalid  ID <ID>, name "<Name>", category "<Category>", price <Price>, quantity <Quantity>
    Then the product should not be added to the database and the error message should be equals to "<errorMessage>"

    Examples:
      | ID  | Name            | Category      | Price   | Quantity | errorMessage
      | 11  | Spoiler         | Watches       | 20.0    | 10       | Invalid Category!
      | 12  | Spoiler         | exterior      | -10.0   | 10       | Invalid Price!
      | 13  | Spoiler         | exterior      | 20.0    | -10      | Invalid Quantity!




