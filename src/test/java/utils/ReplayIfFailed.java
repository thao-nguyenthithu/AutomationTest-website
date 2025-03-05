package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class ReplayIfFailed implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = 5; // Số lần thử lại tối đa

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("Test bi failde, thu lai lan: " + retryCount);
            return true;
        }
        return false;
    }
}
