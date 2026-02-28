package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseUI {
    public void waitAndClick(WebElement element){
        waitUntilClickable(20, element);
        element.click();
    }

    /**
     * This method will wait for element to become visible
     * then it clears existing value and sends new keys
     * @param element - the input field
     * @param keys - the data to be sent
     */
    public void clearAndSendKeys(WebElement element, String keys){
        waitUntilVisible(20, element);
        element.click();
        element.sendKeys(Keys.COMMAND + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(keys);
    }

    public void jsClick(WebElement element){
        waitUntilClickable(20, element);
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    public void clearInputField(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].value = ''", element);
    }

    public void sendKeys(WebElement element, String keys){
        waitUntilVisable(20, element);
        element.sendKeys(keys);
    }

    public void waitUntilVisable(int seconds, WebElement element){
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void jsSendKeys(WebElement element, String value){
        waitUntilVisible(20, element);
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].value='';", element);
        js.executeScript("arguments[0].value=arguments[1];", element, value);
    }

    public WebDriverWait explicitWait(int seconds){
        return new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds));
    }

    public void waitUntilClickable(int seconds, WebElement element){
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilVisible(int seconds, WebElement element){
        new  WebDriverWait(Driver.getDriver(), Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    /*
     This method waits until the element appears on the page and becomes visible, then returns it.
     findElement() tries to find the element immediately and fails if it’s not there and trows NoSuchElementException.
     This wait method keeps checking until the element shows up (up to the timeout).
     */

    public WebElement waitUntilVisibleByLocator(int timeoutSeconds, By locator) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
