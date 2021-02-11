package com.cei.listeners;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import com.cei.BaseTest.BaseTest;
import com.cei.Baseconfig.DriverConfig;
import com.cei.parallel.driver.DriverFactory;
import com.cei.parallel.driver.ListofDriver;
import com.cei.reporter.ExtTest;
import com.cei.reporter.BaseReporter;
import com.relevantcodes.extentreports.ExtentTest;

public class InvokedMethodListener implements IInvokedMethodListener{

	public static ThreadLocal<ExtentTest> tltest = new ThreadLocal<ExtentTest>();
	DriverConfig baseProp;
	public static ThreadLocal<String> testcase = new ThreadLocal<String>();
	public static ThreadLocal<String> browsertype = new ThreadLocal<String>();
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {

			String testname;
			String browsername;
			baseProp = new DriverConfig();
			testname = method.getTestMethod().getMethodName();
			String classname = method.getTestMethod().getTestClass().getName().replaceAll(".*\\.","");
			testcase.set(testname);
			BaseReporter.folderName = method.getTestMethod().getMethodName();
			System.out.println("Test Method BeforeInvocation is started. " + Thread.currentThread().getId());
			browsername = method.getTestMethod().getXmlTest().getLocalParameters().get("browser");
			DriverFactory.setBrowserType(browsername);
			System.out.println(browsername +" : "+ testname);
			tltest.set(BaseTest.extentReport
					.startTest(testname + "<br> <div class=\"categories\"> <span class=\"category text-white\">"
							+ "Browser : " + browsername + "</span></div>"));

			ExtTest.setTest(tltest.get());
			ExtTest.getTest().assignCategory(classname);
			try {
				if (browsername.equalsIgnoreCase("windows.app")) {
					ExtTest.getTest().assignCategory("Local.Win.Chrome");
					DriverFactory.setWindowAppDriver(baseProp.getWinAppConfig());
				}

				// local drivers
				else if (browsername.equalsIgnoreCase("local.win.chrome")) {
					ExtTest.getTest().assignCategory("Local.Win.Chrome");
					DriverFactory.setWindowsWebDriver("chrome", baseProp.getLocalWinChromeConfig());
				} else if (browsername.equalsIgnoreCase("local.win.firefox")) {
					ExtTest.getTest().assignCategory("Local.Win.Firefox");
					DriverFactory.setWindowsWebDriver("firefox", baseProp.getLocalWinFirefoxConfig());
				} else if (browsername.equalsIgnoreCase("local.mac.safari")) {
					ExtTest.getTest().assignCategory("Local.Mac.Safari");
					DriverFactory.setMacWebDriver("safari", baseProp.getLocalMacSafariConfig());
				} else if (browsername.equalsIgnoreCase("local.mac.chrome")) {
					ExtTest.getTest().assignCategory("Local.Mac.Chrome");
					DriverFactory.setMacWebDriver("chrome", baseProp.getLocalMacChromeConfig());
				}

				// remote drivers
				else if (browsername.equalsIgnoreCase("remote.mac.safari")) {
					ExtTest.getTest().assignCategory("Remote.Mac.Safari");
					DriverFactory.setRemoteDriver("chrome", baseProp.getRemoteMacSafariConfig());
				} else if (browsername.equalsIgnoreCase("remote.mac.chrome")) {
					ExtTest.getTest().assignCategory("Remote.Mac.Chrome");
					DriverFactory.setRemoteDriver("chrome", baseProp.getRemoteMacChromeConfig());
				} else if (browsername.equalsIgnoreCase("remote.win.chrome")) {
					ExtTest.getTest().assignCategory("Remote.Win.Chrome");
					DriverFactory.setRemoteDriver("chrome", baseProp.getRemoteWinChromeConfig());
				} else if (browsername.equalsIgnoreCase("remote.win.firefox")) {
					ExtTest.getTest().assignCategory("Remote.Win.Firefox");
					DriverFactory.setRemoteDriver("chrome", baseProp.getRemoteWinFirefoxConfig());
				}

				// mobile drivers
				else if (browsername.equalsIgnoreCase("android.chrome")) {
					ExtTest.getTest().assignCategory("Android.Chrome");
					DriverFactory.setAndroidDriver(baseProp.getAndroidChromeConfig());
				} else if (browsername.equalsIgnoreCase("ios.safari")) {
					ExtTest.getTest().assignCategory("IOS.Safari");
					DriverFactory.setIOSDriver(baseProp.getIOSSafariConfig());
				} else if (browsername.equalsIgnoreCase("ios.chrome")) {
					ExtTest.getTest().assignCategory("IOS.Chrome");
					DriverFactory.setIOSDriver(baseProp.getIOSChromeConfig());
				}
				// web driver chrome
				else if (browsername.equalsIgnoreCase("chrome")) {
					ExtTest.getTest().assignCategory("Chrome");
					DriverFactory.setWebDriverManager("chrome");
				}

				else {
					BaseReporter.logFail(browsername + " is not available. Please choose browsertype from the following:<br>"
							+ ListofDriver.browserListInReport()
							,null);
					Assert.fail();
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			System.out.println("Test Method AfterInvocation is started. " + Thread.currentThread().getId());
			WebDriver driver = DriverFactory.getDriver();
			if (driver != null) {
				driver.quit();
			}
			BaseTest.extentReport.endTest(ExtTest.getTest());
			BaseTest.extentReport.flush();
		}
	}

}
