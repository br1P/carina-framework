package android.aptoide;



import com.zebrunner.carina.utils.android.IAndroidUtils;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import com.zebrunner.carina.webdriver.locator.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;

public abstract class BasePage extends AbstractPage implements IAndroidUtils {
    final PAGES pages;

    public BasePage(WebDriver driver, PAGES section) {
        super(driver);
        this.pages = section;
    }

    @FindBy(id = "bottom_navigation")
    private ExtendedWebElement bottomNav;

    @Context(dependsOn = "bottomNav")
    @FindBy(id = "%s")
    private ExtendedWebElement navBtn;

    public final ExtendedWebElement getBtn(PAGES btn) {
        return this.navBtn.format(btn.elementId);
    }

    public <T extends BasePage> T navigateTo(PAGES pages) {
        ExtendedWebElement btnNav = this.getBtn(pages);
        btnNav.click();

        try {
            @SuppressWarnings("unchecked")
            Class<T> sectionPageClass = (Class<T>) pages.pageClass;
            Constructor<T> classConst = sectionPageClass.getDeclaredConstructor(WebDriver.class);
            return classConst.newInstance(getDriver());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isPageOpened(Duration timeout) {
        ExtendedWebElement btnInNav = this.getBtn(this.pages);
        String attr = btnInNav.getAttribute("selected");
        return super.isPageOpened(timeout) && attr.equals("true");
    }
}