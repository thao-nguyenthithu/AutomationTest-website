package tests;

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

public class MouseActions {

    WebDriver driver;
    String baseUrl;

    @BeforeClass
    public void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.get("https://www.lottecinemavn.com/LCHS/Contents/Cinema/Cinema-Detail.aspx");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize(); 
    }

    @AfterClass
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testHoverOnLink() {
        WebElement link = driver.findElement(By.xpath("//a[contains(text(),'Hà Nội')]"));

        Actions actions = new Actions(driver);
        
        actions.moveToElement(link).perform();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement tooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Hà Đông")));

        Assert.assertTrue(tooltip.isDisplayed(), "Tooltip không hiển thị khi hover vào liên kết!");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testClick() {
        WebElement link = driver.findElement(By.xpath("//a[contains(text(),'Hà Nội')]"));

        Actions actions = new Actions(driver);
        
        actions.moveToElement(link).perform();

        WebElement tooltip = driver.findElement(By.linkText("Hà Đông"));

        Assert.assertTrue(tooltip.isDisplayed(), "Tooltip không hiển thị khi hover vào liên kết!");
        
        tooltip.click();
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testDoubleClick() {
    	Actions actions = new Actions(driver);
    	
    	actions.moveToElement(driver.findElement(By.xpath("//h2[@id='cinemaName1']"))).click().doubleClick();
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void testRightClick() {
    	Actions actions = new Actions(driver);
    	
    	actions.moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Lịch chiếu phim')]"))).contextClick().build().perform();
 //   	actions.contextClick(driver.findElement(By.xpath("//a[contains(text(),'Lịch chiếu phim')]"))).build().perform();
    }
    
//    @Test
//    public void testHoverOnButton() {
//        WebElement button = driver.findElement(By.xpath("//a[@class='time_active t8']"));
//
//        Actions actions = new Actions(driver);
//        
//        actions.moveToElement(button).perform();
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        
//        Boolean backgroundColor = wait.until(ExpectedConditions.attributeToBe(button, "background-color", "#231f20"));
//
//        Assert.assertEquals(backgroundColor, "#231f20", "Màu nền không thay đổi khi hover vào nút!");
//    }
    
    @Test
    public void MouseHoverTest() {
    	Actions actions = new Actions(driver);
    	
    	actions.moveToElement(driver.findElement(By.xpath("//a[contains(text(),'ĐB Sông Hồng')]"))).build().perform();
    	
    	driver.findElement(By.linkText("Nam Định")).click(); 
    	
    	boolean text = driver.findElement(By.xpath("(//h2[contains(text(),'Nam Định')])[1]")).isDisplayed();
    	System.out.println(text);
    	
    	if(text) {
    		System.out.println("Nam Dinh is true");
    	}
    	else {
    		System.out.println("Nam Dinh is false");
    	}
    	
    }
}
