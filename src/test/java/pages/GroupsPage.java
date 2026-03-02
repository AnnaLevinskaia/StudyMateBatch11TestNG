package pages;

import com.github.javafaker.service.RandomService;
import org.openqa.selenium.By;
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

public class GroupsPage extends BaseUI {

    WebDriver driver = Driver.getDriver();

    public GroupsPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[text()='Create group']")
    public WebElement createGroupBtn;

    @FindBy(css = "input[type='file']")
    public WebElement groupImage;

    @FindBy(xpath = "//input[@name='name']")
    public WebElement groupName;

    @FindBy(xpath = "//input[@name='dateOfFinish']")
    public WebElement groupDate;

    @FindBy(xpath = "//textarea[@name='description']")
    public WebElement groupDescription;

    @FindBy(xpath = "//button[text()='Create']")
    public WebElement createBtn;

    @FindBy(xpath = "//button[text()='Cancel']")
    public WebElement cancelBtn;

    @FindBy(xpath = "//div[@class='MuiAlert-message css-1xsto0d']")
    public WebElement successAlert;

    @FindBy(css = "div.MuiPaper-root.MuiCard-root[blocked='false']:nth-of-type(1) button[aria-haspopup='true']")
    public WebElement groupSubMenu;

    @FindBy(xpath = "//li[text()='Edit group']")
    public WebElement editBtn;

    @FindBy(xpath = "//li[text()='Delete group']")
    public WebElement deleteGroupBtn;

    @FindBy(xpath = "//button[text()='Delete']")
    public WebElement deleteBtn;

    public String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yy"));

    @FindBy(xpath = "//div[text()='Dustin D. Furniture']")
    public WebElement createdGroupName;

    @FindBy(xpath = "//p[text()='Hoppe']")
    public WebElement descr;



    @FindBy(css = "div.MuiPaper-root.MuiCard-root")
    public List<WebElement> groupCards;

    public void createNewGroup() {
        waitAndClick(createGroupBtn);
    }

    private By threeDotsBtn = By.cssSelector("button[aria-haspopup='true']");

    public void deleteRandomGroup() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Find cards fresh (avoid stale elements)
        By cardsLocator = By.cssSelector("div.MuiPaper-root.MuiCard-root[blocked='false']");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(cardsLocator, 0));
        List<WebElement> cards = driver.findElements(cardsLocator);

        WebElement randomCard = cards.get(new Random().nextInt(cards.size()));

        // 3-dots in THIS card
        WebElement dots = randomCard.findElement(By.cssSelector("button[aria-haspopup='true']"));

        // scroll into view (important!)
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", dots);

        wait.until(ExpectedConditions.elementToBeClickable(dots)).click();

        // MUI menu that is currently open (visible)
        By visibleDeleteItem = By.xpath(
                "//ul[contains(@class,'MuiMenu-list') and not(ancestor::*[contains(@style,'visibility: hidden')])]" +
                        "//li[@role='menuitem' and normalize-space()='Delete group']"
        );

        wait.until(ExpectedConditions.elementToBeClickable(visibleDeleteItem)).click();

        // Confirm in dialog
        By confirmDelete = By.xpath("//button[normalize-space()='Delete']");
        wait.until(ExpectedConditions.elementToBeClickable(confirmDelete)).click();
    }
}
