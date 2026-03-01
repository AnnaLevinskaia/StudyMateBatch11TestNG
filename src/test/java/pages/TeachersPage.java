package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BaseUI;
import utils.Driver;

public class TeachersPage extends BaseUI {

    public TeachersPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//button[text()='Add teacher']")
    public WebElement addTeacherButton;

    @FindBy (css = "input[name='name']")
    public WebElement firstNameField;

    @FindBy (css = "input[name='lastName']")
    public WebElement lastNameField;

    @FindBy (css = "input[name='phoneNumber']")
    public WebElement phoneNumberField;

    @FindBy (css = "input[name='email']")
    public WebElement emailField;

    @FindBy (css = "input[name='specialization']")
    public WebElement specializationField;

    @FindBy (css = "div[role='button']")
    public WebElement chooseCoursesDropDown;

    @FindBy (css = "li[role='option']")
    public WebElement SDETdropDownOption;

    @FindBy (xpath = "//button[normalize-space()='Add']")
    public WebElement addButton;

    @FindBy (xpath = "//button[@type='submit']/preceding-sibling::button")
    public WebElement cancelButton;
}
