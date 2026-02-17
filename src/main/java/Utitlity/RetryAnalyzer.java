package Utitlity;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry Analyzer for handling flaky tests
 * This class will retry failed tests up to a maximum of 3 times
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private static final int maxTries = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxTries) {
            count++;
            LoggerUtil.warn("Test failed: " + result.getMethod().getMethodName() + ". Retrying attempt " + count + " of " + maxTries);
            return true;
        }
        return false;
    }
}

