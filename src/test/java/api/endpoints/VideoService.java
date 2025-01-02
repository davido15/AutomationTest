package api.endpoints;

import api.payload.Video;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class VideoService {

    // Get Video by ID
    public Response getVideoById(String videoId) {
        // Ensure the token is available
        String token =  "";// AuthService.getToken(); // Retrieve the token directly
                      

        Response response = RestAssured.given()
                .baseUri(Routes.baseUrl)
                .header("Authorization", "Bearer " + token) // Use Bearer + token
                .log().all()  // Logs the entire request (headers, body, params)
                .get(Routes.getVideoByIdUrl(videoId));

        // Log the response headers and body
        response.then().log().all();  // Logs the entire response
        System.out.println("Response Status Code for log by id: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        return response;
    }

    // Post a new Video
    public static Response postVideo(Video video) {
        // Ensure the token is available
        String token = AuthService.getToken(); // Retrieve the token directly

        Response response = RestAssured.given()
                .baseUri(Routes.baseUrl)
                .header("Authorization", "Bearer " + token) // Use Bearer + token
                .header("Content-Type", "application/json")
                .body(video)
                .log().all()  // Logs the entire request (headers, body, params)
                .post(Routes.postVideoUrl());

        // Log the response headers and body
        response.then().log().all();  // Logs the entire response

        return response;
    }

    // Update an existing Video
    public Response updateVideo(String videoId, Video video) {
        // Ensure the token is available
        String token = AuthService.getToken(); // Retrieve the token directly

        Response response = RestAssured.given()
                .baseUri(Routes.baseUrl)
                .header("Authorization", "Bearer " + token) // Use Bearer + token
                .header("Content-Type", "application/json")
                .body(video)
                .log().all()  // Logs the entire request (headers, body, params)
                .put(Routes.updateVideoUrl(videoId));

        // Log the response headers and body
        response.then().log().all();  // Logs the entire response

        return response;
    }

    // Delete a Video by ID
    public Response deleteVideo(String videoId) {
        // Ensure the token is available
        String token = AuthService.getToken(); // Retrieve the token directly

        Response response = RestAssured.given()
                .baseUri(Routes.baseUrl)
                .header("Authorization", "Bearer " + token) // Use Bearer + token
                .log().all()  // Logs the entire request (headers, body, params)
                .delete(Routes.deleteVideoUrl(videoId));

        // Log the response headers and body
        response.then().log().all();  // Logs the entire response

        return response;
    }

    // Post a Video with a provided token and URL
    public static Response postVideoWithToken(Video video, String token, String url) {
        return RestAssured.given()
                .baseUri(url)
                .header("Authorization", "Bearer " + token) // Use Bearer + token
                .header("Content-Type", "application/json")
                .body(video)
                .log().all() // Logs the entire request (headers, body, params)
                .post();
    }
}
