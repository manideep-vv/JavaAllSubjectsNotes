
Feature:demo the post method with header

  Background:  cfg the base url
    Given url 'https://api.realworld.io/api'

@mani
  Scenario: login to get the token and pass that token as header to publish the article
    Given path '/users/login'
    And request { "user": {   "email": "vv.manideeep1@gmail.com",  "password": "MANIdeep@123" } }
    When method Post
    Then status 200
    * def token = response.user.token

    Given header Authorization = 'Token '+ token
    Given path '/articles'
    And request {"article": {"tagList": [],"title": "ram  in hero","description": "chiru  in hero","body": "cn"}}
    When method post
    Then status 201
    And match response.article.title == "ram  in hero"


