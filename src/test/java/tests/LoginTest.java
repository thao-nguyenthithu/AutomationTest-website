
package tests;

import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ReplayIfFailed;

import java.util.List;

public class LoginTest extends BaseTest {

    @BeforeMethod
    public void setUpTest() {
        try {
            WebElement logoutButton = waitForElement(By.id("lbtnLogout"), 5);
            logoutButton.click();
            System.out.println("Da dang xuat truoc khi chay test.");
        } catch (TimeoutException e) {
            System.out.println("Khong can dang xuat, tiep tuc test.");
        }
    }

    @AfterMethod
    public void tearDownTest() {
        webDriver.manage().deleteAllCookies();
        System.out.println("Xoa cookies sau moi test.");
    }

    private boolean isErrorMessageDisplayed(String expectedMessage) {
        try {
            WebElement errorElement = waitForElement(By.id("errorBeforeBtn"), 5);
            return errorElement.getText().contains(expectedMessage);
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void loginTrue() {
        System.out.println("---TETS DANG NHAP THANH CONG---");
        openLoginPage();
        login("thithutheo@gmail.com", "PKA123456a@");
        System.out.println("pass test");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void loginWithWrongPassword() {
        System.out.println("---TEST PASSWORD SAI---");
        openLoginPage();
        login("thithutheo@gmail.com", "WrongPass123!");
        Assert.assertTrue(isErrorMessageDisplayed("Sai mật khẩu, vui lòng thử lại."), "Thong bao loi khong xuat hien dung.");
        System.out.println("pass test");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void loginWithInvalidEmailFormat() {
        System.out.println("---TEST EMAIL SAI---");
        openLoginPage();
        WebElement usernameField = waitForElement(By.id("username"), 5);
        usernameField.sendKeys("12345@12");

        WebElement verifyButton = waitForElement(By.id("verifyPhone"), 5);
        Assert.assertFalse(verifyButton.isEnabled(), "Nut TIEP TUC van hoat dong du email sai dinh dang.");
        System.out.println("pass test");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void loginWithEmptyFields() {
        System.out.println("---TEST BO TRONG EMAIL & PASSWORD---");
        openLoginPage();
        WebElement verifyButton = waitForElement(By.id("verifyPhone"), 5);
        Assert.assertFalse(verifyButton.isEnabled(), "Nut Tiep tuc van co the nhan khi email trong.");
        System.out.println("pass test");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void loginPerformanceTest() {
        System.out.println("---TEST HIEU NANG DANG NHAP---");
        openLoginPage();
        long startTime = System.currentTimeMillis();
        login("thithutheo@gmail.com", "PKA123456a@");
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;

        System.out.println("Thoi gian dang nhap: " + responseTime + "ms");
        Assert.assertTrue(responseTime < 5000, "Thoi gian dang nhap qua lau!");
        System.out.println("pass test");
    }
}
