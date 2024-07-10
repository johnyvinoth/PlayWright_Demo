package com.example.tests;

import com.example.utils.PropertyReader;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected Playwright playwright;
    protected Page page;
    protected Browser browser;
    PropertyReader propertyReader;

    @BeforeClass
    @Parameters("browserName")
    public void setup(String browserName) {
        playwright = Playwright.create();

        switch (browserName.toLowerCase()) {
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        page = browser.newPage();


    }

    @AfterClass
    public void teardown() {
        page.close();
        browser.close();
        playwright.close();
    }
}
