package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final String REPORT_FOLDER = "reports";
    private static String reportFileName;

    // Initialize ExtentReports
    private static void initExtentReports() {
        if (extent == null) {
            // Generate the current date and time to add to the report filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            reportFileName = "ExtentReport_" + timestamp + ".html";

            // Create the reports folder if it doesn't exist
            File reportDir = new File(REPORT_FOLDER);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            // Set the path for the report file
            String reportPath = REPORT_FOLDER + File.separator + reportFileName;

            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName("Automation Test Results");
            reporter.config().setDocumentTitle("Extent Report");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Your Name");
            extent.setSystemInfo("Project Name", "Your Project");

            // Print the location of the report
            System.out.println("Extent Report will be available at: " + new File(reportPath).getAbsolutePath());
        }
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite started: " + context.getName());
        initExtentReports(); // Initialize the report
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite finished: " + context.getName());
        if (extent != null) {
            extent.flush(); // Write results to the report
            // Print the final location of the report
            System.out.println("Extent Report generated at: " + new File(REPORT_FOLDER + File.separator + reportFileName).getAbsolutePath());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        System.out.println("Starting Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
        System.out.println("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, "Test Failed");
        extentTest.get().log(Status.FAIL, result.getThrowable());
        System.out.println("Test Failed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped");
        System.out.println("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test failed but within success percentage: " + result.getMethod().getMethodName());
    }
}
