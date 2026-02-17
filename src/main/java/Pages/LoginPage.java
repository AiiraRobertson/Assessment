package Pages;

import Base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Locate Username field
    @FindBy(id = "user-name")
    private WebElement usernameInput;

    // Locate Password field
    @FindBy(id = "password")
    private WebElement passwordInput;

    // Locate Login button
    @FindBy(id = "login-button")
    private WebElement loginButton;

    // Login method with waits
    public void login(String username, String password) {
        waitForVisibility(usernameInput);
        enterText(usernameInput, username);

        waitForVisibility(passwordInput);
        enterText(passwordInput, password);

        click(loginButton);
    }
}
