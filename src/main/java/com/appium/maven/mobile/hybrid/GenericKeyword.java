package com.appium.maven.mobile.hybrid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import com.appium.maven.mobile.hybrid.util.ProjectConstants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class GenericKeyword {

	private Properties prop;
	private FileInputStream fs;
	public static AndroidDriver<MobileElement> driver;
	ExtentTest test;
	
	public GenericKeyword(ExtentTest test) {
		this.test=test;
	}
	
	public String openapp() {
		prop=new Properties();
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties";
		try {
			fs=new FileInputStream(path);
		} catch (FileNotFoundException e) {
			reportFailure(e.getMessage());
			return ProjectConstants.FAIL;
		}
		try {
			prop.load(fs);
		} catch (IOException e) {
			reportFailure(e.getMessage());
			return ProjectConstants.FAIL;
		}
		File app=new File(prop.getProperty("apkPath"));
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName",prop.getProperty("deviceName"));
		caps.setCapability("udid", prop.getProperty("udid")); // Give Device ID of your
		caps.setCapability("platformName",prop.getProperty("platformName"));
		caps.setCapability("platformVersion", prop.getProperty("platformVersion"));
		caps.setCapability("app", app.getAbsolutePath());
		caps.setCapability("autoGrantPermissions", "true");
		caps.setCapability("noReset", "true");
		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);

		} catch (MalformedURLException e) {
			test.log(LogStatus.FAIL, ""+e.getMessage());
			System.out.println(e.getMessage());
		}
		
		test.log(LogStatus.PASS, ""+"SUCCESS");
		return ProjectConstants.PASS;
	}
	
	public void click(String locatorkey)  {

		getElement(locatorkey).click();
		System.out.println("clicking::"+locatorkey);
	}
	
	public void type(String locatorkey,String data) {
		getElement(locatorkey).sendKeys(data);
	}
	
	public void verifyTest(String locator,String expectedText) {

		System.out.println("verify test");
	}
	
	public void closeapp() {
		if(null!=driver) {
			driver.quit();
		}
		
	}
	
	public MobileElement getElement(String locatorkey) {
		MobileElement e=null;
		try {
		if(locatorkey.endsWith("_xpath"))
			e= driver.findElement(By.xpath(prop.getProperty(locatorkey)));
		else if(locatorkey.endsWith("_id"))
			e= driver.findElement(By.id(prop.getProperty(locatorkey)));
		else
			reportFailure("unable to find element");
		}catch (Exception exception) {
			reportFailure("unable to find element");
		}
		return e;
		
	}
	
	/**************************reporting functions***********************************/
	public void reportFailure(String failureMsg){
		//take screenshot
		takeScreenshot();
		test.log(LogStatus.FAIL, failureMsg);
		Assert.fail(failureMsg);
	}
	
	public void reportPass(String passMsg){
		//take screenshot
		takeScreenshot();
		test.log(LogStatus.PASS, passMsg);
	}
	
	public void takeScreenshot(){
		Date d = new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ","_")+".png";
		String path=ProjectConstants.REPORT_PATH+"screenshots\\"+screenshotFile;

		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.INFO,"Snapshot below: ("+screenshotFile+")"+
				 test.addScreenCapture(path));
	}
	

}
