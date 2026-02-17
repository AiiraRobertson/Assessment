package Utitlity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {

    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    // Log info message
    public static void info(String message) {
        logger.info(message);
    }

    // Log error message
    public static void error(String message) {
        logger.error(message);
    }

    // Log error with exception
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    // Log debug message
    public static void debug(String message) {
        logger.debug(message);
    }

    // Log warning message
    public static void warn(String message) {
        logger.warn(message);
    }

    // Log test start
    public static void testStart(String testName) {
        logger.info("========== TEST START: " + testName + " ==========");
    }

    // Log test end
    public static void testEnd(String testName) {
        logger.info("========== TEST END: " + testName + " ==========");
    }

    // Log test step
    public static void testStep(String stepDescription) {
        logger.info("STEP: " + stepDescription);
    }

    // Log assertion
    public static void assertion(String assertionMessage) {
        logger.info("ASSERTION: " + assertionMessage);
    }
}

