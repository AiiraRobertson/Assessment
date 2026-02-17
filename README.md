# E-Commerce Test Automation Framework

## Overview
This is a robust test automation framework built with Java, Selenium, and TestNG for automating e-commerce testing scenarios. The framework includes comprehensive features for logging, reporting, retries, and best practices for test automation.

## Project Structure

```
Assessment/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Base/
│   │   │   │   ├── BaseClass.java          # Base test class with WebDriver setup
│   │   │   │   └── BasePage.java           # Base page class with common methods
│   │   │   ├── Pages/
│   │   │   │   ├── LoginPage.java          # Login page object
│   │   │   │   ├── ProductPage.java        # Product listing page object
│   │   │   │   ├── ProductDetailPage.java  # Product detail page object
│   │   │   │   ├── CartPage.java           # Shopping cart page object
│   │   │   │   └── CheckoutPage.java       # Checkout page object
│   │   │   ├── Tests/
│   │   │   │   └── EcomTests.java          # Main test class with all test cases
│   │   │   └── Utitlity/
│   │   │       ├── ConfigTestData.java     # Configuration and test data loader
│   │   │       ├── LoggerUtil.java         # Logging utility
│   │   │       ├── RetryAnalyzer.java      # Retry mechanism for flaky tests
│   │   │       └── TestListener.java       # Custom TestNG listener for reporting
│   │   └── resources/
│   │       └── log4j2.properties           # Log4j2 configuration
│   └── target/                              # Compiled classes
├── testng.xml                               # TestNG configuration file
├── testData.properties                      # Test data (credentials, URLs, etc.)
├── pom.xml                                  # Maven configuration
└── README.md                                # This file
```

## Features

### ✅ Core Features
- **Page Object Model (POM):** Clean separation of test logic and page interactions
- **WebDriver Management:** Centralized WebDriver initialization and cleanup
- **Explicit Waits:** Proper wait strategies for element visibility and interaction
- **Soft Assertions:** Collect multiple assertion failures without stopping test execution
- **Configuration Management:** External property files for test data and configuration

### ✅ Bonus Features

#### 1. Retry Mechanism
- Automatic retry for flaky tests (up to 3 attempts by default)
- Configured via `@Test(retryAnalyzer = RetryAnalyzer.class)`
- Helps handle intermittent failures due to network delays or UI lag

#### 2. Logging and Reporting
- **LoggerUtil:** Comprehensive logging utility for test steps and assertions
- **TestListener:** Custom TestNG listener for suite-level reporting
- **Log4j2:** Structured logging with file and console output
- Detailed test execution logs in `test-logs/automation-tests.log`

#### 3. Best Practices
- **DRY Principle:** Reusable methods in BasePage for common interactions
- **Encapsulation:** Private element locators with public methods
- **Error Handling:** Proper exception handling and meaningful error messages
- **Code Organization:** Clear separation of concerns

## Prerequisites

### System Requirements
- Java 8 or higher
- Maven 3.6+
- Chrome/Edge/Firefox browser

### Dependencies
All dependencies are configured in `pom.xml`:
- Selenium 4.40.0
- TestNG 7.10.2
- Log4j2 2.25.3
- WebDriverManager 6.3.3

## Installation & Setup

### Step 1: Clone/Download the Project
```bash
cd Assessment
```

### Step 2: Install Dependencies
```bash
mvn clean install
```

### Step 3: Configure Test Data
Edit `testData.properties`:
```properties
baseUrl = https://www.saucedemo.com
browser = chrome
username = standard_user
password = secret_sauce
```

### Step 4: Run Tests

#### Option 1: Using Maven
```bash
mvn test -DsuiteXmlFile=testng.xml
```

#### Option 2: Using IDE
Right-click on `testng.xml` and select "Run"

#### Option 3: Run Specific Test Class
```bash
mvn test -Dtest=Tests.EcomTests
```

## Test Scenarios

### Test 1: Navigate to E-Commerce Website (Priority 1)
- **Description:** Verify successful navigation to the inventory page
- **Steps:**
  1. Log in with valid credentials
  2. Verify current URL is `https://www.saucedemo.com/inventory.html`
- **Expected Result:** URL matches expected value

### Test 2: Search for a Specific Product (Priority 2)
- **Description:** Find and navigate to a specific product
- **Steps:**
  1. Click on "Sauce Labs Fleece Jacket"
  2. Verify back button is displayed
  3. Verify product details container is displayed
- **Expected Result:** Product detail page loads correctly

### Test 3: Add a Product to the Cart (Priority 3)
- **Description:** Add a product to shopping cart
- **Steps:**
  1. Navigate to product detail page
  2. Verify add to cart button is visible
  3. Click add to cart
  4. Verify cart badge appears with item count
- **Expected Result:** Product added to cart, badge displays count

### Test 4: Proceed to Checkout (Priority 4)
- **Description:** Complete full checkout flow
- **Steps:**
  1. Add product to cart
  2. Navigate to cart page
  3. Click checkout
  4. Fill checkout form (First Name, Last Name, Postal Code)
  5. Click continue
  6. Click finish
  7. Verify checkout completion
- **Expected Result:** Order completed successfully

## Test Execution Flow

```
SetupTest (BeforeMethod)
  ├── Navigate to baseUrl
  ├── Initialize page objects
  ├── Initialize soft assertions
  └── Login with credentials

Test Execution
  ├── Test-specific steps
  ├── Assertions (soft)
  └── assertAll() - collect all failures

Teardown (AfterClass)
  └── Quit WebDriver
```

## Logging Output

Logs are generated in the following locations:
- **Console:** Real-time log output in IDE console
- **File:** `test-logs/automation-tests.log`

### Log Format
```
[HH:mm:ss] LEVEL ClassName - Message
```

### Log Levels
- **INFO:** Test steps and assertions
- **WARN:** Retries and warnings
- **ERROR:** Test failures and exceptions
- **DEBUG:** Detailed diagnostic information

## Retry Mechanism

The framework automatically retries failed tests:

```java
@Test(retryAnalyzer = RetryAnalyzer.class)
public void testCase() { ... }
```

**Configuration:**
- Max retries: 3
- Log message: Failure details and retry count
- Use case: Handling flaky tests due to timing issues

## CI/CD Integration

### Jenkins Integration

1. **Create Pipeline Job:**
```groovy
pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git 'your-repo-url'
            }
        }
        
        stage('Build & Test') {
            steps {
                sh 'mvn clean test -DsuiteXmlFile=testng.xml'
            }
        }
        
        stage('Report') {
            steps {
                publishHTML([
                    reportDir: 'test-output',
                    reportFiles: 'index.html',
                    reportName: 'Test Report'
                ])
            }
        }
    }
    
    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
```

### GitHub Actions Integration

Create `.github/workflows/tests.yml`:
```yaml
name: E-Commerce Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: windows-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '11'
      - run: mvn test -DsuiteXmlFile=testng.xml
      - uses: actions/upload-artifact@v2
        if: always()
        with:
          name: test-logs
          path: test-logs/
```

## Troubleshooting

### Common Issues

**1. WebDriver Not Found**
- Install WebDriverManager (auto-managed via pom.xml)
- Ensure Chrome/Edge/Firefox is installed

**2. Element Not Found**
- Increase wait timeout in BasePage (default: 10 seconds)
- Verify locators against current website DOM

**3. Tests Timing Out**
- Check network connectivity
- Increase implicit wait in BaseClass
- Review log files for detailed error information

**4. Login Failure**
- Verify credentials in testData.properties
- Check if website has changed login mechanism

## Best Practices Implemented

✅ **Page Object Model** - Clear separation of concerns  
✅ **Explicit Waits** - Element visibility and interaction waits  
✅ **Soft Assertions** - Continue tests after failures  
✅ **Logging** - Comprehensive execution logs  
✅ **Retry Mechanism** - Handle flaky tests  
✅ **Configuration Management** - Centralized test data  
✅ **Error Messages** - Meaningful assertion messages  
✅ **Code Comments** - Well-documented code  
✅ **DRY Principle** - Reusable base classes  
✅ **CI/CD Ready** - Jenkins and GitHub Actions compatible  

## Configuration Details

### WebDriver Options
Configured in BaseClass with:
- Accept insecure certificates
- No sandbox mode
- Disable dev-shm-usage
- Disable GPU acceleration
- Window maximization
- Implicit wait: 80 seconds

### Browser Support
- Chrome (Default)
- Edge
- Firefox

To change browser, edit `testData.properties`:
```properties
browser = chrome  # or edge, firefox
```

## Performance

### Test Execution Time
- Average per test: 15-30 seconds
- Full suite (4 tests): ~2 minutes

### Optimization Tips
- Use explicit waits instead of sleeps
- Implement proper element waits
- Use CSS selectors over XPath when possible
- Minimize browser maximization operations

## Maintenance

### Updating Selectors
When website changes, update locators in respective Page classes:
```java
@FindBy(id = "new-element-id")
private WebElement element;
```

### Adding New Tests
1. Add test method in EcomTests
2. Set priority number
3. Add description
4. Include retryAnalyzer
5. Add LoggerUtil calls for tracking

### Updating Dependencies
```bash
mvn versions:display-dependency-updates
mvn versions:use-latest-versions
```

## Support & Contributions

For issues, questions, or contributions:
1. Check existing documentation
2. Review test logs for detailed errors
3. Verify element locators against current website
4. Test with different browsers if applicable

## License

This project is for educational and testing purposes.

---

**Framework Version:** 1.0  
**Last Updated:** February 2026  
**Maintained By:** QA Automation Team

