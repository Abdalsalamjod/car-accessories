
Feature: Admin updates a product


  Scenario Outline: successful update process
    When admin updates the product with ID <ExistingID> to have a new price <NewPrice>, and quantity <NewQuantity>
    Then the with ID <ExistingID> price should be updated to <NewPrice> and the product quantity should be updated to <NewQuantity>

    Examples:
      | ExistingID |  NewPrice | NewQuantity |
      | 1          |  199.99   | 10          |
      | 2          |  29.99    | 20          |
      | 3          |  50.0     | 8           |



  Scenario Outline: update existing product with invalid price and quantity
    When admin updates the product with ID <ExistingID> to have an invalid <NewPrice> andOR invalid <NewQuantity>
    Then the error message should be equals to "<message>"

    Examples:
      | ExistingID |  NewPrice | NewQuantity | message            |
      | 1          |  -20.0    | 10          | Invalid Price!     |
      | 2          |  19.5     | -5          | Invalid Quantity!  |

