package stepDefinitions;


import io.cucumber.java.Before;
import utilities.RestAssuredExtension;

public class testInitialize_hook {

    @Before
    public void setup(){
        RestAssuredExtension restAssuredExtension =new RestAssuredExtension();

    }

}
