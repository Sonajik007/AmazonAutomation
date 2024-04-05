package UtilityClass;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Library {

	public static void scrollByPixel(WebDriver driver, int iteration) throws InterruptedException {

		for (int i = 0; i < iteration; i++) {
		Thread.sleep(2000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,500)");
		}
		
	}
	public static String removeCommas(String numberWithCommas) {
        return numberWithCommas.replaceAll(",", "");
	}
	
	public static void clickUsingJavaScript(WebDriver driver, WebElement element) throws InterruptedException {
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("arguments[0].click();", element);
	}
}
