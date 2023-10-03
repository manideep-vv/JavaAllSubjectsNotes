Feature: database interaction
  Scenario: insert a record in database by calling java method
   * def h2DaoClass = Java.type('helpers.H2Dao')
  * def result = h2DaoClass.insertData(4,"rangayya","kavali")

    Scenario: fetch a record and assert those values
      * def h2DaoClass = Java.type('helpers.H2Dao')
      * def result = h2DaoClass.fetchData(2)
      * print 'Got result from database as ' + result
      * print 'Got emp name as ' + result.empname
      * match result  == "santhoshi"

