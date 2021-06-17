package pl.mwewerek.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.mwewerek.base.TestBase;

import java.lang.reflect.Method;

public class BankManagerLoginTest extends TestBase {

    //Keep the same name of test as Class name and sheet name !!!
    @Test
    public void bankManagerLoginTest() throws InterruptedException {
        log.debug("Inside " + Method.class);
        waitForElementAndClickIt("bankLManagerLoginButton_CSS");
        Assert.assertTrue(isElementPresent(By.cssSelector(locators.getProperty("addCustomerButton_CSS"))),
                "Login not successful!!!");
        Assert.fail("Failed to login as a bank manager");
        log.debug("Login successfully executed !!!");
    }
}
