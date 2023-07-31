package stepDefinations;

import io.cucumber.java.Before;

import java.io.IOException;

//hooks help to configure pre-conditions and post-conditions
public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        //execute this code only when place id is null

        StepDefinations stepDefinations = new StepDefinations();
        if (stepDefinations.place_id == null) {
            stepDefinations.add_place_payload_with("Totla_Tiwari", "Bengali", "Palki Nagar");
            stepDefinations.user_call_with_http_request("AddPlaceAPI", "POST");
            stepDefinations.verify_place_id_created_maps_to_using("Totla_Tiwari", "getPlaceAPI");
        }
    }
}
