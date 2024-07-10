package com.example.tests;

import com.example.pages.LoginPage;
import com.example.utils.PropertyReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
    public void loginWithValidCredentials() throws Exception {
        propertyReader = new PropertyReader();

        String URL=propertyReader.getProperty("url");
        String username=propertyReader.getProperty("username");
        String password=propertyReader.getProperty("password");

        page.navigate(URL);
        LoginPage loginPage = new LoginPage(page);
        loginPage.login(username,password);
        Assert.assertTrue(loginPage.isLoggedIn());

    }
}
