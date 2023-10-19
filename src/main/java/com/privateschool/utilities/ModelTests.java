package com.privateschool.utilities;

import com.fasterxml.jackson.annotation.*;

public class ModelTests {
	private long id;
    private String testName;
    private boolean isExcuted;
    private String testStatus;
    private String lastExecutionDate;
    private String testJiraLink;
    private String environment;
    private String browser;
    private Object executionCompleted;

    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }

    @JsonProperty("testName")
    public String getTestName() { return testName; }
    @JsonProperty("testName")
    public void setTestName(String value) { this.testName = value; }

    @JsonProperty("isExcuted")
    public boolean getIsExcuted() { return isExcuted; }
    @JsonProperty("isExcuted")
    public void setIsExcuted(boolean value) { this.isExcuted = value; }

    @JsonProperty("testStatus")
    public String getTestStatus() { return testStatus; }
    @JsonProperty("testStatus")
    public void setTestStatus(String value) { this.testStatus = value; }

    @JsonProperty("lastExecutionDate")
    public String getLastExecutionDate() { return lastExecutionDate; }
    @JsonProperty("lastExecutionDate")
    public void setLastExecutionDate(String value) { this.lastExecutionDate = value; }

    @JsonProperty("testJiraLink")
    public String getTestJiraLink() { return testJiraLink; }
    @JsonProperty("testJiraLink")
    public void setTestJiraLink(String value) { this.testJiraLink = value; }

    @JsonProperty("environment")
    public String getEnvironment() { return environment; }
    @JsonProperty("environment")
    public void setEnvironment(String value) { this.environment = value; }

    @JsonProperty("browser")
    public String getBrowser() { return browser; }
    @JsonProperty("browser")
    public void setBrowser(String value) { this.browser = value; }

    @JsonProperty("executionCompleted")
    public Object getExecutionCompleted() { return executionCompleted; }
    @JsonProperty("executionCompleted")
    public void setExecutionCompleted(Object value) { this.executionCompleted = value; }
}
