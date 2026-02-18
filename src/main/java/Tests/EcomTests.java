package Tests;

import Base.BaseClass;
import Pages.CartPage;
import Pages.CheckoutPage;
import Pages.LoginPage;
import Pages.ProductDetailPage;
import Pages.ProductPage;
import Utitlity.LoggerUtil;
import Utitlity.RetryAnalyzer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class EcomTests extends BaseClass {
    String username;
    String password;
    ProductPage productPage;
    ProductDetailPage productDetailPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    LoginPage loginPage;
    SoftAssert softAssert;

    @BeforeMethod
    @SuppressWarnings("null")
    public void setUpTest() {
        LoggerUtil.info("Setting up test...");
        username = testData.getUsername();
        password = testData.getPassword();
        driver.get(baseUrl);
        softAssert = new SoftAssert();
        productPage = new ProductPage(driver);
        productDetailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        loginPage = new LoginPage(driver);
        LoggerUtil.info("Logging in with username: " + username);
        loginPage.login(username, password);
        LoggerUtil.info("Setup complete");
    }

    @Test(priority = 1, description = "Navigate to an e-commerce website", retryAnalyzer = RetryAnalyzer.class)
    public void NavigateToEcommerceLandingPage() {
        LoggerUtil.testStart("NavigateToEcommerceLandingPage");
        LoggerUtil.testStep("Verifying current URL matches expected URL");
        String actualUrl = driver.getCurrentUrl();
        LoggerUtil.info("Current URL: " + actualUrl);
        softAssert.assertEquals(actualUrl,
                "https://www.saucedemo.com/inventory.html",
                "The current URL did not match the expected URL.");
        LoggerUtil.assertion("URL verification completed");
        softAssert.assertAll();
        LoggerUtil.testEnd("NavigateToEcommerceLandingPage");
    }

    @Test(priority = 2, description = "Search for a specific product", retryAnalyzer = RetryAnalyzer.class)
    public void SearchForProduct() {
        LoggerUtil.testStart("SearchForProduct");
        LoggerUtil.testStep("Finding product: Sauce Labs Fleece Jacket");
        productPage.findProductByName("Sauce Labs Fleece Jacket");
        LoggerUtil.info("Product found and navigated to detail page");

        LoggerUtil.testStep("Verifying back to products button is displayed");
        boolean isBackToProductsButtonDisplayed = productDetailPage.backToProductsButton.isDisplayed();
        softAssert.assertTrue(isBackToProductsButtonDisplayed, "The back to products button is not displayed.");
        LoggerUtil.assertion("Back to products button verification completed");

        LoggerUtil.testStep("Verifying product details container is displayed");
        boolean isProductDetailsDisplayed = productDetailPage.productDetailsContainer.isDisplayed();
        softAssert.assertTrue(isProductDetailsDisplayed, "The product details page is not displayed.");
        LoggerUtil.assertion("Product details container verification completed");

        softAssert.assertAll();
        LoggerUtil.testEnd("SearchForProduct");
    }

    @Test(priority = 3, description = "Add a product to the cart", retryAnalyzer = RetryAnalyzer.class)
    public void AddProductToCart() {
        LoggerUtil.testStart("AddProductToCart");
        LoggerUtil.testStep("Finding product: Sauce Labs Fleece Jacket");
        productPage.findProductByName("Sauce Labs Fleece Jacket");
        LoggerUtil.info("Product found and navigated to detail page");

        LoggerUtil.testStep("Verifying add to cart button is displayed");
        softAssert.assertTrue(productDetailPage.isAddToCartButtonDisplayed(),
                "The add to cart button is not displayed.");

        LoggerUtil.testStep("Clicking add to cart button");
        productDetailPage.addProductToCart();
        LoggerUtil.info("Product added to cart");

        LoggerUtil.testStep("Verifying cart badge is displayed");
        softAssert.assertTrue(productDetailPage.isCartBadgeDisplayed(),
                "The cart badge is not displayed after adding product.");
        LoggerUtil.assertion("Cart badge verification completed");

        LoggerUtil.testStep("Verifying cart count is not empty");
        String cartCount = productDetailPage.getCartCount();
        LoggerUtil.info("Cart count: " + cartCount);
        softAssert.assertNotNull(cartCount, "The cart count should not be null.");
        softAssert.assertTrue(!cartCount.isEmpty(), "The cart count should not be empty.");
        LoggerUtil.assertion("Cart count verification completed");

        softAssert.assertAll();
        LoggerUtil.testEnd("AddProductToCart");
    }

    @Test(priority = 4, description = "Proceed to checkout", retryAnalyzer = RetryAnalyzer.class)
    public void ProceedToCheckout() {
        LoggerUtil.testStart("ProceedToCheckout");

        LoggerUtil.testStep("Adding product to cart");
        productPage.findProductByName("Sauce Labs Fleece Jacket");
        productDetailPage.addProductToCart();
        LoggerUtil.info("Product added to cart");

        LoggerUtil.testStep("Verifying cart badge is displayed");
        softAssert.assertTrue(productDetailPage.isCartBadgeDisplayed(), "The cart badge is not displayed.");
        LoggerUtil.assertion("Cart badge verification completed");

        LoggerUtil.testStep("Navigating to cart page");
        productDetailPage.goToCart();
        LoggerUtil.info("Cart page loaded");

        LoggerUtil.testStep("Verifying cart page elements");
        softAssert.assertTrue(cartPage.isCartListDisplayed(), "The cart list is not displayed.");
        softAssert.assertTrue(cartPage.isCartItemDisplayed(), "The cart item is not displayed.");
        LoggerUtil.assertion("Cart page verification completed");

        LoggerUtil.testStep("Verifying checkout button is displayed");
        softAssert.assertTrue(cartPage.isCheckoutButtonDisplayed(), "The checkout button is not displayed.");
        LoggerUtil.assertion("Checkout button verification completed");

        LoggerUtil.testStep("Clicking checkout button");
        cartPage.clickCheckout();
        LoggerUtil.info("Checkout page loaded");

        LoggerUtil.testStep("Verifying checkout form is displayed");
        softAssert.assertTrue(checkoutPage.isFirstNameInputDisplayed(),
                "The first name input is not displayed on checkout page.");
        softAssert.assertTrue(checkoutPage.isContinueButtonDisplayed(),
                "The continue button is not displayed on checkout page.");
        LoggerUtil.assertion("Checkout form verification completed");

        LoggerUtil.testStep("Filling checkout form with test data");
        checkoutPage.fillCheckoutForm("John", "Doe", "12345");
        LoggerUtil.info("Checkout form filled");

        LoggerUtil.testStep("Clicking continue button");
        checkoutPage.clickContinue();
        LoggerUtil.info("Checkout step 2 page loaded");

        LoggerUtil.testStep("Verifying finish button is displayed");
        softAssert.assertTrue(checkoutPage.isFinishButtonDisplayed(),
                "The finish button is not displayed on checkout step 2.");
        LoggerUtil.assertion("Finish button verification completed");

        LoggerUtil.testStep("Clicking finish button");
        checkoutPage.clickFinish();
        LoggerUtil.info("Checkout completed");

        LoggerUtil.testStep("Verifying checkout completion");
        softAssert.assertTrue(checkoutPage.isCheckoutCompleteDisplayed(),
                "The checkout completion page is not displayed.");
        LoggerUtil.assertion("Checkout completion page verification completed");

        LoggerUtil.testStep("Retrieving completion message");
        String completionMessage = checkoutPage.getCompletionMessage();
        LoggerUtil.info("Completion message: " + completionMessage);
        softAssert.assertNotNull(completionMessage, "The completion message should not be null.");
        LoggerUtil.assertion("Completion message verification completed");

        softAssert.assertAll();
        LoggerUtil.testEnd("ProceedToCheckout");
    }
}
