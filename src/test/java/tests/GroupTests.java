package tests;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.GroupsPage;
import pages.LoginPage;
import pages.MainPage;
import utils.BaseUI;
import utils.ConfigurationReader;
import utils.Driver;

import java.nio.file.Paths;

public class GroupTests extends BaseUI {

    MainPage mainPage = new MainPage();
    GroupsPage groupsPage = new GroupsPage();
    LoginPage loginPage = new LoginPage();
    Faker faker = new Faker();

    @AfterMethod
    void tearDown() {
        Driver.closeDriver();
    }

    @Test
    void openGroupPage() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));

        waitAndClick(mainPage.groupPage);

        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("groups"));
    }

    @Test
    void createGroupWithValidData() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));

        groupsPage.createNewGroup();
        //groupsPage.groupImage.sendKeys("/Users/elenakhokha/Desktop/Studymate/src/test/resources/build.jpg");
        String imagePath = Paths.get("src", "test", "resources", "build.jpg")
                .toAbsolutePath()
                .toString();

        groupsPage.groupImage.sendKeys(imagePath);

        groupsPage.groupName.sendKeys(faker.funnyName().name());
        groupsPage.groupDate.sendKeys(groupsPage.todayDate);
        groupsPage.groupDescription.sendKeys(faker.name().lastName());

        waitAndClick(groupsPage.createBtn);
        waitUntilVisible(2, groupsPage.successAlert);

        Assert.assertTrue(groupsPage.successAlert.isDisplayed());
    }

    @Test
    void editGroupDetails() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));

        jsClick(groupsPage.groupSubMenu);
        waitAndClick(groupsPage.editBtn);
        waitUntilVisible(1, groupsPage.groupName);

        String groupNameBefore = groupsPage.groupName.getAttribute("value");
        clearInputField(groupsPage.groupName);
        groupsPage.groupName.sendKeys(faker.funnyName().name());
        String groupNameAfter = groupsPage.groupName.getAttribute("value");

        String groupDescriptionBefore = groupsPage.groupDescription.getAttribute("value");
        clearInputField(groupsPage.groupDescription);
        groupsPage.groupDescription.sendKeys(faker.funnyName().name());
        String groupDescriptionAfter = groupsPage.groupDescription.getAttribute("value");

        Assert.assertNotEquals(groupNameBefore, groupNameAfter);
        Assert.assertNotEquals(groupDescriptionBefore, groupDescriptionAfter);
    }

    @Test
    void deleteRandomGroupTest() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));

        waitAndClick(mainPage.groupPage);
        groupsPage.deleteRandomGroup();

        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("groups"));
    }

    @Test
    void editGroupDetailsWithoutSaving() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));

        jsClick(groupsPage.groupSubMenu);
        waitAndClick(groupsPage.editBtn);
        waitUntilVisible(1, groupsPage.groupName);

        String groupNameBefore = groupsPage.groupName.getAttribute("value");
        clearInputField(groupsPage.groupName);
        groupsPage.groupName.sendKeys(faker.funnyName().name());
        groupsPage.cancelBtn.click();

        jsClick(groupsPage.groupSubMenu);
        waitAndClick(groupsPage.editBtn);
        waitUntilVisible(1, groupsPage.groupName);

        String groupNameAfter = groupsPage.groupName.getAttribute("value");

        Assert.assertEquals(groupNameBefore, groupNameAfter);
    }
}
