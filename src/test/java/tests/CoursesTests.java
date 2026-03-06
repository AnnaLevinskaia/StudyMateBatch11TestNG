package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.BaseUI;
import utils.ConfigurationReader;
import utils.Driver;

import javax.management.InstanceAlreadyExistsException;
import java.time.Duration;
public class CoursesTests extends BaseUI{
    LoginPage loginPage;
    CoursesPage coursesPage;
    Faker faker ;
    MainPage mainPage;

    @BeforeMethod(alwaysRun = true)
    public void setup() throws InterruptedException {

        // Start driver first
        Driver.getDriver();

        // Initialize page objects AFTER driver starts
        loginPage = new LoginPage();
        faker = new Faker();
        mainPage = new MainPage();

        // Login
        loginPage.loginWithCorrectCredentials(
                ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password")
        );

        // Navigate to Announcements page
        waitAndClick(mainPage.courses);

    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Driver.closeDriver();
    }


    @Test
    public void createNewCourse() throws InterruptedException {
        mainPage = new MainPage();
       coursesPage = new CoursesPage();
        loginPage = new LoginPage();
        faker = new Faker();

        waitAndClick(coursesPage.createCourseBtn);
        //coursesPage.uploadThePhoto.sendKeys("/Users/nargizasulaimankulova/Desktop/StudyMateBatch11TestNG/src/test/resources/download.jpeg");
        coursesPage.courseNameInput.sendKeys(faker.lorem().sentence(5));
        coursesPage.dateInput.sendKeys(coursesPage.currentDate);
        coursesPage.description.sendKeys(faker.lorem().sentence());
        waitAndClick(coursesPage.createBtn);

        waitUntilVisible(20,coursesPage.successAlert);
        Assert.assertTrue(coursesPage.successAlert.isDisplayed());

    }

    @Test (groups = "smoke")
    public void editCourseDetails() throws InterruptedException {
        mainPage = new MainPage();
        coursesPage = new CoursesPage();
        loginPage = new LoginPage();
        faker = new Faker();

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

        mainPage = new MainPage();
        coursesPage = new CoursesPage();
        loginPage = new LoginPage();

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
        mainPage = new MainPage();
        coursesPage = new CoursesPage();
        loginPage = new LoginPage();

        waitAndClick(coursesPage.editSign);
        waitAndClick(coursesPage.delete);
        coursesPage.deleteBtn.click();

        waitUntilVisible(20,coursesPage.successfullyDeletedAlert);
        Assert.assertTrue(coursesPage.successAlert.isDisplayed());

    }

}
