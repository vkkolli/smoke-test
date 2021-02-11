package com.appPages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cei.pages.Page;
import com.cei.parallel.driver.DriverFactory;
import com.cei.reporter.BaseReporter;

public class MasterDataPages extends Page {


	WebDriver driver = DriverFactory.getDriver();

	Actions a = new Actions(driver);

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]")
	private WebElement test;

	@FindBy(xpath = "//span[contains(@class, 'menu-title') and text() = 'Masters Data']")
	private List<WebElement> masterDatabtn;

	@FindBy(xpath = "//a[@href=\"#\" and text()=\"County\"]")
	private WebElement CountySideMenu;

	//County - County Link


	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[4]/ul/li[1]/ul/li[1]/a")
	private WebElement countyPage;


	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='County Data Sources']")
	private WebElement countyPageTitle;

	@FindBy(xpath = "//select[@name='state']")
	private List<WebElement> dropdown_state;

	@FindBy(xpath = "//select[@name='county']")
	private List<WebElement> dropdown_county;

	//County - County Import Link

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[4]/ul/li[1]/ul/li[2]/a")
	private List<WebElement> countyImportPage;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='County Source Import']")
	private WebElement countyImportPageTitle;


	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[4]/ul/li[2]/a")
	private List<WebElement> userPage;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[4]/ul/li[2]/ul/li[1]/a")
	private List<WebElement> userSetup;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[4]/ul/li[2]/ul/li[2]/a")
	private List<WebElement> skillSetup;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Users Management']")
	private WebElement userSetupTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Employee Setup']")
	private WebElement skillSetupTitle;

	@FindBy(linkText = "Knowledge Base")
	private List<WebElement> knowledgeBaseBtn;

	@FindBy(xpath = "//select[@name='customer_id']")
	private List<WebElement> dropdown_customer_id;

	public void selectState(String state) {
		selectFromDropdown(driver, elementList("dropdown_state", dropdown_state), state);
	}

	public MasterDataPages(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		BaseReporter.logPagNav("Master Data Module");
	}


	public WebDriver NavigateToMasterDataModule() {
		a.moveToElement(test).perform();
		clickElement(driver, elementList("Master Data Module", masterDatabtn));
		return driver;
	}

	public WebDriver NavigateToCountycountyPage() {
		JSClick(driver, elementList("County Side Menu", CountySideMenu));
		callForWait(2000);
		JSClick(driver, elementList("County ->County Page", countyPage));
		callForWait(3000);


		if(!(countyPageTitle.isDisplayed())) {
			BaseReporter.logFail("County ->County Page not opened");
		}
		else {
			BaseReporter.logPass(countyPageTitle.getText() + " is opened");
			callForWait(3000);
			selectState("District of Columbia");

			callForWait(3000);
			ArrayList<Object> list = elementList("countylist", dropdown_county);
			if(2==list.size()) {
				BaseReporter.logPass("Two options available under the “Counties” selection list.");
			} else {BaseReporter.logFail("Counties selection is < or > 2");}
		}

		return driver;
	}

	public WebDriver NavigateToCountyCountyImportPage() {
		callForWait(1000);
		NavigateToMasterDataModule();
		JSClick(driver, elementList("County Side Menu", CountySideMenu));
		JSClick(driver, elementList("County ->County Import Page", countyImportPage));

		if(!(countyImportPageTitle.isDisplayed())) {
			BaseReporter.logFail("County ->County Import Page not opened");
		}
		else { BaseReporter.logPass(countyImportPageTitle.getText() + " is opened"); }

		return driver;
	}

	public WebDriver NavigateToUserSetupPage() {
		callForWait(1000);
		NavigateToMasterDataModule();
		JSClick(driver, elementList("User Menu", userPage));
		JSClick(driver, elementList("User ->User Setup Page", userSetup));

		if(!(userSetupTitle.isDisplayed())) {
			BaseReporter.logFail("User ->User Setup Page not opened");
		}
		else { BaseReporter.logPass(userSetupTitle.getText() + " is opened"); }

		return driver;
	}

	public WebDriver NavigateToSkillSetupPage() {
		callForWait(2000);
		NavigateToMasterDataModule();
		JSClick(driver, elementList("User Menu", userPage));
		JSClick(driver, elementList("User ->Skill Setup Page", skillSetup));

		if(!(skillSetupTitle.isDisplayed())) {
			BaseReporter.logFail("User ->Skill Setup Page not opened");
		}
		else { BaseReporter.logPass(skillSetupTitle.getText() + " is opened"); }

		return driver;
	}

	public WebDriver NavigateToKnowledgeBasePage() {
		callForWait(2000);
		NavigateToMasterDataModule();

		JSClick(driver, elementList("Masters Data--Knowledge Base", knowledgeBaseBtn));
		System.out.println(dropdown_customer_id.size());
		if((dropdown_customer_id.isEmpty())) {
			BaseReporter.logFail("Masters Data--Knowledge Base not opened");
		}
		else { BaseReporter.logPass("Masters Data--Knowledge Base is opened"); }

		return driver;
	}

}
