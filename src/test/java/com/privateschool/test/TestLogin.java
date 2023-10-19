package com.privateschool.test;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.privateschool.base.BaseClass;
import com.privateschool.base.PropertiesReader;
import com.privateschool.listeners.RetryListener;
import com.privateschool.pages.LoginPage;

public class TestLogin extends BaseClass {
	String tempSrc = "";

	@Test(priority = 1)
	public void VerifyLogin_ValidCredential() throws IOException {
		WebDriver driver = null;
		ArrayList<String> testSteps = new ArrayList<>();
		LoginPage lp;

		driver = initConfiguration();

		printString("VerifyLogin_ValidCredential:" + driver.hashCode() + "");
		lp = new LoginPage(driver);
		
		String email = PropertiesReader.getPropertyValue("appUser");
		String pass = PropertiesReader.getPropertyValue("appPass");
		int steps = 0;
		try {

			testSteps.add("Step "+(++steps)+" : Visit app url");
			navigateToURL(PropertiesReader.getPropertyValue("appURL"), driver);
			
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			
			testSteps.add("Step "+(++steps)+" : Entering Email: "+email);
			lp.enterUserName(driver, email);
			
			testSteps.add("Step "+(++steps)+" : Entering Password: "+pass);
			lp.enterPassword(driver, pass);
			
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			
			testSteps.add("Step "+(++steps)+" : Click On Login Button");
			lp.clickOnLoginButton(driver);
			
			testSteps.add("Step "+(++steps)+" : Verifying <b>'User Successfully Logged in'</b>");
			assertTrue(lp.isDashboardDisplaying(driver),"User is not able to Logged in successfully");
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			testSteps.add("Step "+(++steps)+" : Verified: <b>'User Successfully Logged in'</b>");

			testSteps.add("Step "+(++steps)+" : Close the Browser");
			AddTest_IntoReport("VerifyLogin_ValidCredential", testSteps, driver);

		} catch (Exception e) {
			
			testSteps.add("Failed: VerifyLogin_ValidCredential " + "<br><b>Exception:</b><br> " + e.toString());
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			if(BaseClass.methodNamelist.get("VerifyLogin_ValidCredential") == RetryListener.maxRetryCnt) {
			AddTest_IntoReport("VerifyLogin_ValidCredential", testSteps, driver);}
			else {closeBrowser(driver);}
			assertTrue(false);
		} catch (Error e) {
			
			testSteps.add("Failed: VerifyLogin_ValidCredential " + "<br><b>Error:</b><br> " + e.toString());
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			if(BaseClass.methodNamelist.get("VerifyLogin_ValidCredential") == RetryListener.maxRetryCnt) {
			AddTest_IntoReport("VerifyLogin_ValidCredential", testSteps, driver);}
			else {closeBrowser(driver);}
			assertTrue(false);
		}
	}

	@Test(priority = 2)
	public void VerifyLogin_InvalidCredential() throws IOException {
		WebDriver driver = null;
		ArrayList<String> testSteps = new ArrayList<>();
		LoginPage lp;

		driver = initConfiguration();

		printString("VerifyLogin_InvalidCredential:" + driver.hashCode() + "");
		lp = new LoginPage(driver);
		
		String email = PropertiesReader.getPropertyValue("invalid_appUser");
		String pass = PropertiesReader.getPropertyValue("invalid_appPass");
		int steps = 0;
		try {

			testSteps.add("Step "+(++steps)+" : Visit app url");
			navigateToURL(PropertiesReader.getPropertyValue("appURL"), driver);
			
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			
			testSteps.add("Step "+(++steps)+" : Entering Invalid Email: "+email);
			lp.enterUserName(driver, email);
			
			testSteps.add("Step "+(++steps)+" : Entering Invalid Password: "+pass);
			lp.enterPassword(driver, pass);
			
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			
			testSteps.add("Step "+(++steps)+" : Click On Login Button");
			lp.clickOnLoginButton(driver);
			
			testSteps.add("Step "+(++steps)+" : Verifying <b>'User or password is not correct'</b> is Displaying");
			assertTrue(lp.isInvalidErrorMessageDisplaying(driver),"<b>'User or password is not correct'</b> is not Displaying");
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			testSteps.add("Step "+(++steps)+" : Verified: <b>'User or password is not correct'</b> is Displaying");
			
			testSteps.add("Step "+(++steps)+" : Close the Browser");
			AddTest_IntoReport("VerifyLogin_InvalidCredential", testSteps, driver);

		} catch (Exception e) {
			
			testSteps.add("Failed: VerifyLogin_InvalidCredential " + "<br><b>Exception:</b><br> " + e.toString());
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			if(BaseClass.methodNamelist.get("VerifyLogin_InvalidCredential") == RetryListener.maxRetryCnt) {
			AddTest_IntoReport("VerifyLogin_InvalidCredential", testSteps, driver);}
			else {closeBrowser(driver);}
			assertTrue(false);
		} catch (Error e) {
			
			testSteps.add("Failed: VerifyLogin_InvalidCredential " + "<br><b>Error:</b><br> " + e.toString());
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			if(BaseClass.methodNamelist.get("VerifyLogin_InvalidCredential") == RetryListener.maxRetryCnt) {
			AddTest_IntoReport("VerifyLogin_InvalidCredential", testSteps, driver);}
			else {closeBrowser(driver);}
			assertTrue(false);
		}
	}


}
