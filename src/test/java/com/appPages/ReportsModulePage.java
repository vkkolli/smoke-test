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

public class ReportsModulePage extends Page {

	WebDriver driver = DriverFactory.getDriver();

	Actions a = new Actions(driver);
	@FindBy(xpath = "//span[contains(@class, 'menu-title') and text() = 'Reports']")
	private List<WebElement> reportsBtn;

//	@FindBy(xpath = "//a[text()='Import Orders'")
//	private List<WebElement> importModuleBtn;

	@FindBy(linkText = "MCDS Extract")
	private List<WebElement> mcdsReportBtn;

	@FindBy(linkText = "Online to Offline Report")
	private List<WebElement> onlineToOfflineReportBtn;

	@FindBy(linkText = "Fee Approved – Online to Offline")
	private List<WebElement> feeApprovedReportBtn;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[6]/ul/li[4]/a")
	private List<WebElement> portFolioReportBtn;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[6]/ul/li[5]/a")
	private List<WebElement> sageReportBtn;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[6]/ul/li[6]/a")
	private List<WebElement> statusReportBtn;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[6]/ul/li[7]/a")
	private List<WebElement> errorLogReportBtn;

	@FindBy(linkText = "Production Tracker")
	private List<WebElement> productionTrackerReportBtn;

	@FindBy(linkText = "Project Tracker")
	private List<WebElement> projectTrackerReportBtn;


	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[6]/ul/li[10]/a")
	private List<WebElement> qualityScoresBtn;

	@FindBy(linkText = "Turn Around")
	private List<WebElement> turnAroundReportBtn;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='MCDS Extract']")
	private WebElement mcdsReportTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Online to Offline Report']")
	private WebElement onlineToOfflineRporttitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Fee Approved - Online to Offline Files']")
	private WebElement feeApprovedReporttitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Portfolio Extract']")
	private WebElement portFolioReporttitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Sage Report']")
	private WebElement sageReportTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Status Report']")
	private WebElement statusReporttitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Error Log Reporting']")
	private WebElement errorLogReportTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Production Tracker Report']")
	private WebElement productionTrackerReportTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Project Tracker Report']")
	private WebElement projectTrackerReportTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Quality Scores Report']")
	private WebElement qualityScorestitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Turn Around Report']")
	private WebElement turnAroundReporttitle;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]")
	private WebElement test;

	@FindBy(xpath = "//*[@id=\"source\"]")
	private WebElement sourceDrpdown;

	public ReportsModulePage(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		BaseReporter.logPagNav("Reports Module");
	}

	public WebDriver NavigateToReportsModule() {
		a.moveToElement(test).perform();
		clickElement(driver, elementList("reportsModule", reportsBtn));
		return driver;
	}


	public WebDriver NavigateToMcdsReport() {

		a.moveToElement(test).perform();
		JSClick(driver, elementList("reportsModule", reportsBtn));
		callForWait(3000);
		JSClick(driver, elementList("mcdsReport", mcdsReportBtn));
		if(!(mcdsReportTitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("MCDS Report Page not opened");

		}
		else {BaseReporter.logPass("MCDS report page is opened");}

		return driver;
	}

public WebDriver NavigateToStatusReport() {

		a.moveToElement(test).perform();
		JSClick(driver, elementList("reportsModule", reportsBtn));
		callForWait(3000);
		JSClick(driver, elementList("statusReport", statusReportBtn));
		if(!(statusReporttitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("Status Report Page not opened");

		}
		else {BaseReporter.logPass("Status report page is opened");}
		return driver;
	}
	public WebDriver NavigateToOnlineToOfflineReport() {

		a.moveToElement(test).perform();
		clickElement(driver, elementList("reportsModule", reportsBtn));
		callForWait(3000);
		JSClick(driver, elementList("onlineToOfflineReport", onlineToOfflineReportBtn));
		if(!(onlineToOfflineRporttitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("Online to Offline Report Page not opened");

		}
		else {BaseReporter.logPass("online to offline report page is opened");}
		return driver;
	}

	public WebDriver NavigateToFeeApprovedReport() {

		a.moveToElement(test).perform();
		JSClick(driver, elementList("reportsModule", reportsBtn));
		callForWait(3000);
		JSClick(driver, elementList("feeApprovedReport", feeApprovedReportBtn));
		if(!(feeApprovedReporttitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("Fee Approved Report Page not opened");

		}
		else {BaseReporter.logPass("Fee Approved report page is opened");}
		return driver;
	}
	public WebDriver NavigateToPortfolioReport() {

		a.moveToElement(test).perform();
		clickElement(driver, elementList("reportsModule", reportsBtn));
		callForWait(3000);
		JSClick(driver, elementList("portfolioReport", portFolioReportBtn));
		if(!(portFolioReporttitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("PortFolio Report Page not opened");

		}
		else {BaseReporter.logPass("Portfolio report page is opened");}
		return driver;
	}

	public WebDriver NavigateToSageReport() {
//		driver.navigate().refresh();
		a.moveToElement(test).perform();
		JSClick(driver, elementList("reportsModule", reportsBtn));
		callForWait(3000);
		JSClick(driver, elementList("sageReport", sageReportBtn));
		if(!(sageReportTitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("Sage Report Page not opened");

		}
		else {BaseReporter.logPass("sage report page is opened");}
			return driver;
	}
	public WebDriver NavigateToErrorlogReport() {

		//driver.navigate().refresh();
		a.moveToElement(test).perform();
		JSClick(driver, elementList("reportsModule", reportsBtn));
		JSClick(driver, elementList("errorlogReport", errorLogReportBtn));
		//driver.findElement(By.linkText("Prioritize Pool")).click();
		if(!(errorLogReportTitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("Error Log Report Page not opened");

		}
		else {BaseReporter.logPass("error log report page is opened");}
		return driver;
	}
	public WebDriver NavigateToProductionTrackerReport() {

		//driver.navigate().refresh();
		a.moveToElement(test).perform();
		JSClick(driver, elementList("reportsModule", reportsBtn));
		JSClick(driver, elementList("productionTrackerReport", productionTrackerReportBtn));
		if(!(productionTrackerReportTitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("Production Tracker Page not opened");

		}
		else {BaseReporter.logPass("Production tracker report page is opened");}
		return driver;
	}

public WebDriver NavigateToProjectTrackerReport() {

		//driver.navigate().refresh();
		a.moveToElement(test).perform();
		JSClick(driver, elementList("reportsModule", reportsBtn));
		JSClick(driver, elementList("projectTrackerReport", projectTrackerReportBtn));
		if(!(projectTrackerReportTitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("project Tracker Page not opened");

		}
		else {BaseReporter.logPass("project tracker report page is opened");}
		return driver;
	}

public WebDriver NavigateToQualityScoresReport() {

	//driver.navigate().refresh();
	a.moveToElement(test).perform();
	JSClick(driver, elementList("reportsModule", reportsBtn));
	JSClick(driver, elementList("qualityScoresReport", qualityScoresBtn));
	if(!(qualityScorestitle.isDisplayed())) {
		//System.out.println("Allocate Orders page is opened and source drop down is present");
		BaseReporter.logFail("Quality Scores Page not opened");

	}
	else {BaseReporter.logPass("quality scores report page is opened");}
	return driver;
}

public WebDriver NavigateToTurnAroundReport() {

//	driver.navigate().refresh();
	a.moveToElement(test).perform();
	JSClick(driver, elementList("reportsModule", reportsBtn));
	JSClick(driver, elementList("turnAroundReport", turnAroundReportBtn));
	if(!(turnAroundReporttitle.isDisplayed())) {
		//System.out.println("Allocate Orders page is opened and source drop down is present");
		BaseReporter.logFail("Turn Around Report Page not opened");

	}
	else {BaseReporter.logPass("Turn round report page is opened");}
	return driver;
}


}
