package security;
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


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Security {
	WebDriver driver;
	String baseUrl;

    @BeforeMethod
    public void setUp() throws Exception {
        // Khởi tạo WebDriver
        driver = new ChromeDriver();
        baseUrl = "https://www.lottecinemavn.com/";
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        // Đóng trình duyệt sau khi kiểm tra
        if (driver != null) {
            driver.quit();
        }


    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testXSSInjection() {
        driver.get("https://www.lottecinemavn.com/");
        hideAds();

        driver.findElement(By.xpath("//a[@id='topCustomerService']")).click();
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='searchKeyword']"));
        searchBox.click();
        searchBox.sendKeys("<script>alert('XSS Attack');</script>");
        searchBox.submit(); 

        try {
            String alertText = driver.switchTo().alert().getText();
            assertTrue(alertText.equals("XSS Attack"), "XSS không được bảo vệ!");
            driver.switchTo().alert().accept();  // Đóng alert
        } catch (Exception e) {
            System.out.println("Không có alert xuất hiện, có thể bảo vệ khỏi XSS.");
        }
    }
    
//    @Test
//    public void testSQLInjectionInSearch() {
//        driver.get("https://www.lottecinemavn.com/LCHS/Contents/Movie/Movie-List.aspx");
//        hideAds();
//        driver.findElement(By.xpath("//a[@id='topCustomerService']")).click();
//        WebElement searchBox = driver.findElement(By.xpath("//input[@id='searchKeyword']"));
//        searchBox.click();
//        searchBox.sendKeys("' OR 1=1--");
//        searchBox.submit();
//        String pageSource = driver.getPageSource();
//        assertFalse(pageSource.contains("error"), "SQL Injection Subquery thành công!");
//    }
    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testSQLInjectionInURL() {
        driver.get("https://www.lottecinemavn.com/LCHS/Contents/Movie/Movie-List.aspx' OR 1=1 --");

        String pageSource = driver.getPageSource();
        assertFalse(pageSource.contains("error"), "SQL Injection thông qua URL thành công!");
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

