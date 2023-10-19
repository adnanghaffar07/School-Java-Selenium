package com.privateschool.pages;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.privateschool.base.BaseClass;
import com.privateschool.base.PropertiesReader;
import com.privateschool.errorCollectors.ErrorCollector;

public class LoginPage extends BaseClass {

	private WebDriver podriver = null;

	@FindBy(xpath = ("//input[@name='UserID1']"))
	WebElement userInput;
	
	@FindBy(xpath = ("//input[@name='Pass1']"))
	WebElement passInput;
	
	@FindBy(xpath = ("(//button[text()='Login'])[1]"))
	WebElement staffLoginButton;
	
	@FindBy(xpath = ("//span[text()='Dashboard']"))
	WebElement dashboardTab;
	
	@FindBy(xpath = ("//div[text()='User or password is not correct']"))
	WebElement invalidErrorMessage;
	
	
	


	public LoginPage(WebDriver driverParam) {
		this.podriver = driverParam;
		PageFactory.initElements(this.podriver, this);
	}


	public void enterUserName(WebDriver driver, String user) {
		type(userInput, user, driver);
	}
	
	public void enterPassword(WebDriver driver, String pass) {
		type(passInput, pass, driver);
	}


	public void clickOnLoginButton(WebDriver driver) {
		click(staffLoginButton, driver);
	}
	
	public Boolean isDashboardDisplaying(WebDriver driver) {
		try {
			waitForElementVisibility(dashboardTab, defaultTimeForVisibility, driver);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public Boolean isInvalidErrorMessageDisplaying(WebDriver driver) {
		try {
			waitForElementVisibility(invalidErrorMessage, defaultTimeForVisibility, driver);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	
}
