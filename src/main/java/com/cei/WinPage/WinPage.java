package com.cei.WinPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.cei.reporter.BaseReporter;

import io.appium.java_client.windows.WindowsDriver;

public class WinPage {
	
	public static void open(String cmd) throws IOException {
		String[] cmdArray = cmd.split(" ");
		Runtime.getRuntime().exec(cmdArray, null);
	}
	
	public static void close(WindowsDriver<?> driver) {

		driver.closeApp();
		BaseReporter.logPass("Application is closed");
	}
	
	protected static void Click(WindowsDriver<?> driver, List<?> ele) {

		try {
			((WebElement) ele.get(1)).click();
			BaseReporter.logPass(ele.get(0) + " is clicked");
		} catch (Exception e) {
			BaseReporter.logException(driver, e);
		}
	}
	
	protected static List<Object> elementList(String elementname, WebElement element) {
		List<Object> list = new ArrayList<Object>();
		list.clear();
		list.add(elementname);
		list.add(element);
		return list;
	}

	protected static List<Object> elementList(String elementname, List<?> webElementlist) {
		List<Object> list = new ArrayList<Object>();
		list.clear();
		list.add(elementname);
		list.add(webElementlist);
		return list;
	}
}
