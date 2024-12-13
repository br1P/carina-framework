package myfitnesspal.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class InputNameM extends AbstractUIObject
{
    public InputNameM(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
    @FindBy(xpath = "//input")
    ExtendedWebElement nameInput;

    @FindBy(xpath = "//button")
    ExtendedWebElement nextBtn;

    public void enterName(String name){
        nameInput.type(name);
    }

    public void clickNext(){
        nextBtn.click();
    }
}
