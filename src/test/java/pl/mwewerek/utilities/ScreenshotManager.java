package pl.mwewerek.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pl.mwewerek.base.TestBase;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ScreenshotManager extends TestBase {

    // dateStamp make unique file name, so new jpg file is always created for every single failure
    public static String dateStamp = createDateStamp();
    public static String screenshotName = dateStamp + ".jpg";
    public static String screenshotPath = "target\\surefire-reports\\" + screenshotName;

    public static void captureScreenshot() throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File(screenshotPath));
    }

    public static String createDateStamp() {
        Date date = new Date();
        return date.toString().replace(" ", "_").replace(":", "_");
    }
}
