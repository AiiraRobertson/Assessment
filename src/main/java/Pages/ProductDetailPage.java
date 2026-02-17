package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailPage extends ProductPage {
    public ProductDetailPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "inventory_details_back_button")
    public WebElement backToProductsButton;

    @FindBy(className = "inventory_details_container")
    public WebElement productDetailsContainer;

    @FindBy(css = "button[data-test='add-to-cart']")
    private WebElement addToCartButton;

    // Locate cart badge
    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    // Locate shopping cart link
    @FindBy(css = "a[data-test='shopping-cart-link']")
    private WebElement cartLink;

    // ===========================
    // Public Methods (Exposed to Test Class)
    // ===========================

    // Add product to cart
    public void addProductToCart() {
        click(addToCartButton);
    }

    // Get cart count (badge text)
    public String getCartCount() {
        return getText(cartBadge);
    }

    // Check if add to cart button is displayed
    public boolean isAddToCartButtonDisplayed() {
        return addToCartButton.isDisplayed();
    }

    // Check if cart badge is displayed
    public boolean isCartBadgeDisplayed() {
        return cartBadge.isDisplayed();
    }

    // Click on cart link to go to cart page
    public void goToCart() {
        click(cartLink);
    }
}
