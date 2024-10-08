package com.example.tests;

import com.example.pages.LoginPage;
import com.example.pages.MainPage;
import com.example.utils.PropertyReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test(enabled = true)
    public void loginWithValidCredentials() throws Exception {
//        changed following two functions to test the failed cases. need to change it back.
        MainPage mainPage = navigateToURLWithCredentials(true);
        //Added the following to test the video generation for failed cases.
        Assert.assertTrue(false);

    }

    @Test(enabled = true)
    public void loginWithInvalidCredentials() throws Exception {
        MainPage mainPage = navigateToURLWithCredentials(false);
        //Added the following to test the video generation for failed cases.
        Assert.assertTrue(false);

    }
}
