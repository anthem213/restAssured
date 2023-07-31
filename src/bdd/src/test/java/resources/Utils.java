package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification req;  //here we make it as Static because if we dont make it as
    //static then 2nd time (we are providing three parameters) if it runs then again it will consider req as null
    //making static helps to share this variable for entire execution
    //now in logging.txt file we can see the three payload with different parameter data

    public RequestSpecification requestSpecification() throws IOException {

        if (req == null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));  //log all data into logging.txt file
            req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .addFilter(RequestLoggingFilter.logRequestTo(log))   //to log the request in the above file
                .addFilter(ResponseLoggingFilter.logResponseTo(log)) // to log the response in the above file
                .setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
    }

    public String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("/Users/aniket.lanjewar/IdeaProjects/restAssured/src/bdd/src/test/java/resources/global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public String getJsonPath(Response response, String key) {
        var resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }
}
