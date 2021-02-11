package com.cei.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.cei.reporter.BaseReporter;

public class WebTable extends Page {

	int rowcount;
	int rowno, columnno;
	List<WebElement> tableRows;
	List<WebElement> rowData;
	String rowpath, columnpath;
//	ArrayList<Integer> rownoarr = null;
//	ArrayList<Integer> colnoarr = null;

	void getPath(String tableformat) {
		if (tableformat.equalsIgnoreCase("usersearch")) {
			rowpath = "//div[@class='rt-tr-group']";
			columnpath = ".//div[@class='rt-td']";
		} else {
			rowpath = "//tbody/tr";
			columnpath = "/.//td";
		}
	}

	void fetchRow(WebElement table) {

		tableRows = table.findElements(By.xpath(rowpath));
		rowcount = tableRows.size();
	}
	
	boolean findRow(String cellvalue) {
		boolean found = false;
		outerloop: for (int row = 0; row < rowcount; row++) {
			List<WebElement> columndata = tableRows.get(row).findElements(By.xpath(columnpath));
			for (int cell = 0; cell < columndata.size(); cell++) {
				String celldata;
				boolean flag = false;
				WebElement cellElement = columndata.get(cell);

				if (flag == false && cellElement.getAttribute("value") != null
						&& cellElement.getAttribute("type").equals("text")) {
					celldata = cellElement.getAttribute("value");
					flag = textPartialVerify(celldata, cellvalue);
				}
				if (flag == false && cellElement.getAttribute("innerHTML") != null) {
					celldata = cellElement.getAttribute("innerHTML");
					flag = textPartialVerify(celldata, cellvalue);
				}
				if (flag == false && cellElement.getText() != null && !cellElement.getText().equals("")) {
					celldata = cellElement.getText();
					flag = textPartialVerify(celldata, cellvalue);
				}
				if (flag == false && cellElement.getAttribute("innerText") != null) {
					celldata = cellElement.getAttribute("innerText");
					flag = textPartialVerify(celldata, cellvalue);
				}

				if (flag) {
					rowno = row;
					columnno = cell;
					found = true;
					break outerloop;
				}
			}
		}
		return found;
	}

	boolean findNextRow(String cellvalue) {
		boolean found = false;
		outerloop: for (int row = rowno; row < rowcount; row++) {
			List<WebElement> columndata = tableRows.get(row).findElements(By.xpath(columnpath));
			for (int cell = columnno + 1; cell < columndata.size(); cell++) {
				String celldata;
				boolean flag = false;
				WebElement cellElement = columndata.get(cell);

				if (flag == false && cellElement.getAttribute("value") != null
						&& cellElement.getAttribute("type").equals("text")) {
					celldata = cellElement.getAttribute("value");
					flag = textPartialVerify(celldata, cellvalue);
				}
				if (flag == false && cellElement.getAttribute("innerHTML") != null) {
					celldata = cellElement.getAttribute("innerHTML");
					flag = textPartialVerify(celldata, cellvalue);
				}
				if (flag == false && cellElement.getText() != null && !cellElement.getText().equals("")) {
					celldata = cellElement.getText();
					flag = textPartialVerify(celldata, cellvalue);
				}
				if (flag == false && cellElement.getAttribute("innerText") != null) {
					celldata = cellElement.getAttribute("innerText");
					flag = textPartialVerify(celldata, cellvalue);
				}

				if (flag) {
					rowno = row;
					columnno = cell;
					found = true;
					break outerloop;
				}
			}
		}
		return found;
	}

	public boolean matchData(WebElement table, String expecteddata, String tableformat) throws InterruptedException {
		getPath(tableformat);
		return matchTableData(table, expecteddata);
	}

	public boolean matchTableData(WebElement table, String expecteddata) throws InterruptedException {

		List<String> testdataarr = new ArrayList<String>();
		try {
			if (expecteddata.contains("||")) {
				testdataarr = Arrays.asList(expecteddata.split("\\|\\|"));

			} else {
				testdataarr.add(expecteddata);
			}

			fetchRow(table);
			if (!findRow(testdataarr.get(0))) {
				System.out.println("unable to find row");
				return false;
			}

			do {
				int count = 0;
				rowData = tableRows.get(rowno).findElements(By.xpath(columnpath));
				for (int c = columnno; c < rowData.size(); c++) {

					boolean flag = false;
					WebElement cellElement = rowData.get(c);
					String celldata = null;
					if (count == testdataarr.size())
						break;
					else {
						if (flag == false && cellElement.getAttribute("value") != null
								&& cellElement.getAttribute("type").equals("text")) {
							celldata = cellElement.getAttribute("value");
							flag = textPartialVerify(celldata, testdataarr.get(count));
						}
						if (flag == false && cellElement.getAttribute("innerHTML") != null) {
							celldata = cellElement.getAttribute("innerHTML");
							flag = textPartialVerify(celldata, testdataarr.get(count));
						}
						if (flag == false && cellElement.getText() != null && !cellElement.getText().equals("")) {
							celldata = cellElement.getText();
							flag = textPartialVerify(celldata, testdataarr.get(count));
						}
						if (flag == false && cellElement.getAttribute("innerText") != null) {
							celldata = cellElement.getAttribute("innerText");
							flag = textPartialVerify(celldata, testdataarr.get(count));
						}
					}
					if (flag) {
						count++;
						continue;
					} else {
						break;
					}
				}
				if (count == testdataarr.size())
					return true;
			} while (findNextRow(testdataarr.get(0)));
		} catch (StaleElementReferenceException e) {
			System.out.println("Stale Exception");
			Thread.sleep(3000);
			return false;
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}

		return false;
	}

	public boolean clickLink(WebDriver browser, WebElement table, String testdata, String linkpath, String tableformat)
			throws InterruptedException {
		getPath(tableformat);
		return clickTableLink(browser, table, testdata, linkpath);
	}

	
	public boolean clickTableLink(WebDriver browser, WebElement table, String testdata, String linkpath)
			throws InterruptedException {

		WebElement link = null;
		List<String> testdataarr = new ArrayList<String>();
		if (testdata.contains("||")) {
			testdataarr = Arrays.asList(testdata.split("\\|\\|"));
		} else {
			testdataarr.add(testdata);
		}
		List<WebElement> userDetaillink = null;
		try {

			fetchRow(table);
			if (!findRow(testdataarr.get(0))) {
				System.out.println("unable to find row");
				return false;
			}

			outer : do {
				int count = 0;
				rowData = tableRows.get(rowno).findElements(By.xpath(columnpath));
				for (int c = columnno; c < rowData.size(); c++) {

					boolean flag = false;
					WebElement cellElement = rowData.get(c);
					String celldata = null;
					if (count == testdataarr.size())
						break;
					else {
						if (flag == false && cellElement.getAttribute("value") != null
								&& cellElement.getAttribute("type").equals("text")) {
							celldata = cellElement.getAttribute("value");
							flag = textPartialVerify(celldata, testdataarr.get(count));
						}
						if (flag == false && cellElement.getAttribute("innerHTML") != null) {
							celldata = cellElement.getAttribute("innerHTML");
							flag = textPartialVerify(celldata, testdataarr.get(count));
						}
						if (flag == false && cellElement.getText() != null && !cellElement.getText().equals("")) {
							celldata = cellElement.getText();
							flag = textPartialVerify(celldata, testdataarr.get(count));
						}
						if (flag == false && cellElement.getAttribute("innerText") != null) {
							celldata = cellElement.getAttribute("innerText");
							flag = textPartialVerify(celldata, testdataarr.get(count));
						}
					}
					if (flag) {
						count++;
						continue;
					} else {
						break;
					}
				}
				if (count == testdataarr.size()) {
					for (int col = 0; col < rowData.size(); col++) {
						userDetaillink = rowData.get(col).findElements(By.xpath(linkpath));
						if (userDetaillink.size() == 0) {
							continue;
						} else if (userDetaillink.size() > 0) {
							link = userDetaillink.get(0);
							break outer;
						}
					}
				}
			} while (findNextRow(testdataarr.get(0)));

			if (link == null)
				return false;
			else if (link.getAttribute("disabled") == null) {
				link.click();
				return true;
			} 
//			else
//				BaseReporter.logFail("Link is not clickable");

		} catch (StaleElementReferenceException e) {
			System.out.println("Stale Exception");
			Thread.sleep(3000);
			return false;
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
		return true;
	}

}
