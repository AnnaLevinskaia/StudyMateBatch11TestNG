package listeners;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.Driver;

import java.io.File;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        try {
            WebDriver driver = Driver.getDriver();
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File("target/screenshots/" + result.getName() + ".png"));
        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }
}
