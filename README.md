# AutomationTest
Automated test suite for a Java application using JUnit/TestNG. The project includes CI/CD integration with GitHub Actions for continuous testing. Maven is used for build automation, and Docker is optional for containerized tests. Push changes to trigger tests and CI pipeline.

Automated Test Suite for Java Application
Description
This project consists of an automated test suite for a Java application, using JUnit and TestNG. The tests validate different API endpoints, handle authentication, and log results. CI/CD integration with GitHub Actions ensures continuous testing on code changes.

Features
Automated Testing: Uses JUnit/TestNG to test API endpoints.
CI/CD Integration: GitHub Actions for continuous testing.
Logging: Request and response details are logged to files.
Token Authentication: Handles token generation and management.
Setup
Clone the repository:

bash
Copy code
git clone https://github.com/your-username/your-project-name.git
cd your-project-name
Install dependencies:

Ensure you have Java 15 or above installed.
Install Maven for build management:
bash
Copy code
mvn clean install
Set up GitHub Actions:

GitHub Actions is configured to automatically run tests on pull requests and pushes to the main branch.
Run Tests Locally: To run tests locally, use Maven:

bash
Copy code
mvn test
CI/CD Pipeline
The GitHub Action workflow will trigger on:

Push: Push to the main branch triggers the test suite.
Pull Request: Tests are run when creating or updating a pull request.
Logging
Test results, including request/response details, are logged into specific log files located in:

src/test/resources/logs/
