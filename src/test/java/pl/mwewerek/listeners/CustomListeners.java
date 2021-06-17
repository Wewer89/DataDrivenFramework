package pl.mwewerek.listeners;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import pl.mwewerek.base.TestBase;
import pl.mwewerek.utilities.ScreenshotManager;

import java.io.IOException;

public class CustomListeners extends TestBase implements ITestListener {

    // it is very important to remember add listeners in testng.xml to invoke them !!!

    @Override
    public void onTestStart(ITestResult result) {
        // Remember to add below code to start test
        extentTest = extentReport.startTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // test is instance from TestBase class
        // result.getName() return name of TC
        extentTest.log(LogStatus.PASS, result.getName() + " -> PASS");
        //remember to end test
        extentReport.endTest(extentTest);
        //remember to flush test otherwise extent report will not be generated
        extentReport.flush();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // required only for reportng dependence to capture html tag in report
        System.setProperty("org.uncommons.reportng.escape-output", "false");

        // if screenshot is in the same folder as report there is no necessary to provide full path
        // only name e.g. error.jpg or extent.html
        try {
            ScreenshotManager.captureScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Extent Report part:
        extentTest.log(LogStatus.FAIL, result.getName() + " -> FAIL"); //Add result.getThrowable() to print exception
        //Add screenshot to extent report
        extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ScreenshotManager.screenshotName));
        //remember to end test
        extentReport.endTest(extentTest);
        //remember to flush test otherwise extent report will not be generated
        extentReport.flush();

        // Emailable report and testng report part:
        // "_blank" open in new tab
        // Screenshot is the name of link
        Reporter.log("Click the following link: ");
        Reporter.log("<a target='_blank' href=" + ScreenshotManager.screenshotName + ">ScreenshotError</a>");
        //<br> break statement -> new line
        Reporter.log("<br>");
        Reporter.log("<a target='_blank' href=" + ScreenshotManager.screenshotName + ">" +
                "<img src=" + ScreenshotManager.screenshotName + " height=200 width=200></img></a>");

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
