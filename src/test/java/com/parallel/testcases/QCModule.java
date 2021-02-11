package com.parallel.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.appPages.LoginPage;
import com.appPages.QCPages;
import com.cei.parallel.driver.DriverFactory;
import com.cei.reporter.BaseReporter;

public class QCModule extends BaseTestcase {

	@Test
	public void QC()
	{
		WebDriver driver;

		driver = DriverFactory.getDriver();
		launchApp(driver);

			try {

			loginPage = new LoginPage(driver);

			QC = new QCPages(driver);

			driver = loginPage.loginAsAgent(baseProp.getUsername(), baseProp.getPassword());

			driver = QC.NavigateToQCModule();

			driver = QC.NavigateToErrorDescriptionPage();

			driver = QC.NavigateToProductChecklistPage();

			} catch (Exception e){
		    	BaseReporter.logException(driver, e);
			}

	}




}
