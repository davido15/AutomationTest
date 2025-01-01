package api.endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private static String token = null;  // To hold the token in memory

    // Authentication request to get the token
    public static String authenticate(String username, String password) {
        // Prepare payload for authentication
        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);

        // Send request to get the token
        Response response = RestAssured.given()
                .baseUri("https://www.videogamedb.uk:443/api/authenticate")
                .contentType(ContentType.JSON)
                .body(payload)
                .post();

        if (response.getStatusCode() == 200) {
            // Token received successfully
            token = response.jsonPath().getString("token");
            return token;
        } else {
            // If authentication fails, throw an exception
            throw new RuntimeException("Authentication failed, response code: " + response.getStatusCode());
        }
    }

    // Getter for the token
    public static String getToken() {
        // Attempt to authenticate if the token is null
        if (token == null) {
            authenticate("admin", "admin"); // Replace with your default credentials if necessary
        }
        return token;  // Return the token (will be non-null if authentication succeeded)
    }

    // Method to clear the token (logout or token expiry scenario)
    public static void clearToken() {
        token = null;
    }
}
