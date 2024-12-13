package myfitnesspal.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class UserNameM extends SelectOptionM {
    public UserNameM(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @FindBy(xpath = ".//input[@id=\"Create a username\"]")
    ExtendedWebElement userNameInput;

    @FindBy(xpath = ".//span[text()=\"Next\"]/..")
    ExtendedWebElement nextBtn;

    public void clickNext(){
        nextBtn.click();
    }

    public void enterUserName(String userName){
        userNameInput.getElement().sendKeys(Keys.CONTROL + "a");
        userNameInput.getElement().sendKeys(Keys.BACK_SPACE);
        userNameInput.type(userName);
    }




}
