package Utility.com;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;

public class ExtentReportGenerator extends BaseClass implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(ExtentReportGenerator.class);

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent-report.html");
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("ERail.Com");
        sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Project Name", "ERail.Com");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("TOOL", "Selenium");
        extent.setSystemInfo("QA", "Sonaji");

        logger.info("Test Suite started!");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        logger.info("Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
        logger.info("Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, result.getThrowable());
        String screenshotPath = takeScreenshot(result.getMethod().getMethodName(), driver);
        test.get().addScreenCaptureFromPath(screenshotPath);
        logger.error("Test failed: " + result.getMethod().getMethodName(), result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
        logger.info("Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        logger.info("Test Suite finished!");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    public static String takeScreenshot(String screenshotName, WebDriver driver) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy-hhmmss");
        String strDate = formatter.format(date);
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "C:\\Users\\SONAJI AMBADAS KHAND\\eclipse-workspace\\Java1.com\\OrangeHRM\\target\\" + screenshotName + ".jpg";
        try {
            Files.copy(srcFile, new File(path));
            logger.info("Screenshot taken: " + path);
        } catch (IOException e) {
            logger.error("Error taking screenshot: " + e.getMessage());
        }
        return path;
    }
}
