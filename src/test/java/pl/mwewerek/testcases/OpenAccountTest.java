package pl.mwewerek.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.mwewerek.base.TestBase;
import pl.mwewerek.utilities.DataProviderManager;

import java.util.Hashtable;

public class OpenAccountTest extends TestBase {

    //Keep the same name of test as Class name and sheet name !!!
    @Test(dataProviderClass = DataProviderManager.class, dataProvider = "dataProvider")  // we must provide name of class where is dataProvide and its name
    public void openAccountTest(Hashtable<String, String> data) throws InterruptedException {
        click("openAccountButton_CSS");
        select("customerDropdown_CSS", data.get("customer"));
        select("currencyDropdown_CSS", data.get("currency"));
        waitForElementAndClickOnIt("processButton_CSS");

        Thread.sleep(2000);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains("Account created successfully with account Number"));
        alert.accept();
    }
}
