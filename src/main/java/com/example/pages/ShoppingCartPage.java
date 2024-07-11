package com.example.pages;

import com.microsoft.playwright.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartPage {
    Page page;
    private final String cartPageTitleTxt = ".title";
    private final String productsListLbl ="//div[@class='inventory_item_name' and text()='%s']";

    public ShoppingCartPage(Page page) {
        this.page = page;

    }

    public boolean isCartPageDisplayed() {
        if (page.isVisible(cartPageTitleTxt)) {
            String pageTitleTxt = page.textContent(cartPageTitleTxt).trim().replaceAll("\\s+", " ").toLowerCase();
            boolean isPageCartTitleDisplayed= pageTitleTxt.equalsIgnoreCase("Your Cart");
            if(isPageCartTitleDisplayed)
            {
                System.out.println("The cart page page is displayed");
                return isPageCartTitleDisplayed;
            }
            else
            {
                System.out.println("The cart page page is not displayed");
            }
        }
        return false;
    }

    public List<String> getProductsList() {
        return page.locator(productsListLbl).allTextContents().stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public boolean isProductInCart(String productName)
    {
        String productXpath=String.format(productsListLbl,productName);
        boolean isProductInCart=page.locator(productXpath).isVisible();
        if(isProductInCart)
        {
            System.out.println("The product " + productName + " is displayed in cart");
        }
        else {
             System.out.println("The product " + productName + " is not displayed in the cart");
        }
        return isProductInCart;
    }

}
