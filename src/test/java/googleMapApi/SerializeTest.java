package googleMapApi;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import pojo.AddPlaceTest;
import pojo.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SerializeTest {

    @Test
    public void serializeTest() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlaceTest addPlaceTest = new AddPlaceTest();
        addPlaceTest.setAccuracy(50);
        addPlaceTest.setAddress("29, side layout, cohen 09");
        addPlaceTest.setLanguage("French-IN");
        addPlaceTest.setPhone_number("89908098908");
        addPlaceTest.setWebsite("https://rahulshettyacademy.com");
        addPlaceTest.setName("Frontline house");
        List<String> myList = Arrays.asList("shoe park", "shop");
        addPlaceTest.setTypes(myList);
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlaceTest.setLocation(location);

        var res = given().queryParam("key", "qaclick123")
            .body(addPlaceTest)
            .when()
            .post("/maps/api/place/add/json")
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .response();

        var responseString = res.asString();
        System.out.println(responseString);
    }
}
