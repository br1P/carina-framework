package myfitnesspal.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class UserM extends SelectOptionM{
    @FindBy(xpath = "//input[@id=\"Email address\"]")
    ExtendedWebElement emailInput;

    @FindBy(xpath = "//input[@id=\"Create a password\"]")
    ExtendedWebElement passwordInput;

    @FindBy(xpath = "//input[@type=\"checkbox\"]")
    ExtendedWebElement ConditionsCheck;

    @FindBy(xpath = "//span[text()=\"Continue\"]/..")
    ExtendedWebElement continueBtn;

    public UserM(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void enterEmail(String email){
        emailInput.type(email);
    }

    public void enterPassword(String password){
        passwordInput.type(password);
    }

    public void checkTermsAndConditions(){
        ConditionsCheck.click();
    }

    public void clickContinue(){
        continueBtn.click();
    }
}
