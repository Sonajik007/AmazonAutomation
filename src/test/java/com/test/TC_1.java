package com.test;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import UtilityClass.BaseClass;
import UtilityClass.Library;

public class TC_1 extends BaseClass {

	String ProductName = "Apple iPhone 15 Pro (256 GB) - Blue Titanium";

	@Test(priority = 1)
	public void testProductSearch() throws InterruptedException {

		// The test should search for a specific product and verify that the correct
		// search results are displayed
		// Perform product search

		WebElement searchBox = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));

		searchBox.sendKeys(ProductName + Keys.ENTER);

		Thread.sleep(3000);

		WebElement searchResult = driver
				.findElement(By.xpath("//span[text()='Apple iPhone 15 Pro (256 GB) - Blue Titanium']"));
		String actualResult = searchResult.getText();

		if (!actualResult.contains("Apple iPhone 15 Pro")) {
			Assert.assertTrue(false);
		}

	}

	@Test(priority = 2, dependsOnMethods = "testProductSearch")
	public void filteringTheSearchResults() throws InterruptedException {

		// The test should filter the search results by price range and verify that the
		// filtered results are displayed correctly

		Thread.sleep(4000);
		WebElement sortby = driver.findElement(By.xpath("//*[@id=\"a-autoid-0-announce\"]"));
		sortby.click();

		WebElement clickOnLowToHigh = driver.findElement(By.xpath("//a[contains(text(),'Price: Low to High')]"));
		clickOnLowToHigh.click();

		WebElement Price1Element = driver.findElement(By.xpath("//span[@class='a-price-whole']"));
		String Price1 = Price1Element.getText();

		String removeCommas1 = Library.removeCommas(Price1);
		int pr1 = Integer.parseInt(removeCommas1);
		System.out.println("Get Price of 1st Product: "+pr1);
		Thread.sleep(3000);
		Library.scrollByPixel(driver, 5);
		WebElement Price2Element = driver.findElement(By.xpath("(//span[@class='a-price-whole'])[3]"));
		String Price2 = Price2Element.getText();

		String removeCommas2 = Library.removeCommas(Price2);
		int pr2 = Integer.parseInt(removeCommas2);
		System.out.println("Get Price of third Product: "+pr2);

		if (!(pr1 < pr2)) {
			Assert.assertTrue(false);
		}
	}

	@Test(priority = 3, dependsOnMethods = "filteringTheSearchResults")
	public void addingProductToTheShoppingCart() throws InterruptedException {
		Thread.sleep(3000);

		//The test should navigate to a product page, select the desired options (such as size or color), and add the product to the shopping cart
		// Perform product search

		WebElement searchBox = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		searchBox.sendKeys(ProductName + Keys.ENTER);

		Thread.sleep(3000);

		WebElement searchResult = driver
				.findElement(By.xpath("//span[text()='Apple iPhone 15 Pro (256 GB) - Blue Titanium']"));
		searchResult.click();
		Thread.sleep(4000);

		// navigate to add to cart page
		Set<String> windowHandles = driver.getWindowHandles();
		System.out.println("Get the Size of Open Windows :: " + windowHandles.size());

		ArrayList arr = new ArrayList(windowHandles);
		arr.get(0);
		arr.get(1);
		driver.switchTo().window((String) arr.get(1));

		Library.scrollByPixel(driver, 1);
		// select color of product
		Thread.sleep(2000);
		WebElement productColor = driver.findElement(By.xpath("//*[@id='a-autoid-13-announce']/div/div[1]/img"));
		productColor.click();

		// select ram size
		Thread.sleep(2000);
		WebElement ramSize = driver.findElement(By.xpath("//*[@id='a-autoid-18-announce']/div/div[1]/p"));
		ramSize.click();

		// add to cart
		Thread.sleep(2000);
		WebElement addToCart = driver.findElement(By.xpath("(//*[@id='add-to-cart-button'])[2]"));
		addToCart.click();

		Thread.sleep(7000);
		WebElement closeThePopUpPage = driver.findElement(By.xpath("//a[@id='attach-close_sideSheet-link']"));
		Library.clickUsingJavaScript(driver, closeThePopUpPage);

	}

	@Test(priority = 4, dependsOnMethods = "addingProductToTheShoppingCart")
	public void proceedingToCheckout() throws InterruptedException {
		Thread.sleep(3000);

		// The test should proceed to the checkout page and verify that the correct
		// product and options are displayed in the shopping cart.
		// navigate to Shopping Cart page
		Thread.sleep(2500);
		WebElement cart = driver
				.findElement(By.xpath("//a[@href='https://www.amazon.in/gp/cart/view.html?ref_=nav_cart']"));
		cart.click();

		// verify that the correct product and options are displayed in the shopping
		// cart

		// verify product name
		WebElement productName = driver.findElement(By.xpath(
				"//ul[@class='a-unordered-list a-nostyle a-vertical a-spacing-base sc-item-content-list']//li[contains(@class,'a-spacing-mini sc-item')]"));
		String actualProductName = productName.getText();
		System.out.println("Get Expected Product Name: "+actualProductName);
		if (!actualProductName.contains(ProductName)) {
			Assert.assertTrue(false);
		}

		// verify color
		WebElement productColor = driver.findElement(By.xpath(
				"(//ul[@class='a-unordered-list a-nostyle a-vertical a-spacing-base sc-item-content-list']//li[contains(@class,'sc-product-')])[1]"));
		String actualProductColor = productColor.getText();
		System.out.println("Get product color: " + actualProductColor);

		if (!actualProductColor.contains("Blue Titanium")) {
			Assert.assertTrue(false);
		}

		// verify RAM Size
		WebElement ramSize = driver.findElement(By.xpath(
				"(//ul[@class='a-unordered-list a-nostyle a-vertical a-spacing-base sc-item-content-list']//li[contains(@class,'sc-product-')])[2]"));
		String actualramSize = ramSize.getText();
		System.out.println("Get product ram size: " + actualramSize);

		if (!actualramSize.contains("256")) {
			Assert.assertTrue(false);
		}

	}

	@Test(priority = 5, dependsOnMethods = "proceedingToCheckout")
	public void fillingOutTheCheckoutForm() throws InterruptedException {
		Thread.sleep(3000);
		// The test should fill out the checkout form with the user's personal and
		// payment information and submit the form.

		// click on proceed to buy button

		WebElement proceedToBuy = driver.findElement(By.xpath("//input[@name='proceedToRetailCheckout']"));
		proceedToBuy.click();

		// login
		WebElement userName = driver.findElement(By.id("ap_email"));
		userName.sendKeys("9834525773" + Keys.ENTER);
		WebElement passWord = driver.findElement(By.id("ap_password"));
		passWord.sendKeys("9834525773" + Keys.ENTER);

		// Enter a new shipping address
		WebElement enterFullName = driver.findElement(By.id("address-ui-widgets-enterAddressFullName"));
		enterFullName.sendKeys("rohit sharma");
		WebElement enterPhoneNumber = driver.findElement(By.id("address-ui-widgets-enterAddressPhoneNumber"));
		enterPhoneNumber.sendKeys("983452573");
		String id;
		WebElement enterPostelCode = driver.findElement(By.id("address-ui-widgets-enterAddressPostalCode"));
		enterPostelCode.sendKeys("122001");
		WebElement enterAddress = driver.findElement(By.id(id = "address-ui-widgets-enterAddressLine1"));
		enterAddress.sendKeys("sector 38, islampur colony");
		WebElement enterCityName = driver.findElement(By.id("address-ui-widgets-enterAddressCity"));
		enterCityName.sendKeys("gurgaon");
		WebElement enterState = driver.findElement(By.id("address-ui-widgets-enterAddressStateOrRegion"));
		enterState.sendKeys("haryana" + Keys.ENTER);
		//driver.findElement(By.id("address-ui-widgets-form-submit-button")).click();

		// payment method

	}

}
