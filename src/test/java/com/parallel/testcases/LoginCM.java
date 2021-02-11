package com.parallel.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.appPages.LoginPage;
import com.cei.parallel.driver.DriverFactory;
import com.cei.reporter.BaseReporter;

public class LoginCM extends BaseTestcase{
	@Test
	public void validLoginasAgent() {



		WebDriver driver;
		driver = DriverFactory.getDriver();

		launchApp(driver);
		try {
			loginPage = new LoginPage(driver);

			driver = loginPage.loginAsAgent(baseProp.getUsername(), baseProp.getPassword());
			driver = loginPage.logoutAsAgent();

		} catch (Exception e) {
			BaseReporter.logException(driver, e);
		}
	}
}
