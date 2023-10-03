Feature: assingments bro
  Background: cfg base url and token generation
    #this url is configured in js file
    Given url apiUrl
    And path '/login'

    #read()  is a java method , so to pass file name as string we should enclose in single quoted
    # here we have placed credentials in json and we are loading to send as part of POST request
    * def credentials = read ('classpath:/bdds/json/credentials.json')
    And request credentials
    When method post
    Then status 200
    * def token = response.token
    * print 'received token as -->' + token

    Scenario: save new emp with existing modified data
      Given path '/getAllEmployees'
      When method Get
      Then status 200
      * def resultObj = response[response.length -1]
      * print 'received result as '+ resultObj.empname
      * def existingCount = response.length

      #now we have to take the previous response and modify it and save it to the list by hitting POST request
      Given path '/saveEmp'
      # fetch and modify values of previous object
      * def newEmpno = resultObj.empno +1
      * def newEmpMarks = resultObj.marks +1
      # to generate emp name we will call java class instance method and get a random name
      * def EmpFakerClass = Java.type('helpers.EmpFaker')
      * def empObj = new EmpFakerClass()
    * def newEmpName = empObj.getEmployeeName()

      # now with all above values we will modify the above json object
    * set   resultObj.empno = newEmpno
      * set resultObj.marks = newEmpMarks
      * set resultObj.empname = newEmpName

      And request resultObj
      And header Authorization = token
      When method post
      Then status 200
      * print 'new count of employees is ' + response.length
      And match response[10].empname == '#(newEmpName)'
#      And match response.length == '20'

