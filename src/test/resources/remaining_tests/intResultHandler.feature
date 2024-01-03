Feature: IntResultHandler remaining coverage

  Scenario: Handling ResultSet with a single integer value
    Given a ResultSet with a single integer value 42
    When the IntResultHandler handles the ResultSet
    Then the result should be 42

  Scenario: Handling an empty ResultSet
    Given an empty ResultSet
    When the IntResultHandler handles the ResultSet
    Then the result should be 0
