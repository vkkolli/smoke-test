package com.cei.Baseconfig;

public class Report_Prop extends Base_Prop{

	static String module = "Report";
	
	public static String getauthor() {
		return getValue(module, "author");
	}
	
	public static String getapplication() {
		return getValue(module, "application");
	}
	
	public static String getproject() {
		return getValue(module, "project");
	}

}	
	
	