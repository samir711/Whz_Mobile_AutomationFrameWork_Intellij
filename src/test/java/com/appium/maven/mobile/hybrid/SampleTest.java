package com.appium.maven.mobile.hybrid;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.appium.maven.mobile.hybrid.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SampleTest {

	private ExtentReports report;
	private Driverscript ds;
	private ExtentTest test;
	
	@Test
	public void testApp( ) {
		

		report = ExtentManager.getInstance();
		test = report.startTest("Test App");
		test.log(LogStatus.INFO, "Starting the test");
		ds = new Driverscript(test);
		try {
		ds.executekeyword("ContactManager");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		test.log(LogStatus.INFO, "Ending the test");

	}
	
	@AfterMethod
	public void quit() {
		if(null!=ds)
		ds.getKeywords().closeapp();

		if(null!=report) {
		report.endTest(test);
		report.flush();

		}

	}

}
