package com.cei.parallel.driver;

import java.util.ArrayList;

public class ListofDriver {

	
	static ArrayList<String> addBrowserList() {
		
		ArrayList<String> browserlist = new ArrayList<String>();
		browserlist.add("windows.app");
		
		browserlist.add("local.win.chrome");
		browserlist.add("local.win.firefox");
		browserlist.add("local.mac.safari");
		browserlist.add("local.mac.chrome");
		
		browserlist.add("remote.mac.safari");
		browserlist.add("remote.mac.chrome");
		browserlist.add("remote.win.chrome");
		browserlist.add("remote.win.firefox");
		
		browserlist.add("android.chrome");
		browserlist.add("ios.safari");
		browserlist.add("ios.chrome");
		
		return browserlist;
	}
	
	
	public static String browserListInReport() {
		ArrayList<String> browserlist = addBrowserList();
		String report = "";
		for(int i=0; i<browserlist.size(); i++) {
			report = "<i>" + report + browserlist.get(i) + "</i><br>";
		}
		return report;
	}
}
