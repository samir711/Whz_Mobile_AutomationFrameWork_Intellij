package com.appium.maven.mobile.hybrid;

import com.appium.maven.mobile.hybrid.util.ProjectConstants;
import com.relevantcodes.extentreports.ExtentTest;

public class AppKeyword extends GenericKeyword {
	
	public AppKeyword(ExtentTest test) {
		super(test);

	}


	public void addContact() {
		System.out.println("Adding Contact");
	}


}
