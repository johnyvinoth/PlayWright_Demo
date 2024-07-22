package com.example.tests;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainRunner {
    static TestNG testng;

    public static void main(String[] args) throws IOException {
        testng = new TestNG();
//        String path = System.getProperty("user.dir") + "/src/test/resources/testng.xml";
        String testngXmlPathh = getTestNgPath();
        String testngXmlPath="/testng.xml";
        System.out.println("TestNG XML path: " + testngXmlPath);


        // Load testng.xml from the classpath
        File tempFile = null;
        try (InputStream inputStream = MainRunner.class.getResourceAsStream(testngXmlPath)) {
            if (inputStream == null) {
                throw new RuntimeException("Cannot find " + testngXmlPath + " in classpath");
            }

            tempFile = File.createTempFile("testng", ".xml");
            try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            tempFile.deleteOnExit();

//      classpath  File testngXmlFile = new File(testngXmlPath);
//        if (!testngXmlFile.exists()) {
//            throw new RuntimeException("Cannot find " + testngXmlPath);
//        }
            System.out.println("TestNG XML path: " + testngXmlPath);

            List<String> testngXmlList = new ArrayList<>();
//        testngXmlList.add(testngXmlPath);
            testngXmlList.add(tempFile.getAbsolutePath());  // Using temp file instead of setting test classes directly

            testng.setTestSuites(testngXmlList);  // Using XML file instead of setting test classes directly

            testng.addListener(new CustomListener());

            System.out.println("Running TestNG...");
            testng.run();
            System.out.println("Finished TestNG run.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load testng.xml from the classpath", e);
        }

    }


    public static String getTestNgPath() {
        String testngXMLPath;
        URL testngUrl = MainRunner.class.getResource("/testng.xml");
        if (testngUrl != null) {
//            System.out.println(testngUrl);
            testngXMLPath = URLDecoder.decode(testngUrl.getFile(), StandardCharsets.UTF_8);
        } else {
//            testngXMLPath = "/src/test/resources/testng.xml"; // For IDE or Maven
            testngXMLPath = System.getProperty("user.dir") + "/src/test/resources/testng.xml";
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
