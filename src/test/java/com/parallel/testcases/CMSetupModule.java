package com.parallel.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.appPages.CMSetUpPage;
import com.appPages.LoginPage;
import com.cei.parallel.driver.DriverFactory;
import com.cei.reporter.BaseReporter;

public class CMSetupModule extends BaseTestcase {
	@Test

	public void SetupModule() {
		WebDriver driver;

		driver = DriverFactory.getDriver();
		launchApp(driver);

		try {
			loginPage = new LoginPage(driver);

			setupPage = new CMSetUpPage(driver);

			driver = loginPage.loginAsAgent(baseProp.getUsername(), baseProp.getPassword());

			//driver = setupPage.NavigateToSetUpModule();

			driver = setupPage.NavigateToProductsModule();

			driver = setupPage.NavigateToCustomerModule();

			driver = setupPage.NavigateToCustomerFeeModule();

			driver = setupPage.NavigateToCustomerProductMappingModule();

			driver = setupPage.NavigateToUserRolesModule();

			driver = setupPage.NavigateToVendorSetupModule();

			driver = setupPage.NavigateToVendorFeeModule();

		} catch (Exception e) {
			BaseReporter.logException(driver, e);
		}
	}

}
