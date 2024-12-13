package android.aptoide;


import com.zebrunner.carina.utils.android.IAndroidUtils;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class StartPage extends AbstractPage implements IAndroidUtils {
    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"cm.aptoide.pt:id/title\"]")
    private ExtendedWebElement title;

    @FindBy(id = "skip_button")
    private ExtendedWebElement skipBtn;

    public StartPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(title);
    }


    public HomePage skipStart() {
        this.skipBtn.click();
        return new HomePage(getDriver());
    }
}