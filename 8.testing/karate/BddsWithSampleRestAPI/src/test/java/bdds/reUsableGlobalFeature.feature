Feature: writing a reusable feature
Scenario: Reusable feature -login to get the token
  Given url 'http://localhost:8080/employeeApi/'
  And path 'login'
  # we can load the json credentials from json file instead of
#  And request { "user" : "mani", "password" : "santu" }
  * def credentials = read('classpath:/bdds/json/credentials.json')
  And request credentials
  When method Post
  Then status 200
  * def token = response.token
  * print 're-usable feature executed successfully'
  Scenario: call java method and get name of employee
    * def EmpFakerClass = Java.type('helpers.EmpFaker')
    * def empObj = new EmpFakerClass ();
    * def empName = empObj.getEmployeeName()
    * print 'reusable scenario exec successfully and sending empnname as' + empName