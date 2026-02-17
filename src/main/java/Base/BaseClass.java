package Base;

import Utitlity.ConfigTestData;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


import java.io.File;
import java.io.IOException;

import static java.time.Duration.ofSeconds;

public class BaseClass {

    public static ConfigTestData testData = new ConfigTestData();
    public static String browser = testData.setBrowser();
    public static String baseUrl = testData.setBaseURL();
    public static WebDriver driver;
    public static JavascriptExecutor jsExecutor;

    public static void captureScreen(WebDriver driver, String testName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File target = new File(System.getProperty("user.dir") + "/Screenshots/" + testName + ".png");
        FileUtils.copyFile(source, target);
        System.out.println("Screenshot taken: " + target.getAbsolutePath());
    }

    private static void initializeWebDriver(String browser) {
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setAcceptInsecureCerts(true);
                chromeOptions.addArguments("--headless"); // Run in headless mode
                chromeOptions.addArguments("--no-sandbox"); // Required for some CI environments
                chromeOptions.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
                chromeOptions.addArguments("--disable-gpu");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setAcceptInsecureCerts(true);
                driver = new EdgeDriver(edgeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setAcceptInsecureCerts(true);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    @BeforeMethod
    public static void setUp() {
        initializeWebDriver(browser);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(ofSeconds(80));
        jsExecutor = (JavascriptExecutor) driver;

    }

    @AfterMethod
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
