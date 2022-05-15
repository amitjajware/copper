package stepDefinitions;

import com.fasterxml.jackson.databind.util.JSONPObject;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.core.options.CucumberProperties;
import io.cucumber.datatable.CucumberDataTableException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.apache.http.entity.mime.content.StringBody;
import org.hamcrest.core.Is;
import org.junit.runner.Request;
import org.testng.annotations.Test;
import utilities.RestAssuredExtension;
import utilities.commonLib;
import utilities.serializeDeserialize;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;


public class commonSteps {

  private static ResponseOptions<Response> response;
  public static String JSON_Folder_Location = "/src/data/JSON_Files/";


    @Given("The user verify the running service content at {string}")
    public void theUserVerifyTheRunningService(String aFilePath) throws Exception {

        serializeDeserialize.pojoToJson();
        serializeDeserialize.pojoToXML();
        serializeDeserialize.pojoToHTML();
        serializeDeserialize.jsonToPOJO();

            // Passing url value from data table to this function
            response = RestAssuredExtension.getRequest(baseURI+aFilePath);
            assert response != null;
            // Verify the default structure
            assertThat(response.getBody().jsonPath().get(aFilePath), hasItem("id"));
            assertThat(response.getBody().jsonPath().get(aFilePath), hasItem("name"));
            assertThat(response.getBody().jsonPath().get(aFilePath), hasItem("currency"));
            assertThat(response.getBody().jsonPath().get(aFilePath), hasItem("year"));
            assertThat(response.getBody().jsonPath().get(aFilePath), hasItem("value"));

    }
    @When("The user post assets records to FilePath {string}")
    public void theUserPostAssetsRecordsIntoSystem(@NotNull DataTable aDataTable, String filePath) throws Exception{
        HashMap<String,String> postContent = new HashMap<>();

        List<String> myDataList = aDataTable.transpose().asList(String.class);
        int listlength = myDataList.size();
        for(int count =0; count <=listlength-2;count =count+2){
            String myTemplateValue = myDataList.get(count);
            String myActualValue = myDataList.get(count+1);
            // Passing data value from data table to this HashMap
            postContent.put(myTemplateValue,myActualValue);

        }

        // Submitting post request
        RestAssuredExtension.postPayLoad(filePath,postContent);
        wait(1000);
        // Verify if recently added data is available or not?
        assertThat(response.getBody().jsonPath().get("Currency"), hasItem("USD"));

    }

    @Then("the user verify the inserted record")
    public void theUserVerifyTheInsertedRecord(@NotNull DataTable aDataTable) throws Exception {
        List<String> myDataList = aDataTable.transpose().asList(String.class);
        int listlength = myDataList.size();
        for (int count = 0; count <= listlength - 2; count = count + 2) {
            String myTemplateValue = myDataList.get(count);
            String myActualValue = myDataList.get(count + 1);
            // Passing url value from data table to this function
            response = RestAssuredExtension.getRequest(RestAssuredExtension.baseURL().toString());
            assert response != null;
            // Verify the default structure
            assertThat(response.getBody().jsonPath().get(myTemplateValue), hasItem(myActualValue));
        }

    }

    @Given("Copper user verify the running service content at {string}")
    public void copperUserVerifyTheRunningServiceContentAt(String aFilePath) throws URISyntaxException {

        response = RestAssuredExtension.getRequest(baseURI+aFilePath);
        assert response != null;
        // Verify the default structure
        assertThat(response.getBody().jsonPath().get(aFilePath), hasItem("id"));
        assertThat(response.getBody().jsonPath().get(aFilePath), hasItem("name"));
        assertThat(response.getBody().jsonPath().get(aFilePath), hasItem("currency"));
        assertThat(response.getBody().jsonPath().get(aFilePath), hasItem("year"));
        assertThat(response.getBody().jsonPath().get(aFilePath), hasItem("value"));
    }

    @When("Copper user post assets records to FilePath {string}")
    public void copperUserPostAssetsRecordsToFilePath(String filePath) throws URISyntaxException {
        //First Read the JSON Files from absolute path
        File path = new File(JSON_Folder_Location);
        File[] files = path.listFiles();
        System.out.println(files);
        for(int i=0; i<files.length; i++){
            File file = new File(JSON_Folder_Location+files[i].getName());
            String JSON_PayLoad = commonLib.readFileAsString(file.getAbsolutePath());

            //Now pass the Json files/messages to the post method

            RestAssuredExtension.postPayLoad_asString(filePath,JSON_PayLoad);
        }

    }

    @Then("Copper user verify the inserted record")
    public void copperUserVerifyTheInsertedRecord() {
        // Verify if recently added data is available or not?
        assertThat(response.getBody().jsonPath().get("Currency"), hasItem("USD"));
        assertThat(response.getBody().jsonPath().get("Currency"), hasItem("GBP"));
        assertThat(response.getBody().jsonPath().get("id"), hasItem("10000001"));
        assertThat(response.getBody().jsonPath().get("year"), hasItem("2021"));

    }
}
