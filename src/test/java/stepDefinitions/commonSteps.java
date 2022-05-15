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
import org.apache.juneau.json.JsonParser;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.parser.ParseException;
import org.apache.juneau.serializer.SerializeException;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.runner.Request;
import org.testng.annotations.Test;
import utilities.AssetPOJO;
import utilities.RestAssuredExtension;
import utilities.commonLib;
import utilities.serializeDeserialize;

import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;


public class commonSteps {

  private static ResponseOptions<Response> response;
  public static String JSON_Folder_Location = "src/data/JSON_Files/";

    @Given("Copper user post assets records to FilePath {string}")
    public void copperUserPostAssetsRecordsToFilePath(String filePath) throws URISyntaxException, SerializeException, InterruptedException {
        //First Read the JSON Files from absolute path
        File path = new File(JSON_Folder_Location);
        File[] files = path.listFiles();
        System.out.println(files);
        for(int i=0; i<files.length; i++) {
            File file = new File(JSON_Folder_Location + files[i].getName());
            String JSON_PayLoad = commonLib.readFileAsString(file.getAbsolutePath());

            // read and split the values coming from flat input file

            String[] JsonString = JSON_PayLoad.split("\\n");

            for (int j = 0; j < JsonString.length; j++) {
                String SplitJson = JsonString[j];
                String[] Split_further_Json = SplitJson.split(",");
                // Assigning all the values to string index
                long id = Long.parseLong(Split_further_Json[0]);
                String name = Split_further_Json[1];
                String Currency = Split_further_Json[2];
                short year = Short.parseShort(Split_further_Json[3]);
                BigDecimal bdec = new BigDecimal(Split_further_Json[4]);


                //Serialize the json prior to post
                AssetPOJO assetPOJO = new AssetPOJO(id, name, Currency, year, bdec);

                //POJO to JSON
                JsonSerializer jsonSerializer = JsonSerializer.DEFAULT_READABLE;
                String json = jsonSerializer.serialize(assetPOJO);
                System.out.println(json);

                //Now pass the Json files/messages to the post method

                RestAssuredExtension.postPayLoad_asString(filePath, json);

            }
        }

    }

    @Then("Copper user verify the inserted record")
    public void copperUserVerifyTheInsertedRecord() throws ParseException, URISyntaxException {
        // Verify if recently added data is available or not?
        //Again Read the JSON Files from expected values
        File path = new File(JSON_Folder_Location);
        File[] files = path.listFiles();
        System.out.println(files);
        for (int i = 0; i < files.length; i++) {
            File file = new File(JSON_Folder_Location + files[i].getName());
            String JSON_PayLoad = commonLib.readFileAsString(file.getAbsolutePath());

            // read and split the values coming from flat input file

            String[] JsonString = JSON_PayLoad.split("\\n");

            for (int j = 0; j < JsonString.length; j++) {
                String SplitJson = JsonString[j];
                String[] Split_further_Json = SplitJson.split(",");
                // Assigning all the values to string index
                long id = Long.parseLong(Split_further_Json[0]);
                String name = Split_further_Json[1];
                String Currency = Split_further_Json[2];
                short year = Short.parseShort(Split_further_Json[3]);
                BigDecimal bdec = new BigDecimal(Split_further_Json[4]);

                ///using JSON parser below for response
                JsonParser jsonParser = JsonParser.DEFAULT;
                String ResponseValue = String.valueOf(RestAssuredExtension.getRequest("http://localhost:8080/collaterals/" + j));
                AssetPOJO assetPOJO1 = jsonParser.parse(ResponseValue, AssetPOJO.class);

                //Putting assert for validation
                Assert.assertEquals(Optional.of(id), assetPOJO1.getId());
                Assert.assertEquals(name, assetPOJO1.getName());
                Assert.assertEquals(Currency, assetPOJO1.getCurrency());
                Assert.assertEquals(Optional.of(year), assetPOJO1.getYear());
                Assert.assertEquals(bdec, assetPOJO1.getValue());
                //********************
            }
        }
    }
        @Given("The user verify the running service content at {string}")
    public void theUserVerifyTheRunningService(String aFilePath) throws Exception {

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
    public void theUserPostAssetsRecordsIntoSystem(DataTable aDataTable, String filePath) throws Exception{
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



    }

