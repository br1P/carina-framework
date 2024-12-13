package android.aptoide;

import com.zebrunner.carina.utils.android.IAndroidUtils;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = HomePage.class)
public class HomePage extends BasePage implements IAndroidUtils {
    public HomePage(WebDriver driver) {
        super(driver, PAGES.HOME);
        setUiLoadedMarker(mainContent);
    }



    @FindBy(id = "user_actionbar_icon")
    public ExtendedWebElement actionbarIcon;

    @FindBy(id = "main_home_container_content")
    public ExtendedWebElement mainContent;

    @FindBy(id = "games_chip")
    public ExtendedWebElement gamesBtn;

    @FindBy(id = "apps_chip")
    public ExtendedWebElement appsBtn;

    @FindBy(id = "featured_graphic_list")
    public ExtendedWebElement featuredAppList;

    @FindBy(id = "root_cardview")
    public ExtendedWebElement gotwSign;

    @FindBy(id = "appcoins_icon")
    public ExtendedWebElement coinIcon;

}
