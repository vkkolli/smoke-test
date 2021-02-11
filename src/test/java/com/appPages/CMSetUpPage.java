package com.appPages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cei.pages.Page;
import com.cei.parallel.driver.DriverFactory;
import com.cei.reporter.BaseReporter;

public class CMSetUpPage extends Page{

	WebDriver driver = DriverFactory.getDriver();

	Actions a = new Actions(driver);
	@FindBy(xpath = "//span[contains(@class, 'menu-title') and text() = 'Set Up']")
	private List<WebElement> setUpBtn;


	@FindBy(linkText = "Products")
	private List<WebElement> productsBtn;

	@FindBy(xpath = "//a[@href=\"#\" and text()=\"Customer\"]")
	private List<WebElement> customerBtn;


	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[3]/ul/li[1]/ul/li[1]/a")
	private List<WebElement> customerBtn2;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[3]/ul/li[1]/ul/li[2]/a")
	private List<WebElement> customerFee;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[3]/ul/li[1]/ul/li[3]/a")
	private List<WebElement> customerProductMappingBtn;

	@FindBy(linkText = "User")
	private List<WebElement> userBtn;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[3]/ul/li[3]/ul/li/a")
	private List<WebElement> rolesBtn;

	@FindBy(linkText = "Vendor")
	private List<WebElement> vendorBtn;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]/li[3]/ul/li[4]/ul/li[1]/a")
	private List<WebElement> vendorSetupBtn;

	@FindBy(linkText = "Vendor Fee")
	private List<WebElement> vendorFeeBtn;

	@FindBy(xpath = "//select[@id=\"customer_id\"]")
	private WebElement custIdDrpdown;

	@FindBy(xpath = "//select[@id=\"state\"]")
	private WebElement custstateDrpdown;

	@FindBy(xpath = "//select[@id=\"product_id\"]")
	private WebElement custproductDrpdown;

	@FindBy(linkText = "Customer Fee Bulk Import")
	private List<WebElement> customerFeeBulkBtn;

	@FindBy(linkText = "Vendor Fee Bulk Import")
	private List<WebElement> vendorFeeBulkBtn;



	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Products']")
	private WebElement productsTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Customer Setup']")
	private WebElement customerSetupTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Customer Fee']")
	private WebElement customerFeetitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Customer Product Mapping']")
	private WebElement customerProductMappingTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Roles']")
	private WebElement rolesTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Vendor Setup']")
	private WebElement vendorSetupTitle;

	@FindBy(xpath = "//h3[@class='panel-title text-danger' and text()='Vendor Fee']")
	private WebElement vendorFeeTitle;

	@FindBy(xpath = "//*[@id=\"mainnav-menu\"]")
	private WebElement test;

	@FindBy(xpath = "//select[@id=\"state\"]")
	private WebElement vendstateDrpdown;

	@FindBy(xpath = "//select[@id=\"county\"]")
	private WebElement vendcountyDrpdown;

	@FindBy(xpath = "//select[@id=\"product_id\"]")
	private WebElement vendprodDrpdown;

	@FindBy(xpath = "//select[@id=\"vendor\"]")
	private WebElement vendorNamesDrpdown;

	@FindBy(xpath = "//*[@id=\"customer\"]")
	private WebElement custDrpdown;

	@FindBy(xpath = "//*[@id=\"product_table_info\"]")
    private WebElement prodTable;

	@FindBy(xpath = "//*[@id=\"role_table\"]/tbody")
    private WebElement roleTable;

	public CMSetUpPage(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		BaseReporter.logPagNav("CM SetUp Module");
	}

	public WebDriver NavigateToSetUpModule() {
		a.moveToElement(test).perform();
		clickElement(driver, elementList("setUpModule", setUpBtn));
		return driver;
	}

	public WebDriver NavigateToProductsModule() {

		a.moveToElement(test).perform();
		JSClick(driver, elementList("setUpModule", setUpBtn));
		JSClick(driver, elementList("productsModule", productsBtn));
		if (!(productsTitle.isDisplayed())) {

			BaseReporter.logFail("Products Page not opened");

		} else {
			BaseReporter.logPass("products page is opened");
			callForWait(2000);
			String products = prodTable.getText();
			String prodCount = products.substring(19, 21);
			int productsCount = Integer.parseInt(prodCount);
			if(productsCount >= 25) {BaseReporter.logPass("Products are greater than 25 are displayed");}
			else {BaseReporter.logFail("Products are greater than 25 are not displayed");}
		}

		return driver;
	}

	public WebDriver NavigateToCustomerModule() {

		a.moveToElement(test).perform();
		JSClick(driver, elementList("setUpModule", setUpBtn));

		JSClick(driver, elementList("customersModule", customerBtn));
		JSClick(driver, elementList("customersModule", customerBtn2));

		if(!(customerSetupTitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("Customer set up Page not opened");

		}

		else {BaseReporter.logPass("Customers page is opened");}
		return driver;
	}

	public WebDriver NavigateToCustomerFeeModule() {

		a.moveToElement(test).perform();
		JSClick(driver, elementList("setUpModule", setUpBtn));
		JSClick(driver, elementList("customersModule", customerBtn));
		callForWait(5000);
		JSClick(driver, elementList("customersFEEModule", customerFee));
		if(!(customerFeetitle.isDisplayed() )) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("customers fee Page not opened");

		}
		else {BaseReporter.logPass("Cuatomers fee page is opened");
		callForWait(5000);
				if(custIdDrpdown.isDisplayed()
						&& custstateDrpdown.isDisplayed() && custproductDrpdown.isDisplayed()) {
					BaseReporter.logPass("Verified dropdown-Customer,State,Product");
				}}
		JSClick(driver, elementList("customersFeeBulkModule", customerFeeBulkBtn));
		if(!(customerFeetitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("Customer fee bulk Page not opened");

		}
		else {BaseReporter.logPass("customers fee bulk page is opened");}
		return driver;
	}


	public WebDriver NavigateToCustomerProductMappingModule() {

		driver.navigate().refresh();
		a.moveToElement(test).perform();
		JSClick(driver, elementList("setUpModule", setUpBtn));
		JSClick(driver, elementList("customersModule", customerBtn));
		JSClick(driver, elementList("customersproductMappingModule", customerProductMappingBtn));
		if(!(customerProductMappingTitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("customer product mapping Page not opened");

		}
		else {BaseReporter.logPass("customer procuct mapping page is opened");}
		return driver;
	}
	public WebDriver NavigateToUserRolesModule() {

		a.moveToElement(test).perform();
		JSClick(driver, elementList("setUpModule", setUpBtn));
		JSClick(driver, elementList("userModule", userBtn));
		JSClick(driver, elementList("userRolesModule", rolesBtn));
		if(!(rolesTitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("User Roles Page not opened");

		}
		else {BaseReporter.logPass("User Roles page is opened");

		WebElement test123 = driver.findElement(By.id("role_table_next"));
		Boolean testBtn;
		do {

			 List<WebElement> list=new ArrayList<WebElement>();
				list = roleTable.findElements(By.tagName("tr"));

				for(int i=0;i<list.size();i++) {
					WebElement testelement = list.get(i);
					Boolean deleteBtnPresent = isElementPresent(testelement);

					if(!(deleteBtnPresent)) {
						BaseReporter.logPass("Delete button is not Displayed for role : "+i);
					}
					else {BaseReporter.logFail("Delete button is displayed");}
				}
				 testBtn = isClickable(test123, driver);
				 if(testBtn) {BaseReporter.logPass("Next button is enabled and clicked");}
				 else {BaseReporter.logPass("Next button is disabled...so Closing browser...");
				 break;}

		}while(testBtn);
		}
		return driver;
	}

	public boolean isElementPresent(WebElement ele) {
        try {
           WebElement deleteBtn =  ele.findElement(By.linkText("Delete"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

	public static boolean isClickable(WebElement el, WebDriver driver)
    {
        try{
            WebDriverWait wait = new WebDriverWait(driver, 6);
            wait.until(ExpectedConditions.elementToBeClickable(el));
            JSClick(driver, elementList("NextButton", el));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
	public WebDriver NavigateToVendorSetupModule() {
		driver.navigate().refresh();
		a.moveToElement(test).perform();
		clickElement(driver, elementList("setUpModule", setUpBtn));
		JSClick(driver, elementList("vendorpModule", vendorBtn));
		JSClick(driver, elementList("vendorSetupModule", vendorSetupBtn));
		if(!(vendorSetupTitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("vendor setup Page not opened");

		}
		else {BaseReporter.logPass("Vendor setup page is opened");}
			return driver;
	}
	public WebDriver NavigateToVendorFeeModule() {

		a.moveToElement(test).perform();
		JSClick(driver, elementList("setUpModule", setUpBtn));
		JSClick(driver, elementList("vendorModule", vendorBtn));
		callForWait(3000);
		JSClick(driver, elementList("vendorFeeModule", vendorFeeBtn));
		if(!(vendorFeeTitle.isDisplayed())) {
			//System.out.println("Allocate Orders page is opened and source drop down is present");
			BaseReporter.logFail("vendor fee Page not opened");

		}
		else {
			BaseReporter.logPass("Vendor Fee page is opened");
		callForWait(3000);
		if( vendstateDrpdown.isDisplayed() && vendcountyDrpdown.isDisplayed() && vendprodDrpdown.isDisplayed() && vendorNamesDrpdown.isDisplayed()) {
			BaseReporter.logPass("Verified dropdown-State,County,Product,Vendor Name");
			}
		}

		JSClick(driver, elementList("vendorFeeModule", vendorFeeBulkBtn));
		if(!(vendorNamesDrpdown.isDisplayed())) {
			BaseReporter.logFail("vendor bulk fee Page not opened");

		}
		else {BaseReporter.logPass("Vendor fee bulk page is opened");}
		return driver;
	}






}
