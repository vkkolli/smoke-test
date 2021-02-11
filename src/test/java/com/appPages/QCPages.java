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

public class QCPages extends Page{
	
	WebDriver driver = DriverFactory.getDriver();

	Actions a = new Actions(driver);
	
	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]")
	private WebElement test;
	
	@FindBy(xpath = "//span[contains(@class, 'menu-title') and text() = 'QC']")
	private List<WebElement> qcBtn;
	
	@FindBy(linkText = "Error Description")
	private List<WebElement> errorDescPage;
	
	@FindBy(linkText = "Product Checklist")
	private List<WebElement> productChecklistPage; 
	
	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='QC Error Description']")
	private WebElement errorDescTitle;
	
	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Product Checklist ']")
	private WebElement productChecklistTitle;
	
	 
	
	public QCPages(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		BaseReporter.logPagNav("QC Module");
	}
	
	public WebDriver NavigateToQCModule() {
		a.moveToElement(test).perform();
		clickElement(driver, elementList("QC Module", qcBtn));
		return driver;
	}
	
	public WebDriver NavigateToErrorDescriptionPage() {
		
		
		JSClick(driver, elementList("QC Error Description Page", errorDescPage));
		if(!(errorDescTitle.isDisplayed())) {
			BaseReporter.logFail("QC Error Description Page not opened");
		} 
			  else { BaseReporter.logPass(errorDescTitle.getText() + " is opened"); } 
	
		return driver;
	}
	
	public WebDriver NavigateToProductChecklistPage() {
		
		NavigateToQCModule();
		JSClick(driver, elementList("QC Product Checklist Page", productChecklistPage));
		if(!(productChecklistTitle.isDisplayed())) {
			BaseReporter.logFail("QC Product Checklist Page not opened");
		} else BaseReporter.logPass(productChecklistTitle.getText() + " is opened");
		return driver;
		
	}
	
}
