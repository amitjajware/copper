@coreTest
@Jira_1xxxx

Feature: Post assets records into system

  Scenario: Post assets via Scenarios
    Given Copper user verify the running service content at "/collaterals/"
    When Copper user post assets records to FilePath "/collaterals/"
    Then Copper user verify the inserted record