Feature: User Operations in User Module

  Background:
    Given a user is logged in to the system


  # Failure Scenario for updateDatabaseObject
  Scenario: Fail to update database object due to database error
    When the user attempts to update with a database error
    Then the update should fail

  # Scenario for updateProfileObject
  Scenario: Update profile object
    When the user updates the profile's "location" to "New Location"
    Then the profile's "location" for the user should be updated to "New Location"

  # Failure Scenario for updateProfileObject
  Scenario: Failure Update profile object
    When the user updates the profile's "location" to "New Location" with error
    Then the profile's "location" for the user should not be updated to "New Location"

  # Scenario for viewRequisitesHistory
  Scenario: View requisites history
    When the user views their requisites history
    Then the completed requisites for the user are displayed

  # Failure Scenario for viewRequisitesHistory
  Scenario: View requisites history
    When the user views their requisites history with error
    Then the completed requisites for the user are not displayed

  # Scenario for viewInstallationRequests
  Scenario: View installation requests
    When the user views their installation requests
    Then the installation requests made by the user are displayed
  # Failure Scenario for viewInstallationRequests
  Scenario: View installation requests
    When the user views their installation requests with error
    Then the installation requests made by the user arenot displayed
