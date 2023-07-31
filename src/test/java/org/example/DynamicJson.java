package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) throws IOException {
        RestAssured.baseURI = "http://216.10.245.166";
        var resp = given().header("Content-Type", "application/json").body(PayloadTest.addBook(isbn, aisle)).log().all().when().post("/Library/Addbook.php")
            .then()
            .assertThat()
            .statusCode(200)
            .log().all()
            .body("Msg", equalTo("successfully added"))
            .body("ID", equalTo(isbn.concat(aisle)))
            .extract()
            .response()
            .asString();


//        given().header("Content-Type", "application/json")
//            .body(new String(Files.readAllBytes(Paths.get(""))))


        JsonPath js = new JsonPath(resp);
        var id = js.get("ID").toString();
        System.out.println(id);


        //my method here I uses JSON enum
        var resp1 = given().contentType(ContentType.JSON).body(PayloadTest.addBook("1231", "BrunoZard")).when().post("/Library/Addbook.php").then().assertThat().statusCode(200)
            .body("Msg", equalTo("successfully added"))
            .extract()
            .response()
            .asString();
    }

    @DataProvider(name = "BooksData")
    public Object[][] getData() {
        return new Object[][]{{"223344", "wdweq"}, {"1222", "ssddffgg"}, {"0000", "qwerty"}};

    }
}
