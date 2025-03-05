package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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


//import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class MovieScheduleTest {

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
    public void testMovieScheduleForMultipleDays() {
        String[] daysToTest = {"March6", "March7", "March8", "March9"};

        for (String day : daysToTest) {
            testMovieScheduleForDay(day);
        }
    }

    private void testMovieScheduleForDay(String day) {
        driver.get("https://www.lottecinemavn.com/LCHS/Contents/Cinema/Cinema-Detail.aspx");

        WebElement movieScheduleSection = driver.findElement(By.xpath("//a[contains(text(),'Lịch chiếu phim')]"));
        Assert.assertNotNull(movieScheduleSection, "Không tìm thấy phần lịch chiếu phim!");

        WebElement scheduleForDay = movieScheduleSection.findElement(By.xpath("//label[@for='" + day + "']"));
        Assert.assertNotNull(scheduleForDay, "Không tìm thấy lịch chiếu phim cho ngày " + day + "!");
        
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", scheduleForDay);

        WebElement movieDetails = driver.findElement(By.xpath("//div[@class='time_box time_list02']"));
        Assert.assertNotNull(movieDetails, "Không tìm thấy thông tin chi tiết phim cho ngày " + day + "!");
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
