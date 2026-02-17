package Pages;

import Base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(css = "input[data-test='continue']")
    private WebElement continueButton;
    
    @FindBy(css = "button[data-test='finish']")
    private WebElement finishButton;

    @FindBy(className = "checkout_complete_container")
    private WebElement checkoutCompleteContainer;

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        enterText(firstNameInput, firstName);
        enterText(lastNameInput, lastName);
        enterText(postalCodeInput, postalCode);
    }

    public void clickContinue() {
        click(continueButton);
    }

    
    public void clickFinish() {
        click(finishButton);
    }

    public boolean isCheckoutCompleteDisplayed() {
        return checkoutCompleteContainer.isDisplayed();
    }

    public String getCompletionMessage() {
        return getText(completeHeader);
    }

    public boolean isFirstNameInputDisplayed() {
        return firstNameInput.isDisplayed();
    }

    public boolean isContinueButtonDisplayed() {
        return continueButton.isDisplayed();
    }

    public boolean isFinishButtonDisplayed() {
        return finishButton.isDisplayed();
    }
}

