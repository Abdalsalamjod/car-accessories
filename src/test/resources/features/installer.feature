Feature: Installer management of installation requests

  Scenario: View available installation requests
    Given an installer is logged in
    When the installer views installation requests
    Then the list of available installation requests should be displayed

  Scenario: Error while viewing installation requests
    Given an installer is logged in
    When an error occurs while viewing installation requests
    Then a failure message should be displayed

  Scenario: Schedule an installation request
    Given an installer is logged in
    And there are available installation requests
    When the installer schedules a request with ID "1"
    Then the request with ID "1" should be scheduled

  Scenario: Exit Schedule an installation
    Given an installer is logged in
    And there are available installation requests
    When the installer EXIT schedules a request
    Then a failure message should be displayed
  Scenario: Error while scheduling an installation request
    Given an installer is logged in
    And there are available installation requests
    When an error2 occurs while scheduling a request with ID "1"
    Then a failure message should be displayed

  Scenario: Error while scheduling an installation request
    Given an installer is logged in
    And there are available installation requests
    When an error occurs while scheduling a request with ID "1"
    Then a failure message should be displayed


  Scenario: Mark an installation request as done
    Given an installer is logged in
    And there are selected installation requests
    When the installer marks a request with ID "1" as done
    Then the request with ID "1" should be marked as done


  Scenario: Mark an installation request as done
    Given an installer is logged in
    And there are selected installation requests
    When the installer marks a request with ID "1" as done2
    Then the request with ID "1" should be marked as done

  Scenario: Mark an installation request as done
    Given an installer is logged in
    And there are selected installation requests
    When the installer marks a request with ID "1" as done2 error
    Then the request with ID "1" should be marked as done

  Scenario: Error while marking an installation request as done
    Given an installer is logged in
    And there are selected installation requests
    When an error occurs while marking a request with ID "1" as done
    Then a failure message should be displayed
  Scenario: Error while marking an installation request as done
    Given an installer is logged in
    And there are selected installation requests
    When an error2 occurs while marking a request with ID "1" as done
    Then a failure message should be displayed

  Scenario: Exit mark as done
    Given an installer is logged in
    And there are selected installation requests
    When the installer EXIT mark as done
    Then a failure message should be displayed

