package com.example.tests;

import com.example.pages.MainPage;
import com.example.pages.ShoppingCartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TrasactProductsTest extends BaseTest {
    @Test
    public void addProductsAndVerifyCart() {
        MainPage mainPage = navigateToURLWithCredentials(true);
        mainPage.addToCartBackpack();
        mainPage.addToCartLightSauce();

        ShoppingCartPage shoppingCartPage = mainPage.goToShoppingCart();

        Assert.assertTrue(shoppingCartPage.isCartPageDisplayed(), "Shopping Cart page is not displayed");
        Assert.assertTrue(shoppingCartPage.isProductInCart("Sauce Labs Backpack"), "Product not found in the cart");
        Assert.assertTrue(shoppingCartPage.isProductInCart("Sauce Labs Bike Light"), "Product not found in the cart");
//      Added the following to test the video generation for failed cases.
        Assert.assertTrue(false);
    }
}
