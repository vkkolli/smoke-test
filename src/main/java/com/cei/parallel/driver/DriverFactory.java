package com.cei.parallel.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.cei.Baseconfig.Base_Prop;
import com.cei.Baseconfig.DriverConfig;
import com.cei.reporter.BaseReporter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.windows.WindowsDriver;
import io.github.bonigarcia.wdm.WebDriverManager;


public class DriverFactory {
	private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	private static ThreadLocal<AndroidDriver> tlAndroidDriver = new ThreadLocal<>();

	private static final String RESOURCE_PATH = Base_Prop.getBrowserDir();

	private static Process winProcess;
	private static String browserType;
	private static DriverConfig baseProp;

	public static synchronized void setWindowsWebDriver(String browserName, JSONObject object) {

		String driverpath;

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			/*
			 * if (object.containsKey("version")) driverpath = RESOURCE_PATH +
			 * "chrome/Windows/chromedriver_" + (String) object.get("version") + ".exe";
			 * else driverpath = RESOURCE_PATH + "chrome/Windows/chromedriver.exe";
			 */
			// Map<String, Object> prefs = new HashMap<String, Object>();

			// prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
			// prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
			// prefs.put("profile.default_content_setting_values.notifications", 1);

			System.setProperty("webdriver.chrome.whitelistedIps", "");
			//System.setProperty("webdriver.chrome.driver", driverpath);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--enable-javascript");
			options.addArguments("-enable-automation");
			options.addArguments("disable-extensions");
			options.addArguments("ignore-certificate-errors");
			options.addArguments("use-fake-device-for-media-stream");
			options.addArguments("use-fake-ui-for-media-stream");
			// options.addArguments("--disable-notifications");
			// options.setExperimentalOption("prefs", prefs);

			options.setExperimentalOption("useAutomationExtension", false);

			if (object.containsKey("binaryPath"))
				options.setBinary((String) object.get("binaryPath"));

			tlDriver.set(new ChromeDriver(options));
		}

		else if (browserName.equalsIgnoreCase("firefox")) {
			if (object.containsKey("version"))
				driverpath = RESOURCE_PATH + "firefox/Windows/geckodriver_" + (String) object.get("version") + ".exe";
			else
				driverpath = RESOURCE_PATH + "firefox/Windows/geckodriver.exe";

			System.setProperty("webdriver.gecko.driver", driverpath);
			FirefoxOptions options = new FirefoxOptions();

			if (object.containsKey("binaryPath"))
				options.setBinary((String) object.get("binaryPath"));

			tlDriver.set(new FirefoxDriver(options));
		}

		else {
			BaseReporter.logFail("Browser Type Windows:[" + browserName + "] is not available", null);
		}
		tlDriver.get().manage().window().maximize();
		tlDriver.get().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	public static synchronized void setWebDriverManager(String browserName) {


		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();


			System.setProperty("webdriver.chrome.whitelistedIps", "");
			//System.setProperty("webdriver.chrome.driver", driverpath);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--enable-javascript");
			options.addArguments("-enable-automation");
			options.addArguments("disable-extensions");
			options.addArguments("ignore-certificate-errors");
			options.addArguments("use-fake-device-for-media-stream");
			options.addArguments("use-fake-ui-for-media-stream");


			options.setExperimentalOption("useAutomationExtension", false);

			tlDriver.set(new ChromeDriver(options));
		}


		else {
			BaseReporter.logFail("Browser Type Windows:[" + browserName + "] is not available", null);
		}
		tlDriver.get().manage().window().maximize();
		tlDriver.get().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	public static synchronized void setWindowAppDriver(JSONObject objects) throws MalformedURLException {

		String driverpath = (String) objects.get("driverpath");
		startWinAppDriver(driverpath);
		String key, value;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		for (Object obj : objects.keySet()) {
			key = (String) obj;
			value = (String) objects.get(key);
			if (!key.equals("driverpath"))
				capabilities.setCapability(key, value);
		}
		WindowsDriver<?> winDriver = new WindowsDriver<>(new URL("http://127.0.0.1:4723"), capabilities);
		tlDriver.set(winDriver);
	}

	public static synchronized void setMacWebDriver(String browserName, JSONObject object) {

		String driverpath;

		if (browserName.equalsIgnoreCase("chrome")) {
			if (object.containsKey("version"))
				driverpath = RESOURCE_PATH + "chrome/Mac/chromedriver_" + (String) object.get("version");
			else
				driverpath = RESOURCE_PATH + "chrome/Mac/chromedriver";

			System.setProperty("webdriver.chrome.driver", driverpath);
			ChromeOptions options = new ChromeOptions();

			if (object.containsKey("binaryPath"))
				options.setBinary((String) object.get("binaryPath"));

			tlDriver.set(new ChromeDriver(options));
		}

		else if (browserName.equalsIgnoreCase("safari")) {

			SafariOptions options = new SafariOptions();
			tlDriver.set(new SafariDriver(options));
		}

		else {
			BaseReporter.logFail("Browser Type Windows:[" + browserName + "] is not available", null);
		}
		tlDriver.get().manage().window().maximize();
		tlDriver.get().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public static synchronized void setAndroidDriver(JSONObject objects) throws MalformedURLException {

		String key, value;
		String appiumurl = (String) objects.get("appiumurl");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		for (Object obj : objects.keySet()) {
			key = (String) obj;
			value = (String) objects.get(key);
			if (!key.equals("appiumurl"))
				capabilities.setCapability(key, value);
		}

		// tlDriver.set(new RemoteWebDriver(new URL(appiumurl), capabilities));
		AndroidDriver<?> andyDriver = new AndroidDriver(new URL(appiumurl), capabilities);
		tlAndroidDriver.set(andyDriver);
		RemoteWebDriver driver = andyDriver;
		tlDriver.set(driver);
	}

	public static synchronized AndroidDriver getAndroidDriver() {
		return tlAndroidDriver.get();
	}

	public static synchronized void setIOSDriver(JSONObject objects) throws MalformedURLException {

		String key, value;
		String appiumurl = (String) objects.get("appiumurl");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		for (Object obj : objects.keySet()) {
			key = (String) obj;
			value = (String) objects.get(key);
			if (!key.equals("appiumurl"))
				capabilities.setCapability(key, value);
		}

		tlDriver.set(new RemoteWebDriver(new URL(appiumurl), capabilities));
	}

	public static synchronized void setRemoteDriver(String browserName, JSONObject objects) throws Exception {

		String nodeURL = (String) objects.get("node");
		if (browserName.equalsIgnoreCase("chrome")) {

			ChromeOptions chromeoption = new ChromeOptions();
			tlDriver.set(new RemoteWebDriver(new URL(nodeURL), chromeoption));
		} else if (browserName.equalsIgnoreCase("firefox")) {

			FirefoxOptions foxoption = new FirefoxOptions();
			tlDriver.set(new RemoteWebDriver(new URL(nodeURL), foxoption));
		} else if (browserName.equalsIgnoreCase("safari")) {

			SafariOptions safoption = new SafariOptions();
			tlDriver.set(new RemoteWebDriver(new URL(nodeURL), safoption));
		} else {

			BaseReporter.logFail("Browser Type Windows:[" + browserName + "] is not available", null);
		}

		tlDriver.get().manage().window().maximize();
		tlDriver.get().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	private static void startWinAppDriver(String windriverpath) {

		ProcessBuilder pBuilder = new ProcessBuilder(windriverpath);
		pBuilder.inheritIO();
		try {
			winProcess = pBuilder.start();
		} catch (Exception e) {
			winProcess.destroy();
		} finally {
			Runtime.getRuntime().addShutdownHook(new Thread(new WinAppCleanup()));
		}
	}

	private static class WinAppCleanup implements Runnable {
		public void run() {
			try {
				winProcess.destroy();
			} catch (Exception x) {
				winProcess.destroy();
			}
		}
	}

	// ~~new function - need to check with threadsafety in parallel execution
	public static void createDriver(String browsername) {
		baseProp = new DriverConfig();
		if (browsername.equalsIgnoreCase("local.win.chrome")) {

			DriverFactory.setWindowsWebDriver("chrome", baseProp.getLocalWinChromeConfig());
		} else if (browsername.equalsIgnoreCase("local.win.firefox")) {

			DriverFactory.setWindowsWebDriver("firefox", baseProp.getLocalWinFirefoxConfig());
		}
	}

	public static String getBrowserType() {
		return browserType;
	}

	public static void setBrowserType(String browser) {
		browserType = browser;
	}



//	Functions to handle multiple browser close in single test [Ex.Closing Script]
//  Future Implementation
//	private static ThreadLocal<ArrayList<WebDriver>> drivers = new ThreadLocal<>();
//	private static ThreadLocal<ArrayList> arraylist = new ThreadLocal<>();
//
//
//	public static synchronized ArrayList<WebDriver> getDriverList() {
//		return drivers.get();
//	}
//
//	public static synchronized void setDriverList(ArrayList<WebDriver> driverlist) {
//		drivers.set(driverlist);
//	}
//
//	public static synchronized ArrayList getList() {
//		return arraylist.get();
//	}
//
//	public static synchronized void setList(ArrayList list) {
//		arraylist.set(list);
//	}

}
