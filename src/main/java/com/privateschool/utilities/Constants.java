package com.privateschool.utilities;

public class Constants {
	
	////////////////////////////////
	// BaseClass
	//////////////////////////////

	// This is the default path to data package
	public static String excelFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\";

	// This is the default path to imageUpload
	public static String imagePath = System.getProperty("user.dir") + "\\src\\test\\resources\\images\\";
	public static String UtilityscreenshotPath;
	public static String UtilityscreenshotName;
	// This is column name from which we need to get row
	public static final String colName = "env";
	// This is row index of environment column from which we need to get data
	public static int rowIndex = 0;
	// Excel file name
	public static final String testDataFile = "testData";
	// Excel sheetname
	public static final String testDataSheet = "TestData";
	public static final String KYC_ApprovedFundedAccountVest = "KYC_ApprovedFundedAccountVest";

	public static final int defaultTimeForVisibility = 30;
	public static final int defaultTimeTOBeClickable = 30;
	public static final String AppUrl = "AppURL";
	// Test Suite Runner File Name
	public static final String testSuiteRunnerFileName = "SuiteTests_Web";
	// Test Suite Runner Sheet Name
	public static final String testSuiteRunnerSheetName = "Tests";
	

}
