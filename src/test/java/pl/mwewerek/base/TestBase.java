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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import pl.mwewerek.utilities.ExcelReader;
import pl.mwewerek.utilities.ExtentManager;
import pl.mwewerek.utilities.ScreenshotManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties locators = new Properties();
    public static FileInputStream file;
    public static WebDriverWait wait;
    public static Logger log = Logger.getLogger(TestBase.class.getName());
    public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "//src/test/java/pl" +
            "/mwewerek//resources/excel/DataDrivenFramework.xlsx");
    public static ExtentReports extentReport = ExtentManager.getInstance();
    public static ExtentTest extentTest;  // Add logs inside test cases
    public static SoftAssert softAssert = new SoftAssert();
    public static WebElement dropdown;
    public static String browser;


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

            // Below code is necessary in order to gets value of the specified environment variable
            // in our case this value is getting from temporary env variable set up by Jenkins
            // as "browser" parameter
            if(System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
                browser = System.getenv("browser");
                config.setProperty("browser", browser);
            }

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

    public static void click(String locator) {
        if(locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(locators.getProperty(locator))).click();
        } else if(locator.endsWith("_XPATH")) {
            driver.findElement(By.cssSelector(locators.getProperty(locator))).click();
        } else if(locator.endsWith("_ID")) {
            driver.findElement(By.cssSelector(locators.getProperty(locator))).click();
        }
        extentTest.log(LogStatus.INFO, "Clicking on: " + locator);
    }
    public static void waitForElementAndClickOnIt(String locator) {
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
    public static void select(String locator, String value) {
        if(locator.endsWith("_CSS")) {
            dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locators.getProperty(locator))));
        } else if(locator.endsWith("_XPATH")) {
            dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locators.getProperty(locator))));
        } else if(locator.endsWith("_ID")) {
            dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locators.getProperty(locator))));
        }
        Select select = new Select(dropdown);  // Select class is using to handle dropdown
        select.selectByVisibleText(value);

        extentTest.log(LogStatus.INFO, "Selecting from dropdown: " + locator + " values as " + value);
    }

    // Custom soft assertion method -> allows to catch error without breaking the code
    // Instead of below code use softAssertion
    public static void verifyEquals(String actual, String expected) throws IOException {
        try {
            Assert.assertEquals(actual, expected);
        } catch (Exception exception) {
            ScreenshotManager.captureScreenshot();
            // Extent Reports:
            extentTest.log(LogStatus.FAIL, "Verification failed with exception" + exception.getMessage());
            //Add screenshot to extent report
            extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ScreenshotManager.screenshotName));

            // Emailable and testNG reports:
            Reporter.log("<br>" + "Verification failure: " + exception.getMessage() + "<br>");
            Reporter.log("<a target='_blank' href=" + ScreenshotManager.screenshotName + ">" +
                    "<img src=" + ScreenshotManager.screenshotName + " height=200 width=200></img></a>");
            Reporter.log("<br>");
        }
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.debug("Test execution completed !!!");
        }
    }
}
