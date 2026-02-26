Feature: User Management API

  @GetUser
  Scenario Outline: Get a user using ID
    Given the API is available
    When I request user with ID <userId>
    Then the response status should be 200
    And the user fields should not be null

    Examples:
      | userId |
      | 1      |
      | 2      |

  @CreateUser
  Scenario Outline: Create a user using JSON file
    Given the API is available
    When I create a user using "<jsonFile>"
    Then the response status should be 200
    And the response should contain an id

    Examples:
      | jsonFile   |
      | user1.json |
      | user2.json |
