package test.selenium;

import com.event.site.dao.SqliteJDBCConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import test.selenium.pageobjectmodel.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class SeleniumTest {
	private WebDriver driver;
	private AddEventPageModel event = new AddEventPageModel();
	private AddCategoryPageModel category = new AddCategoryPageModel();
	private HomePageModel home = new HomePageModel();
	private ManagerPageModel manager = new ManagerPageModel();
	private EditEventPageModel editEvent = new EditEventPageModel();

	@Before
	public void setUp() throws Exception {
		driver = new ChromeDriver();
		PageObject.setDriver(driver);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@Test
	public void testAddEvent() throws Exception {
		event.jumpToUrl();
		event.addEvent("sample", "sample description");
		assertTrue(manager.isEventExist("sample", 1));

	}

	@Test
	public void testAddCategory() throws Exception {
		category.jumpToUrl();
		category.addCategory("sample");
	}

	@Test
	public void testEditEvent() throws Exception {
		testAddEvent();
		editEvent.jumpToUrl();
		editEvent.editEvent("edit");
		assertTrue(manager.isEventExist("edit", 1));
	}

	@Test
	public void testRemoveButton() throws Exception {
		testAddEvent();
		manager.clickRemoveEventButton();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		SqliteJDBCConnector.clearDatabase();
	}
}
