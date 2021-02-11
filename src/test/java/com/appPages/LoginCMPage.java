package com.appPages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.cei.reporter.BaseReporter;

public class LoginCMPage {
	
	WebDriver driver;

	public LoginCMPage(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		BaseReporter.logPagNav("Login Page");
	}
	public void CMLogin() {
		
		
	}
	

}
