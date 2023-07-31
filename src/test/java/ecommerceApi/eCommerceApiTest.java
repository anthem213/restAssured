package ecommerceApi;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class eCommerceApiTest {

    @Test
    public void testECommerce() {
        var req = new RequestSpecBuilder()
            .setBaseUri("https://rahulshettyacademy.com")
            .setContentType(ContentType.JSON)
            .build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("anthem.304lan@gmail.com");
        loginRequest.setUserPassword("Rahul@123");
        var reqLogin = given().spec(req).body(loginRequest);
        var responseLogin = reqLogin.
            when()
            .post("/api/ecom/auth/login")
            .then()
            .extract()
            .response()
            .as(LoginResponse.class);

        System.out.println("UserId: " + responseLogin.getUserId());
        System.out.println("Token: " + responseLogin.getToken());
        var token = responseLogin.getToken();


        //Order Product
//
//        var createOrderBaseRequest = new RequestSpecBuilder()
//            .setBaseUri("https://rahulshettyacademy.com")
//            .addHeader("authorization", token)//for authorization
//            .setContentType(ContentType.JSON)
//            .build();
//
//        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
//        createOrderRequest.setProductOrderedId("6262e990e26b7e1a10e89bfa");
//        createOrderRequest.setCountry("British Indian Ocean Territory");
//        List<CreateOrderRequest> orderList = new ArrayList<CreateOrderRequest>();
//        orderList.add(createOrderRequest);
//
//        CreateOrderResponse createOrderRes = new CreateOrderResponse();
//        createOrderRes.setOrders(orderList);
//
//        var createOrder = given().spec(createOrderBaseRequest).body(createOrderRes);
//        var createOrderResponseDetails = createOrder
//            .when()
//            .post("/api/ecom/order/create-order")
//            .then()
//            .statusCode(201)
//            .extract()
//            .response()
//            .asString();
//        System.out.println(createOrderResponseDetails);

        //Delete Product

        var deleteProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
            .addHeader("authorization", token)
            .setContentType(ContentType.JSON)
            .build();

        var deleteProdReq = given().spec(deleteProdBaseReq).pathParam("productId", "63ce26df568c3e9fb1ff3c8c");
        var deleteProdResp = deleteProdReq.when().delete("/api/ecom/order/delete-order/{productId}")
            .then().extract().response()
            .asString();

        JsonPath jsonPath1 = new JsonPath(deleteProdResp);
        Assert.assertEquals("Orders Deleted Successfully", jsonPath1.get("message"));

    }
}
