package Test.com;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.com.HomePage;
import Utility.com.BaseClass;

public class HomeTest extends BaseClass {
    private HomePage hpage;

    @BeforeMethod
    public void setUp() {
        hpage = new HomePage(driver); // Initialize hpage with the WebDriver instance
    }

    @Test(priority = 1)
    public void verifyFormFieldDropdown() throws InterruptedException, IOException {
        hpage.clickOnFormFieldAndClear();
       // hpage.getSelectedStationFromDropdown();
    }

    @Test(priority = 2)
    public void getDataInExcelAndComapreSheetData() throws InterruptedException, IOException {
        hpage.enterTextInDropDownAndGetAllOptionsAndStoreItIntoExcelSheet();
         hpage.comapareExcelSheet();

    }
    @Test(priority = 3)
    public void selectFutureDateFromCalender() throws InterruptedException {
        hpage.selectFutureDateFromCurrentDateDynamically();
        Thread.sleep(3000);
    }
    
}

