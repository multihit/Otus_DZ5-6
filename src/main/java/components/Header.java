package components;

import common.AbsCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Header extends AbsCommon {

    private String telHeaderMarkerSelector = "section > a[href*='tel']";
    private String signInBtnLocator = "//button[text()='Войти']";

    private String iconUserSelector = "img[src*='blue-owl']";


    public Header(WebDriver driver) {
        super(driver);
    }

    public void waitMarkerTelNumber() {
        waitTools.waitElementPresent(By.cssSelector(telHeaderMarkerSelector));
    }

    public void waitSignInBtnIsPresent() {
        waitTools.waitElementPresent(By.xpath(signInBtnLocator));
    }

    public void waitSignInBtnToBeClicable() {
        waitTools.waitElementToBeClicable(By.xpath(signInBtnLocator));
    }

    public void clickSignInButton() {
        WebElement signInBtn = driver.findElement(By.xpath(signInBtnLocator));

        String authPopupSelector = "#__PORTAL__ > div";
        waitTools.waitForCondition(ExpectedConditions.not(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(authPopupSelector))));

        signInBtn.click();
    }

    public void checkLogoUser() {
        waitTools.waitForCondition(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector(iconUserSelector)));
    }

    public void clickPersonalArea() {
        driver.findElement(By.cssSelector(iconUserSelector)).click();
        driver.findElement(By.xpath("//*[contains(@href, '/lk/biography/personal')]")).click();
    }
}