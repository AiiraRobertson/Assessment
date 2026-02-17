package Utitlity;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Custom TestNG Listener for enhanced logging and reporting
 */
public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        LoggerUtil.info("========== TEST SUITE START: " + context.getName() + " ==========");
        LoggerUtil.info("Total tests to execute: " + context.getAllTestMethods().length);
    }

    @Override
    public void onFinish(ITestContext context) {
        LoggerUtil.info("========== TEST SUITE FINISH: " + context.getName() + " ==========");
        LoggerUtil.info("Total tests executed: " + context.getAllTestMethods().length);
        LoggerUtil.info("Tests Passed: " + context.getPassedTests().size());
        LoggerUtil.info("Tests Failed: " + context.getFailedTests().size());
        LoggerUtil.info("Tests Skipped: " + context.getSkippedTests().size());
    }

    @Override
    public void onTestStart(ITestResult result) {
        LoggerUtil.testStart(result.getMethod().getMethodName());
        LoggerUtil.info("Test Description: " + result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LoggerUtil.info("✓ TEST PASSED: " + result.getMethod().getMethodName());
        LoggerUtil.testEnd(result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LoggerUtil.error("✗ TEST FAILED: " + result.getMethod().getMethodName());
        LoggerUtil.error("Failure Reason: " + result.getThrowable().getMessage());
        LoggerUtil.testEnd(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LoggerUtil.warn("⊘ TEST SKIPPED: " + result.getMethod().getMethodName());
        LoggerUtil.testEnd(result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LoggerUtil.info("TEST FAILED BUT WITHIN SUCCESS PERCENTAGE: " + result.getMethod().getMethodName());
    }
}

