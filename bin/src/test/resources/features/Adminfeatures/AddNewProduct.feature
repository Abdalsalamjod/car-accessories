
Feature: Add new product
  Background:
    Given  I am admin
    And    I am in the admin dashboard
    And    I am in the add product option
    When   I press enter


  Scenario: valid information

    When  I fill 'productID' with 'A-123'
    And   I fill 'name' with 'spoiler'
    And   I fill 'description' with '100*20*30cm'
    And   I fill 'price' with '70'
    And   I fill 'quantity' with '6'
    Then  I should see message says that the information has been entered successfully
    And   I will add new product to the database



  Scenario: empty information

    Then  I should see message says empty information



  Scenario Outline: invalid information

    When  I fill 'productID' with '<productID>'
    And   I fill 'name' with '<name>'
    And   I fill 'description' with '<description>'
    And   I fill 'price' with '<price>'
    And   I fill 'quantity' with '<quantity>'
    Then  I should see '<message>'

    Examples:
    | productID | name    | description | price | quantity     | message              |
    | productA  | spoiler | 100*20*30cm | 70    | 6            | Invalid ID!          |
    | A-123     | @3sdf   | 100*20*30cm | 70    | 6            | Invalid Name!        |
    | A-123     | spoiler | 1233        | 70    | 6            | Invalid Description! |
    | A-123     | spoiler | 100*20*30cm | -10   | 6            | Invalid Price!       |
    | A-123     | spoiler | 100*20*30cm | 70    | -8           | Invalid quantity!    |





