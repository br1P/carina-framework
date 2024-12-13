package myfitnesspal.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class PermissionM extends SelectOptionM{
    public PermissionM(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @FindBy(xpath = "//input[@aria-label=\"Accept All\"]")
    ExtendedWebElement checkAllBox;

    @FindBy(xpath = "//span[text()=\"Finish\"]/..")
    ExtendedWebElement finishBtn;

    public void acceptConsents(){
        checkAllBox.click();
    }

    public void clickFinish(){
        finishBtn.click();
    }
}
