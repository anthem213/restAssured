package resources;

import pojo.AddPlaceTest;
import pojo.Location;

import java.util.Arrays;
import java.util.List;

public class TestDataBuild {

    public AddPlaceTest addPlacePayLoad(String name, String language, String address) {

        AddPlaceTest addPlaceTest = new AddPlaceTest();
        addPlaceTest.setAccuracy(50);
        addPlaceTest.setAddress(address);
        addPlaceTest.setLanguage(language);
        addPlaceTest.setPhone_number("89908098908");
        addPlaceTest.setWebsite("https://rahulshettyacademy.com");
        addPlaceTest.setName(name);
        List<String> myList = Arrays.asList("shoe park", "shop");
        addPlaceTest.setTypes(myList);
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlaceTest.setLocation(location);
        return addPlaceTest;
    }

    public String deletePlacePayload(String placeId){
        return "{\n" +
            "\n" +
            "     \"place_id\": \""+placeId+"\"\n" +
            "}";
    }
}
