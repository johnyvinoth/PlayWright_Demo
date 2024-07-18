package com.example.tests;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainRunner {
    static TestNG testng;

    public static void main(String[] args) {
        testng = new TestNG();
//        String path = System.getProperty("user.dir") + "/src/test/resources/testng.xml";
        String testngXmlPath = getTestNgPath();

        // Load testng.xml from the classpath
        InputStream inputStream = MainRunner.class.getResourceAsStream(testngXmlPath);
        if (inputStream == null) {
            throw new RuntimeException("Cannot find " + testngXmlPath + " in classpath");
        }
        System.out.println("TestNG XML path: " + testngXmlPath);

        List<String> testngXmlList = new ArrayList<>();
        testngXmlList.add(testngXmlPath);
        testng.setTestSuites(testngXmlList);  // Using XML file instead of setting test classes directly

        testng.addListener(new CustomListener());

        System.out.println("Running TestNG...");
        testng.run();
        System.out.println("Finished TestNG run.");
    }

    public static String getTestNgPath() {
        String testngXMLPath;
        URL testngUrl = MainRunner.class.getResource("/testng.xml");
        if (testngUrl != null) {
            System.out.println(testngUrl);
            testngXMLPath = "/testng.xml"; // For executable JAR
        } else {
            testngXMLPath = "/src/test/resources/testng.xml"; // For IDE or Maven
        }
        return testngXMLPath;
    }
}


class CustomListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult tr) {
        System.out.println("Test Failed: " + tr.getName() + " due to " + tr.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        System.out.println("Test Skipped: " + tr.getName());
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        System.out.println("Test Passed: " + tr.getName());
    }

    @Override
    public void onConfigurationFailure(ITestResult tr) {
        System.out.println("Configuration Failed: " + tr.getName() + " due to " + tr.getThrowable());
    }

    @Override
    public void onStart(ITestContext testContext) {
        System.out.println("Starting Test: " + testContext.getName());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        System.out.println("Finished Test: " + testContext.getName());
    }
}
