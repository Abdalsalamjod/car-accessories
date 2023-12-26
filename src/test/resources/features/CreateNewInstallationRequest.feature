Feature: user request relation

  Scenario: successful request
    When user make a new installation request with id = 11, productId = 1, userId = "s12027747@stu.najah.edu", description = "TEST"
    Then the installation request with id = 11 should be added to the database
    And  an email should be send to the installer with email = "s12027670@stu.najah.edu" and user with id= = "s12027747@stu.najah.edu"
    And the date with index = 2 should be removed from dates array

  Scenario: user cancel installation request
    When user remove the request with id=11
    Then the request with id=11 should be removed
    And  an email should be send to the installer with email = "s12027670@stu.najah.edu" and user with id= = "s12027747@stu.najah.edu"
    And the removed date should be available again in the dates array