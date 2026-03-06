package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    @FindBy(id = "mui-component-select-groupId")
    public WebElement groupDropdown;

    @FindBy(id = "mui-component-select-studyFormat")
    public WebElement studyFormatDropdown;

    @FindBy(xpath = "//*[@role='option']")
    public List<WebElement> dropdownOptions;

    @FindBy(css = "input[name='email'], input[placeholder*='Email'], input[type='email']")
    public WebElement emailInput;

    @FindBy(css = "input[name='phoneNumber'], input[name='phone'], input[placeholder*='Phone']")
    public WebElement phoneInput;

    @FindBy(css = "button[type='submit']")
    public WebElement submitBtn;

    @FindBy(css = "div[role='alert'], .Toastify__toast, .alert")
    public WebElement successAlert;

    @FindBy(xpath = "//p[contains(.,'Add student')]")
    public WebElement addStudentModalTitle;

    @FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Cancel']")
    public WebElement cancelBlockBtn;

    public void clickAddStudent() {
        waitAndClick(addStudentBtn);
        waitUntilVisible(20, firstNameInput);
    }

    public void closeBlockPopupIfVisible() {
        try {
            if (cancelBlockBtn.isDisplayed()) {
                waitAndClick(cancelBlockBtn);
            }
        } catch (Exception e) {
            // popup is not open, do nothing
        }
    }

    public void fillStudentForm(String first, String last, String email, String phone) throws InterruptedException {
        clearAndSendKeys(firstNameInput, first);
        clearAndSendKeys(lastNameInput, last);
        clearAndSendKeys(emailInput, email);
        clearAndSendKeys(phoneInput, phone);

        waitUntilVisible(10, groupDropdown);
        selectGroup();

        Thread.sleep(2000);
        closeBlockPopupIfVisible();
        Thread.sleep(1000);

        selectStudyFormat();
    }

    public void selectFirstOption(WebElement dropdown) {
        waitUntilVisible(10, dropdown);

        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].click();", dropdown);

        explicitWait(10).until(d ->
                Driver.getDriver().findElements(By.xpath("//*[contains(@class,'MuiMenuItem-root')]")).size() > 0
        );

        List<WebElement> options = Driver.getDriver()
                .findElements(By.xpath("//*[contains(@class,'MuiMenuItem-root')]"));

        WebElement firstOption = options.get(0);

        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", firstOption);

        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].click();", firstOption);
    }

    public void selectGroup() {
        selectFirstOption(groupDropdown);
    }

    public void selectStudyFormat() {
        waitUntilVisible(10, studyFormatDropdown);
        studyFormatDropdown.click();
        selectFirstOption(studyFormatDropdown);
    }

    public void submit() throws InterruptedException {
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", submitBtn);

        Thread.sleep(1000);

        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].click();", submitBtn);
    }

    public boolean isSuccessVisible() {
        waitUntilVisible(10, successAlert);
        return successAlert.isDisplayed();
    }


    public boolean isAddStudentModalClosed() {
        try {
            return !addStudentModalTitle.isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }
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


