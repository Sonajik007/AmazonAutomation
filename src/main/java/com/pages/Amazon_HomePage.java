package com.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Amazon_HomePage {

	// Declare Variables
	@FindBy(xpath = "//input[@id='twotabsearchtextbox']")
	private WebElement searchbox;

	
    //Initialize
	public Amazon_HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	
	// Usage
	public void clickOnSearchBox(String productname) throws InterruptedException {
		searchbox.click();
		searchbox.sendKeys(productname);
		Thread.sleep(2000);
		searchbox.submit();

	}

}
