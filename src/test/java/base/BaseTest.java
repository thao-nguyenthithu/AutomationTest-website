
package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class BaseTest {
    protected WebDriver webDriver;
    protected WebDriverWait wait;

    @BeforeClass
    public void setUpClass() {

        /*--------ChromeDriver---------*/
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--disable-notifications");
        webDriver = new ChromeDriver(options);

//        /*--------EdgeDriver---------*/
//        WebDriverManager.edgedriver().setup();
//        EdgeOptions options = new EdgeOptions();
//        options.addArguments("--start-maximized", "--disable-notifications");
//        webDriver = new EdgeDriver(options);

        wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
    }

    @AfterClass
    public void tearDownClass() {
        if (webDriver != null) {
            webDriver.quit();
            System.out.println("----------DONG TRINH DUYET------------");
        }
    }

    public void openLoginPage() {
        webDriver.get("https://www.lottecinemavn.com");
        hideAds();
        try {
            WebElement loginButton = waitForElement(By.id("lbtnLogin"), 5);
            loginButton.click();
        } catch (TimeoutException e) {
            System.out.println("Khong the click vao nut Dang nhap.");
        }
    }

    public void login(String email, String password) {
        try {
            waitForElement(By.id("username"), 3).sendKeys(email);
            waitForElement(By.id("verifyPhone"), 3).click();
            waitForElement(By.id("phonePassword"), 3).sendKeys(password);
            waitForElement(By.id("verifyPhoneUser"), 3).click();

            boolean isLoggedIn = !webDriver.findElements(By.className("loginInfo")).isEmpty();
            Assert.assertTrue(isLoggedIn, "Dang nhap that bai");
        } catch (TimeoutException e) {
            System.out.println("Loi khi dang nhap: " + e.getMessage());
        }
    }

    public void hideAds() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".d_loading")));
            WebElement closeBtn = waitForElementToBeClickable(By.cssSelector(".btn_layerClose"), 20);
            closeBtn.click();
            System.out.println("AN QUANG CAO THANH CONG");
        } catch (TimeoutException e) {
            System.out.println("Ko tim thay quang cao");
        }
    }

    public WebElement waitForElement(By locator, int timeout) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBeClickable(By locator, int timeout) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].style.border='3px solid red'", element);
        return element;
    }

    public WebElement waitForElementToBeClickable(WebElement element, int timeout) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void scrollToElement(By locator) {
        WebElement element = waitForElement(locator, 20);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }


}
