package Pages.com;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utility.com.Config;
import Utility.com.Library;

public class HomePage {
	private static final Logger LOGGER = LogManager.getLogger(HomePage.class);
	private WebDriver driver;

	// Element locators
	@FindBy(id = "txtStationFrom")
	private WebElement formField;

	@FindBy(xpath = "//div[@class='autocomplete-w1']/div")
	private List<WebElement> options;

	@FindBy(xpath = "(//div[@class='autocomplete'][contains(@id,'Autocomplete_')]//following-sibling::div[3])[1]")
	private WebElement select4thOption;

	@FindBy(xpath = "//input[@title='Select Departure date for availability']")
	private WebElement sortOnDate;

	@FindBy(xpath = "//*[@id=\"tdDateFromTo\"]/a[2]")
	private WebElement nextButtonXPath;

	// Constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Methods for interactions
	public void clickOnFormFieldAndClear() throws InterruptedException {
		LOGGER.info("Clicking on form field and clearing the field...");
		formField.click();
		formField.clear();
		Thread.sleep(2000); 

	}

	public void enterTextInDropDownAndGetAllOptionsAndStoreItIntoExcelSheet() throws InterruptedException, IOException {
		LOGGER.info("Trying to send keys...");
		formField.sendKeys("DEL");
		Thread.sleep(5000);
		LOGGER.info("Trying to get data from dropdown and add it into excel sheet...");
		Library.writeDataToExcel(Config.excelPath, options);
		LOGGER.info("Trying to get selected option on formField...");
		String selectedOption = select4thOption.getText();
		select4thOption.click();
		Thread.sleep(2000);
		System.out.println("Selected option: " + selectedOption);
	}
	public void getSelectedStationFromDropdown() throws InterruptedException {
		LOGGER.info("Trying to get selected option on formField...");
		String selectedOption = select4thOption.getText();
		select4thOption.click();
		Thread.sleep(3000);
		System.out.println("Selected option: " + selectedOption);
	}

	public void comapareExcelSheet() throws EncryptedDocumentException, IOException {
		LOGGER.info("Trying to compare excel sheet data to with each other...");
		String sheetName = "Sheet1"; // Update the sheet name as needed

		List<List<String>> data1 = Library.readExcel(Config.excelPath, sheetName);
		List<List<String>> data2 = Library.readExcel(Config.filePath1, sheetName);

		if (data1.equals(data2)) {
			System.out.println("======sheet are identical========");
		} else {
			System.out.println("=========sheet are different======");
		}

	}

	public void selectFutureDateFromCurrentDateDynamically() {
		LOGGER.info("Trying to select future date...");
		Library.handleCalenederDynamically(driver, sortOnDate);
	}

}
