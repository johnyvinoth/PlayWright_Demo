package com.example.tests;

import com.example.pages.LoginPage;
import com.example.pages.MainPage;
import com.example.utils.PropertyReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test(enabled = true)
    public void loginWithValidCredentials() throws Exception {
        MainPage mainPage = navigateToURLWithCredentials(true);
    }

    @Test(enabled = false)
    public void loginWithInvalidCredentials() throws Exception {
        MainPage mainPage = navigateToURLWithCredentials(false);
    }
}
