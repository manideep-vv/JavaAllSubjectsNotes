@ignore
Feature:  Api testing
  Background:  Define base url
  Given url 'https://api.realworld.io/api'

  Scenario: hit url with query parameters https://api.realworld.io/api/articles?limit=10&offset=1
    Given  path '/articles'
    And params {limit:10, offset:10}
    When  method Get
    Then status 200
    # this returns json whose key is articles and value is array of objects whose size is 10
    And match response.articles == '#[10]'
    # this is asserting a field
    And match response.articlesCount == 197
@ignore
  Scenario: hit PLain Get method url
    Given path  '/tags'
    When method Get
    Then status 200
    #the below asserts tags value is having 10 objects
    And match response.tags == '#[10]'
    And match response.tags contains ['welcome']
    And match response.tags contains 'ipsum'
    And match response.tags !contains 'orayya'
    # asserting the output datatype is array
    And match response.tags =='#array'
    # loop and assert each element as string
  And match each response.tags == '#string'
