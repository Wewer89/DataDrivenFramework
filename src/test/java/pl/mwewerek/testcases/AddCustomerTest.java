package pl.mwewerek.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pl.mwewerek.base.TestBase;
import pl.mwewerek.utilities.DataProviderManager;

import java.lang.reflect.Method;
import java.util.Hashtable;

public class AddCustomerTest extends TestBase {

    //Keep the same name of test as Class name and sheet name !!!
    @Test(dataProviderClass = DataProviderManager.class, dataProvider = "dataProvider")  // we must provide name of class where is dataProvide and its name
    public void addCustomerTest(Hashtable<String, String> data, Method method) throws InterruptedException {
        // Below logic is responsible for which test data will be executed via test case 
        if(!data.get("runMode").equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test: " + method.getName());
        }

        waitForElementAndClickOnIt("addCustomerButton_CSS");
        waitForElementAndSendKeys("inputFirstName_CSS", data.get("firstName"));
        waitForElementAndSendKeys("inputLastName_CSS", data.get("lastName"));
        waitForElementAndSendKeys("inputPostCode_CSS", data.get("postCode"));
        waitForElementAndClickOnIt("downAddCustomerButton_CSS");

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(data.get("alertText")));  // Assert true if alert contains specific text
        alert.accept();

        //Assert.fail("Add Customer Test failed");
    }
}
