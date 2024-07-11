package com.example.pages;

import com.microsoft.playwright.Page;

import java.beans.Visibility;

public class MainPage {
    Page page;
    private final String addToCartBackpackBtn = "#add-to-cart-sauce-labs-backpack";
    private final String removeFromCartBackpackBtn = "#remove-sauce-labs-backpack";
    private final String addToCartLightBtn = "#add-to-cart-sauce-labs-bike-light";
    private final String removeFromCartLightBtn = "#remove-sauce-labs-bike-light";
    private final String shoppingCartBtn = ".shopping_cart_link";

    public MainPage(Page page) {
        this.page = page;
    }

    public void addToCartBackpack() {
        if (page.isVisible(removeFromCartBackpackBtn)) {
            page.waitForSelector(removeFromCartBackpackBtn, new Page.WaitForSelectorOptions().setTimeout(2000));
            page.click(removeFromCartBackpackBtn);
        } else {

            page.click(addToCartBackpackBtn);
        }
    }

    public void removeBackpack() {
        if (page.isVisible(removeFromCartBackpackBtn)) {

            page.click(removeFromCartBackpackBtn);
        } else {
            System.out.println("Backpack is not added to cart.");
        }
    }

    public void addToCartLightSauce() {
        if (page.isVisible(removeFromCartLightBtn)) {
            page.waitForSelector(removeFromCartLightBtn, new Page.WaitForSelectorOptions().setTimeout(2000));
            page.click(removeFromCartBackpackBtn);
        } else {

            page.click(addToCartLightBtn);
        }
    }

    public void removeLightSauce() {
        if (page.isVisible(removeFromCartLightBtn)) {
            page.click(removeFromCartLightBtn);
        } else {

            System.out.println("Bike Light Sauce is not added to cart.");
        }
    }

    public ShoppingCartPage goToShoppingCart() {
        page.click(shoppingCartBtn);
        return new ShoppingCartPage(page);
    }
}
