package pl.mwewerek.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.mwewerek.base.TestBase;

import java.io.IOException;
import java.lang.reflect.Method;

public class BankManagerLoginTest extends TestBase {

    //Keep the same name of test as Class name and sheet name !!!
    @Test
    public void bankManagerLoginTest() throws InterruptedException, IOException {
        //verifyEquals("XYZ", "ZYX");  // custom soft assertion method
        //softAssert.assertEquals("ABC", "CBA");

        log.debug("Inside " + Method.class);
        waitForElementAndClickOnIt("bankLManagerLoginButton_CSS");
        Assert.assertTrue(isElementPresent(By.cssSelector(locators.getProperty("addCustomerButton_CSS"))),
                "Login not successful!!!");

        //Assert.fail("Failed to login as a bank manager");
        log.debug("Login successfully executed !!!");
        //softAssert.assertAll();  // Remember to add assertAll() to include in report extent
        //Assert.fail();
    }
}
