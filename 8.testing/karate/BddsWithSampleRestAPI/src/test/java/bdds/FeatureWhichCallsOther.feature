Feature: hit my local api methods
  Background:  demo for @Before and @BeforeEach
   # when we used call method, since we kept this in background, this will be executed for every scenario
   * def featureResponse = call read('classpath:bdds/reUsableGlobalFeature.feature')

    #as we used CALLONCE() method,eventhough we used placed this in background,this method will be cached and
    # this method will be executed only once
#   * def featurecalledOneTime = callonce read('classpath:bdds/reUsableGlobalFeature.feature')
    * def ename = featureResponse.empName
  Scenario: s1
    * print 'scenario 1 exec and received name as '+ ename
    Scenario: s2
      * print 'scenario 2 exec and received name as '+ ename

