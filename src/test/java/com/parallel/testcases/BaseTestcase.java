package com.parallel.testcases;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.appPages.CMSetUpPage;
import com.appPages.LoginPage;
import com.appPages.MasterDataPages;
import com.appPages.OrderManagementPage;
import com.appPages.QCPages;
import com.appPages.ReportsModulePage;
import com.cei.BaseTest.BaseTest;
import com.cei.Baseconfig.Base_Prop;
import com.cei.WinPage.WinPage;
import com.cei.pages.Page;
import com.cei.utility.Utility;
import com.configPage.Common_Prop;
import com.relevantcodes.extentreports.ExtentReports;

public class BaseTestcase extends BaseTest {

	Base_Prop baseProp = new Base_Prop();
	Common_Prop commonProp = new Common_Prop();

	LoginPage loginPage;
	OrderManagementPage OrderManagementModule;
	QCPages QC;
	MasterDataPages MasterData;
	ReportsModulePage reportsPage;
	CMSetUpPage setupPage;

	WinPage windowsPage;
	Utility utilobj;

	public static ExtentReports extentReport;

	public static HashMap<String, Integer> loginMethodCalls = new HashMap<>();

	public void launchApp(WebDriver browser) {
		try {
			Page.navigateURL(browser, baseProp.getUrl());
			Page.verifyURL(browser, baseProp.getUrl(), true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}



}
