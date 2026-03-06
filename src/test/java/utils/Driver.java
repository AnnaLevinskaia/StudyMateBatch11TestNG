package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Driver {

    private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    public static WebDriver getDriver() {

        String browser = ConfigurationReader.getProperty("browser");
        boolean headless = Boolean.parseBoolean(ConfigurationReader.getProperty("headless"));

        if (driverPool.get() != null) {
            return driverPool.get();
        }

        WebDriver driver;

        // Handle browser with Chrome options
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();

        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("safebrowsing.enabled", false);
        prefs.put("safebrowsing.disable_download_protection", true);

        options.setExperimentalOption("prefs", prefs);

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-features=PaintHolding");
        }

        switch (browser) {
            case "chrome":
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "safari":
                driver = new SafariDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException
                        ("please provide the browser name from following options: chrome, firefox, safari, edge");
        }

        // Don't maximize in headless
        if (!headless) {
            driver.manage().window().maximize();
        }

        //this waits for page to fully loaded
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driverPool.set(driver);
        return driverPool.get();
    }

    public static void closeDriver() {
        WebDriver driver = driverPool.get();
        if (driver != null) {
            driver.quit();
            driverPool.remove();
        }
    }
}
