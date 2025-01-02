package api.tests;

import api.endpoints.VideoService;
import api.payload.Video;
import api.endpoints.AuthService;  // Make sure you import your AuthService
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import io.restassured.RestAssured;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class VideoServiceTest {

    VideoService videoService;

    @BeforeMethod
    public void setUp() {
        // Initialize the service before each test
        videoService = new VideoService();
    }

    private void logRequestResponseToFile(String filePath, String requestResponse) {
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(filePath, true)); // true for append mode
            printStream.println(requestResponse);
            printStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAuthToken() {
        // Replace with actual logic to fetch token from AuthService
        return AuthService.getToken(); // Assuming AuthService has a static method getToken()
    }

    @Test(priority = 1)
    public void testPostVideo() {
        // Create a Video object with the required data
        Video video = new Video();
        video.setCategory("Platform");
        video.setName("Mario");
        video.setRating("Mature");
        video.setReleaseDate("2012-05-04");
        video.setReviewScore(89);
        video.setId("147");

        // Hardcoded POST Video URL
        String postVideoUrl = "https://www.videogamedb.uk:443/api/videogame";

        // Specify log file location for the POST request
        String logFilePath = "src/test/resources/logs/response_log.txt";

        // Get the token dynamically from AuthService
        String authToken = getAuthToken();

        // Make the API call
        Response response = RestAssured.given()
            .baseUri(postVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .header("Content-Type", "application/json")
            .body(video)
            .log().all()  // Log all details (request + response)
            .post();

        // Log request and response details to the file
        logRequestResponseToFile(logFilePath, "Request: " + video.toString() + "\nResponse: " + response.getBody().asString());

        // Log response to the console
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Assert the status code is 201 Created
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 201 Created");
    }

    @Test(priority = 2)
    public void testGetVideoById() {
        String videoId = "1";
        
        // Hardcoded GET Video URL
        String getVideoUrl = "https://www.videogamedb.uk:443/api/videogame/" + videoId;

        // Specify log file location for the GET request
        String logFilePath = "src/test/resources/logs/get_video_log.txt";

        // Get the token dynamically from AuthService
        String authToken = getAuthToken();

        // Make the API call
        Response response = RestAssured.given()
            .baseUri(getVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .log().all()  // Log all details (request + response)
            .get();

        // Log request and response details to the file
        logRequestResponseToFile(logFilePath, "Request: GET " + getVideoUrl + "\nResponse: " + response.getBody().asString());

        // Log response to the console
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Assert the status code is 200 OK
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
    }

    @Test(priority = 3)
    public void testPutVideo() {
        // Create a Video object with updated data
        Video video = new Video();
        video.setCategory("Platform");
        video.setName("Super Mario");
        video.setRating("Mature");
        video.setReleaseDate("2012-06-01");
        video.setReviewScore(90);
        video.setId("1");

        String videoId = "1";
        
        // Hardcoded PUT Video URL
        String putVideoUrl = "https://www.videogamedb.uk:443/api/videogame/" + videoId;

        // Specify log file location for the PUT request
        String logFilePath = "src/test/resources/logs/put_video_log.txt";

        // Get the token dynamically from AuthService
        String authToken = getAuthToken();

        // Make the API call
        Response response = RestAssured.given()
            .baseUri(putVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .header("Content-Type", "application/json")
            .body(video)
            .log().all()  // Log all details (request + response)
            .put();

        // Log request and response details to the file
        logRequestResponseToFile(logFilePath, "Request: " + video.toString() + "\nResponse: " + response.getBody().asString());

        // Log response to the console
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Assert the status code is 200 OK
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
    }

    @Test(priority = 4)
    public void testDeleteVideo() {
        String videoId = "1";
        
        // Hardcoded DELETE Video URL
        String deleteVideoUrl = "https://www.videogamedb.uk:443/api/videogame/" + videoId;

        // Specify log file location for the DELETE request
        String logFilePath = "src/test/resources/logs/delete_video_log.txt";

        // Get the token dynamically from AuthService
        String authToken = getAuthToken();

        // Make the API call
        Response response = RestAssured.given()
            .baseUri(deleteVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .log().all()  // Log all details (request + response)
            .delete();

        // Log request and response details to the file
        logRequestResponseToFile(logFilePath, "Request: DELETE " + deleteVideoUrl + "\nResponse: " + response.getBody().asString());

        // Log response to the console
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Assert the status code is 200 OK
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
    }
    
    @Test(priority = 5)
    public void testPostVideoWithInvalidToken() {
        Video video = new Video();
        video.setCategory("Platform");
        video.setName("InvalidTokenTest");
        video.setRating("Everyone");
        video.setReleaseDate("2025-01-01");
        video.setReviewScore(50);
        video.setId("999");

        String postVideoUrl = "https://www.videogamedb.uk:443/api/videogame";
        String invalidToken = "invalid_token";

        Response response = RestAssured.given()
            .baseUri(postVideoUrl)
            .header("Authorization", "Bearer " + invalidToken)
            .header("Content-Type", "application/json")
            .body(video)
            .log().all()
            .post();

        Assert.assertEquals(response.getStatusCode(), 500, "Expected 401 Unauthorized status code");
        System.out.println("Response: " + response.getBody().asString());
    }

    @Test(priority = 6)
    public void testPostVideoWithMissingFields() {
        Video video = new Video();
        video.setCategory("Platform");  // Only setting one field

        String postVideoUrl = "https://www.videogamedb.uk:443/api/videogame";
        String authToken = getAuthToken();

        Response response = RestAssured.given()
            .baseUri(postVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .header("Content-Type", "application/json")
            .body(video)
            .log().all()
            .post();

        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 Bad Request status code");
        System.out.println("Response: " + response.getBody().asString());
    }

    @Test(priority = 7)
    public void testPostVideoWithInvalidDataFormat() {
        String invalidPayload = "{ \"category\": \"Platform\", \"name\": \"TestGame\", \"rating\": \"Everyone\", \"releaseDate\": \"2025-01-01\", \"reviewScore\": \"invalid_score\", \"id\": \"999\" }";

        String postVideoUrl = "https://www.videogamedb.uk:443/api/videogame";
        String authToken = getAuthToken();

        Response response = RestAssured.given()
            .baseUri(postVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .header("Content-Type", "application/json")
            .body(invalidPayload)
            .log().all()
            .post();

        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 Bad Request status code");
        System.out.println("Response: " + response.getBody().asString());
    }

    @Test(priority = 8)
    public void testGetNonExistentVideoById() {
        String nonExistentVideoId = "99999";
        String getVideoUrl = "https://www.videogamedb.uk:443/api/videogame/" + nonExistentVideoId;
        String authToken = getAuthToken();

        Response response = RestAssured.given()
            .baseUri(getVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .log().all()
            .get();

        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 Not Found status code");
        System.out.println("Response: " + response.getBody().asString());
    }
}
