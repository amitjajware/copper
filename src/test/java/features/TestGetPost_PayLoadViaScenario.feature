@coreTest
@Jira_1xxxx
#This a another way of loading Json from feature file.
#  And verify the loaded json at given address
#  This feature gives the flexibility to the user to write scenario in gerkin language

#  WIP


  Feature: Post assets records into system
    Scenario Outline: Post assets via Scenarios
      Given The user post assets records to FilePath "/collaterals/"
        | id          | <id>       |
        | name        | <name>     |
        | currency    | <currency> |
        | year        | <year>     |
        | value       | <value>    |
      When The user verify the running service content at "/collaterals/"
      Then the user verify the inserted record
        | id          | <id>       |
        | name        | <name>     |
        | currency    | <currency> |
        | year        | <year>     |
        | value       | <value>    |

      Examples:
        | id        | name     | currency | year | value   |
        | 200000001 | Copper   | GBP      | 2022 | 101.0002 |
        | 200000002 | CopperCo | USD      | 2022 | 101.4509 |

