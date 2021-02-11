package com.appPages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cei.pages.Page;
import com.cei.parallel.driver.DriverFactory;
import com.cei.reporter.BaseReporter;

public class LoginPage extends Page {

	WebDriver driver = DriverFactory.getDriver();

	Actions a = new Actions(driver);
	@FindBy(xpath = "//input[@id='employee_id']")
	private List<WebElement> txt_username;

	@FindBy(xpath = "//input[@id='password']")
	private List<WebElement> txt_password;

	@FindBy(xpath = "//*[text()='Sign In']")
	private List<WebElement> btn_signin;

	@FindBy(xpath = "//span[contains(@class, 'menu-title') and text() = 'Reports']")
	private WebElement reportsBtn;

	@FindBy(xpath = "//div[contains(@class, 'username hidden-xs') and text()='Super  User ( F001 )']")
	private WebElement title;


	@FindBy(xpath = "//a[@href=\"https://capitalmarkets-pt.mortgageconnectlp.com/logout\"]")
	private WebElement logoutBtn;


	public void enterusername(String uname) {
		enterText(driver, elementList("txt_emailid", txt_username), uname);
	}

	public void enterpassword(String pwd) {
		enterText(driver, elementList("txt_password", txt_password), pwd, true);
	}

	public void clicksubmit() {
		JSClick(driver, elementList("btn_submit", btn_signin));
	}

	public LoginPage(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		BaseReporter.logPagNav("Login Page");
	}

	public WebDriver loginAsAgent(String username, String password) {
		enterusername(username);
		enterpassword(password);
		clicksubmit();
		if(!(title.isDisplayed())) {
			BaseReporter.logFail("Login Failed");
		}
		return driver;

	}
	public WebDriver logoutAsAgent() {
		JSClick(driver, elementList("logoutModule", title));
		JSClick(driver, elementList("logoutModule", logoutBtn));
		return driver;

	}


	public WebDriver navigateToReportingModule() {
		WebElement test = driver.findElement(By.xpath("//*[@id=\"mainnav-menu\"]"));
		a.moveToElement(test).perform();
		reportsBtn.click();
		return driver;

	}

}
