package org.example;

import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

import java.util.HashMap;
import java.util.HashSet;

public class ComplexJsonParseTest {

    public static void main(String args[]) {

        JsonPath js = new JsonPath(PayloadTest.complexPayloadCourseTest());
        var count = js.getInt("courses.size()");
        System.out.println(count);  //no. of courses returned by API

        var purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);  //print purchase amount

        //extract first course (My method)
        var list = js.getList("courses.title");
        System.out.println(list.get(0));

        //Rahul Method: extract first course
        var courseName = js.get("courses.title[0]");
        System.out.println(courseName);

        //print all course titles and their respective Prices
        list.stream().forEach(a -> System.out.println(a));  //by using stream

//by using traditional for loop
        var priceList = js.getList("courses.price");
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put(list.get(i).toString(), priceList.get(i).toString());  // to get price associated to Course
            System.out.println(hm);
            System.out.println(list.get(i));
        }

        //print no. of copies sold by RPA course
        var copiesList = js.getList("courses.copies");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equalsIgnoreCase("RPA")) {
                System.out.println(copiesList.get(i) + "*******");
                break;
            }
        }

        //verify if sum of all course prices matches with Purchase Amount

        var total = 0;
        for (int i = 0; i < list.size(); i++) {
            var sum = Integer.parseInt(copiesList.get(i).toString()) * Integer.parseInt(priceList.get(i).toString());
            total = sum + total;
        }
        System.out.println("Calculated purchase amount: " + total);
        Assert.assertEquals(total, purchaseAmount);

    }
}
