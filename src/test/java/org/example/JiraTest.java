package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import junit.framework.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class JiraTest {

    @Test
    public void testJira() {

        SessionFilter sessionFilter = new SessionFilter();

        RestAssured.baseURI = "http://localhost:8080";

        //Login scenario in JIRA
        var authentication = given().header("Content-Type", "application/json")
            .body("username:ANiket " +
                "password: sdadsa")
            .filter(sessionFilter)  //this will store the response session
            .when()
            .post("/rest/auth/1/session").then().extract().response().asString();

//Add comment in Jira scenario
        var commentAdded = given()
            .relaxedHTTPSValidation()  // to bypass http certification
            .pathParams("key", "10101")  //here 10101 is Jira id
            .contentType(ContentType.JSON)
            .body(PayloadTest.complexPayloadCourseTest())
            .filter(sessionFilter)  //this will use login cookies session for authentication
            .when()
            .post("/rest/api/2/issue/{key}/comment").then().assertThat().statusCode(201).extract().response().asString();
        JsonPath js = new JsonPath(commentAdded);
        var listOfComment = js.getList("fields.comment.comments");
        var noOfComment = js.getList("").size();
        for (int i = 0; i < noOfComment; i++) {
            var desiredComment = listOfComment.get(i).toString();

            if (desiredComment.equalsIgnoreCase("")) {
                Assert.assertEquals(desiredComment, commentAdded);
                System.out.println("Added comment found");
            } else {
                System.out.println("Added comment not found");
            }
        }

        //Add attachment in Jira
        given().header("X-Atlassian-Token", "no-check").filter(sessionFilter)
            .pathParams("key", "10101")
            .header("Content-Type", "multipart/form-data") // here need to mention the type of header so that api will understand accordingly
            .multiPart("file", new File(""))  // here need to provide the file for attachment
            .when()
            .post("/rest/api/2/issue/{key}/comment").then().assertThat().statusCode(200);

        //get issue details (i.e jira ticket all field present)
        var issueDetails = given().filter(sessionFilter).pathParam("key", "10101")
            .queryParam("fields", "comment")//filtering out only comment field  ("fields" is present in Jira api contract)
            .when().get("").then().extract().response().asString();

    }
}
