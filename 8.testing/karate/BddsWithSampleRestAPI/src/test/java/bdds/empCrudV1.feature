@mani
Feature: hit my local api methods
  Background:  cfg base url
    Given url apiUrl
    And path '/login'
    And request {  "user":"#(usernameFromKarateJs)",  "password":"santu"  }
    When method Post
    Then status 200
    * print 'Logging in and fetching the token'
    * def token = response.token

    Given path 'getAllEmployees'
    When method Get
    Then status 200
    # here the response is stored in the declared variable
    * def currentEmployeesCount = response.length
    * print 'current employees count is ' + currentEmployeesCount

  Scenario: SAVE demo- login to get the token
    #this is to assert the return array size
    # Here we hit a post method with header and request body
    #asserting a json element
    Given path '/saveEmp'
    And header Authorization = token
    #when we want to use the variable we should use '#(some variable name)'  ,
      # if hash is already in triple quotes no need of enclosing it again in single quotes

    #using the variable direclty
    * def newEmpnumber =  currentEmployeesCount+ 1
    * print 'new emp number will be ' + newEmpnumber
    #to generate random emp number call java instance method
    * def EmpFakerClass = Java.type('helpers.EmpFaker')
    # creating object for that class
    * def empObj = new EmpFakerClass()
    # call instance method for this
    * def randomEmpName = empObj.getEmployeeName()
    * def marks =  newEmpnumber + 100
    # we can send as object or we can send in triple double quotes
#    And request { "empno":,  "empname": "sailu",  "marks": 402 }
    And request
    """
            {
                "empno": #(newEmpnumber) ,
                "empname": '#(randomEmpName)',
                "marks": #(marks)
            }
    """

    When method post
    Then status 200
    * print 'Now after saving latest count of employees is ' +response.length
#    And match response[6] contains {"empname": '#(newEmpName)'}

    #delete method demo, by passing json as object and delete last employee
    Scenario: Delete method - to delete last employee
      Given path '/deleteEmp'
      * print 'deleting a list with curernt  size of ' + currentEmployeesCount
      And request { "empno":  #(currentEmployeesCount) }
      And header Authorization = token
      When method Delete
      Then status 200
      * print 'Now after deleting latest count of employees is ' +response.length
      # since response.length is giving a string value of 4, we converted to number 4
      And match parseInt(response.length) == currentEmployeesCount-1




