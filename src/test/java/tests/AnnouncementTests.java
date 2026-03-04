package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.AnnouncementsPage;
import pages.LoginPage;
import utils.BaseUI;
import utils.ConfigurationReader;
import utils.Driver;

import java.util.List;
import java.util.Random;

public class AnnouncementTests extends BaseUI{

    LoginPage loginPage = new LoginPage();
    AnnouncementsPage announcementsPage = new AnnouncementsPage();
    Faker faker = new Faker();
    private String randomText;

    @AfterMethod(alwaysRun = true)
    void tearDown() {

        Driver.closeDriver();
    }

    @Test(groups = "smoke")
    public void announcementDashboard() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));

        waitAndClick(announcementsPage.announcementTab);
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("announcements"));
    }

    @Test
    public void addNewAnnouncement() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));

        waitAndClick(announcementsPage.announcementTab);
        waitAndClick(announcementsPage.addNewAnnouncementTab);

        //add text
        waitUntilVisible(30, announcementsPage.text);
        randomText= faker.lorem().sentence(3);
        announcementsPage.text.clear();
        announcementsPage.text.sendKeys(randomText);
        announcementsPage.text.sendKeys(Keys.TAB);
        System.out.println("Inputted Random Text: " + randomText);

        //click on dropdown to select groups Randomly by index
        waitAndClick(announcementsPage.dropdown);

        //using listBox, because its single WebElement, while groupLists is a List
        waitUntilVisible(10, announcementsPage.listBox);

        //Randomly picking groups from drop down
        List<WebElement> groupLists = announcementsPage.groupLists;

        Random random = new Random();
        int randomIndex = random.nextInt(groupLists.size());

        WebElement randomOption = groupLists.get(randomIndex);
        waitUntilClickable(10, randomOption);

        // scroll into view (important for Material UI)
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", randomOption);

        // use JS click (more stable for MUI)
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].click();", randomOption);

        waitAndClick( announcementsPage.addButton);

        waitUntilVisible(5, announcementsPage.successMessage);
        Assert.assertTrue(announcementsPage.successMessage.isDisplayed());
    }
    @Test
    public void add_And_Delete_Same_Announcement() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));

        waitAndClick(announcementsPage.announcementTab);
        waitAndClick(announcementsPage.addNewAnnouncementTab);

        //add text
        waitUntilVisible(30, announcementsPage.text);
        randomText= faker.lorem().sentence(3);
        announcementsPage.text.clear();
        announcementsPage.text.sendKeys(randomText);
        announcementsPage.text.sendKeys(Keys.TAB);
        System.out.println("Inputted Random Text: " + randomText);

        //click on dropdown to select groups Randomly by index
        waitAndClick(announcementsPage.dropdown);

        //using listBox, because its single WebElement, while groupLists is a List
        waitUntilVisible(10, announcementsPage.listBox);

        //Randomly picking groups from drop down
        List<WebElement> groupLists = announcementsPage.groupLists;

        Random random = new Random();
        int randomIndex = random.nextInt(groupLists.size());

        WebElement randomOption = groupLists.get(randomIndex);
        waitUntilClickable(10, randomOption);

        // scroll into view (important for Material UI)
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", randomOption);

        // use JS click (more stable for MUI)
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].click();", randomOption);

        waitAndClick( announcementsPage.addButton);

        waitUntilVisible(5, announcementsPage.successMessage);
        Assert.assertTrue(announcementsPage.successMessage.isDisplayed());

        WebElement threeDotsSingleElement = Driver.getDriver().findElement(By.xpath("//button[contains(@tabindex,'0') and contains(@aria-haspopup,'true')]"));
        waitUntilVisible(10, threeDotsSingleElement);

        //3 dots to -> click on Delete option
        //click on Delete which text = randomtext which we just created
        List<WebElement> icons = announcementsPage.threeDots;

        for (WebElement icon : icons) {

            //finding card inside the 3 dots icon
            WebElement card = icon.findElement(By.xpath("./ancestor::div[contains(@class,'css-')]"));

            if (card.getText().contains(randomText)) {

                waitUntilClickable(10, icon);
                icon.click();   // Normal click first
                break;
            }
        }

        waitAndClick( announcementsPage.deleteAnnouncement);
        waitAndClick(announcementsPage.confirmationDelete);

        waitUntilVisible(5, announcementsPage.deletedMessage);
        Assert.assertTrue(announcementsPage.deletedMessage.isDisplayed());

    }

    @Test
    public void edit_Existing_Announcement() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));

        waitAndClick(announcementsPage.announcementTab);

        Thread.sleep(2000);

        //3 dots is in List bc, i need to locate 1 box out of 4, this is picking index(0) the first box
        //is hard code picked, I could have done same and as for group dropdown
        List<WebElement> threeDotButtons = announcementsPage.threeDots;
        threeDotButtons.get(0).click();

        waitAndClick( announcementsPage.editButton);

        waitAndClick(announcementsPage.text);

        randomText= faker.lorem().sentence(3);
        clearAndSendKeys(announcementsPage.text, randomText);
        System.out.println("Updated Text " + randomText);

        waitAndClick(announcementsPage.saveButton);

        waitUntilVisible(5, announcementsPage.successMessage);
        Assert.assertTrue(announcementsPage.successMessage.isDisplayed());

    }

}
