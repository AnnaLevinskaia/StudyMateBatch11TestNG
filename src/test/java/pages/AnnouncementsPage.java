package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Driver;

import java.util.List;

public class AnnouncementsPage {

    WebDriver driver = Driver.getDriver();

    public AnnouncementsPage() {

        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement addButton;

    @FindBy(xpath = "//header[@class='css-1v74z38']/button")
    public WebElement addNewAnnouncementTab;

    @FindBy(xpath = "//li[normalize-space()='Announcements']")
    public WebElement announcementTab;

    @FindBy(xpath = "//button[text()='Delete']")
    public WebElement confirmationDelete;

    @FindBy(xpath = "//li[contains(.,'Delete')]")
    public WebElement deleteAnnouncement;

    //@FindBy (xpath = "//*[contains(text(),'Announcement successfully deleted')]")
    @FindBy(xpath = "//p[contains(text(),'successfully deleted')]")
    public WebElement deletedMessage;

    @FindBy(xpath = "//div[@id='mui-component-select-groups']")
    public WebElement dropdown;

    @FindBy(xpath = "//li[@role='menuitem' and normalize-space()='Edit']")
    public WebElement editButton;

    @FindBy(xpath = "//ul[@role='listbox']//li[@role='option' and not(@aria-disabled='true')]")
    public List<WebElement> groupLists;

    @FindBy(xpath = "//ul[@role='listbox']//li[@role='option']")
    public WebElement listBox;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveButton;

    //   @FindBy (xpath = "//p[text()='Announcement successfully saved']")
    @FindBy(xpath = "//*[contains(text(),'Announcement successfully saved')]")
    public WebElement successMessage;

    @FindBy(xpath = "//button[contains(@tabindex,'0') and contains(@aria-haspopup,'true')]")
    public List<WebElement> threeDots;

    @FindBy(name = "text")
    public WebElement text;

}
