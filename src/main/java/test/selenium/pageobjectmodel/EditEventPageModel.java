package test.selenium.pageobjectmodel;

import org.openqa.selenium.By;

public class EditEventPageModel extends PageObject {
	String url = "http://localhost:8888/index/manager/edit/1";
	By nameInput = By.id("formName");
	By descriptionInput = By.id("formDescription");
	By dateInput = By.id("date");
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

	public void setDescription(String description){
		driver.findElement(descriptionInput).clear();
		driver.findElement(descriptionInput).sendKeys(description);
	}

	public void setDate(String date){
		driver.findElement(dateInput).clear();
		driver.findElement(dateInput).sendKeys(date);
	}

	public void clickSubmit(){
		driver.findElement(submitButton).click();
	}

	public void clickHome(){
		driver.findElement(homeButton).click();
	}

	public void editEvent(String name){
		this.setName(name);
		this.clickSubmit();
	}

	public void editEvent(String name, String description){
		this.setName(name);
		this.setDescription(description);
		this.clickSubmit();
	}

	public void editEvent(String name, String description, String date){
		this.setName(name);
		this.setDescription(description);
		this.setDate(date);
		this.clickSubmit();
	}
}
