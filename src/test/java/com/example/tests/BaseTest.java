package com.example.tests;

import com.example.pages.LoginPage;
import com.example.pages.MainPage;
import com.example.utils.PropertyReader;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
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
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        page = browser.newPage();
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

    @AfterClass
    public void teardown() {
        page.close();
        browser.close();
        playwright.close();
    }
//    test push
}
