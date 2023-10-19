package com.privateschool.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelEnvSetup {

	 private String testEnvironment;
	 private String browser;

	 @JsonProperty("TestEnvironment")
	 public String getTestEnvironment() { return testEnvironment.toLowerCase(); }
	 @JsonProperty("TestEnvironment")
	 public void setTestEnvironment(String value) { this.testEnvironment = value; }

	 @JsonProperty("Browser")
	 public String getBrowser() { return browser.toLowerCase(); }
	 @JsonProperty("Browser")
	 public void setBrowser(String value) { this.browser = value; }
}
