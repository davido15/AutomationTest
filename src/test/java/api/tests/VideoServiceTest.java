package api.tests;

import api.endpoints.VideoService;
import api.payload.Video;
import api.endpoints.AuthService;
import api.endpoints.Routes; 
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VideoServiceTest {

    private VideoService videoService;

    @BeforeMethod
    public void setUp() {
        // Initialize the service before each test
        videoService = new VideoService();
    }

    private String getAuthToken() {
        // Replace with actual logic to fetch token from AuthService
        return AuthService.getToken(); // Assuming AuthService has a static method getToken()
    }

    @Test(priority = 1)
    public void testPostVideo() {
        Video video = new Video();
        video.setCategory("Platform");
        video.setName("Mario");
        video.setRating("Mature");
        video.setReleaseDate("2012-05-04");
        video.setReviewScore(89);
        video.setId("147");

        String postVideoUrl = Routes.postVideoUrl(); // Fetch URL from Routes
        String authToken = getAuthToken();

        Response response = RestAssured.given()
            .baseUri(postVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .header("Content-Type", "application/json")
            .body(video)
            .log().all()
            .post();

        System.out.println("Request: POST to " + postVideoUrl + " with body " + video);
        System.out.println("Response: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
    }

    @Test(priority = 2)
    public void testGetVideoById() {
        String videoId = "1";
        String getVideoUrl = Routes.getVideoByIdUrl(videoId); // Fetch URL from Routes
        String authToken = getAuthToken();

        Response response = RestAssured.given()
            .baseUri(getVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .log().all()
            .get();

        System.out.println("Request: GET from " + getVideoUrl);
        System.out.println("Response: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
    }

    @Test(priority = 3)
    public void testPutVideo() {
        Video video = new Video();
        video.setCategory("Platform");
        video.setName("Super Mario");
        video.setRating("Mature");
        video.setReleaseDate("2012-06-01");
        video.setReviewScore(90);
        video.setId("1");

        String videoId = "1";
        String putVideoUrl = Routes.updateVideoUrl(videoId); // Fetch URL from Routes
        String authToken = getAuthToken();

        Response response = RestAssured.given()
            .baseUri(putVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .header("Content-Type", "application/json")
            .body(video)
            .log().all()
            .put();

        System.out.println("Request: PUT to " + putVideoUrl + " with body " + video);
        System.out.println("Response: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
    }

    @Test(priority = 4)
    public void testDeleteVideo() {
        String videoId = "8";
        String deleteVideoUrl = Routes.deleteVideoUrl(videoId); // Fetch URL from Routes
        String authToken = getAuthToken();

        Response response = RestAssured.given()
            .baseUri(deleteVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .log().all()
            .delete();

        System.out.println("Request: DELETE from " + deleteVideoUrl);
        System.out.println("Response: " + response.getBody().asString());

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

        String postVideoUrl = Routes.postVideoUrl(); // Fetch URL from Routes
        String invalidToken = "invalid_token";

        Response response = RestAssured.given()
            .baseUri(postVideoUrl)
            .header("Authorization", "Bearer " + invalidToken)
            .header("Content-Type", "application/json")
            .body(video)
            .log().all()
            .post();

        System.out.println("Request: POST to " + postVideoUrl + " with body " + video + " using invalid token");
        System.out.println("Response: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 500, "Expected 500 Unauthorized status code");
    }

    @Test(priority = 6)
    public void testPostVideoWithMissingFields() {
        Video video = new Video();
        video.setCategory("Platform");

        String postVideoUrl = Routes.postVideoUrl(); // Fetch URL from Routes
        String authToken = getAuthToken();

        Response response = RestAssured.given()
            .baseUri(postVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .header("Content-Type", "application/json")
            .body(video)
            .log().all()
            .post();

        System.out.println("Request: POST to " + postVideoUrl + " with incomplete body " + video);
        System.out.println("Response: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 Bad Request status code");
    }
}
