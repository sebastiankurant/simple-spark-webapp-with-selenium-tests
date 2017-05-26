package test.selenium.pageobjectmodel;

import org.openqa.selenium.By;

public class AddCategoryPageModel extends PageObject{
	String url = "http://localhost:8888/index/manager/newCategory/";
	By nameInput = By.id("formName");
	By submitButton = By.name("submit");
	By homeButton = By.xpath("/html/body/div/a/button");

	@Override
	public void jumpToUrl(){
		driver.get(url);
	}

	public void setName(String name){
		driver.findElement(nameInput).clear();
		driver.findElement(nameInput).sendKeys(name);
	}

	public void clickSubmit(){
		driver.findElement(submitButton).click();
	}

	public void clickHome(){
		driver.findElement(homeButton).click();
	}

	public void addCategory(String name){
		this.setName(name);
		this.clickSubmit();
	}
}
