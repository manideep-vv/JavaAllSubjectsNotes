Feature: hit my local api methods
  Background:  cfg base url
    Given url 'http://localhost:8080/employeeApi/'
    And path '/login'
    And request {  "user":"mani",  "password":"santu"  }
    When method Post
    Then status 200
    * def token = response.token

  Scenario: Hit Get method to fetch all employees
    Given path 'getAll'
    When method Get
    Then status 200
    And match response == '#[11]'

  Scenario: SAVE demo- login to get the token
    #this is to assert the return array size
    # Here we hit a post method with header and request body
    #asserting a json element
    Given path '/saveEmp'
    And header Authorization = token
    And request { "empno":11,  "empname": "sailu",  "marks": 402 }
    When method post
    Then status 200
    And match response[11] contains {"empname": "sailu"}

    #delete method demo, by passing json as object
    Scenario: Delete method demo
      Given path '/deleteEmp'
      And request { "empno": 11 }
      And header Authorization = token
      When method Delete
      Then status 200



