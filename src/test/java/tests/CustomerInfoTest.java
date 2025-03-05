package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ReplayIfFailed;

public class CustomerInfoTest extends BaseTest {

    @BeforeClass
    public void setUpTest() {
        openLoginPage();
        login("thithutheo@gmail.com", "PKA123456a@");
        hideAds();
        System.out.println("---DANG NHAP THANH CONG---");
        WebElement accountMenu = waitForElement(By.id("topMyCinema"), 10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".d_loading")));
        accountMenu.click();
        System.out.println("---VAO TRANG 'TAI KHOAN CUA TOI'---");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void historyTicketPurchase() {
        System.out.println("---LICH SU MUA VE---");
        scrollToElement(By.cssSelector(".Lang-LBL3001"));
        clickElement(By.cssSelector(".Lang-LBL3001"));
        System.out.println("pass");
    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void historyWatchedMovies() {
        System.out.println("---LICH SU XEM PHIM---");
        WebElement userName = waitForElement(By.id("spnUserName"), 30);
        String expectUserName = userName.getText().trim();
        scrollToElement(By.cssSelector(".mvHistory"));
        clickElement(By.cssSelector(".mvHistory"));

        WebElement messageElement = waitForElement(By.cssSelector("p.mView_txt strong.name.Lang-LBL0000"), 10);
        String actualUserName = messageElement.getText().trim();

        Assert.assertEquals(actualUserName, expectUserName, "Thong bao khong khop ten nguoi dung!");
        System.out.println("pass");

    }

    @Test(retryAnalyzer = ReplayIfFailed.class)
    public void updateAccountInfo() {
        System.out.println("---UPDATE THONG TIN TAI KHOAN---");
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("quick")));
            System.out.println("Tat quang cao thanh cong");
        } catch (Exception e) {}
        scrollToElement(By.id("myInfoMn"));
        clickElement(By.id("myInfoMn"));
        clickElement(By.id("aChangeInformation"));

        WebElement nameField = waitForElement(By.id("CustomerName"), 10);
        nameField.clear();
        nameField.sendKeys("Nguyen Van A");

        WebElement genderMale = waitForElement(By.cssSelector("div[class='option-wrap'] label:nth-child(1)"), 10);
        genderMale.click();

        WebElement cityDropdown = waitForElement(By.id("city"), 10);
        cityDropdown.click();
        clickElement(By.cssSelector("option[value='1087']"));

        WebElement districtDropdown = waitForElement(By.id("district"), 10);
        districtDropdown.click();
        clickElement(By.cssSelector("option[value='726']"));

        WebElement addressField = waitForElement(By.id("CustomerAddress"), 5);
        addressField.clear();
        addressField.sendKeys("123 Đường ABC");

        WebElement changeButton = waitForElement(By.id("btnChangeInfo"), 5);
        changeButton.click();

        WebElement successMessage = waitForElement(By.xpath("//div[@class='mgs-text text-center gj-dialog-md-content']"), 30);
        Assert.assertTrue(successMessage.isDisplayed(), "Thong bao ko xuat hien");
        System.out.println("pass");
    }

    private void clickElement(By locator) {
        WebElement element = waitForElement(locator, 20);
        element.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

