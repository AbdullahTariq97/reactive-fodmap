Feature:
  Scenario: The service should return list of fodmap items for a given food group
    Given the database is populated with a record
      | food_group  | name    | overall_rating | stratified_rating                                                               |
      |'vegetable' | 'argula' |      'r'       |{amount:75,fructose:'g',lactose:'g',manitol:'g',sorbitol:'g',gos:'g',fructan:'g'}|
    When a request is made to get fodmap items by food group VEGETABLE
    Then status code of 200 should be returned
    And the response body contains the following fodmap items
      | food_group  | name    | overall_rating | stratified_rating                                                                                |
      | vegetable   | argula  |      r         |{"amount":75,"fructose":"g","lactose":"g","manitol":"g","sorbitol":"g","gos":"g","fructan":"g"} |


#  check case sensitivity store with different case for food group into database and check if we are able to retrive
#  Must insure that the cassandra client is adding data with lowercase
#  Dont think we use a synchornour client for use in functional testing


