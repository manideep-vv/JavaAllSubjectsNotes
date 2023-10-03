Feature: Create and use the variables in feature files
  Background:
    #this apiurl is a variable created in js file
    Given url apiUrl
    And path '/login'
    And request
      """
      {
      "user": "mani",
      "password": "santu"
      }
      """
    When method Post
    Then status 200
    And def token = response.token

  Scenario: create variable and use it
  Given path '/saveEmp'
    # here we created a variable and we will use these variables using #()
    And def userData = {  "tcsempnum": 2000639,  "tcsempname": "Manideep tata",  "tcsmarks": 222 }
    And header Authorization = token
    And request
    """
    {
    "empno":  #(userData.tcsempnum),
    "empname": #(userData.tcsempname),
    "marks":  #(userData.tcsmarks)
   }
    """
    When method post
    Then status 200
    And match response[6] contains {"empname": #(userData.tcsempname)}


Scenario: Assert emp names
  Given path '/getEmpNames'
  When method Get
  Then status 200
  And match response contains ["ranga rao"]
  And match response !contains ["anushka"]


