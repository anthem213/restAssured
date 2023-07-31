package stepDefinations;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.Json;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlaceTest;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class StepDefinations extends Utils {
     RequestSpecification res;
    static ResponseSpecification resspec;
     Response response;
    static String place_id;  //reason behind making it static as if deletePlace API ran without AddPlaceAPi then deletePlaceAPI
    //needed place id to delete hence we are using hook to add precondtion to run addplaceAPi, so it will store the place id
    //  in static variable which can be use in deleteAPI

    TestDataBuild testDataBuild = new TestDataBuild();

    @Given("Add Place payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) throws IOException {

        resspec = new ResponseSpecBuilder().expectStatusCode(200)
            .expectContentType(ContentType.JSON).build();
        res = given().spec(requestSpecification()).body(testDataBuild.addPlacePayLoad(name, language, address));

    }

    @When("user call {string} with {string} http request")
    public void user_call_with_http_request(String resource, String httpMethod) {

        var resourceApi = APIResources.valueOf(resource).getResource();

        if (httpMethod.equalsIgnoreCase("POST")) {
            response = res.when().post(resourceApi)
                .then().spec(resspec).extract().response();
        } else if (httpMethod.equalsIgnoreCase("GET")) {
            response = res.when().get(resourceApi)
                .then().spec(resspec).extract().response();
        }

        else if(httpMethod.equalsIgnoreCase("DELETE")) {
            response = res.when().delete(resourceApi)
                .then().spec(resspec).extract().response();
        }
    }

    @Then("the API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(int int1) {
        assertEquals(response.getStatusCode(), 200);  //junit assert
    }

    @Then("{string} in response body is {string}")
    public void status_in_response_body_is(String keyValue, String expectedValue) {
        assertEquals(getJsonPath(response, keyValue), expectedValue);
    }


    @Then("verify place_Id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
        place_id = getJsonPath(response, "place_id");
        res = given().spec(requestSpecification()).queryParam("place_id", place_id);
        user_call_with_http_request(resource, "GET");
        var actualName = getJsonPath(response, "name");
        assertEquals(expectedName, actualName);
    }

    @Given("Verify if Delete Place functionality is working")
    public void verify_if_delete_place_functionality_is_working() throws IOException {
        res = given().spec(requestSpecification()).body(testDataBuild.deletePlacePayload(place_id));
    }
}
