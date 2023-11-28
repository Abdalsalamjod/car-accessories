Feature: User creates new installation request

  Scenario: successful request
    Given the user make a new installation request with valid information
    Then the request should be done
    And a conformation email will be sent to the user and installer


  Scenario: user cancel installation request
    Given the user lists his requests and want to cancel one of them
    Then  the request should be removed
    And a conformation email will be sent to the user and installer