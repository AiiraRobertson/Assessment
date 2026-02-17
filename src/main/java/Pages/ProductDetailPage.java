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

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = "a[data-test='shopping-cart-link']")
    private WebElement cartLink;

    public void addProductToCart() {
        click(addToCartButton);
    }

    public String getCartCount() {
        return getText(cartBadge);
    }

    public boolean isAddToCartButtonDisplayed() {
        return addToCartButton.isDisplayed();
    }

    public boolean isCartBadgeDisplayed() {
        return cartBadge.isDisplayed();
    }

    public void goToCart() {
        click(cartLink);
    }
}
