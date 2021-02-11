package com.parallel.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.appPages.LoginPage;
import com.appPages.OrderManagementPage;
import com.cei.parallel.driver.DriverFactory;
import com.cei.reporter.BaseReporter;

public class OrderManagementModule extends BaseTestcase {
	@Test
	public void OrderManagement() {
		WebDriver driver;
		driver = DriverFactory.getDriver();

		launchApp(driver);

		try {

			loginPage = new LoginPage(driver);

			OrderManagementModule = new OrderManagementPage(driver);

			driver = loginPage.loginAsAgent(baseProp.getUsername(), baseProp.getPassword());

			driver = OrderManagementModule.NavigateToOrderManagementModule();

			driver = OrderManagementModule.NavigateToImportOrdersModule();

			driver = OrderManagementModule.NavigateToAllocateOrdersModule();

			driver = OrderManagementModule.NavigateToReassignOrdersModule();

			driver = OrderManagementModule.NavigateToOrderStatusModule();

			driver = OrderManagementModule.NavigateToSetDueDateModule();

			driver = OrderManagementModule.NavigateToPrioritizeOrdersModule();

			driver = OrderManagementModule.NavigateToPrioritizePoolModule();

			driver = OrderManagementModule.NavigateToCancelOrdersModule();
		} catch (Exception e) {
			BaseReporter.logException(driver, e);
		}

	}

}
