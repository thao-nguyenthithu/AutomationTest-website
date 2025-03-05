
package security;

import java.time.Duration;  // Thêm import Duration
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ReplayIfFailed;

public class BruteForceLogin {
    WebDriver driver;
    String baseUrl = "https://www.lottecinemavn.com/LCHS/Contents/Member/Login.aspx";  // URL của trang đăng nhập
    String phone = "0398756652";
    String invalidPassword = "a";
    String correctPassword = "Dothingan@483";  // Thay đổi theo mật khẩu đúng của bạn

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testBruteForceLogin() {
        driver.get(baseUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='errorBeforeBtn']"))); 
//        WebElement accountLockedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='errorBeforeBtn']")));
//        WebElement usernameField = driver.findElement(By.xpath("//input[@id='username']"));
//        usernameField.sendKeys(phone);
//        WebElement nextButton = driver.findElement(By.xpath("//button[@id='verifyPhone']"));
//        WebElement passwordField = driver.findElement(By.xpath("//input[@id='phonePassword']"));
//        WebElement loginButton = driver.findElement(By.xpath("//button[@id='verifyPhoneUser']"));
//        WebElement errorMessage = driver.findElement(By.xpath("//div[@id='errorBeforeBtn']")); 
//        WebElement accountLockedMessage = driver.findElement(By.xpath("//div[@id='errorBeforeBtn']"));

        for (int i = 1; i <= 7; i++) {
            try {
                System.out.println("Lần thử đăng nhập: " + i);

                WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='username']")));
                usernameField.clear();
                usernameField.sendKeys(phone);

                WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='verifyPhone']")));
                nextButton.click();

                WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='phonePassword']")));
                passwordField.clear();
                passwordField.sendKeys(invalidPassword);

                WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='verifyPhoneUser']")));
                loginButton.click();

                Thread.sleep(2000);

                WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='errorBeforeBtn']")));

                if (i < 7) {
                    Assert.assertTrue(errorMessage.isDisplayed(), "Thông báo lỗi không hiển thị sau lần thử thứ " + i);
                }

                if (i == 7) {
                    WebElement accountLockedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='errorBeforeBtn']")));
                    Assert.assertTrue(accountLockedMessage.isDisplayed(), "Tài khoản không bị khóa sau 7 lần thử sai.");
                }

            } catch (Exception e) {
                System.out.println("Lỗi xảy ra ở lần thử: " + i + " - " + e.getMessage());
                break; // Dừng vòng lặp nếu có lỗi
            }
        }

    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testValidLogin() {
        driver.get(baseUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='username']")));
        usernameField.clear();
        usernameField.sendKeys(phone);
        WebElement nextButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='verifyPhone']")));
        nextButton.click();
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='phonePassword']")));
        passwordField.clear();
        passwordField.sendKeys(correctPassword);
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='verifyPhoneUser']")));
        loginButton.click();






        // Bạn có thể kiểm tra thông báo đăng nhập thành công ở đây (nếu cần)
        // WebElement loggedInMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logged-in-message"))); 
        // Assert.assertTrue(loggedInMessage.isDisplayed(), "Thông báo đăng nhập thành công không hiển thị.");
    }
}
