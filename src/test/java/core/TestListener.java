package core;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.testng.*;

import java.io.File;
import java.io.IOException;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter reporter =
                new ExtentSparkReporter("./reports/extent-report.html");

        reporter.config().setReportName("eBay UI Automation Report");
        reporter.config().setDocumentTitle("Extent Report");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test =
                extent.createTest(result.getMethod().getMethodName());
        testThread.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().pass("Test Passed");
        attachScreenshot(result.getName(), "PASS");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testThread.get().fail(result.getThrowable());
        attachScreenshot(result.getName(), "FAIL");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private void attachScreenshot(String testName, String status) {

        WebDriver driver = DriverManager.getDriver();

        if (driver == null) return;

        String screenshotDir =
                System.getProperty("user.dir") + "/reports/screenshots/";

        new File(screenshotDir).mkdirs(); // ensure folder exists

        String screenshotPath =
                screenshotDir + testName + "_" + status + ".png";

        String relativePath =
                "screenshots/" + testName + "_" + status + ".png";

        try {
            File file =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(file, new File(screenshotPath));

            testThread.get()
                    .addScreenCaptureFromPath(relativePath);

        } catch (IOException e) {
            log.error("Screenshot failed", e);
        }
    }
}
