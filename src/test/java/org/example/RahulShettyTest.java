package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*; //here we use static because some packages in Rest assured are kept static
import static org.hamcrest.Matchers.*;  // 3rd

public class RahulShettyTest {

    //given: all input details
    //when: Submit the API-resource, http methods
    //Then: validate the resource

public static void main(String args[]){
    RestAssured.baseURI = "https://rahulshettyacademy.com";
  var response=  given().queryParam("key","qaclick123")
      .header("Content-Type","application/json")
      .log().all()
        .body("{\n" +
            "  \"location\": {\n" +
            "    \"lat\": -38.383494,\n" +
            "    \"lng\": 33.427362\n" +
            "  },\n" +
            "  \"accuracy\": 50,\n" +
            "  \"name\": \"Frontline house\",\n" +
            "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
            "  \"address\": \"29, side layout, cohen 09\",\n" +
            "  \"types\": [\n" +
            "    \"shoe park\",\n" +
            "    \"shop\"\n" +
            "  ],\n" +
            "  \"website\": \"http://google.com\",\n" +
            "  \"language\": \"French-IN\"\n" +
            "}\n").when().post("/maps/api/place/add/json")
        .then().assertThat().statusCode(200).log().all().body("scope",equalTo("APP"))
        .header("server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
            // "equalTo()" is added by 3rd import

    System.out.println(response);

    //extracting a place_id value
    JsonPath js = new JsonPath(response);  //convert string into JSON
    var placeId= js.getString("place_id");
    System.out.println(placeId);


    //Update Place
var newAdress = "Dighori Nagpur";

    given().queryParam("key","qaclick123").header("Content-Type","application/json")
        .log().all()
        .body("{\n" +
            "\"place_id\":\""+placeId+"\",\n" +
            "\"address\":\""+newAdress+"\",\n" +
            "\"key\":\"qaclick123\"\n" +
            "}")
        .when().put("maps/api/place/update/json")
        .then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));


    //get place
  //here in get method we dont require to use header() method because in get method we dont provide the body, only thing we needed is endpoint

   var getPlaceResponse= given().log().all().queryParam("key","qaclick123")
        .queryParam("place_id",placeId)
        .when().get("maps/api/place/get/json")
        .then()
        .assertThat().log().all().statusCode(200)          //.body("address",equalTo(newAdress));  // this is one method for assertion
        .extract().response().asString();    //this is another method to assertion

    JsonPath js1 = new JsonPath(getPlaceResponse);
   var actualAddress= js1.getString("address");
   System.out.println(actualAddress);
  Assert.assertEquals(actualAddress,newAdress);

}
}
