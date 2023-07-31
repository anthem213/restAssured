Feature: Validating Place API's
@AddPlace
  #below we were using scenario outline instead of scenario. Reason: We are providing data from examples hence outline should be given to understand that data should be read from external
  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given Add Place payload with "<name>" "<language>" "<address>"
    When user call "AddPlaceAPI" with "Post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "getPlaceAPI"

    Examples:
      | name    | language | address                        |
      | Baburao | Marathi  | Shaitan Gali Shamshan ke Piche |
      | Raju    | Hindi    | Takla Makaan                   |
      | Shyam   | Odia     | Karakorm-Timbuktu              |

  @DeletePlace
  #Scenario for deleteAPI
  Scenario: Verify delete Place
    Given Verify if Delete Place functionality is working
    When user call "deletePlaceAPI" with "Delete" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"


