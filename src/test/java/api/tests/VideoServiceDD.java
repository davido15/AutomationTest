package api.tests;

import api.payload.Video;
import api.endpoints.AuthService;
import api.endpoints.Routes;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;

public class VideoServiceDD {

    private XSSFWorkbook workbook;
    private Sheet sheet;
    private FileOutputStream fileOut;

    @BeforeMethod
    public void setUp() throws IOException {
        // Initialize Excel File
        File file = new File("src/test/resources/test_results.xlsx");

        if (!file.exists()) {
            // Create a new workbook if the file doesn't exist
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("TestResults");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Test Case");
            headerRow.createCell(1).setCellValue("Status");
            headerRow.createCell(2).setCellValue("Request");
            headerRow.createCell(3).setCellValue("Response");

        } else {
            // If file exists, load the existing workbook
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
        }
    }

    private void logToExcel(String testCase, String status, String request, String response) throws IOException {
        // Find the next available row
        int rowCount = sheet.getPhysicalNumberOfRows();
        Row row = sheet.createRow(rowCount);

        // Add data to the row
        row.createCell(0).setCellValue(testCase);
        row.createCell(1).setCellValue(status);
        row.createCell(2).setCellValue(request);
        row.createCell(3).setCellValue(response);

        // Write the data to the Excel file
        fileOut = new FileOutputStream("src/test/resources/test_results.xlsx");
        workbook.write(fileOut);
        fileOut.close();
    }

    private String getAuthToken() {
        // Replace with actual logic to fetch token from AuthService
        return AuthService.getToken(); // Assuming AuthService has a static method getToken()
    }

    // Sample method for creating JSON string using concatenation
    private String createVideoJson() {
        return "{"
            + "\"category\": \"Platform\","
            + "\"name\": \"Mario\","
            + "\"rating\": \"Mature\","
            + "\"releaseDate\": \"2012-05-04\","
            + "\"reviewScore\": 89,"
            + "\"id\": \"147\""
            + "}";
    }

    // Negative test case for invalid video creation
    private String createInvalidVideoJson() {
        return "{"
            + "\"category\": \"Platform\","
            + "\"name\": \"InvalidGame\","
            + "\"rating\": \"Everyone\","
            + "\"releaseDate\": \"invalid-date\","
            + "\"reviewScore\": \"invalid-score\","
            + "\"id\": \"123\""
            + "}";
    }

    @Test(priority = 1)
    public void testPostVideo() throws IOException {
        // Create a Video object with the required data
        Video video = new Video();
        video.setCategory("Platform");
        video.setName("Mario");
        video.setRating("Mature");
        video.setReleaseDate("2012-05-04");
        video.setReviewScore(89);
        video.setId("147");

        // Use Routes class to get the full POST Video URL
        String postVideoUrl = Routes.postVideoUrl();

        // Get the token dynamically from AuthService
        String authToken = getAuthToken();

        // Make the API call
        Response response = RestAssured.given()
            .baseUri(postVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .header("Content-Type", "application/json")
            .body(createVideoJson())  // Use the concatenated string JSON
            .post();

        // Log the request and response to Excel
        logToExcel("testPostVideo", 
            response.getStatusCode() == 200 ? "Passed" : "Failed", 
            createVideoJson(), 
            response.getBody().asString());

        // Assert the status code is 200 OK
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
    }

    @Test(priority = 2)
    public void testGetVideoById() throws IOException {
        String videoId = "1";
        
        // Use Routes class to get the full GET Video URL
        String getVideoUrl = Routes.getVideoByIdUrl(videoId);

        // Get the token dynamically from AuthService
        String authToken = getAuthToken();

        // Make the API call
        Response response = RestAssured.given()
            .baseUri(getVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .get();

        // Log the request and response to Excel
        logToExcel("testGetVideoById", 
            response.getStatusCode() == 200 ? "Passed" : "Failed", 
            "GET " + getVideoUrl, 
            response.getBody().asString());

        // Assert the status code is 200 OK
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
    }

    @Test(priority = 3)
    public void testPutVideo() throws IOException {
        // Create a Video object with updated data
        Video video = new Video();
        video.setCategory("Platform");
        video.setName("Super Mario");
        video.setRating("Mature");
        video.setReleaseDate("2012-06-01");
        video.setReviewScore(90);
        video.setId("1");

        String videoId = "1";
        
        // Use Routes class to get the full PUT Video URL
        String putVideoUrl = Routes.updateVideoUrl(videoId);

        // Get the token dynamically from AuthService
        String authToken = getAuthToken();

        // Make the API call
        Response response = RestAssured.given()
            .baseUri(putVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .header("Content-Type", "application/json")
            .body(createVideoJson())  // Use the concatenated string JSON
            .put();

        // Log the request and response to Excel
        logToExcel("testPutVideo", 
            response.getStatusCode() == 200 ? "Passed" : "Failed", 
            createVideoJson(), 
            response.getBody().asString());

        // Assert the status code is 200 OK
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
    }

    @Test(priority = 4)
    public void testDeleteVideo() throws IOException {
        String videoId = "1";
        
        // Use Routes class to get the full DELETE Video URL
        String deleteVideoUrl = Routes.deleteVideoUrl(videoId);

        // Get the token dynamically from AuthService
        String authToken = getAuthToken();

        // Make the API call
        Response response = RestAssured.given()
            .baseUri(deleteVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .delete();

        // Log the request and response to Excel
        logToExcel("testDeleteVideo", 
            response.getStatusCode() == 200 ? "Passed" : "Failed", 
            "DELETE " + deleteVideoUrl, 
            response.getBody().asString());

        // Assert the status code is 200 OK
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
    }

    @Test(priority = 5)
    public void testInvalidPostVideo() throws IOException {
        // Test with invalid JSON body
        String invalidVideoJson = createInvalidVideoJson();

        // Use Routes class to get the full POST Video URL
        String postVideoUrl = Routes.postVideoUrl();

        // Get the token dynamically from AuthService
        String authToken = getAuthToken();

        // Make the API call with invalid data
        Response response = RestAssured.given()
            .baseUri(postVideoUrl)
            .header("Authorization", "Bearer " + authToken)
            .header("Content-Type", "application/json")
            .body(invalidVideoJson)  // Use invalid JSON string
            .post();

        // Log the request and response to Excel
        logToExcel("testInvalidPostVideo", 
            response.getStatusCode() == 400 ? "Passed" : "Failed", 
            invalidVideoJson, 
            response.getBody().asString());

        // Assert the status code is 400 Bad Request
        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400 Bad Request");
    }
}
