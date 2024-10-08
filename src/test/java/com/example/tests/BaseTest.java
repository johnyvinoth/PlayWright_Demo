package com.example.tests;

import com.example.pages.LoginPage;
import com.example.pages.MainPage;
import com.example.utils.PropertyReader;
import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.nio.file.Paths;

public class BaseTest {
    protected Playwright playwright;
    protected Page page;
    protected Browser browser;
    PropertyReader propertyReader;
    BrowserContext context;
    String videoFileName;
    Boolean testFailed = false;

    @BeforeClass
//    @Parameters("browserName")
    public void setup() {
        playwright = Playwright.create();
        String browserName = System.getProperty("browserName", "chrome");
        System.out.println("Browser Name: " + browserName);

        switch (browserName.toLowerCase()) {
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("videos/temp-videos"))
                .setRecordVideoSize(960, 540));

//        page = browser.newPage();
        page = context.newPage();
        page.setViewportSize(1500, 1080);

    }

    protected MainPage navigateToURLWithCredentials(boolean isValidLogin) {
        propertyReader = new PropertyReader();
        String URL = propertyReader.getProperty("url");
        String username = isValidLogin ? propertyReader.getProperty("username") : propertyReader.getProperty("invalid_username");
        String password = propertyReader.getProperty("password");

        page.navigate(URL);
        LoginPage loginPage = new LoginPage(page);
        loginPage.login(username, password);

        if (isValidLogin) {
            Assert.assertTrue(loginPage.isLoggedIn(), "Username or password is not valid");
            System.out.println("Login Successful");
            return new MainPage(page);
        } else {
            Assert.assertFalse(loginPage.isLoggedIn(), "Login succeeded for invalid credentials");
            System.out.println("Login Un-successful");
            return null;
        }
    }

    @AfterMethod

    public void checkTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test Failed, Video will be saved later ...");

            testFailed = true;
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String timestamp = now.format(formatter);

            videoFileName = result.getName() + "_" + timestamp + ".webm";
            Video video = page.video();

            page.waitForTimeout(2000);
            page.close();
            Path videoPath = Paths.get("videos", videoFileName);

            video.saveAs(videoPath);
            System.out.println("Video saved at: " + videoPath.toAbsolutePath());
        } else {
            System.out.println("Test passed , no video will be saved for: " + result.getName());
            page.close();
        }

        page = context.newPage();
    }

    /**
 * Deletes all files within a specified folder and then deletes the folder itself.
 *
 * @param folderName The name of the folder to be deleted.
 *
 * @throws SecurityException If a security manager exists and its {@code checkDelete} method denies deletion of the folder.
 * @throws IllegalArgumentException If the folder name is null.
 */
public void deleteFilesAndFolder(String folderName) {
    Path tempVideoDir = Paths.get(folderName);

    // Check if the folder exists
    if (tempVideoDir.toFile().exists()) {
        // Get all files and directories in the folder
        File[] files = tempVideoDir.toFile().listFiles();

        // If there are files in the folder
        if (files != null) {
            // Iterate through each file
            for (File file : files) {
                // If the file is not a directory, delete it
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
        }

        // Delete the folder itself
        tempVideoDir.toFile().delete();
    }
}

    @AfterClass
    public void teardown() {
        String tempVideoDir="videos/temp-videos/";
        deleteFilesAndFolder(tempVideoDir);

        context.close();
        browser.close();
        playwright.close();
    }
//    test push
}
