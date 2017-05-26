package test.selenium.pageobjectmodel;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ManagerPageModel extends PageObject {

	String url = "http://localhost:8888/index/manager/";
	By homeButton = By.xpath("/html/body/div/div[1]/a[1]/button");
	By addEventButton = By.xpath("/html/body/div/div[1]/a[2]/button");
	By addCategoryButton = By.xpath("/html/body/div/div[1]/a[3]/button");
	By removeEventButton = By.xpath("/html/body/div/div[2]/div/table/tbody/tr/td[4]/a[2]/button");

	@Override
	public void jumpToUrl(){
		driver.get(url);
	}

	public void clickHome(){
		driver.findElement(homeButton).click();
	}

	public void clickAddEvent(){
		driver.findElement(addEventButton).click();
	}

	public void clickAddCategory(){
		driver.findElement(addCategoryButton).click();
	}

	public void clickRemoveEventButton(){
		driver.findElement(removeEventButton).click();
	}

	public boolean isEventExist(String name, Integer row){
		String xpath = getProperXpathByRow(row);
		WebElement event = driver.findElement(By.xpath(xpath));
		if (this.isDisplayed(event)){
			return event.getText().equals(name);
		}
		return false;
	}

	private String getProperXpathByRow(Integer row){
		String xpath;
		if(row == 1) {
			xpath = "/html/body/div/div[2]/div/table/tbody/tr/td[1]";
		} else {
			xpath = "/html/body/div/div[2]/div/table/tbody/tr[" + row + "]/td[1]";
		}
		return xpath;
	}

	private boolean isDisplayed(WebElement webElement){
		return webElement.isDisplayed();
	}
}