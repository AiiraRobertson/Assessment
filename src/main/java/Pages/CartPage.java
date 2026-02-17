package Pages;

import Base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "cart_item")
    private WebElement cartItem;

    @FindBy(css = "button[data-test='checkout']")
    private WebElement checkoutButton;

    @FindBy(className = "cart_list")
    private WebElement cartList;

    public boolean isCartItemDisplayed() {
        return cartItem.isDisplayed();
    }

    public boolean isCheckoutButtonDisplayed() {
        return checkoutButton.isDisplayed();
    }

    public void clickCheckout() {
        click(checkoutButton);
    }

    public boolean isCartListDisplayed() {
        return cartList.isDisplayed();
    }
}

