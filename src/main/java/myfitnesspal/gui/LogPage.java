package myfitnesspal.gui;

import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LogPage extends HomePage {

    @FindBy(xpath = "//a/p[text()=\"Sign Up\"]")
    ExtendedWebElement signUpBtn;


    public SignUpPage goToSignUp() {
        signUpBtn.click();
        return new SignUpPage(getDriver());
    }


    public LogPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void open() {
        getDriver().navigate().to(R.CONFIG.get("url_login"));
        if (iframe.isElementPresent()) {
            getDriver().switchTo().frame(iframe.getElement());
            acceptCookies.clickIfPresent(waiting);
            getDriver().switchTo().defaultContent();
        }
    }
}
