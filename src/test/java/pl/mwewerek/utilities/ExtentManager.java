package pl.mwewerek.utilities;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

import java.io.File;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        // As constructor parameter provide path to where create extent report
        //DisplayOrder.OLDEST_FIRST displays test cases regarding to order in pom.xml
        extent = new ExtentReports("target\\surefire-reports\\extent.html", true,
                DisplayOrder.OLDEST_FIRST);
        //provide path to configuration xml file
        extent.loadConfig(new File("src\\test\\java\\pl\\mwewerek\\resources\\extent_config\\ReportsConfig.xml"));
        return extent;
    }
}
