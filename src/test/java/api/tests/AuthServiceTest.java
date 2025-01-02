package api.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Map;

public class AuthServiceTest {

    private static final String BASE_URL = "https://www.videogamedb.uk:443/api";  // Use your base URL here
    private static final String AUTH_URL = BASE_URL + "/authenticate";  // Hardcoded authentication endpoint

    @BeforeClass
    public void setup() {
        System.out.println("Setting up base URI...");
        RestAssured.baseURI = BASE_URL;
        System.out.println("Base URI set to: " + BASE_URL);
    }

    @Test(priority = 1)
    public void testSuccessfulAuthentication() {
        System.out.println("Running test: testSuccessfulAuthentication");

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "admin");
        credentials.put("password", "admin");

        System.out.println("Sending request to authenticate with valid credentials...");
        Response response = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(credentials)
                .post(AUTH_URL);

        // Log response details for debugging
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        Assert.assertNotNull(response.jsonPath().getString("token"), "Token should not be null");

        System.out.println("Test successful authentication passed.");
    }

    @Test(priority = 2)
    public void testAuthenticationWithInvalidCredentials() {
        System.out.println("Running test: testAuthenticationWithInvalidCredentials");

        Map<String, String> invalidCredentials = new HashMap<>();
        invalidCredentials.put("username", "invalid_user");
        invalidCredentials.put("password", "wrong_password");

        System.out.println("Sending request to authenticate with invalid credentials...");
        Response response = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(invalidCredentials)
                .post(AUTH_URL);

        // Log response details for debugging
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 403, "Status code should be 403");
        Assert.assertTrue(response.jsonPath().getString("error").contains("Forbidden"), "Error message should indicate invalid credentials");

        System.out.println("Test authentication with invalid credentials passed.");
    }

    @Test(priority = 3)
    public void testServerErrorDuringAuthentication() {
        System.out.println("Running test: testServerErrorDuringAuthentication");

        String malformedPayload = "{ \"username\": \"admin\", \"password\" ";

        System.out.println("Sending request with malformed payload to trigger server error...");
        Response response = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(malformedPayload)
                .post(AUTH_URL);

        // Log response details for debugging
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400");
        Assert.assertTrue(response.jsonPath().getString("error").contains("Bad Request"), "Error message should indicate server-side issue");

        System.out.println("Test server error during authentication passed.");
    }
}
