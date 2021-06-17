package pl.mwewerek.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.mwewerek.base.TestBase;

import java.util.Hashtable;

public class OpenAccountTest extends TestBase {

    //Keep the same name of test as Class name and sheet name !!!
    @Test(dataProviderClass = DataProvider.class, dataProvider = "dataProvider")  // we must provide name of class where is dataProvide and its name
    public void openAccountTest(Hashtable<String, String> data) throws InterruptedException {

    }
}
