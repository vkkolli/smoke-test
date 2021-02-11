package com.appPages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cei.pages.Page;
import com.cei.parallel.driver.DriverFactory;
import com.cei.reporter.BaseReporter;

public class OrderManagementPage extends Page {

	WebDriver driver = DriverFactory.getDriver();

	Actions a = new Actions(driver);
	@FindBy(xpath = "//span[contains(@class, 'menu-title') and text() = 'Order Management']")
	private List<WebElement> orderManagementBtn;

	@FindBy(linkText = "Import Orders")
	private List<WebElement> importModuleBtn;

	@FindBy(linkText = "Allocate Orders")
	private List<WebElement> allocateOrderseBtn;

	@FindBy(linkText = "Reassign Orders")
	private List<WebElement> reassignOrderseBtn;

	@FindBy(linkText = "Order Status")
	private List<WebElement> orderStatusBtn;

	@FindBy(linkText = "Set DueDate")
	private List<WebElement> setDuedateBtn;

	@FindBy(linkText = "Prioritize Orders")
	private List<WebElement> priorityOrdersBtn;

	@FindBy(linkText = "Prioritize Pool")
	private List<WebElement> prioritizePoolBtn;

	@FindBy(linkText = "Cancel Orders")
	private List<WebElement> cancelOrddersBtn;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Order Import']")
	private WebElement importOrdersTitle;

	@FindBy(xpath = "//h3[@class='panel-title' and text()='Allocate Orders']")
	private WebElement allocatetOrdersTitle;

	@FindBy(xpath = "//h3[@class='panel-title' and text()='Reassign Orders']")
	private WebElement reassignOrdersTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Order Status']")
	private WebElement orderStatusTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Order Due Date']")
	private WebElement setDuedateTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Order Priority']")
	private WebElement priorityOrdersTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Pool Rush']")
	private WebElement prioritizePoolTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Cancel Order']")
	private WebElement cancelOrdersTitle;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]")
	private WebElement test;

	@FindBy(xpath = "//*[@id=\"source\"]")
	private WebElement sourceDrpdown;

	@FindBy(xpath = "//input[@type='search']")
	private List<WebElement> searchTxtbox;

	@FindBy(xpath = "//*[@id=\"orders_table_info\"]")
	private WebElement ordersTable;

	public OrderManagementPage(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		BaseReporter.logPagNav("OrderManagement Module");
	}

	public WebDriver NavigateToOrderManagementModule() {
		a.moveToElement(test).perform();
		clickElement(driver, elementList("orderManagementModule", orderManagementBtn));
		return driver;
	}

	public WebDriver NavigateToImportOrdersModule() {
		JSClick(driver, elementList("importOrderstModule", importModuleBtn));
		if (!(importOrdersTitle.isDisplayed())) {

			BaseReporter.logFail("Import Orders Page not opened");

		} else {
			BaseReporter.logPass(importOrdersTitle.getText() + "Page opens");
		}

		return driver;
	}

	public WebDriver NavigateToAllocateOrdersModule() {

		a.moveToElement(test).perform();
		clickElement(driver, elementList("orderManagementModule", orderManagementBtn));

		JSClick(driver, elementList("allocateOrddersModule", allocateOrderseBtn));
		if (!(allocatetOrdersTitle.isDisplayed())) {

			BaseReporter.logFail("Allocate Orders Page not opened");

		} else {
			BaseReporter.logPass(allocatetOrdersTitle.getText() + "Page opens");
			callForWait(3000);
			if (sourceDrpdown.isDisplayed()) {
				BaseReporter.logPass("Verified Source dropdown");
			}
		}
		return driver;
	}

	public WebDriver NavigateToReassignOrdersModule() {

		a.moveToElement(test).perform();
		clickElement(driver, elementList("orderManagementModule", orderManagementBtn));
		JSClick(driver, elementList("reassignOrdersModule", reassignOrderseBtn));
		if (!(reassignOrdersTitle.isDisplayed())) {
			BaseReporter.logFail("Reassign orders Page not opened");
		} else {
			BaseReporter.logPass(reassignOrdersTitle.getText() + "Page opens");
		}
		return driver;
	}

	public WebDriver NavigateToOrderStatusModule() {

		a.moveToElement(test).perform();
		clickElement(driver, elementList("orderManagementModule", orderManagementBtn));
		JSClick(driver, elementList("orderStatusModule", orderStatusBtn));
		if (!(orderStatusTitle.isDisplayed())) {
			BaseReporter.logFail("Order Status Page not opened");
		} else {
			BaseReporter.logPass("Order Status Page opens");
			callForWait(5000);
			enterText(driver, elementList("searchButton", searchTxtbox), "1234");
			String orders = ordersTable.getText();
			if (orders.contains("Showing 1")) {
				BaseReporter.logPass("Order results displayed");
			} else {
				BaseReporter.logFail("Order results not displayed");
			}
		}
		return driver;
	}

	public WebDriver NavigateToSetDueDateModule() {

		a.moveToElement(test).perform();
		clickElement(driver, elementList("orderManagementModule", orderManagementBtn));
		JSClick(driver, elementList("setDueDateModule", setDuedateBtn));
		if (!(setDuedateTitle.isDisplayed())) {

			BaseReporter.logFail("SetDue Date Page not opened");
		} else {
			BaseReporter.logPass(setDuedateTitle.getText() + "Page opens");
		}
		return driver;
	}

	public WebDriver NavigateToPrioritizeOrdersModule() {
		driver.navigate().refresh();
		a.moveToElement(test).perform();
		clickElement(driver, elementList("orderManagementModule", orderManagementBtn));
		JSClick(driver, elementList("prioritizeOrderModule", priorityOrdersBtn));
		if (!(priorityOrdersTitle.isDisplayed())) {
			BaseReporter.logFail("Priority Order Page not opened");

		} else {
			BaseReporter.logPass(priorityOrdersTitle.getText() + "Page opens");
		}
		return driver;
	}

	public WebDriver NavigateToPrioritizePoolModule() {

		driver.navigate().refresh();
		a.moveToElement(test).perform();
		clickElement(driver, elementList("orderManagementModule", orderManagementBtn));
		JSClick(driver, elementList("prioritizePoolModule", prioritizePoolBtn));

		if (!(prioritizePoolTitle.isDisplayed())) {
			BaseReporter.logFail("Prioritize pool Page not opened");

		} else {
			BaseReporter.logPass(prioritizePoolTitle.getText() + "Page opens");
		}
		return driver;
	}

	public WebDriver NavigateToCancelOrdersModule() {

		driver.navigate().refresh();
		a.moveToElement(test).perform();
		clickElement(driver, elementList("orderManagementModule", orderManagementBtn));
		JSClick(driver, elementList("cancelOrdersModule", cancelOrddersBtn));
		if (!(cancelOrdersTitle.isDisplayed())) {

			BaseReporter.logFail("Cancel Orders Page not opened");

		} else {
			BaseReporter.logPass(cancelOrdersTitle.getText() + "Page opens");
		}
		return driver;
	}

}
