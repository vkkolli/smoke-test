package com.cei.pages;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cei.Baseconfig.Base_Prop;
import com.cei.reporter.BaseReporter;

public class Page {

	static int count;
	static long wait;

	// public static final String ARTIFACT_PATH = "Resource/Artifacts/";
	protected static void JSClick(WebDriver browser, ArrayList<?> ele) {
		JSClick(browser, ele, false);
	}
	
	

	protected static void JSClick(WebDriver browser, ArrayList<?> ele, boolean report) {
		pageReady(browser);
		int stalecount = 0;
		while (stalecount < count) {
			try {
				WebElement element = getElement(browser, ele);
				JavascriptExecutor js = (JavascriptExecutor) browser;
				js.executeScript("arguments[0].click()", element);
				if (report)
					BaseReporter.logPass(ele.get(0) + " is clicked", browser);
				else
					BaseReporter.logPass(ele.get(0) + " is clicked");
				break;
			} catch (ElementNotInteractableException e) {
				stalecount++;
				if (stalecount >= count) {
					clickElement(browser, ele, report);
					break;
				}
				callForWait(wait);
				System.out.println("Not Interactable. Retrying...");
			} catch (Exception e) {
				clickElement(browser, ele, report);
				break;
				// BaseReporter.logException(browser, e);
			}
		}
	}

	public static void verifyURL(WebDriver browser, String expectedURL) throws Exception {
		verifyURL(browser, expectedURL, false);
	}

	public static void verifyURL(WebDriver browser, String expectedURL, boolean report) throws Exception {
		pageReady(browser);
		loadWaitValues();
		boolean flag = false;
		for (int i = 0; i < count; i++) {
			if (browser.getCurrentUrl().equals(expectedURL)) {
				flag = true;
				break;
			}
			System.out.println("failed to match url. Retrying..");
			Thread.sleep(wait);
		}
		if (flag) {
			if (report)
				BaseReporter.logPass("URL Verified : " + expectedURL, browser);
			else
				BaseReporter.logPass("URL Verified : " + expectedURL);
		} else {
			BaseReporter.logFail("URL Mismatch : Actual URL is " + browser.getCurrentUrl(), browser);
			Assert.fail();
		}
	}

	public static void tearDown(WebDriver driver) {

		if (driver != null)
			driver.quit();
	}

	public static void closeBrowser(WebDriver browser) {

		browser.close();
		BaseReporter.logPass("Browser is closed");
	}

	protected static void enterText(WebDriver browser, ArrayList<?> ele, String testdata) {
		enterText(browser, ele, testdata, "disabled", false);
	}

	protected static void enterText(WebDriver browser, ArrayList<?> ele, String testdata, String seq) {
		enterText(browser, ele, testdata, seq, false);
	}

	protected static void enterText(WebDriver browser, ArrayList<?> ele, String testdata, boolean report) {
		enterText(browser, ele, testdata, "disabled", report);
	}

	protected static void enterText(WebDriver browser, ArrayList<?> ele, String testdata, String seq, boolean report) {
		pageReady(browser);
		int stalecount = 0;
		try {
			WebElement element = getElement(browser, ele);

			int len = element.getAttribute("value").length();

			while (stalecount < count) {
				try {
					if (len > 0 || seq.contains("enabled")) {
						for (int i = 0; i < len; i++) {
							element.sendKeys(Keys.BACK_SPACE);
						}
						char[] arr = testdata.toCharArray();
						for (int i = 0; i < arr.length; i++) {
							element.sendKeys(Character.toString(arr[i]));
						}
					} else {
						element.sendKeys(testdata);
					}
					break;
				} catch (ElementNotInteractableException e) {
					stalecount++;
					if (stalecount >= count)
						BaseReporter.logException(browser, e);
					callForWait(wait);
					System.out.println("Not Interactable. Retrying...");
				}
			}
			if (element.getAttribute("type").equalsIgnoreCase("password")) {
				testdata = testdata.replaceAll(".", "*");
			}
			if (report)
				BaseReporter.logPass(testdata + " is entered in " + ele.get(0), browser);
			else
				BaseReporter.logPass(testdata + " is entered in " + ele.get(0));
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
	}

	protected static void clickElement(WebDriver browser, ArrayList<?> ele) {
		clickElement(browser, ele, false);
	}

	protected static void clickElement(WebDriver browser, ArrayList<?> ele, boolean report) {
		pageReady(browser);
		int stalecount = 0;
		try {
			WebElement element = getElement(browser, ele);
			while (stalecount < count) {
				try {
					element.click();
					break;
				} catch (ElementNotInteractableException e) {
					stalecount++;
					if (stalecount >= count)
						BaseReporter.logException(browser, e);
					callForWait(wait);
					System.out.println("Not Interactable. Retrying...");
				}
			}
			if (report)
				BaseReporter.logPass(ele.get(0) + " is clicked", browser);
			else
				BaseReporter.logPass(ele.get(0) + " is clicked");

		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
	}

	public static void navigateURL(WebDriver browser, String url) {

		browser.get(url);
		BaseReporter.logPass("Browser is navigated to " + url);
	}

	protected static void verifyText(WebDriver browser, ArrayList<?> ele, String testdata) {
		verifyText(browser, ele, testdata, false);
	}

	protected static void verifyText(WebDriver browser, ArrayList<?> ele, String testdata, boolean report) {
		pageReady(browser);
		boolean flag = false;
		int stalecount = 0;
		while (stalecount < count) {
			try {
				WebElement element = getElement(browser, ele);

				String actualValue;
				if (flag == false && element.getAttribute("value") != null
						&& element.getAttribute("type").equals("text")) {
					actualValue = element.getAttribute("value");
					flag = textVerify(actualValue, testdata);
				}
				if (flag == false && element.getAttribute("innerHTML") != null) {
					actualValue = element.getAttribute("innerHTML");
					flag = textVerify(actualValue, testdata);
				}
				if (flag == false && element.getText() != null && !element.getText().equals("")) {
					actualValue = element.getText();
					flag = textVerify(actualValue, testdata);
				}
				if (flag == false && element.getAttribute("innerText") != null) {
					actualValue = element.getAttribute("innerText");
					flag = textVerify(actualValue, testdata);
				}
				if (flag) {
					if (report)
						BaseReporter.logPass("\"" + testdata + "\" is displayed on " + ele.get(0), browser);
					else
						BaseReporter.logPass("\"" + testdata + "\" is displayed on " + ele.get(0));
				} else {
					BaseReporter.logFail("\"" + testdata + "\" is not displayed on " + ele.get(0), browser);
				}
				break;
			} catch (ElementNotInteractableException e) {
				stalecount++;
				if (stalecount >= count) {
					BaseReporter.logException(browser, e);
					break;
				}
				callForWait(wait);
				System.out.println("Not Interactable. Retrying...");
			} catch (Exception e) {
				BaseReporter.logException(browser, e);
			}
		}
	}

	protected static void verifyPartialText(WebDriver browser, ArrayList<?> ele, String testdata) {
		verifyPartialText(browser, ele, testdata, false);
	}

	protected static void verifyPartialText(WebDriver browser, ArrayList<?> ele, String testdata, boolean report) {
		pageReady(browser);
		boolean flag = false;
		try {
			WebElement element = getElement(browser, ele);

			String actualValue;
			if (flag == false && element.getAttribute("value") != null && element.getAttribute("type").equals("text")) {
				actualValue = element.getAttribute("value");
				flag = textPartialVerify(actualValue, testdata);
			}
			if (flag == false && element.getAttribute("innerHTML") != null) {
				actualValue = element.getAttribute("innerHTML");
				flag = textPartialVerify(actualValue, testdata);
			}
			if (flag == false && element.getText() != null && !element.getText().equals("")) {
				actualValue = element.getText();
				flag = textPartialVerify(actualValue, testdata);
			}
			if (flag == false && element.getAttribute("innerText") != null) {
				actualValue = element.getAttribute("innerText");
				flag = textPartialVerify(actualValue, testdata);
			}
			if (flag) {
				if (report)
					BaseReporter.logPass("\"" + testdata + "\" is displayed on " + ele.get(0), browser);
				else
					BaseReporter.logPass("\"" + testdata + "\" is displayed on " + ele.get(0));
			} else {
				BaseReporter.logFail("\"" + testdata + "\" is not displayed on " + ele.get(0), browser);
			}
		} catch (

		Exception e) {
			BaseReporter.logException(browser, e);
		}
	}

	public static boolean textVerify(String actual, String expected) {
		boolean flag;
		actual = actual.trim().replaceAll("[^a-zA-Z0-9]", "");
		expected = expected.trim().replaceAll("[^a-zA-Z0-9]", "");
		if (actual.equals(expected)) {
			flag = true;
			return flag;
		} else {
			flag = false;
			return flag;
		}
	}

	public static boolean textPartialVerify(String actual, String expected) {
		boolean flag;
		actual = actual.trim().replaceAll("[^a-zA-Z0-9]", "");
		expected = expected.trim().replaceAll("[^a-zA-Z0-9]", "");
		if (actual.contains(expected)) {
			flag = true;
			return flag;
		} else {
			flag = false;
			return flag;
		}
	}

	protected static void selectByRadio(WebDriver browser, ArrayList<?> ele, String testdata) {
		selectByRadio(browser, ele, testdata, false);
	}

	protected static void selectByRadio(WebDriver browser, ArrayList<?> ele, String testdata, boolean report) {
		pageReady(browser);
		try {

			WebElement element = getElement(browser, ele);

			boolean flag = false;
			List<WebElement> elements = element.findElements(By.tagName("label"));
			for (WebElement item : elements) {
//				System.out.println(item.getText());
//				System.out.println(testdata);
				if (item.getText().contains(testdata)) {
					flag = true;
					item.click();
					return;
				}
			}

			if (flag) {
				if (report)
					BaseReporter.logPass(testdata + " is selected from " + ele.get(0), browser);
				else
					BaseReporter.logPass(testdata + " is selected from " + ele.get(0));
			} else
				BaseReporter.logFail("Unable to find " + testdata + " in " + ele.get(0), browser);
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
	}
	
	protected static void selectByRadioindex(WebDriver browser, ArrayList<?> ele, int testdata) {
		selectByRadioindex(browser, ele, testdata, false);
	}

	protected static void selectByRadioindex(WebDriver browser, ArrayList<?> ele, int testdata, boolean report) {
		pageReady(browser);
		try {

			WebElement element = getElement(browser, ele);

			boolean flag = false;
			List<WebElement> elements = element.findElements(By.tagName("label"));
			for (int i=0; i<elements.size(); i++) {
				if (i == testdata) {
					flag = true;
					elements.get(i).click();
					return;
				}
			}

			if (flag) {
				if (report)
					BaseReporter.logPass(testdata + " is selected from " + ele.get(0), browser);
				else
					BaseReporter.logPass(testdata + " is selected from " + ele.get(0));
			} else
				BaseReporter.logFail("Unable to find index: " + testdata + " in " + ele.get(0), browser);
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
	}

	protected static void selectFromDropdown(WebDriver browser, ArrayList<?> ele, String testdata) {
		selectFromDropdown(browser, ele, testdata, false);
	}

	protected static void selectFromDropdown(WebDriver browser, ArrayList<?> ele, String testdata, boolean report) {
		pageReady(browser);
		try {
			WebElement element = getElement(browser, ele);
			Select option = null;
			boolean flag = false;
			option = new Select(element);
			List<WebElement> listofoptions = option.getOptions();
			for (WebElement opt : listofoptions) {
				if (opt.getText().equalsIgnoreCase(testdata)) {
					flag = true;
					break;
				} else if (opt.getAttribute("innerText").equalsIgnoreCase(testdata)) {
					highlight(browser, opt);
					flag = true;
					break;
				}
			}

			if (flag) {
				// option.selectByValue(testdata);
				// asdoption.selectByIndex(2);
				option.selectByVisibleText(testdata);
				if (report)
					BaseReporter.logPass(testdata + " is selected from " + ele.get(0), browser);
				else
					BaseReporter.logPass(testdata + " is selected from " + ele.get(0));
			} else {
				BaseReporter.logFail("unable to locate " + testdata + " in dropdown", browser);
			}
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
	}

	protected static void verifyElementExist(WebDriver browser, ArrayList<?> ele, boolean flag) {
		verifyElementExist(browser, ele, flag, false);
	}

	protected static void verifyElementExist(WebDriver browser, ArrayList<?> ele, boolean flag, boolean report) {
		pageReady(browser);
		loadWaitValues();
		List<?> templist = (List<?>) ele.get(1);
		try {
			boolean check = false;
			for (int i = 0; i < count; i++) {
				if (templist.size() > 0 && flag) {
					check = true;
					if (report)
						BaseReporter.logPass(ele.get(0) + " is available on screen", browser);
					else
						BaseReporter.logPass(ele.get(0) + " is available on screen");
				} else if (templist.size() <= 0 && !flag) {
					check = true;
					if (report)
						BaseReporter.logPass(ele.get(0) + " not available on screen", browser);
					else
						BaseReporter.logPass(ele.get(0) + " not available on screen");
				} else {
					check = false;
				}

				if (check)
					break;
				else {
					System.out.println("unable to find element. Retrying...");
					Thread.sleep(wait);
				}
			}
			if (!check)
					BaseReporter.logFail(
						"Condition mismatch : Element count = " + templist.size() + " and Boolean Flag = " + flag,
									(browser != null) ? browser : null);

		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
	}

	protected static void scrolltoView(WebDriver browser, ArrayList<?> ele) {

		pageReady(browser);
		try {
			WebElement element = getElement(browser, ele);
			JavascriptExecutor js = (JavascriptExecutor) browser;
			js.executeScript("arguments[0].scrollIntoView();", element);
			js.executeScript("window.scrollBy(0,+50)");
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
	}

	protected static void selectFromList(WebDriver browser, String testdata, ArrayList<?> ele) {
		selectFromList(browser, testdata, ele, false);
	}

	// Function: works for dropdown without tags 'SELECT'.
	protected static void selectFromList(WebDriver browser, String testdata, ArrayList<?> ele, boolean report) {
		pageReady(browser);
		loadWaitValues();
		List<?> templist = (List<?>) ele.get(1);
		boolean check = false;

		try {
			for (int i = 0; i < count; i++) {
				if (templist.size() > 0)
					check = true;
				if (check)
					break;
				else {
					System.out.println("unable to find element. Retrying...");
					Thread.sleep(wait);
				}
			}

			boolean flag = false;
			@SuppressWarnings("unchecked")
			List<WebElement> listofoptions = (List<WebElement>) templist;
			for (WebElement opt : listofoptions) {
				if (opt.getText().equalsIgnoreCase(testdata)) {
					opt.click();
					flag = true;
					break;
				}
			}
			if (flag) {
				if (report)
					BaseReporter.logPass(testdata + " is selected from " + ele.get(0), browser);
				else
					BaseReporter.logPass(testdata + " is selected from " + ele.get(0));
			} else
				BaseReporter.logFail("unable to locate " + testdata + " in dropdown", browser);
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}

	}

	protected static void tableMatchData(WebDriver browser, ArrayList<?> ele, String testdata, boolean report) {
		tableMatchData(browser, ele, testdata, "", report);
	}

	protected static void tableMatchData(WebDriver browser, ArrayList<?> ele, String testdata) {
		tableMatchData(browser, ele, testdata, "", false);
	}

	// tableformat : "usersearch"
	protected static void tableMatchData(WebDriver browser, ArrayList<?> ele, String testdata, String tableformat) {
		tableMatchData(browser, ele, testdata, tableformat, false);
	}

	protected static void tableMatchData(WebDriver browser, ArrayList<?> ele, String testdata, String tableformat,
			boolean report) {

		pageReady(browser);
		String resultdata;
		if (testdata.contains("||")) {
			resultdata = testdata.replaceAll("\\|\\|", ",");
		} else {
			resultdata = testdata;
		}
		WebTable tableobj = new WebTable();
		WebElement table;
		boolean flag = false;
		try {
			for (int i = 0; i < 5; i++) {
				table = ((WebElement) ((List<?>) ele.get(1)).get(0));
				// waitforLoading(browser);
				 Thread.sleep(5000);
				if (tableobj.matchData(table, testdata, tableformat))
					flag = true;
				if (flag)
					break;
				System.out.println("failed to find  [" + testdata + "], retrying...");
			}
			if (flag) {
				if (report)
					BaseReporter.logPass("[" + resultdata + "] is matched in " + ele.get(0), browser);
				else
					BaseReporter.logPass("[" + resultdata + "] is matched in " + ele.get(0));
			} else
				BaseReporter.logFail("Unable to match " + resultdata + " in " + ele.get(0), browser);
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}

	}

	protected static void tableLinkClick(WebDriver browser, ArrayList<?> ele, String testdata, String linkToClick,
			boolean report) {
		tableLinkClick(browser, ele, testdata, linkToClick, "", report);
	}

	protected static void tableLinkClick(WebDriver browser, ArrayList<?> ele, String testdata, String linkToClick) {
		tableLinkClick(browser, ele, testdata, linkToClick, "", false);
	}

	// tableformat : "usersearch"
	protected static void tableLinkClick(WebDriver browser, ArrayList<?> ele, String testdata, String linkToClick,
			String tableformat) {
		tableLinkClick(browser, ele, testdata, linkToClick, tableformat, false);
	}

	protected static void tableLinkClick(WebDriver browser, ArrayList<?> ele, String testdata, String linkToClick,
			String tableformat, boolean report) {

		pageReady(browser);
		WebTable tableobj = new WebTable();
		WebElement table;
		boolean flag = false;
		try {
			for (int i = 0; i < 15; i++) {
				table = ((WebElement) ((List<?>) ele.get(1)).get(0));
				waitforLoading(browser);
				Thread.sleep(5000);
				if (tableobj.clickLink(browser, table, testdata, linkToClick, tableformat))
					flag = true;
				if (flag)
					break;
				System.out.println("failed to find  [" + testdata + "], retrying...");
			}
			if (flag) {
				if (report)
					BaseReporter.logPass(ele.get(0) + " is clicked", browser);
				else
					BaseReporter.logPass(ele.get(0) + " is clicked");
			} else
				BaseReporter.logFail("Unable to click " + ele.get(0), browser);
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
	}

	protected static void setCheckbox(WebDriver browser, ArrayList<?> ele, String testdata) {
		setCheckbox(browser, ele, testdata, false);
	}

	protected static void setCheckbox(WebDriver browser, ArrayList<?> ele, String testdata, boolean report) {

		boolean flag = true;
		try {
			WebElement element = getElement(browser, ele);
			if (testdata.equalsIgnoreCase("checked")) {
				if (!element.isSelected()) {
					JSClick(browser, ele);
					//element.click();
					flag = true;
				} else if (element.isSelected()) {
					flag = true;
				} else {
					flag = false;
				}
			} else if (testdata.equalsIgnoreCase("uncheck")) {
				if (element.isSelected()) {
					JSClick(browser, ele);
					flag = true;
				} else if (!element.isSelected()) {
					flag = true;
				} else {
					flag = false;
				}
			}
			if (flag) {
				if (report)
					BaseReporter.logPass(ele.get(0) + " checkbox is " + testdata + "ed", browser);
				else
					BaseReporter.logPass(ele.get(0) + " checkbox is " + testdata + "ed");
			} else {
				BaseReporter.logFail("Unable to retrieve checkbox property of " + ele.get(0),
						(browser != null) ? browser : null);
			}
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}

	}

	protected static void verifyCheckboxStatus(WebDriver browser, ArrayList<?> ele, String status) {
		verifyCheckboxStatus(browser, ele, status, false);
	}

	protected static void verifyCheckboxStatus(WebDriver browser, ArrayList<?> ele, String status, boolean report) {
		try {
			WebElement element = getElement(browser, ele);
			if (status.equalsIgnoreCase("checked")) {
				if (element.isSelected()) {
					if (report)
						BaseReporter.logPass(ele.get(0) + " checkbox is checked", browser);
					else
						BaseReporter.logPass(ele.get(0) + " checkbox is checked");
				} else {
					BaseReporter.logFail(ele.get(0) + " checkbox is not checked", (browser != null) ? browser : null);
				}
			} else if (status.equalsIgnoreCase("unchecked")) {
				if (!element.isSelected()) {
					if (report)
						BaseReporter.logPass(ele.get(0) + " checkbox is unchecked", browser);
					else
						BaseReporter.logPass(ele.get(0) + " checkbox is unchecked");
				} else
					BaseReporter.logFail(ele.get(0) + " checkbox is checked", (browser != null) ? browser : null);
			} else
				BaseReporter.logFail("[" + status + "] parameter doesnot match, Please pass \"checked/unchecked\"",
						(browser != null) ? browser : null);
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}

	}

	protected static void verifyElementColor(WebDriver browser, ArrayList<?> ele, String expectedcolor) {
		verifyElementColor(browser, ele, expectedcolor, false);
	}

	protected static void verifyElementColor(WebDriver browser, ArrayList<?> ele, String expectedcolor,
			boolean report) {
		try {
			WebElement element = getElement(browser, ele);
			highlight(browser, element);
			String RGBcolor = element.getCssValue("background-color");
			String colorcode = Color.fromString(RGBcolor).asHex();
			if (colorcode.equalsIgnoreCase(expectedcolor)) {
				BaseReporter.logPass(ele.get(0) + " has the expected color", browser);
			} else {
				BaseReporter.logFail("Expected color is  " + expectedcolor + " but Actual colour is "
						+ element.getCssValue("background-color"), browser);
			}

		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
	}

	public static void sendEscape(WebDriver browser) {
		Actions action = new Actions(browser);
		action.sendKeys(Keys.ESCAPE).build().perform();
	}

	protected static void pageReady(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int i = 0; i < 25; i++) {
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public static void waitforLoading(WebDriver browser) {
		try {
			WebDriverWait loadingwait = new WebDriverWait(browser, 20);
			WebElement loadingFrames = browser.findElement(By.xpath("//h2[contains(text(),'Loading...')]"));
			loadingwait.until(ExpectedConditions.invisibilityOf(loadingFrames));
			pageReady(browser);
			Thread.sleep(1000);
		} catch (Exception e) {
			//System.out.println("loading..");
		}
	}
	
	public static void waitforLoading(WebDriver browser, int waittime) {
		try {
			WebDriverWait loadingwait = new WebDriverWait(browser, waittime);
			WebElement loadingFrames = browser.findElement(By.xpath("//h2[contains(text(),'Loading...')]"));
			loadingwait.until(ExpectedConditions.invisibilityOf(loadingFrames));
			pageReady(browser);
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("loading..");
		}
	}

	public static void highlight(WebDriver driver, WebElement element) {
		for (int i = 0; i < 8; i++) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"color: yellow; border: 2px solid yellow;");
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	protected static ArrayList<Object> elementList(String elementname, WebElement element) {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(elementname);
		list.add(element);
		return list;
	}

	protected static ArrayList<Object> elementList(String elementname, List<WebElement> webElementlist) {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(elementname);
		list.add((List<WebElement>) webElementlist);
		return list;
	}

	@SuppressWarnings("unchecked")
	protected static WebElement getElement(WebDriver driver, ArrayList<?> ele) {

		//waitforLoading(driver);
		//pageReady(driver);
		loadWaitValues();
		List<WebElement> templist = new ArrayList<WebElement>();
		WebElement element = null;
		boolean flag = false;
		try {
			if (ele.get(1) instanceof List<?>) {
				templist = (List<WebElement>) ele.get(1);
			} else if (ele.get(1) instanceof WebElement) {
				templist.add((WebElement) ele.get(1));
			}
			for (int i = 0; i < count; i++) {
				if (templist.size() > 0) {
					element = templist.get(0);
					//highlight(driver, element);
					flag = true;
				} else {
					System.out.println("unable to find element. Retrying...");
					Thread.sleep(wait);
				}
				if (flag)
					break;
			}
			if (!flag)
				BaseReporter.logFail("Unable to find " + ele.get(0), driver);
		} catch (Exception e) {
			BaseReporter.logException(driver, e);
		}
		return element;
	}

	static void loadWaitValues() {
		count = Base_Prop.getNumberofAttempt();
		wait = Base_Prop.getWaitForRerun();
	}

	public static void callForWait(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
	}

	public void setFocus(WebDriver driver, WebElement control) {

		if (control != null) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", control);
			js.executeScript("window.scrollBy(0,-80)");
		}
	}

	public static void handleAlert(WebDriver browser, String expectedText) {
		Alert alert = null;
		int failcount = 3;
		for (int i = 0; i < failcount; i++) {
			try {
				alert = browser.switchTo().alert();
				break;
			} catch (Exception e) {
				callForWait(wait);
				System.out.println("unable to find alert. Retrying...");
			}
		}

		String gettext = alert.getText();
		boolean flag = false;
		for (int i = 0; i < 3; i++) {
			if (expectedText.contains(gettext)) {
				flag = true;
				alert.accept();
				BaseReporter.logPass(expectedText + " is displayed on screen", browser);
				break;
			} else {
				flag = false;
			}
		}

		if (!flag) {
			BaseReporter.logFail(expectedText + " not displayed on screen", browser);
		}
	}

	protected static void verifyFromList(WebDriver browser, String testdata, ArrayList<?> ele) {
		verifyFromList(browser, testdata, ele, false);
	}

	@SuppressWarnings("null")
	protected static void verifyFromList(WebDriver browser, String testdata, ArrayList<?> ele, boolean report) {
		pageReady(browser);
		loadWaitValues();
		List<?> templist = (List<?>) ele.get(1);
		boolean check = false;
		int icount = 0;
		List<String> testdataarr = null;
		if (testdata.contains("||")) {
			testdataarr = Arrays.asList(testdata.split("\\|\\|"));
		} else {
			testdataarr.add(testdata);
		}

		try {
			for (int i = 0; i < count; i++) {
				if (templist.size() > 0)
					check = true;
				if (check)
					break;
				else {
					System.out.println("unable to find element. Retrying...");
					Thread.sleep(wait);
				}
			}

			boolean flag = false;
			@SuppressWarnings("unchecked")
			List<WebElement> listofoptions = (List<WebElement>) templist;
			for (String str : testdataarr) {
				for (WebElement opt : listofoptions) {
					if (opt.getText().equalsIgnoreCase(str)) {
						icount = icount + 1;
						break;
					}
				}
			}
			if (icount == testdataarr.size())
				flag = true;
			if (flag) {
				if (report)
					BaseReporter.logPass("[" + testdata + "] is selected from " + ele.get(0), browser);
				else
					BaseReporter.logPass("[" + testdata + "] is selected from " + ele.get(0));
			} else
				BaseReporter.logFail("unable to locate [" + testdata + "] in List", browser);
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}

	}
	
	protected static void verifyPartialFromList(WebDriver browser, String testdata, ArrayList<?> ele) {
		verifyPartialFromList(browser, testdata, ele, false);
	}

	@SuppressWarnings("null")
	protected static void verifyPartialFromList(WebDriver browser, String testdata, ArrayList<?> ele, boolean report) {
		pageReady(browser);
		loadWaitValues();
		List<?> templist = (List<?>) ele.get(1);
		boolean check = false;
		int icount = 0;
		List<String> testdataarr = null;
		if (testdata.contains("||")) {
			testdataarr = Arrays.asList(testdata.split("\\|\\|"));
		} else {
			testdataarr.add(testdata);
		}

		try {
			for (int i = 0; i < count; i++) {
				if (templist.size() > 0)
					check = true;
				if (check)
					break;
				else {
					System.out.println("unable to find element. Retrying...");
					Thread.sleep(wait);
				}
			}

			boolean flag = false;
			@SuppressWarnings("unchecked")
			List<WebElement> listofoptions = (List<WebElement>) templist;
			for (String str : testdataarr) {
				for (WebElement opt : listofoptions) {
					if (opt.getText().contains(str)) {
						icount = icount + 1;
						break;
					}
				}
			}
			if (icount == testdataarr.size())
				flag = true;
			if (flag) {
				if (report)
					BaseReporter.logPass("[" + testdata + "] is selected from " + ele.get(0), browser);
				else
					BaseReporter.logPass("[" + testdata + "] is selected from " + ele.get(0));
			} else
				BaseReporter.logFail("unable to locate [" + testdata + "] in List", browser);
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}

	}

	protected static void verifyElementStatus(WebDriver browser, ArrayList<?> ele, String expectedstatus) {
		verifyElementStatus(browser, ele, expectedstatus, false);
	}

	protected static void verifyElementStatus(WebDriver browser, ArrayList<?> ele, String expectedstatus,
			boolean report) {
		try {
			WebElement element = getElement(browser, ele);
			highlight(browser, element);
			if (expectedstatus.equalsIgnoreCase("disabled")) {
				if (!element.isEnabled()) {
					if (report)
						BaseReporter.logPass(ele.get(0) + " element is disabled", browser);
					else
						BaseReporter.logPass(ele.get(0) + " element is disabled");
				} else {
					BaseReporter.logFail(ele.get(0) + " element is not disabled", (browser != null) ? browser : null);
				}
			} else if (expectedstatus.equalsIgnoreCase("enabled")) {
				if (element.isEnabled()) {
					if (report)
						BaseReporter.logPass(ele.get(0) + " element is enabled", browser);
					else
						BaseReporter.logPass(ele.get(0) + " element is enabled");
				} else
					BaseReporter.logFail(ele.get(0) + " element is disabled", (browser != null) ? browser : null);
			} else
				BaseReporter.logFail(
						"[" + expectedstatus + "] parameter doesnot match, Please pass \"enabled/disabled\"",
						(browser != null) ? browser : null);
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
	}

	protected static int monthDifference(int year, String month) {
		LocalDate today = LocalDate.now();
		LocalDate userday = LocalDate.of(year, Month.valueOf((month.toUpperCase())), 1);
		Period diff = Period.between(userday, today);
		System.out.println(diff.getMonths() + " Month()s\n");
		return diff.getMonths();
	}

	public static boolean fileExist(String path) {
		File file;
		boolean flag = false;
		try {
			file = new File(path);
			for (int i = 0; i < count; i++) {
				if (file.exists()) {
					flag = true;
					break;
				}
				System.out.println("Could not find the file. Retrying...");
				callForWait(2000);
			}
//			if (flag)
//				BaseReporter.logPass("File in [" + path + "] path is available");
//			else
//				BaseReporter.logFail("file is not available in [" + path + "] path");
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
		return flag;
	}

	public static void fileDelete(String path) {
		File file;
		try {
			file = new File(path);
			if (file.exists()) {
				if (file.delete())
					BaseReporter.logPass("File in [" + path + "] path is deleted");
			} else
				BaseReporter.logPass("File is not available for deletion in [" + path + "] path");
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
	}

	public static WebDriver switchWindow(WebDriver browser, int windowindex) {
		try {
			ArrayList<String> windowlist = new ArrayList<String>(browser.getWindowHandles());
			for (int i = 0; i < count; i++) {
				if (windowlist.size() > 1) {
					break;
				}
				callForWait(wait);
			}
			browser.switchTo().window(windowlist.get(windowindex));
			BaseReporter.logPass("Window exists", browser);
		} catch (Exception e) {
			BaseReporter.logFail("Window does not exist", browser);
		}
		return browser;
	}

	protected static String getText(WebDriver browser, ArrayList<?> ele) {
		pageReady(browser);
		boolean flag = false;
		String text = null;
		try {
			WebElement element = getElement(browser, ele);
			if (element.getAttribute("value") != null && element.getAttribute("type").equals("text")) {
				if (text == null || text == "")
					text = element.getAttribute("value");
				flag = true;
			}
			if (flag == false && element.getAttribute("innerHTML") != null) {
				if (text == null || text == "")
					text = element.getAttribute("innerHTML");
				flag = true;
			}
			if (flag == false && element.getText() != null && !element.getText().equals("")) {
				if (text == null || text == "")
					text = element.getText();
				flag = true;
			}
			if (flag == false && element.getAttribute("innerText") != null) {
				if (text == null || text == "")
					text = element.getAttribute("innerText");
				flag = true;
			}
		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
		BaseReporter.logPass("Text is received from " + ele.get(0), browser);
		return text;
	}

	@SuppressWarnings({ "unchecked" })
	protected static void getAllValueFromList(WebDriver browser, ArrayList<?> ele, String verifyList) {
		pageReady(browser);
		loadWaitValues();
		List<?> templist = (List<?>) ele.get(1);
		ArrayList<String> getListValues = new ArrayList<String>();
		List<String> verifyListValues = new ArrayList<String>();
		boolean flag = false;
		List<WebElement> listofoptions = null;
		boolean check = false;
		try {
			for (int i = 0; i < count; i++) {
				if (templist.size() > 0)
					check = true;
				if (check)
					break;
				else {
					System.out.println("unable to find element. Retrying...");
					Thread.sleep(wait);
				}
			}

			if (verifyList.contains("||")) {
				verifyListValues = Arrays.asList(verifyList.split("\\|\\|"));
			} else {
				verifyListValues.add(verifyList);
			}
			listofoptions = (List<WebElement>) templist;
			if (listofoptions != null) {
				flag = true;
				for (WebElement opt : listofoptions) {
					getListValues.add(opt.getText());
				}
				if (getListValues.equals(verifyListValues)) {
					if (flag) {
						BaseReporter.logPass(getListValues.toString() + " is matched in " + ele.get(0) + " Drop down",
								browser);
					}
				} else {
					BaseReporter.logFail(
							"Unable to match " + getListValues.toString() + " in " + ele.get(0) + " Drop down",
							browser);
				}
			}

		} catch (Exception e) {
			BaseReporter.logException(browser, e);
		}
	}
	
	protected static void verifyDelText(WebDriver browser, ArrayList<?> ele, String testdata, boolean report) {
		pageReady(browser);
		boolean flag = false;
		try {
			WebElement element = getElement(browser, ele);

			String actualValue;
			if (flag == false && element.getAttribute("value") != null && element.getAttribute("type").equals("text")) {
				actualValue = element.getAttribute("value");
				flag = textVerify(actualValue, testdata);
			}
			if (flag == false && element.getAttribute("innerHTML") != null) {
				actualValue = element.getAttribute("innerHTML");
				flag = textVerify(actualValue, testdata);
			}
			if (flag == false && element.getText() != null && !element.getText().equals("")) {
				actualValue = element.getText();
				flag = textVerify(actualValue, testdata);
			}
			if (flag == false && element.getAttribute("innerText") != null) {
				actualValue = element.getAttribute("innerText");
				flag = textVerify(actualValue, testdata);
			}
			if (!flag) {
				if (report)
					BaseReporter.logPass("\"" + testdata + "\" is not displayed on " + ele.get(0), browser);
				else
					BaseReporter.logPass("\"" + testdata + "\" is not displayed on " + ele.get(0));
			} else {
				BaseReporter.logFail("\"" + testdata + "\" is displayed on " + ele.get(0), browser);
			}
		} catch (

		Exception e) {
			BaseReporter.logException(browser, e);
		}
	}
	
}
