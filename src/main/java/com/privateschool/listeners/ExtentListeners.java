package com.privateschool.listeners;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.privateschool.base.BaseClass;
import com.privateschool.base.PropertiesReader;
import com.privateschool.utilities.EnvSetup;
import com.privateschool.utilities.ModelEnvSetup;
import com.privateschool.utilities.ReportUtils;
import com.privateschool.utilities.SendEmail;
import com.privateschool.utilities.SlackUtils;
import com.privateschool.utilities.ZipUtils;
import java.awt.Desktop;

public class ExtentListeners extends BaseClass implements ITestListener,ISuiteListener {

	
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onTestSuccess(ITestResult result) {
		printString(result.getTestName()+ "Test Passed");
	}

	public void onTestFailure(ITestResult result) {
		printString(result.getTestName()+ " Test Failed");		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ISuite suite) {
		
		try {
			FileUtils.cleanDirectory(new File(System.getProperty("user.dir")+"/reports/"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onFinish(ISuite suite) {
		String emailBody = null;
		ZipUtils.generateZipFile();
 		try {
 			emailBody =  testResult(suite);
		} catch (Exception e) {System.out.println("Error: "+e.getMessage());}
 		
 		openReportFileInDefaultBrowser();
 	    
	}	
	public String testResult(ISuite suite) {
		Map<String, ISuiteResult> getResults = suite.getResults();
 		ISuiteResult iSuiteResult = getResults.get(getResults.keySet().toArray()[0]);
 		ITestContext iTestContext = iSuiteResult.getTestContext();
 		String nameString = iTestContext.getName();
 		int pass = iTestContext.getPassedTests().size();
 		int fail = iTestContext.getFailedTests().size();
 		
 		//Failed Test Cases
 		Object[]FailedTest =  iTestContext.getFailedTests().getAllMethods().toArray() ;
 		String FailedTestCases =  "-------------------------------------\nFailed Test Cases: ";
 		for(int i = 0;i<FailedTest.length;i++) {
 			String ClassName = FailedTest[i].toString().split("\\(")[0];
 			String MethodName = ClassName.toString().split("\\.")[1];
 			FailedTestCases = FailedTestCases+"\n-> "+MethodName;
 		}
 		FailedTestCases = FailedTestCases+"\n-------------------------------------\n";
 		
 		//Passed Test Cases
 		Object[]PassedTest =  iTestContext.getPassedTests().getAllMethods().toArray() ;
 		String PassedTestCases =  "-------------------------------------\nPassed Test Cases: ";
 		for(int i = 0;i<PassedTest.length;i++) {
 			String ClassName = PassedTest[i].toString().split("\\(")[0];
 			String MethodName = ClassName.toString().split("\\.")[1];
 			PassedTestCases = PassedTestCases+"\n-> "+MethodName;
 		}
 		PassedTestCases = PassedTestCases+"\n-------------------------------------\n";
 		int skip = (TestFilterListener.methodNamelist.size()-(pass+fail));
 		int total = pass+fail+skip;
 		String emailBody = "=============================================================\n"+
 							nameString+"("+PropertiesReader.getPropertyValue("env").toUpperCase()+")"+"\n"+
 							"Tests Run: "+total+", Passed: "+pass+", Failures: "+fail+", Skipped: "+skip+"\n"+FailedTestCases+"\n"+PassedTestCases+"\n"+
 							"=============================================================\n";
 		System.out.print(emailBody);
 		return emailBody;
	}
 

	public void openReportFileInDefaultBrowser() {
		// Specify the path to the HTML file you want to open
        String htmlFilePath = System.getProperty("user.dir")+File.separator+"reports"+File.separator+"Web Automation Test Report.html";

        // Create a File object representing the HTML file
        File htmlFile = new File(htmlFilePath);

        // Check if Desktop is supported on the current platform
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();

            // Check if the HTML file exists
            if (htmlFile.exists()) {
                try {
                    // Open the HTML file with the default web browser
                    desktop.open(htmlFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("HTML file does not exist.");
            }
        } else {
            System.out.println("Desktop is not supported on this platform.");
        }
	}
}
