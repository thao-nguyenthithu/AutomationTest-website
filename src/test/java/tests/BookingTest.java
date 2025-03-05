
package tests;

import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ReplayIfFailed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookingTest extends BaseTest {

    @BeforeMethod
    public void setUpTest() {
        openLoginPage();
        login("thithutheo@gmail.com", "PKA123456a@");
        hideAds();
        System.out.println("---DANG NHAP THANH CONG---");
        waitForElement(By.cssSelector("a[title='MUA VÉ']"), 10).click();
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void bookingSuccessfully() {
        selectCinemaAddress();
        selectCinemaName();
        selectTime();
        selectNumberOfSeats(2);
        selectSeats();
        confirmSeatSelection();
    }

    private void selectCinemaAddress() {
        System.out.println("---CHON KHU VUC RAP---");
        waitForPageReady();
        List<WebElement> cinemaAddresses = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".theater_zone > li")));

        WebElement selectedAddress = cinemaAddresses.get(new Random().nextInt(cinemaAddresses.size()));
        clickElement(selectedAddress);
        System.out.println("Chon thanh cong khu vuc rap: " + selectedAddress.getText());
    }

    private void selectCinemaName() {
        System.out.println("---CHON TEN RAP---");
        waitForPageReady();
        List<WebElement> cinemaNames = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".area_cont.on ul.area_list li a")));
        List<WebElement> availableCinemas = new ArrayList<>();
        for (WebElement cinema : cinemaNames) {
            List<WebElement> selectedCheck = cinema.findElements(By.xpath("./div[contains(text(),'Đã chọn')]"));
            if (selectedCheck.isEmpty()) {
                availableCinemas.add(cinema);
            }
        }
        WebElement selectedCinema = availableCinemas.get(new Random().nextInt(availableCinemas.size()));
        clickElement(selectedCinema);
        System.out.println("Chon thanh cong ten rap: " + selectedCinema.getText());
    }

    private void selectTime() {
        System.out.println("---CHON KHUNG GIO---");
        waitForPageReady();
        List<WebElement> noDataElements = webDriver.findElements(By.cssSelector(".time_noData"));
        if (!noDataElements.isEmpty() && noDataElements.get(0).isDisplayed()) {
            System.out.println("Khong con suat chieu cho rap");
            return;
        }

        List<WebElement> times;
        try {
            times = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy( By.xpath("//ul[contains(@class, 'theater_time')]//li//a")));
        } catch (TimeoutException e) {
            System.out.println("Khong con suat chieu kha dung");
            return;
        }
        WebElement time = times.get(new Random().nextInt(times.size()));
        clickElement(time);
        System.out.println("Chon thanh cong suat chieu: " + time.getText());
    }


    private void selectNumberOfSeats(int seatCount) {
        System.out.println("---CHON SO LUONG GHE---");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("content")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("person0")));

        clickElement(waitForElementToBeClickable(By.cssSelector("div.select_box a.ui_fold_btn"), 10));
        clickElement(waitForElementToBeClickable(By.xpath("//div[@class='select_box']//ul//li//a[text()='" + seatCount + "']"), 10));
        System.out.println("Chon thanh cong so luong ghe: " + seatCount);
    }

    private void selectSeats() {
        System.out.println("---CHON VI TRI GHE NGOI---");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("person0")));
        List<WebElement> availableSeats = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.cssSelector(".seat_Barea a:not(.no_select):not(.completed):not(.selected)")));

        if (!availableSeats.isEmpty()) {
            WebElement seatToClick = availableSeats.get(new Random().nextInt(availableSeats.size()));
            clickElement(seatToClick);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Chon thanh cong vi tri ghe: " + seatToClick.getDomProperty("data-seat"));
        } else {
            System.out.println("Khong co ghe trong de chon");
        }
    }

    private void confirmSeatSelection() {
        System.out.println("---VAO TRANG THANH TOAN---");
        clickElement(waitForElementToBeClickable(By.cssSelector("a[title='Các bước tiếp theo']"), 30));
        System.out.println("Da vao trang thanh toan tien thanh cong.");
    }

    private void waitForPageReady() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".d_loading")));
        List<WebElement> loadingElements = webDriver.findElements(By.xpath("//div[contains(@class, 'loading')]"));
        if (!loadingElements.isEmpty()) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class, 'loading')]")));
        }
    }

    private void clickElement(WebElement element) {
        try {
            waitForPageReady();
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            waitForElementToBeClickable(element, 30).click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Click bi chan.");
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", element);
        }
    }
}