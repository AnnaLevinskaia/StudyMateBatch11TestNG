package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.TeachersPage;
import utils.BaseUI;
import utils.ConfigurationReader;
import utils.Driver;


public class TeachersTests extends BaseUI {

    MainPage mainPage = new MainPage();
    LoginPage loginPage = new LoginPage();
    Faker faker = new Faker();
    TeachersPage teachersPage = new TeachersPage();
    Actions actions = new Actions(Driver.getDriver());

    @BeforeMethod
    void loginTeachersPage() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));

        waitAndClick(mainPage.teachers);
    }

    @AfterMethod
    void tearDown() {
        Driver.closeDriver();
    }

    @Test
    void addTeacher() throws InterruptedException {

        //clicking on Add teacher button
        waitUntilClickable(30, teachersPage.addTeacherButton);
        teachersPage.addTeacherButton.click();

        //filling out the form
        String firstName = faker.name().firstName();
        teachersPage.firstNameField.sendKeys(firstName);
        String lastName = faker.name().lastName();
        String fullName = firstName + " " + lastName;
        teachersPage.lastNameField.sendKeys(lastName);
        teachersPage.phoneNumberField.click();
        teachersPage.phoneNumberField.sendKeys(faker.number().digits(10));
        teachersPage.emailField.sendKeys(faker.internet().emailAddress());
        teachersPage.specializationField.sendKeys("QA");
        teachersPage.chooseCoursesDropDown.click();
        teachersPage.SDETdropDownOption.click();

        teachersPage.SDETdropDownOption.sendKeys(Keys.ESCAPE);

        teachersPage.addButton.click();

        // 1. ASSERT that full name appears in the table:
        By createdTeacherLocator = By.xpath(
                "//td[contains(@class,'MuiTableCell-body') and normalize-space()='" + fullName + "']");

        WebElement createdTeacher = waitUntilVisibleByLocator(30, createdTeacherLocator);
        Assert.assertTrue(createdTeacher.isDisplayed());

        // 2. ASSERT message Instructor successfully saved is there:
        WebElement successfullyGeneratedMessage = Driver.getDriver().findElement(By.xpath("//p[text()='Instructor successfully saved']"));
        Assert.assertTrue(successfullyGeneratedMessage.isDisplayed());
    }

    @Test
    public void editExistingTeacher() throws InterruptedException {

        //clicking on Add teacher button
        waitUntilClickable(30, teachersPage.addTeacherButton);
        teachersPage.addTeacherButton.click();

        //filling out the form
        String firstName = faker.name().firstName();
        teachersPage.firstNameField.sendKeys(firstName);
        String lastName = faker.name().lastName();
        String fullName = firstName + " " + lastName;
        teachersPage.lastNameField.sendKeys(lastName);
        teachersPage.phoneNumberField.click();
        String oldPhoneNumber = faker.number().digits(10);
        teachersPage.phoneNumberField.sendKeys(oldPhoneNumber);
        teachersPage.emailField.sendKeys(faker.internet().emailAddress());
        teachersPage.specializationField.sendKeys("QA");
        teachersPage.chooseCoursesDropDown.click();
        teachersPage.SDETdropDownOption.click();

        teachersPage.SDETdropDownOption.sendKeys(Keys.ESCAPE);
        teachersPage.addButton.click();

        //Clicking on Actions menu, but have to wait first
        By createdTeacherLocator = By.xpath(
                "//td[contains(@class,'MuiTableCell-body') and normalize-space()='" + fullName + "']");
        waitUntilVisibleByLocator(30, createdTeacherLocator);

        WebElement actionsMenu = Driver.getDriver().findElement(By.xpath("//tr[td[normalize-space()='" + fullName + "']]//button"));
        actionsMenu.click();

        //Clicking on Edit
        WebElement editButton = Driver.getDriver().findElement(By.xpath("(//ul[@role='menu' and not(contains(@style,'display: none'))]//li[contains(.,'Edit')])[1]"));
        editButton.click();

        //Changing the phone number
        waitUntilVisible(30, teachersPage.phoneNumberField);
        teachersPage.phoneNumberField.click();
        String newPhoneNumber = faker.number().digits(10);
        teachersPage.phoneNumberField.sendKeys(newPhoneNumber);

        //Changing the specialization. Have to delete previous one first.
        actions.click(teachersPage.specializationField)     // focus the field
                .keyDown(Keys.CONTROL)                      // hold CTRL
                .sendKeys("a")                              // select all
                .keyUp(Keys.CONTROL)                        // release CTRL
                .sendKeys(Keys.DELETE)                      // delete
                .sendKeys("SDET")                           // type new value
                .perform();

        WebElement saveButton = Driver.getDriver().findElement(By.xpath("//button[text()='Save']"));
        saveButton.click();

        waitUntilVisibleByLocator(30, By.xpath("//p[text()='Instructor successfully updated']"));

        // ASSERT message Instructor successfully updated is there:
        WebElement successfullyGeneratedMessage = Driver.getDriver().findElement(By.xpath("//p[text()='Instructor successfully updated']"));
        Assert.assertTrue(successfullyGeneratedMessage.isDisplayed());
    }
}
