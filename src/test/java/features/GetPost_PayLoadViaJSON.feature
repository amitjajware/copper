@coreTest
@Jira_1xxxx
#  **********   READ ME   *********************
# This feature is to load all the JSON records available in PayLoad_1.txt file
#  Feature will loop through all the files available at absolute path, and will pickup all jsons
#  And split, to transform/Serialize them into JSON format prior to post
#  once post is done, all the values will be verified/assert.
#  *********************************************
Feature: Post assets records into system

  Scenario: Post assets via Scenarios
    Given Copper user post assets records to FilePath "/collaterals/"
    Then Copper user verify the inserted record