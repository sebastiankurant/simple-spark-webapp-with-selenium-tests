package test.selenium.pageobjectmodel;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public abstract class PageObject {

	public static WebDriver driver;

	protected String url;

	public void jumpToUrl(){
		driver.get(url);
	}

	public static void setDriver(WebDriver driver){
		PageObject.driver = driver;
	}

	public static WebDriver getDriver(){
		return driver;
	}

	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
