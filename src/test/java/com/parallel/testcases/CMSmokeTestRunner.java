package com.parallel.testcases;

import java.util.List;

import org.testng.TestNG;
import org.testng.collections.Lists;


public class CMSmokeTestRunner {

		public static void main(String[] args) throws Exception {
		    System.out.println("Started!");
		    TestNG testng = new TestNG();
		    List<String> suites = Lists.newArrayList();
		    suites.add(".//testngParallel.xml");
		    testng.setTestSuites(suites);
		    testng.setUseDefaultListeners(false);
		    testng.run();
		}
}
