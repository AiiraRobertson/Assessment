package Pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ===========================
    // Web Elements
    // ===========================

    // Locate product sort dropdown
    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    // ===========================
    // Public Methods (Exposed to Test Class)
    // ===========================

    // Select filter option: Price (low to high)
    public void sortByLowToHigh() {
        Select select = new Select(sortDropdown);
        select.selectByValue("lohi");
    }

    // Click product by its name - finds product link and clicks it
    public void findProductByName(String productName) {
        // Find the product name div and click it to navigate to product detail page
        WebElement product = driver.findElement(By.xpath("//div[@class='inventory_item_name ' and contains(text(), '" + productName + "')]"));
        click(product);
    }

    // Complete flow method
    public void sortAndSelectProduct(String productName) {
        sortByLowToHigh();
        findProductByName(productName);
    }
}
