package test.selenium.pageobjectmodel;

import org.openqa.selenium.By;

public class HomePageModel extends PageObject {

	String url = "http://localhost:8888/index";
	By managerButton = By.id("manager");

	@Override
	public void jumpToUrl(){
		driver.get(url);
	}

	public void clickManager(){
		driver.findElement(managerButton).click();
	}
}
