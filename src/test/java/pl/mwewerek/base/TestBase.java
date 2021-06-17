package pl.mwewerek.base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import pl.mwewerek.utilities.ExcelReader;
import pl.mwewerek.utilities.ExtentManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
    /*
    In TestBase class we initialize before TCs the following things:
    - WebDriver
    - Properties
    - Logs -> log4j jar, application.log, selenium.log, log4j.properties, Logger (org.apache.log4j)
    - ExtentReports
    - DB
    - Excel
    - Mail
    - Jenkins
     */

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties locators = new Properties();
    public static FileInputStream file;
    public static WebDriverWait wait;
    public static Logger log = Logger.getLogger(TestBase.class.getName());
    public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "//src/test/java/pl" +
            "/mwewerek//resources/excel/TestData.xlsx");
    public static ExtentReports extentReport = ExtentManager.getInstance();
    public static ExtentTest extentTest;  // Add logs inside test cases

    @BeforeSuite
    public void setUp() throws IOException {
        if (driver == null) {
            PropertyConfigurator.configure(System.getProperty("user.dir") + "\\src" +
                    "\\test\\java\\pl\\mwewerek\\resources\\properties\\log4j.properties");

            // Config
            file = new FileInputStream(System.getProperty("user.dir") +
                    "\\src\\test\\java\\pl\\mwewerek\\resources\\properties\\Config.properties");
            config.load(file);
            log.debug("Config file loaded !!!");

            // Locators
            file = new FileInputStream(System.getProperty("user.dir") +
                    "\\src\\test\\java\\pl\\mwewerek\\resources\\properties\\Locators.properties");
            locators.load(file);
            log.debug("Config file loaded !!!");

            if (config.getProperty("browser").equals("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                log.debug("Firefox lunched !!!");
            } else if (config.getProperty("browser").equals("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                log.debug("Chrome lunched !!!");
            }

            driver.get(config.getProperty("url"));
            log.debug("Navigated to: " + config.getProperty("url"));
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, 10);
        }
    }

    public static boolean isElementPresent(By by) {
        try {
            //driver.findElement(by);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    //Below methods are worth to be implemented to capture steps in Exntent Reports
    public static void waitForElementAndClickIt(String locator) {
        if(locator.endsWith("_CSS")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locators.getProperty(locator)))).click();
        } else if(locator.endsWith("_XPATH")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locators.getProperty(locator)))).click();
        } else if(locator.endsWith("_ID")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locators.getProperty(locator)))).click();
        }
        extentTest.log(LogStatus.INFO, "Clicking on: " + locator);
    }
    public static void waitForElementAndSendKeys(String locator, String value) {
        if(locator.endsWith("_CSS")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locators.getProperty(locator)))).
                    sendKeys(value);
        } else if(locator.endsWith("_XPATH")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locators.getProperty(locator)))).sendKeys(value);
        } else if(locator.endsWith("_ID")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locators.getProperty(locator)))).sendKeys(value);
        }
        extentTest.log(LogStatus.INFO, "Typing in: " + locator + " Entered value as: " + value);
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.debug("Test execution completed !!!");
        }
    }
}
