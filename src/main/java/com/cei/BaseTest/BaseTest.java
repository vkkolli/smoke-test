package com.cei.BaseTest;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.cei.reporter.BaseReporter;
import com.relevantcodes.extentreports.ExtentReports;

public class BaseTest {
	
	public static ExtentReports extentReport;

	@BeforeSuite
	public void beforeSuite() {
		extentReport = BaseReporter.createReport();
	}



	@AfterSuite
	public void afterSuite() {
		extentReport.close();
	}
}
