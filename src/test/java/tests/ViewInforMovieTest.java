package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ReplayIfFailed;

import java.util.List;
import java.util.Random;

public class ViewInforMovieTest extends BaseTest {
    @BeforeMethod
    public void setUpTest(){
        webDriver.get("https://www.lottecinemavn.com");
        hideAds();
    }

    @AfterMethod
    public void tearDownTest() {
        webDriver.manage().deleteAllCookies();
        System.out.println("Xoa cokie sau moi test");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void viewInforMovieByButton(){
        System.out.println("---TEST XEM CHI TIET PHIM BANG BUTTON---");
        WebElement selectedMovieItem = selectRandomMovie();

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});", selectedMovieItem);
        wait.until(ExpectedConditions.visibilityOf(selectedMovieItem));
        Actions action = new Actions(webDriver);
        action.moveToElement(selectedMovieItem).perform();
        System.out.println("Di chuot vao phim");

        WebElement detailButton = wait.until(ExpectedConditions.visibilityOf(
                selectedMovieItem.findElement(By.cssSelector("a.btn_View"))
        ));
        Assert.assertTrue(detailButton.isDisplayed(), "Ko hien thi butto 'CHI TIET'");
        detailButton.click();

        WebElement detailInforMovie = waitForElement(By.cssSelector(".wide_info_area"), 20);
        Assert.assertTrue(detailInforMovie.isDisplayed(), "Ko hien thi giao dien chi tiet phim");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("pass");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void viewInforMoieByClickTitle(){
        System.out.println("---TEST XEM CHI TIET PHIM BANG TEN PHIM---");
        WebElement selectedMovieItem = selectRandomMovie();
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});", selectedMovieItem);
        wait.until(ExpectedConditions.visibilityOf(selectedMovieItem));
        WebElement movieTitleElement = selectedMovieItem.findElement(By.xpath(".//dl/dt/a"));
        movieTitleElement.click();
        System.out.println("Click thanh cong");

        WebElement detailInforMovie = waitForElement(By.cssSelector(".wide_info_area"), 20);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(detailInforMovie.isDisplayed(), "Ko hien thi giao dien chi tiet phim");
        System.out.println("pass");
    }

    private WebElement selectRandomMovie(){
        WebElement buttonPhim = waitForElementToBeClickable(By.cssSelector("a[title='PHIM']"), 10);
        buttonPhim.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".d_loading")));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> movieItems = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@id='ulMovieList']/li[not(contains(@class, 'displays'))]")));

        Assert.assertFalse(movieItems.isEmpty(), "Ko co phim.");

        Random random = new Random();
        WebElement selectedMovie = movieItems.get(random.nextInt(movieItems.size()));

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].style.border='3px solid red'", selectedMovie);

        WebElement movieTitle = selectedMovie.findElement(By.xpath(".//dl/dt/a"));
        System.out.println("Da chon phim: " + movieTitle.getText());

        return selectedMovie;
    }
}
