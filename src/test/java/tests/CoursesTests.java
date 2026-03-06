package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CoursesPage;
import pages.LoginPage;
import pages.MainPage;
import utils.BaseUI;
import utils.ConfigurationReader;
import utils.Driver;

import java.time.Duration;


public class CoursesTests extends BaseUI{
    LoginPage loginPage = new LoginPage();
    CoursesPage coursesPage =new CoursesPage();
    Faker faker = new Faker();
    MainPage mainPage = new MainPage();

    @Test
    public void createNewCourse() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));
        waitAndClick(mainPage.courses);

        waitAndClick(coursesPage.createCourseBtn);
        coursesPage.uploadThePhoto.sendKeys("/Users/nargizasulaimankulova/Desktop/StudyMateBatch11TestNG/src/test/resources/download.jpeg");
        coursesPage.courseNameInput.sendKeys(faker.funnyName().name());
        coursesPage.dateInput.sendKeys(coursesPage.currentDate);
        coursesPage.description.sendKeys(faker.lorem().sentence());
        waitAndClick(coursesPage.createBtn);

        waitUntilVisible(20,coursesPage.successAlert);
        Assert.assertTrue(coursesPage.successAlert.isDisplayed());

    }

    @Test (groups = "smoke")
    public void editCourseDetails() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));
        waitAndClick(mainPage.courses);

        waitAndClick(coursesPage.editSign);
        coursesPage.edit.click();
        waitAndClick(coursesPage.courseNameInput);
        coursesPage.courseNameInput.sendKeys(Keys.COMMAND + "a");
        coursesPage.courseNameInput.sendKeys(Keys.DELETE);
        coursesPage.courseNameInput.sendKeys(faker.funnyName().name());

        coursesPage.createBtn.click();
        waitUntilVisible(20,coursesPage.successfullyUpdatedAlert);
         String successAlert = coursesPage.successfullyUpdatedAlert.getText();

         Assert.assertEquals(successAlert,"The course successfully updated");
    }
    @Test
    public void assignTeacher() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));
        waitAndClick(mainPage.courses);
        WebDriver driver = Driver.getDriver();

        waitAndClick(coursesPage.editSign);
        waitAndClick(coursesPage.assignBtn);
        waitAndClick(coursesPage.selectBtn);
        Thread.sleep(2000);

        if (!coursesPage.teachers.isEmpty()) {
            waitAndClick(coursesPage.teachers.get(0));
        }

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();

        waitAndClick(coursesPage.saveBtn);

        waitUntilVisible(20,coursesPage.successAssignedAlert);
        Assert.assertTrue(coursesPage.successAssignedAlert.isDisplayed());

    }


    @Test (groups = "smoke")
    public void deleteCourse() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));
        waitAndClick(mainPage.courses);
        waitAndClick(coursesPage.editSign);
        waitAndClick(coursesPage.delete);
        coursesPage.deleteBtn.click();

        waitUntilVisible(20,coursesPage.successfullyDeletedAlert);
        Assert.assertTrue(coursesPage.successAlert.isDisplayed());

    }

}
