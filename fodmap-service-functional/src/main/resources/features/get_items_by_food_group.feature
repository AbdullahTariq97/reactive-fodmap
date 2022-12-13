Feature:
#// passing bad input scenarios
  Scenario: The service should return list of fodmap items for a given food group
    Given the database is populated with a record
      | food_group  | name     | overall_rating | stratified_rating                                                                 |
      | 'vegetable' | 'argula' | 'r'            | {amount:75,fructose:'g',lactose:'g',manitol:'g',sorbitol:'g',gos:'g',fructan:'g'} |
    When a request is made to get fodmap items by food group VEGETABLE
    Then status code of 200 should be returned
    And the response body contains the following fodmap items
      | food_group  | name     | overall_rating | stratified_rating                                                                               |
      | "vegetable" | "argula" | "r"            | {"amount":75,"fructose":"g","lactose":"g","manitol":"g","sorbitol":"g","gos":"g","fructan":"g"} |

  Scenario: The service should return an empty list of fodmap items for a accepted food group for which no items exist
    Given no fodmap items are persisted in the database for PULSES_TOFU_AND_NUTS
    When a request is made to get fodmap items by food group PULSES_TOFU_AND_NUTS
    Then status code of 200 should be returned
    And the response body contains an empty list

  Scenario: The service should return expected error when database is down
    Given the database is down
    When a request is made to get fodmap items by food group VEGETABLE
    Then status code of 500 should be returned
    And the response body contains the error message downstream failure : database

#  check case sensitivity store with different case for food group into database and check if we are able to retrive
#  Must insure that the cassandra client is adding data with lowercase
#  Dont think we use a synchornour client for use in functional testing


