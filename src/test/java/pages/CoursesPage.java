package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BaseUI;
import utils.Driver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class CoursesPage extends BaseUI {
    WebDriver driver = Driver.getDriver();

    public CoursesPage(){
        PageFactory.initElements(driver,this);
    }

    @FindBy(tagName  = "button")
    public WebElement createCourseBtn;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement uploadThePhoto;

    @FindBy(name ="courseName")
    public WebElement courseNameInput;

    @FindBy(xpath = "//input[@name='dateOfFinish']")
    public WebElement dateInput;

    public String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yy"));

    @FindBy(xpath = "//textarea[@name='description']")
    public WebElement description;

    @FindBy (xpath = "//button[@type='submit']")
    public WebElement createBtn;

    @FindBy(xpath = "//p[contains(text(),'successfully')]")
    public WebElement successAlert;

    @FindBy(xpath = "(//button//*[name()='svg'])[2]")
    public WebElement editSign;

    @FindBy(xpath = "(//li[text()='Edit'])[1]")
    public WebElement edit;

    @FindBy(xpath = "//p[contains(text(),'successfully')]")
    public WebElement successfullyUpdatedAlert;

    @FindBy(xpath = "(//li[text()='Delete'])[1]")
    public WebElement delete;

    @FindBy(xpath = "//button[text()='Delete']")
    public WebElement deleteBtn;

    @FindBy(xpath = "//p[contains(text(),'successfully')]")
    public WebElement successfullyDeletedAlert;

    @FindBy(xpath = "//li[text()='Assign teacher']")
    public WebElement assignBtn;

    @FindBy(xpath = "//div[@aria-haspopup='listbox']")
    public WebElement selectBtn;

    @FindBy(xpath = "//li[@role='option']")
    public List <WebElement> teachers;

    @FindBy(xpath = "//button[text()='Save']")
    public WebElement saveBtn;

    @FindBy(xpath = "//p[contains(text(),'successfully')]")
    public WebElement successAssignedAlert;










//    public void selectRandomCourse() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//
//        List<WebElement> courses = wait.until
//                (ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath
//                        ("//div[@class='css-yys58j']")));
//
//
//        int random = new Random().nextInt(courses.size());
//        courses.get(random).click();
//    }


}
