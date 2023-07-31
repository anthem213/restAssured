package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.Assert;
import org.testng.annotations.Test;

public class test_01_GET {

    @Test
    public void test_01(){

       Response response= RestAssured.get("https://reqres.in/api/users?page=2");
       System.out.println(response.asString());
       System.out.println(response.getBody().asString());
        System.out.println(response.getStatusCode());
        int statuscode = response.getStatusCode();
        Assert.assertEquals(statuscode,200);

    }
}
