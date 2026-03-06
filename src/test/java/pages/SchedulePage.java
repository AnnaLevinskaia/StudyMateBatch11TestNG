package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Driver;

public class SchedulePage {

        public SchedulePage(){
            PageFactory.initElements(Driver.getDriver(), this);
        }

        // ----------- View existing event -----------

        @FindBy(css = "div.fc-daygrid-day-events")
        public WebElement dayEventsContainer;

        @FindBy(xpath = "//a[contains(@class,'fc-daygrid-event') and contains(.,'math')]")
        public WebElement mathEvent;

        @FindBy(xpath = "//aside")
        public WebElement eventDetailsPanel;

        @FindBy(xpath = "//aside//*[contains(text(),'March')]")
        public WebElement detailsTitle;

        @FindBy(xpath = "//aside//*[contains(text(),'Publish')]")
        public WebElement detailsPublishStatus;

        @FindBy(xpath = "//aside//*[contains(text(),'09:00') and contains(text(),'10:00')]")
        public WebElement detailsTime;


        // ----------- Create event -----------

        @FindBy(xpath = "//button[contains(.,'Create event')]")
        public WebElement createBtn;

        @FindBy(id = "title")
        public WebElement nameInput;

        @FindBy(id = "startDate")
        public WebElement dateInput;

        public  void  selectTime(String timeText){
            Driver.getDriver().findElement(By.xpath("//ul[@role='listbox']//*[normalize-space()='" + timeText + "']")).click();

        }

        public  void setStartTime(String timeText){
            startTime.click();
            selectTime(timeText);
        }
        public  void setEndTime(String timeText){
            endTime.click();
            selectTime(timeText);
        }


        @FindBy(id = "startTime")
        public WebElement startTime;

        @FindBy(id = "endTime")
        public WebElement endTime;

        @FindBy(xpath = "//*[contains(text(),'Coding lesson')]")
        public WebElement createdEvent;

        @FindBy(xpath = "//label[normalize-space()='For whom']/following::div[@role='button'][1]")
        public WebElement forWhomDropdown;

        @FindBy(xpath = "(//ul[@role='listbox']//li)[1]")
        public WebElement firstPersonCheckbox;

        @FindBy(xpath = "//button[normalize-space()='Save']")
        public WebElement saveBtn;

        @FindBy(xpath = "//button[normalize-space()='Publish']")
        public WebElement publishBtn;



}
