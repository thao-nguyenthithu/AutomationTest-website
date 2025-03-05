package security;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ReplayIfFailed;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class PageTest {
    WebDriver driver;
    String baseUrl;

    @BeforeClass
    public void setUp() throws Exception {
        // Khởi tạo WebDriver với ChromeOptions
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.get("https://www.lottecinemavn.com/LCHS/Contents/Cinema/Cinema-Detail.aspx");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize(); 
    }

    @AfterClass
    public void tearDown() throws Exception {
        // Đóng trình duyệt sau khi kiểm tra tất cả các phương thức
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testNavigation() {
        WebElement menuLink = driver.findElement(By.xpath("//img[@id='imgLogo']"));
        menuLink.click();
        hideAds();
        
        WebElement productPage = driver.findElement(By.xpath("//a[@href='/LCHS/index.aspx']"));
        Assert.assertTrue(productPage.isDisplayed(), "Không thể điều hướng đến trang sản phẩm!");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testPageReload() {
        driver.navigate().refresh();
        
        WebElement pageContent = driver.findElement(By.xpath("//a[@href='/LCHS/index.aspx']"));
        Assert.assertTrue(pageContent.isDisplayed(), "Trang không tải lại đúng cách!");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testPageLoad() {
        long startTime = System.currentTimeMillis();
        driver.get("https://www.lottecinemavn.com/LCHS/index.aspx#");
        long endTime = System.currentTimeMillis();
        
        long loadTime = endTime - startTime;
        Assert.assertTrue(loadTime < 5000, "Trang tải chậm hơn 5 giây!");
    }

    
    public void hideAds() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));  // Sử dụng WebDriverWait với Duration
            WebElement closeAdButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Đóng')]")));
            closeAdButton.click();
            System.out.println("Đã đóng quảng cáo.");
        } catch (Exception e) {
            System.out.println("Không tìm thấy quảng cáo để đóng.");
        }
    }

}
