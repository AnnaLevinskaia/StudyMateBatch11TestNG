package tests;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import pages.StudentsPage;
import utils.BaseUI;
import utils.ConfigurationReader;
import utils.Driver;

public class StudentTests extends BaseUI {

    LoginPage loginPage;
    MainPage mainPage;
    StudentsPage studentsPage;
    Faker faker;

    @BeforeMethod
    public void setUp() {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
        loginPage = new LoginPage();
        mainPage = new MainPage();
        studentsPage = new StudentsPage();
        faker = new Faker();
    }

    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }

    @Test(groups = {"smoke"})
    public void addStudentTest() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(
                ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password")
        );

        waitAndClick(mainPage.students);
        studentsPage.clickAddStudent();

        String first = faker.name().firstName();
        String last = faker.name().lastName();
        String email = "auto_" + System.currentTimeMillis() + "@gmail.com";
        String phone = "312" + faker.number().digits(7);

        studentsPage.fillStudentForm(first, last, email, phone);
        studentsPage.submit();

        Assert.assertTrue(studentsPage.isSuccessVisible(), "Success alert was NOT displayed!");
    }

        @Test(groups = {"smoke"})
        public void addStudentNegativeTest_requiredFields () throws InterruptedException {

            loginPage.loginWithCorrectCredentials(
                    ConfigurationReader.getProperty("username"),
                    ConfigurationReader.getProperty("password")
            );


            waitAndClick(mainPage.students);
            studentsPage.clickAddStudent();

            // leave fields empty → button should be disabled
            Assert.assertTrue(studentsPage.isAddButtonDisabled(), "Add button should be disabled when fields are empty");
        }
    }


