package demoPractice;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.Json;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.*;

import static io.restassured.RestAssured.given;

public class demoTestRandomAPITest {


    @Test
    public void testDemoRandom() {

        var req = new RequestSpecBuilder().setBaseUri("https://reqres.in/")
            .setBasePath("/api/users/2").build();

        var res = given().spec(req).when().get().then().extract().response().asString();

        JsonPath js = new JsonPath(res);
        var b = js.get("data.email");
        System.out.println("Data is here............" + b.toString());
    }

    @Test
    public void testDemoPost(){

        var req = new RequestSpecBuilder().setBaseUri("https://reqres.in/")
            .setBasePath("/api/users").build();
      var res=  given().spec(req).body("{\n" +
            "    \"name\": \"sda\",\n" +
            "    \"job\": \"lesadaaader\"\n" +
            "}").when().post().prettyPrint();

        JsonPath js = new JsonPath(res);
        System.out.println(res);
    }
}
