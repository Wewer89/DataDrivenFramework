package pl.mwewerek.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.mwewerek.base.TestBase;
import pl.mwewerek.utilities.DataProvider;

import java.util.Hashtable;

public class AddCustomerTest extends TestBase {

    //Keep the same name of test as Class name and sheet name !!!
    @Test(dataProviderClass = DataProvider.class, dataProvider = "dataProvider")  // we must provide name of class where is dataProvide and its name
    public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {
        waitForElementAndClickIt("addCustomerButton_CSS");
        waitForElementAndSendKeys("inputFirstName_CSS", data.get("firstName"));
        waitForElementAndSendKeys("inputLastName_CSS", data.get("lastName"));
        waitForElementAndSendKeys("inputPostCode_CSS", data.get("postCode"));
        waitForElementAndClickIt("downAddCustomerButton_CSS");

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(data.get("alertText")));  // Assert true if alert contains specific text
        alert.accept();
        Assert.fail("Add Customer Test failed");
    }
}
