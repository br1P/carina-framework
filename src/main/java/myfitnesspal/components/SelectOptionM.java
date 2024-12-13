package myfitnesspal.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SelectOptionM extends AbstractUIObject {
    public SelectOptionM(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @FindBy(xpath = ".//div[@role =\"group\"]/button[%d]")
    ExtendedWebElement options;

    @FindBy(xpath = ".//nav//button")
    ExtendedWebElement nextBtn;

    public void clickNext(){
        nextBtn.click();
    }

    public void selectFirstOption() {
        options.format(1).click(); // Always selects the first option (index = 1)
    }
}
