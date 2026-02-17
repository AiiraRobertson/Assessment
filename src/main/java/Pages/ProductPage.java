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

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    public void sortByLowToHigh() {
        Select select = new Select(sortDropdown);
        select.selectByValue("lohi");
    }

    public void findProductByName(String productName) {
        WebElement product = driver.findElement(
                By.xpath("//div[@class='inventory_item_name ' and contains(text(), '" + productName + "')]"));
        click(product);
    }

    public void sortAndSelectProduct(String productName) {
        sortByLowToHigh();
        findProductByName(productName);
    }
}
