package tests;

import com.github.javafaker.Faker;
import jdk.swing.interop.DispatcherWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AnnouncementsPage;
import pages.LoginPage;
import utils.BaseUI;
import utils.ConfigurationReader;
import utils.Driver;

import javax.swing.plaf.TableHeaderUI;
import java.util.List;
import java.util.Random;

public class AnnouncementsTests extends BaseUI {

    LoginPage loginPage;
    AnnouncementsPage announcementsPage;
    Faker faker;
    String randomText;

    @BeforeMethod
    public void setup() throws InterruptedException {

        // Start driver first
        Driver.getDriver();

        // Initialize page objects AFTER driver starts
        loginPage = new LoginPage();
        announcementsPage = new AnnouncementsPage();
        faker = new Faker();

        // Login
        loginPage.loginWithCorrectCredentials(
                ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password")
        );

        // Navigate to Announcements page
        waitAndClick(announcementsPage.announcementTab);

        Assert.assertTrue(
                Driver.getDriver().getCurrentUrl().contains("announcements"),
                "Did not navigate to Announcements page"
        );
    }

    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }

    @Test
    public void addNewAnnouncement() {

        waitAndClick(announcementsPage.addNewAnnouncementTab);

        waitUntilVisible(30, announcementsPage.text);

        randomText = faker.lorem().sentence(3);

        announcementsPage.text.clear();
        announcementsPage.text.sendKeys(randomText);
        announcementsPage.text.sendKeys(Keys.TAB);

        // Open dropdown
        waitAndClick(announcementsPage.dropdown);

        waitUntilVisible(10, announcementsPage.listBox);

        // Random group selection
        List<WebElement> groupLists = announcementsPage.groupLists;
        Random random = new Random();
        WebElement randomOption = groupLists.get(random.nextInt(groupLists.size()));

        waitUntilClickable(10, randomOption);

        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", randomOption);

        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].click();", randomOption);

        waitAndClick(announcementsPage.addButton);

        waitUntilVisible(5, announcementsPage.successMessage);

        Assert.assertTrue(
                announcementsPage.successMessage.isDisplayed(),
                "Success message not displayed"
        );
    }

    @Test
    public void addAndDeleteSameAnnouncement() {

        waitAndClick(announcementsPage.addNewAnnouncementTab);

        waitUntilVisible(30, announcementsPage.text);

        randomText = faker.lorem().sentence(3);

        announcementsPage.text.clear();
        announcementsPage.text.sendKeys(randomText);
        announcementsPage.text.sendKeys(Keys.TAB);

        waitAndClick(announcementsPage.dropdown);

        waitUntilVisible(10, announcementsPage.listBox);

        List<WebElement> groupLists = announcementsPage.groupLists;
        WebElement randomOption = groupLists.get(new Random().nextInt(groupLists.size()));

        waitUntilClickable(10, randomOption);

        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].click();", randomOption);

        waitAndClick(announcementsPage.addButton);

        waitUntilVisible(5, announcementsPage.successMessage);

        Assert.assertTrue(announcementsPage.successMessage.isDisplayed());

        // Find the correct card by text and click 3 dots
        for (WebElement icon : announcementsPage.threeDots) {

            WebElement card = icon.findElement(
                    By.xpath("./ancestor::div[contains(@class,'css-')]")
            );

            if (card.getText().contains(randomText)) {
                waitUntilClickable(10, icon);
                icon.click();
                break;
            }
        }

        waitAndClick(announcementsPage.deleteAnnouncement);
        waitAndClick(announcementsPage.confirmationDelete);

        waitUntilVisible(5, announcementsPage.deletedMessage);

        Assert.assertTrue(
                announcementsPage.deletedMessage.isDisplayed(),
                "Delete confirmation message not displayed"
        );
    }

    @Test
    public void editExistingAnnouncement() throws InterruptedException {
        waitAndClick(announcementsPage.announcementTab);

        List<WebElement> threeDotButtons = announcementsPage.threeDots;

        Thread.sleep(2000);
        waitUntilVisible(10, threeDotButtons.get(0));
        threeDotButtons.get(0).click();

        waitAndClick(announcementsPage.editButton);

        waitUntilVisible(10, announcementsPage.text);

        randomText = faker.lorem().sentence(3);

        clearAndSendKeys(announcementsPage.text, randomText);

        waitAndClick(announcementsPage.saveButton);

        waitUntilVisible(5, announcementsPage.successMessage);

        Assert.assertTrue(
                announcementsPage.successMessage.isDisplayed(),
                "Edit success message not displayed"
        );
    }
}
