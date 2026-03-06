package tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.SchedulePage;
import utils.BaseUI;
import utils.ConfigurationReader;
import utils.Driver;

public class ScheduleTests  extends  BaseUI{
    MainPage mainPage;
    LoginPage loginPage;
    SchedulePage schedulePage;

    @BeforeMethod(alwaysRun = true)
    public  void setUp() throws  InterruptedException{
        Driver.getDriver();

        mainPage = new MainPage();
        loginPage = new LoginPage();
        schedulePage = new SchedulePage();

        loginPage.loginWithCorrectCredentials(
                ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));
        waitAndClick(mainPage.schedule);
    }

    @AfterMethod(alwaysRun = true)
    public  void tearDown(){
        Driver.closeDriver();
    }

    @Test(groups = {"schedule","smoke"}, priority = 1)

    public  void viewExistingEventForSelectedDay(){

        waitAndClick(schedulePage.mathEvent);
        Assert.assertTrue(schedulePage.eventDetailsPanel.isDisplayed(),
                "Event details panel is not displayed");
    }

    @Test(groups = {"schedule", "regression"},priority = 2,
    dependsOnMethods = "viewExistingEventForSelectedDay")
    public  void createNewEvent(){
        waitAndClick(schedulePage.createBtn);
        schedulePage.nameInput.sendKeys("Coding lesson");
        waitAndClick(schedulePage.forWhomDropdown);
        waitAndClick(schedulePage.firstPersonCheckbox);
        Actions actions = new Actions(Driver.getDriver());
        actions.sendKeys(Keys.ESCAPE).perform();
        schedulePage.dateInput.clear();
        schedulePage.dateInput.sendKeys("05.03.26");
        waitUntilClickable(10,schedulePage.startTime);
        schedulePage.startTime.click();
        schedulePage.startTime.sendKeys(Keys.COMMAND+"a");
        schedulePage.startTime.sendKeys(Keys.DELETE);
        schedulePage.startTime.sendKeys("0900");
        schedulePage.startTime.sendKeys(Keys.TAB);

        waitUntilClickable(10,schedulePage.endTime);
        schedulePage.endTime.click();
        schedulePage.endTime.sendKeys(Keys.COMMAND+"a");
        schedulePage.endTime.sendKeys(Keys.DELETE);
        schedulePage.endTime.sendKeys("1000");
        schedulePage.endTime.sendKeys(Keys.TAB);

       waitUntilClickable(10,schedulePage.publishBtn);


        Assert.assertTrue(schedulePage.publishBtn.isEnabled(),
                "Publish should be enabled after filling required fields");


        waitAndClick(schedulePage.publishBtn);
        waitUntilClickable(10,schedulePage.createBtn);
        Assert.assertTrue(schedulePage.createBtn.isDisplayed(),
                "Created event is not visible in calendar");

    }


    @Test(groups = {"schedule", "regression"}, priority = 3)
    public  void createEventWithoutRequiredFields(){
        waitAndClick(schedulePage.createBtn);

        Assert.assertFalse(schedulePage.saveBtn.isEnabled(),
                "Save button should be disabled when required fields are empty");

        Assert.assertFalse(schedulePage.publishBtn.isEnabled(),
                "Publish button should be disabled when required fields are empty");
    }



}
