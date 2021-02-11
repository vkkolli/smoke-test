package com.parallel.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.appPages.LoginPage;
import com.appPages.ReportsModulePage;
import com.cei.parallel.driver.DriverFactory;
import com.cei.reporter.BaseReporter;

public class ReportsModule extends BaseTestcase {
	@Test


	public void Reports()
	{
		WebDriver driver;

		driver = DriverFactory.getDriver();

		launchApp(driver);

		try {
			loginPage = new LoginPage(driver);

			reportsPage = new ReportsModulePage(driver);

			driver = loginPage.loginAsAgent(baseProp.getUsername(), baseProp.getPassword());

			//driver = reportsPage.NavigateToReportsModule();
			driver = reportsPage.NavigateToFeeApprovedReport();

			driver = reportsPage.NavigateToMcdsReport();

			driver = reportsPage.NavigateToOnlineToOfflineReport();

			driver = reportsPage.NavigateToPortfolioReport();

			driver = reportsPage.NavigateToSageReport();

			driver = reportsPage.NavigateToStatusReport();

			driver = reportsPage.NavigateToErrorlogReport();

			driver = reportsPage.NavigateToProductionTrackerReport();

			driver = reportsPage.NavigateToProjectTrackerReport();

			driver = reportsPage.NavigateToQualityScoresReport();

			driver = reportsPage.NavigateToTurnAroundReport();



		} catch (Exception e) {
			BaseReporter.logException(driver, e);
		}
	}

}
