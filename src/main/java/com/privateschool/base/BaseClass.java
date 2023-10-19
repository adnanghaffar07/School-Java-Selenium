package com.privateschool.base;

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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.velocity.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.privateschool.listeners.ExtentManager;
import com.privateschool.listeners.TestFilterListener;
import com.privateschool.utilities.EnvSetup;
import com.privateschool.utilities.ModelEnvSetup;
import com.privateschool.utilities.ModelTests;
import com.privateschool.utilities.TestsDataConverter;
import com.privateschool.utilities.Utility;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass extends Utility {

	public static boolean closeDriver = false;
	public static RemoteWebDriver driver;
	public static WebDriverWait wait;
	public static String browser;
	public static String env;
	public static FileInputStream fis;
//	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExtentTest extentReport;
	public static SoftAssert softAssert;
	// public static ExtentReports rep = ExtentManager.getInstance();
	// This is to set default wait for every method
	public static Integer waitInSeconds = 5;

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

	public static final int defaultTimeForVisibility = 30;
	public static final int defaultTimeTOBeClickable = 30;
	public static final String AppUrl = "AppURL";
	private static String screenshotPath;
	private static String screenshotNam;
	public static String  envVal= PropertiesReader.getPropertyValue("env");

	Object[][] loginData = getData(testDataFile, testDataSheet, driver);
	protected String email = loginData[rowIndex][0].toString();
	protected String password = loginData[rowIndex][1].toString();

	// Test Suite Runner File Name
	public static final String testSuiteRunnerFileName = "SuiteTests_Web";
	// Test Suite Runner Sheet Name
	public static final String testSuiteRunnerSheetName = "Tests";
	// HashMap of All Method Names for Retry
	public static HashMap <String, Integer> methodNamelist = new HashMap<>();

	public static ThreadLocal<RemoteWebDriver> dr = new ThreadLocal<RemoteWebDriver>();

	public static WebDriver getDriver() {
		return dr.get();
	}

	public static void setWebDriver(RemoteWebDriver driver) {
		dr.set(driver);
	}

	public static ExtentTest test;
	private static ExtentReports extent = ExtentManager
			.createInstance(System.getProperty("user.dir") + "/reports/" + "Web Automation Test Report.html");

	public static WebDriver initConfiguration() {
		WebDriver localD = null;
		String osName = System.getProperty("os.name");
		if (osName.contains("Windows")) {
			excelFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\";
			imagePath = System.getProperty("user.dir") + "\\src\\test\\resources\\images\\";
		} else {
			excelFilePath = System.getProperty("user.dir") + "/src/test/resources/data/";
			imagePath = System.getProperty("user.dir") + "/src/test/resources/images/";
		}
		softAssert = new SoftAssert();

		if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
			browser = System.getenv("browser");
		} else {
			browser = PropertiesReader.getPropertyValue("browser");
		}
		if (System.getenv("env") != null && !System.getenv("env").isEmpty()) {
			env = System.getenv("env");
		} else {
			env = PropertiesReader.getPropertyValue("env");
		}
		if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			localD = new FirefoxDriver();
		} else if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			String agentString = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36";
			options.addArguments("--user-agent=" + agentString);
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-infobars");
//			options.addArguments("window-size=1920,1080");
//			options.addArguments("--headless");
			options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
			options.addArguments("--disable-gpu");
			options.addArguments("enable-automation");
			options.addArguments("--dns-prefetch-disable");
			options.addArguments("--disable-browser-side-navigation");
			options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			try {
				localD = new ChromeDriver(options);
			} catch (Exception e) {
				e.printStackTrace();
			}
			localD.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
			localD.manage().timeouts().pageLoadTimeout(60L, TimeUnit.SECONDS);
			localD.manage().timeouts().setScriptTimeout(30L, TimeUnit.SECONDS);
			localD.manage().window().maximize();
		} else if (browser.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			WebDriverManager.safaridriver();
			localD = new EdgeDriver();
		}else if (browser.equals("safari")) {
			WebDriverManager.safaridriver().setup();
			localD = new SafariDriver();
		}else if (browser.equals("chromium")) {
			WebDriverManager.chromiumdriver().setup();
			WebDriverManager.operadriver().setup();
			localD = new ChromeDriver();
		}else if (browser.equals("opera")) {
			WebDriverManager.operadriver().setup();
			localD = new OperaDriver();
		}
		localD.manage().timeouts().pageLoadTimeout(10, TimeUnit.MINUTES);
		return localD;
	}

	public static String getTimeStamp() {
		return new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()).replaceAll("[-: ]", "_");
	}


	public static void AddTest_IntoReport(String TestName, ArrayList<String> test_steps, WebDriver localDriver) {
		test = extent.createTest(TestName);
		for (int i = 0; i < test_steps.size(); i++) {

			if (test_steps.get(i).contains("Failed") || test_steps.get(i).contains("Assertion")) {
				if (test_steps.get(i).toLowerCase().contains("screenshot")) {
					printString("Name" + test_steps.get(i));
					try {

						test.log(Status.FAIL, "<b>" + "<font color=" + "black>" + "Screenshot" + "</font>" + "</b>",
								MediaEntityBuilder.createScreenCaptureFromPath(test_steps.get(i)).build());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					test.log(Status.FAIL, test_steps.get(i));

				}
			} else {
				if (test_steps.get(i).toLowerCase().contains("screenshot")) {
					printString("Name" + test_steps.get(i));
					try {
						test.log(Status.PASS, "<b>" + "<font color=" + "black>" + "Screenshot" + "</font>" + "</b>",
								MediaEntityBuilder.createScreenCaptureFromPath(test_steps.get(i)).build());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					test.log(Status.PASS, test_steps.get(i));

				}
			}
		}

		test_steps.clear();
        closeBrowser(localDriver);

	}

	public static String getScreenshotPath() {
		Date d = new Date();
		return screenshotNam = "ScreenShot" + d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
	}

	public static void captureCapture(WebDriver localDriver, String screen) {

		File scrFile = ((TakesScreenshot) localDriver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		screenshotNam = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/reports/" + screen));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}


	public static void UtilitycaptureScreenshot() throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		UtilityscreenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		FileUtils.copyFile(scrFile,
				new File(System.getProperty("user.dir") + "/target/surefire-reports/html/" + UtilityscreenshotName));

	}
	

	@AfterSuite
	public static void afterSuiteFlush() {
		extent.flush();
	}

}
