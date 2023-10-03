Feature: calling a java methods
  Background: cfg base url and call static and instance method
    Given url apiUrl
    * def faker = Java.type('helpers.EmpFaker')
      #here assume faker as class name

    # here getLoginUserName() is a static method
    * def username =  faker.getLoginUserName()

    # creating obj of class and call instance method
    * def obj = new faker()
    * def loginPass = obj.getLoginPassword()

    And path '/login'
    And request
    """
          {
            "user":#(username),
            "password":#(loginPass)
      }
    """
    When method Post
    Then status 200
    And def token = response.token

  Scenario: calling java instance and static methods
    Given path '/saveEmp'
    # obj is object of that class, since getEmpNumber() is instance method we should call with obj reference
    * def enumber = obj.getEmpNumber()
    * def ename = obj.getEmployeeName()
    # here faker is class var reference name, so we can call with class name
    * def emarks = faker.getEmployeeMarks()
    And header Authorization = token
    And request
    """
          {
              "empno": #(enumber),
              "empname": #(ename),
              "marks": #(emarks),
              "doj" :  "2023-09-22T07:33:53.647"
          }
    """
    When method post
    Then status 200
    And match response[10].empname == '#(ename)'

    Scenario Outline: run same scenario with multiple examples
     Given path '/saveEmp'
      And header Authorization = token
      # here we are calling java instance method to get the random employee name
      And def ename = obj.getEmployeeName()
      And request
      ## here the values for <eno>, <emarks> will be given by examples section
      """
      {

          "empno": "<eno>",
          "empname":  #(ename),
          "marks":  "<emarks>"
      }
      """
      When method post
      Then status 200
      And match response[<index>].empname == '#(ename)'
      And match response[<index>].empno == <eno>
# here now we gave 4 sets of data, now same scenario will be executed 4 times with all below inputs
      Examples:
      |eno    | emarks  |index
      |7         |208          |6
      |8         |209          |7
      |9         |210           |8
      |10        |211           |9




