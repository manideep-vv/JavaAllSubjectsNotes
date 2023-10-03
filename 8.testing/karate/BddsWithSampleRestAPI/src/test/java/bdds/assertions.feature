Feature: assertions
  Background: cfg base url
  #this base url is already configured in the js file
Given url apiUrl
  Scenario: asserting array
  Given path '/getEmpNames'
  When method Get
  Then status 200
    # here the output of the endpoint is like  -->  [ "manideep", "ramadevi", "ranga rao" ]
   # Asserting the array count as 6
  And match response == '#[6]'
    #Here response is array of strings,  Assert the output array as array data type
  And match response == '#array'
    # here response is array of strings, so assert each element in array as String data type
  And match each response == '#string'
    # assert 1 element data as manideep
  And match response contains "manideep"
    And match response !contains "ramadevi1"
    # asserting 2 elements - here in this case both elements must be present
  And match response contains ['manideep',"ramadevi"]
    # asserting any one in the list, means here we gave 2 ,but actual response should contain atleast one ele what is present here
  And match response contains any ["manideep","eleWhichIsNotPresent-Luxembourg"]
    # to assert all the elements in the arry and it should not have any elements beyond it
    And match response contains only [ "santu",  "manideep","sailu", "charan", "ramadevi", "ranga rao" ]
    # asserting first element as manideep
    And match response[0] == "manideep"
    # assert that first element must not be santu
    And match response[0] != "santu"



    #sample output of that end point /getStudentNames is
  #[
  # { "studentName": "manideep", "marks": 101, "addrs": { "cityname": "luxemborg", "countryName": "europe" }, "gender": "male" },
  # { "studentName": "santoor", "marks": 201, "addrs": { "cityname": "hyderabad", "countryName": "india" }, "gender": "female" },
  # { "studentName": "charan", "marks": 301, "addrs": { "cityname": "chicago", "countryName": "America" }, "gender": "male" },
  # { "studentName": "sai", "marks": 100, "addrs": { "cityname": null, "countryName": "America" }, "gender": "male" } ]
  # ]
 Scenario: Asserting array with inner objects
   Given path '/getStudents'
   When method Get
   Then status 200
   # this api will give us array of 4 objes , assert arrray size or count
   And match response == '#[4]'
   #assert first ele json value as string
   And match response[0].studentName == '#string'
   #assert each object student name data type as string
   And match each response..studentName == '#string'
   And match response[0].studentName == 'manideep'
   #iterate through all objects of an array and atleast one obj it should contain 100 marks
   # response[0] means 0th o index object, here response[*] means every element inside an array- response[*].marks gives [101,201,301,100]
  And match response[*].marks contains 100
   # among 4 objects in that array 1 obj value can be null,
  And match response[*].addrs.cityname contains null
   # matching inner objects with doubledot
  # we can also write above statement as
  And match response..cityname contains null
   # we want to compare each and every to male, if we use response[*].gender-- this will give array of genders we cant compare array with single ele
   # so to compare EACH AND EVERY ele we should use each operator whereas the  response[*].gender value is  ["male","male","male","male"]
   And match each response..gender == "male"
   # take gender list and it should not have any 'female' at all, here  response[*].gender -- will gives us the gender list
   And match response[*].gender contains "male"
   # take the gender list and it should contain male also ,
  And match response[*].gender contains "male"

   #sample output of that end point /getStudentNames is
  #[
  # { "studentName": "manideep", "marks": 101, "addrs": { "cityname": "luxemborg", "countryName": "europe" }, "gender": "male" },
  # { "studentName": "santoor", "marks": 201, "addrs": { "cityname": "hyderabad", "countryName": "india" }, "gender": "female" },
  # { "studentName": "charan", "marks": 301, "addrs": { "cityname": "chicago", "countryName": "America" }, "gender": "male" },
  # { "studentName": "sai", "marks": 100, "addrs": { "cityname": null, "countryName": "America" }, "gender": "male" } ]
  # ]
   Scenario: Fuzzy matching
     #refer above for output of this end point
     Given path '/getStudents'
     When method Get
     Then status 200
     #both  response..marks and response[*].marks are same and gives array as output
      And match response..marks contains 100
     And match response[*].marks contains 100
     #asserting each and every element as number datatype, here the values for marks are always number no nulls hence we used single hash
     And match each response..marks == '#number'
     ## match each and every objects cityname as string,but some objects have null , in that case we shold use ..
     And match each response..cityname == '##string'
     # this says match each and every obj's cityname must be string, some can be null , so used double hash
     And match each response[*].cityname == '##string'


     Scenario: schemavalidation
       * def validator = read('classpath:bdds/time-validator.js')
       Given path 'getAllEmployees'
       When method Get
       Then status 200
        And match each response[*] ==
       """
         {
        "empno": '#number',
        "empname": '#string',
        "marks": '#number',
        "doj": "#? validator(_)"
    }
       """