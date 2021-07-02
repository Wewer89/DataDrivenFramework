package pl.mwewerek.listeners;

import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;
import pl.mwewerek.base.TestBase;
import pl.mwewerek.utilities.MailConfig;
import pl.mwewerek.utilities.MonitoringMail;
import pl.mwewerek.utilities.RunModeManager;
import pl.mwewerek.utilities.ScreenshotManager;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CustomListeners extends TestBase implements ITestListener, ISuiteListener {

    // it is very important to remember add listeners in testng.xml to invoke them !!!

    @Override
    public void onTestStart(ITestResult result) {
        // Remember to add below code to start test for Extent Report
        extentTest = extentReport.startTest(result.getName());

        // Y - to start runMode
        // result.getName() return name of TC
        if (!RunModeManager.iSRunnable(result.getName())) {
            throw new SkipException("Skipping the test: " + result.getName());
        }
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
        // if screenshot is in the same folder as report there is no necessary to provide full path
        // only name e.g. error.jpg or extent.html
        try {
            ScreenshotManager.captureScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Extent Reports:
        extentTest.log(LogStatus.FAIL, result.getName() + " -> FAIL"); //Add result.getThrowable() to print exception
        //Add screenshot to extent report
        extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ScreenshotManager.screenshotName));
        //remember to end test
        extentReport.endTest(extentTest);
        //remember to flush test otherwise extent report will not be generated
        extentReport.flush();

        // Emailable and testNG reports:
        // required only for reportng dependence to capture html tag in report
        System.setProperty("org.uncommons.reportng.escape-output", "false");
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
        extentTest.log(LogStatus.SKIP, result.getName() + " -> SKIPPED");
        //remember to end test
        extentReport.endTest(extentTest);
        //remember to flush test otherwise extent report will not be generated
        extentReport.flush();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    // Below code enable sending email automatically whenever test suite is finish
    @Override
    public void onFinish(ITestContext context) {

    }

    @Override
    public void onFinish(ISuite suite) {
        String localHostAddress = null;
        try {
            // get local host address
            localHostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String messageBody = "http://" + localHostAddress + ":8080/job/DataDrivenFrameworkProject/Extent_20Report/";

        MonitoringMail mail = new MonitoringMail();
        try {
            mail.sendMail(MailConfig.server, MailConfig.from, MailConfig.to, MailConfig.subject, messageBody);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
