
# Video Service API Testing Documentation

## Overview

This repository contains automated API tests for the Video Service API, which allows CRUD operations on video data. The tests are written using TestNG and RestAssured, and they handle endpoints for creating, reading, updating, and deleting video records.

---

## Features

- **Automated Testing**: Uses TestNG and RestAssured to test API endpoints.
- **CI/CD Integration**: GitHub Actions for continuous testing.
- **Logging**: Request and response details are logged to an Excel file for easy tracking.
- **Token Authentication**: Handles token generation and management using a dynamic token-fetching method.

---

## Setup

### Prerequisites

- Java 8 or higher
- Maven
- Git
- A valid AuthService that provides a token

### Steps to Set Up

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/your-project-name.git
   cd your-project-name
   ```

2. **Install Dependencies**:
   Run the following Maven command to download the necessary dependencies:
   ```bash
   mvn clean test
   ```

3. **Configure Your Environment**:

   - Ensure that you have a valid `AuthService` for token generation.
   - Edit the `src/test/resources/config.properties` file with the following content:
   ```properties
   # Base URL for the API
   base_url=https://www.videogamedb.uk:443/api/

   # Video endpoints
   video.getById.url=videogame/
   video.post.url=videogame
   video.update.url=videogame
   video.delete.url=videogame

   # Auth endpoint
   auth.url=authenticate
   ```

---

## Dependencies

The project requires the following dependencies:

- **TestNG**: A testing framework that facilitates the creation of tests.
- **RestAssured**: A Java library used for testing REST APIs.
- **Maven**: A build automation tool for managing project dependencies.
- **Excel File Handling**: Apache POI or any other library for reading and writing Excel files to store the test results.

These dependencies are automatically handled by Maven during the `mvn install` command. Ensure that your `pom.xml` file includes the following (or equivalent) dependencies:

```xml
<dependencies>
    <!-- TestNG for unit testing -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.3.0</version>
        <scope>test</scope>
    </dependency>

    <!-- RestAssured for API testing -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>4.4.0</version>
        <scope>test</scope>
    </dependency>

    <!-- Apache POI for Excel file handling -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi</artifactId>
        <version>5.2.3</version>
    </dependency>

    <!-- Logging dependency -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.32</version>
    </dependency>

    <!-- Other dependencies -->
</dependencies>
```

---

## Create a Test Results File

The test results are stored in an Excel file named `test_results.xlsx`, located in the `src/test/resources` directory.
The Extent Report can be viewed ina browser and is located in /**reports** folder

- If the file doesn't exist, it will be automatically created when the tests are run.
- The results include the following information:
  - **Test Case**: The name of the test case.
  - **Status**: Whether the test passed or failed.
  - **Request**: The request payload sent to the API.
  - **Response**: The response received from the API.

This file is overwritten after each test run to store the latest results.

---

## Running the Tests

### Locally:

To execute the tests locally, run the following Maven command:
```bash
mvn test
```

### CI/CD Integration:

- GitHub Actions will automatically run the tests on each push to the repository.
- Ensure that the `maven.yml` file in `.github/workflows` is set up to trigger on pushes or pull requests to the main branch.

---

## Services

### VideoService

This service handles all interactions with the video-related API endpoints. It uses the base URL from `config.properties` to dynamically generate full URLs for each operation.

#### Methods:
- **getVideoByIdUrl(String videoId)**: Returns the full URL for retrieving a video by ID.
- **postVideoUrl()**: Returns the full URL for posting a new video.
- **updateVideoUrl(String videoId)**: Returns the full URL for updating a video by ID.
- **deleteVideoUrl(String videoId)**: Returns the full URL for deleting a video by ID.
- **getAuthHeader()**: Returns the authorization header with a dynamically fetched token.

### AuthService

This service is responsible for managing token authentication. It retrieves the API token dynamically from the configured `auth.url`.

#### Methods:
- **getToken()**: Fetches the token using the AuthService.

---

## Test Cases

The `VideoServiceDD` class contains the test cases for the video service, which include:

- **testPostVideo**: Creates a new video.
- **testGetVideoById**: Retrieves a video by ID.
- **testPutVideo**: Updates an existing video.
- **testDeleteVideo**: Deletes a video by ID.
- **testInvalidPostVideo**: Tests the creation of a video with invalid data.

Each test case:
- Makes an API call to the respective endpoint.
- Logs the request and response details to an Excel file (`test_results.xlsx`).
- Verifies the API response status code.
- Asserts that the response code matches the expected result (e.g., `200 OK` for successful requests, `400 Bad Request` for invalid data).

---

## Logging

The request and response details for each API test are logged to `test_results.xlsx`. This file includes:
- **Test Case**: The name of the test case.
- **Status**: Whether the test passed or failed.
- **Request**: The request payload sent to the API.
- **Response**: The response received from the API.

This file is overwritten after each test run to store the latest results.

---

## Upcoming Features

The following features are planned for future updates:

1. **Parallel Test Execution**: Improve test execution time by running tests in parallel.
2. **Extended Reporting**: Enhance the test reporting system to generate more comprehensive reports, including metrics on response times and error rates.
3. **More Video Endpoints**: Add more CRUD operations for video metadata.
4. **Error Handling Enhancements**: Improve error handling by capturing more detailed information about failures in the tests.

---

## Troubleshooting

- If the tests are failing due to an invalid token, ensure that your `AuthService` is functioning correctly and returning valid tokens.
- If the Excel file is not being updated, ensure that the application has write permissions to the `src/test/resources` directory.
- Make sure that the base URL and API endpoints are correctly configured in `config.properties`.
- If the API is not responding or returning unexpected status codes, verify that the API service is running and reachable.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
