package com.example.pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

public class LoginPage {
    Page page;
    private final String usernameTxt="#user-name";
    private final String passwordTxt="#password";
    private final String usernameBtn="#login-button";
private final String productTitleTxt=".title";
    public LoginPage(Page page) {
        this.page = page;
    }

    public void enterUsername(String username) {
        page.fill(usernameTxt, username);
    }
    public void enterPassword(String password) {
        page.fill(passwordTxt, password);
    }
    public void pressLoginButton() {
        page.click(usernameBtn);
    }
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        pressLoginButton();
        page.waitForLoadState();

    }
    public boolean isLoggedIn() {

        try
        {
            ElementHandle titleElement=page.querySelector(productTitleTxt);
            if(titleElement!=null) {
                String titleTxt = titleElement.innerText().trim();
                return titleTxt.equalsIgnoreCase("Products");
            }
            return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
