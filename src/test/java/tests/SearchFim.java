package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ReplayIfFailed;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SearchFim {

    private static WebDriver chromeDrive;

    @BeforeClass
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        chromeDrive = new ChromeDriver();
        chromeDrive.manage().window().maximize();
        chromeDrive.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDown() {
        if (chromeDrive != null) {
            chromeDrive.quit();
        }
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testFindNowShowingMovies() {
        chromeDrive.get("https://www.lottecinemavn.com/");
        hideAds();

        WebElement phimButton = chromeDrive.findElement(By.xpath("//a[normalize-space()='PHIM']"));
        phimButton.click();

        WebElement nowShowingButton = chromeDrive.findElement(By.xpath("//a[@id='aNow']"));
        nowShowingButton.click();

        WebElement movieList = chromeDrive.findElement(By.xpath("//ul[@id='ulMovieList']"));
        assertTrue(movieList.isDisplayed(), "Không hiển thị danh sách phim đang chiếu!");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testFindUpcomingMovies() {
        chromeDrive.get("https://www.lottecinemavn.com/");
        hideAds();

        WebElement phimButton = chromeDrive.findElement(By.xpath("//a[normalize-space()='PHIM']"));
        phimButton.click();

        WebElement nowShowingButton = chromeDrive.findElement(By.xpath("//a[@id='aSoon']"));
        nowShowingButton.click();

        WebElement movieList = chromeDrive.findElement(By.xpath("//ul[@id='ulMovieList']"));
        assertTrue(movieList.isDisplayed(), "Không hiển thị danh sách phim sắp chiếu!");
    }

        public void hideAds() {
        try {
            WebDriverWait wait = new WebDriverWait(chromeDrive, Duration.ofSeconds(5)); 
            WebElement closeAdButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Đóng')]")));
            closeAdButton.click();
            System.out.println("Đã đóng quảng cáo.");
        } catch (Exception e) {
            System.out.println("Không tìm thấy quảng cáo để đóng.");
        }
    }
}
