package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.BaseUI;
import utils.ConfigurationReader;
import utils.Driver;

public class LoginTests extends BaseUI {


    LoginPage loginPage = new LoginPage();

    @BeforeMethod
    public void setUp() {
        Driver.getDriver();       // IMPORTANT! Open browser
        loginPage = new LoginPage();
    }


    @AfterMethod
    void tearDown() {
        Driver.closeDriver();
    }

    @Test(groups = "smoke")
    void happyPassLoginTest() throws InterruptedException {
        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"),
                ConfigurationReader.getProperty("password"));

        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("admin"));
    }

    @Test(groups = "regression")
    void sadPassLoginTest() {
        loginPage.loginWithWrongCredentials(
                ConfigurationReader.getProperty("wrongUserEmail"),
                ConfigurationReader.getProperty("wrongPassword")
        );

        String actualMessage = loginPage.errorMessage.getText();

        // Change your expected string to match the "Actual" result from your error
        String expectedMessage = "User with email admin1@codewise.com not found";

        Assert.assertEquals(actualMessage, expectedMessage, "The error message on the UI does not match!");
    }

//    @Test
//    void failedLoginTestWithWrongCredentials() throws InterruptedException {
//        loginPage.loginWithCorrectCredentials("admin@gmail.com", "incorrectPassword");
//        waitUntilVisible(2, loginPage.errorMessage);
//        Assert.assertTrue(loginPage.errorMessage.isDisplayed());
//    }
//
//    @Test
//    void failedLoginTestWithWrongPassword() throws InterruptedException {
//        loginPage.loginWithCorrectCredentials(ConfigurationReader.getProperty("username"), "incorrectPassword");
//        Assert.assertTrue(loginPage.invalidCredentialsAlert.isDisplayed());
//    }

}
