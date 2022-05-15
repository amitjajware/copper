package utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class RestAssuredExtension {

    public static RequestSpecification Request;

    public RestAssuredExtension() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("http://localhost:8080");
        builder.setContentType(ContentType.JSON);
        var requestSpec = builder.build();
        Request = RestAssured.given().spec(requestSpec);

    }

    public static ResponseOptions<Response> getRequest(String url) throws URISyntaxException {
        try {
            Request.get(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, String> baseURL() throws URISyntaxException {
        String basrURL = "http://localhost:8080";
        Map<String, String> pathParam = (Map<String, String>) Request.basePath(basrURL);
        return pathParam;

    }

    public static void postPayLoad(String url, Map<String, String> body) throws URISyntaxException {
        Map<String, String> pathParam = baseURL();
        Request.pathParams(pathParam);
        Request.body(body);
        try {
            Request.post(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void postPayLoad_asString(String url, String body) throws URISyntaxException {
        Map<String, String> pathParam = baseURL();
        Request.pathParams(pathParam);
        Request.body(body);
        try {
            Request.post(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}