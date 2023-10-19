package com.privateschool.listeners;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.privateschool.base.BaseClass;

public class RetryListener implements IRetryAnalyzer {
    //You could mentioned maxRetryCnt (Maximiun Retry Count) as per your requirement. Here I took 2, If any failed testcases then it runs two times
    public static int maxRetryCnt = 1;
    //This method will be called everytime a test fails. It will return TRUE if a test fails and need to be retried, else it returns FALSE
    public boolean retry(ITestResult result) {

//    	System.out.println("Value :"+ BaseClass.methodNamelist.get(result.getName()));
    	 if (BaseClass.methodNamelist.get(result.getName()) < maxRetryCnt) {
             System.out.println("Retrying " + result.getName() + " again and the count is " + (BaseClass.methodNamelist.get(result.getName()) +1));
             BaseClass.methodNamelist.put(result.getName(), BaseClass.methodNamelist.get(result.getName()) +1);
             return true;
         }
        return false;
    }
}
