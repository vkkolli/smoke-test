package com.parallel.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.appPages.LoginPage;
import com.appPages.MasterDataPages;
import com.cei.parallel.driver.DriverFactory;
import com.cei.reporter.BaseReporter;

public class MasterDataModule  extends BaseTestcase {

	@Test
	public void MasterData()
	{
		WebDriver driver;

		driver = DriverFactory.getDriver();
		launchApp(driver);

		try {
			loginPage = new LoginPage(driver);

			MasterData = new MasterDataPages(driver);

			driver = loginPage.loginAsAgent(baseProp.getUsername(), baseProp.getPassword());

			driver = MasterData.NavigateToMasterDataModule();

			driver = MasterData.NavigateToCountycountyPage();

			driver = MasterData.NavigateToCountyCountyImportPage();

			driver = MasterData.NavigateToUserSetupPage();

			driver = MasterData.NavigateToSkillSetupPage();

			driver = MasterData.NavigateToKnowledgeBasePage();

		} catch (Exception e) {
			BaseReporter.logException(driver, e);
		}

	}
}
