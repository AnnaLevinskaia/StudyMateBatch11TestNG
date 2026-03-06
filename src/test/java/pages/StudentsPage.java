package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BaseUI;
import utils.Driver;

import java.util.List;

public class StudentsPage extends BaseUI {
    WebDriver driver = Driver.getDriver();

    public StudentsPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(.,'Add student') or contains(.,'Add Student') or contains(.,'Add')]")
    public WebElement addStudentBtn;

    // VERY IMPORTANT: use flexible CSS with comma (OR)
    @FindBy(css = "input[name='name'], input[placeholder*='First']")
    public WebElement firstNameInput;

    @FindBy(css = "input[name='lastName'], input[placeholder*='Last']")
    public WebElement lastNameInput;

    @FindBy(css = "div[role='dialog'] #mui-component-select-groupId")
    public WebElement groupDropdown;

    @FindBy(css = "div[role='dialog'] #mui-component-select-studyFormatId")
    public WebElement studyFormatDropdown;


    @FindBy(css = "ul[role='listbox'], div[role='listbox']")
    public WebElement listBox;


    @FindBy(css = "ul[role='listbox'] li[role='option'], div[role='listbox'] [role='option']")
    public java.util.List<WebElement> dropdownOptions;

    @FindBy(css = "input[name='email'], input[placeholder*='Email'], input[type='email']")
    public WebElement emailInput;

    @FindBy(css = "input[name='phoneNumber'], input[name='phone'], input[placeholder*='Phone']")
    public WebElement phoneInput;

    @FindBy(xpath = "//button[text()='Add']")
    public WebElement submitBtn;

    @FindBy(css = "div[role='alert'], .Toastify__toast, .alert")
    public WebElement successAlert;

    public void clickAddStudent() {
        waitAndClick(addStudentBtn);
        waitUntilVisible(20, firstNameInput);
    }

    public void fillStudentForm(String first, String last, String email, String phone) {
        clearAndSendKeys(firstNameInput, first);
        clearAndSendKeys(lastNameInput, last);
        clearAndSendKeys(emailInput, email);
        clearAndSendKeys(phoneInput, phone);

        selectGroup();
        selectStudyFormat();
    }
    public void selectFirstOption(WebElement dropdown) {
     waitAndClick(dropdown);
     waitUntilVisible(10, listBox);

     // wait until options loaded
     explicitWait(10).until(d -> dropdownOptions.isEmpty());

     dropdownOptions.get(0).click();
    }
    public void selectGroup() {
     selectFirstOption(groupDropdown);
        }

        public void selectStudyFormat() {
     selectFirstOption(studyFormatDropdown);
    }

    public void submit() {
        waitAndClick(submitBtn);
    }

    public boolean isSuccessVisible() {
     waitUntilVisible(10, successAlert);
     return successAlert.isDisplayed();
    }

    // ADD NEW METHOD HERE
    public boolean isAddButtonDisabled() {

     String disabled = submitBtn.getAttribute("disabled");
     if (disabled != null) return true;

     String aria = submitBtn.getAttribute("aria-disabled");
     if ("true".equalsIgnoreCase(aria)) return true;

     String clazz = submitBtn.getAttribute("class");
     if (clazz != null && clazz.toLowerCase().contains("disabled")) return true;

     return !submitBtn.isEnabled();
    }
}


