package com.cei.reporter;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.cei.Baseconfig.Base_Prop;
import com.cei.Baseconfig.Report_Prop;
import com.cei.utility.Utility;

import org.junit.Assert;

public class BaseReporter {

	protected static String REPORT_PATH = Base_Prop.getReportDir();
	public static String resultpath;
	public static String folderName = "";

	public synchronized static ExtentReports createReport() {
		ExtentReports report;
		resultpath = REPORT_PATH + Utility.getTimeStamp();
		report = new ExtentReports(resultpath + "/DailySmokeTestReport.html");
		// report.(ReportDetails_Prop.getproject() + " Report");
		setReportInfo(report);
		Utility.createFolder(resultpath + "/Screenshot/");
		return report;
	}

	public static void setReportInfo(ExtentReports report) {
		report.addSystemInfo("User Name", System.getProperty("user.name"));
		report.addSystemInfo("OS", System.getProperty("os.name"));
		try {
			InetAddress localMachine = java.net.InetAddress.getLocalHost();
			report.addSystemInfo("Host Name", localMachine.getHostName());
			report.addSystemInfo("Environment", Base_Prop.getEnv().toUpperCase());
			report.addSystemInfo("Application", Report_Prop.getapplication());
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		}
	}

	public static synchronized String capture(WebDriver driver) throws IOException {

		File directory = new File(resultpath + "/Screenshot/" + folderName);
		if (!directory.exists())
			directory.mkdir();
		int count = new File(resultpath + "/Screenshot/" + folderName).list().length + 1;
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String errflpath = resultpath + "/Screenshot/" + folderName + "/Image" + count + ".png";
		String hreflink = "./Screenshot/" + folderName + "/Image" + count + ".png";
		File Dest = new File(errflpath);
		try {
			FileUtils.copyFile(scrFile, Dest);
		} catch (IOException e) {
			logException(driver, e);
		}
		return hreflink;
	}

	public static synchronized void logPagNav(String logmsg) {

		ExtTest.getTest().log(LogStatus.INFO, "<i>" + logmsg + "</i>", "");
	}

	public static synchronized void logScenario(String logmsg) {

		ExtTest.getTest().log(LogStatus.INFO, "<b>" + logmsg + "</b>", "");
	}

	public static synchronized void logException(WebDriver driver, Exception e) {
		String exceptionmsg;
		exceptionmsg = Utility.lineBreak(e.getMessage());
		try {
			if(driver != null)
				ExtTest.getTest().log(LogStatus.FAIL, "",
					"<b style=\"background-color: brown; color: bisque;\">Exception :</b> " + exceptionmsg
							+ ExtTest.getTest().addScreenCapture(BaseReporter.capture(driver)));
			else
				ExtTest.getTest().log(LogStatus.FAIL, "",
						"<b style=\"background-color: brown; color: bisque;\">Exception :</b> " + exceptionmsg);
		} catch (IOException e1) {
			ExtTest.getTest().log(LogStatus.ERROR, "",
					"<b style=\"background-color: brown; color: bisque;\">Error :</b> " + e1.getMessage());
		}
		Assert.fail();
	}

	public static synchronized void logFail(String failmsg) {
		logFail(failmsg, null);
	}

	public static synchronized void logFail(String failmsg, WebDriver driver) {
		if (driver != null) {

			try {
				ExtTest.getTest().log(LogStatus.FAIL, "",
						"<b style=\"background-color: brown; color: bisque;\">Failed :</b> " + Utility.lineBreak(failmsg)
								+ ExtTest.getTest().addScreenCapture(BaseReporter.capture(driver)));
			} catch (IOException e) {
				ExtTest.getTest().log(LogStatus.ERROR, "",
						"<b style=\"background-color: brown; color: bisque;\">Error :</b> " + e.getMessage());
			}
		} else {
			ExtTest.getTest().log(LogStatus.FAIL, "",
					"<b style=\"background-color: brown; color: bisque;\">Error :</b> " + Utility.lineBreak(failmsg));
		}
		if(Base_Prop.getAssertCondition())
			Assert.fail();
	}
	

	public static synchronized void logFailForConf(String failmsg, String type) {

		if (type.equalsIgnoreCase("property"))
			ExtTest.getTest().log(LogStatus.FAIL, "",
					"<b style=\"background-color: brown; color: bisque;\">Error in Test Data :</b> " + Utility.lineBreak(failmsg)
							+ " property is not available");
		else if (type.equalsIgnoreCase("json"))
			ExtTest.getTest().log(LogStatus.FAIL, "",
					"<b style=\"background-color: brown; color: bisque;\">Error in Test Data :</b> " + Utility.lineBreak(failmsg)
							+ " json file is not available");
		else
			ExtTest.getTest().log(LogStatus.FAIL, "",
					"<b style=\"background-color: brown; color: bisque;\">Error in Test Data :</b> " + Utility.lineBreak(failmsg));
		Assert.fail();
	}

	public static synchronized void logPass(String logmsg) {
		logPass(logmsg, null);
	}
 
	public static synchronized void logPass(String logmsg, WebDriver driver) {
		if (driver != null) {
			try {
				ExtTest.getTest().log(LogStatus.PASS, "",
						Utility.lineBreak(logmsg) + ExtTest.getTest().addScreenCapture(BaseReporter.capture(driver)));
			} catch (IOException e) {
				BaseReporter.logException(driver, e);
			}
		}
		else {
			ExtTest.getTest().log(LogStatus.PASS, "", Utility.lineBreak(logmsg));
		}
	}

}
