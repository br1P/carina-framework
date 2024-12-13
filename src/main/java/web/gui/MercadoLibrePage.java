package web.gui;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MercadoLibrePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MercadoLibrePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "accept-cookies")
    private WebElement cookiesButton;

    @FindBy(name = "as_word")
    private WebElement searchField;

    @FindBy(xpath = "//button[@type='submit' and contains(@class, 'nav-search-btn')]")
    private WebElement searchButton;

    @FindBy(xpath = "//span[contains(@class, 'ui-search-search-result__quantity-results')]")
    private WebElement resultsHeader;

    @FindBy(xpath = "//h2[contains(@class, 'ui-search-item__title')]")
    private List<WebElement> productTitles;

    @FindBy(xpath = "//a[@class='nav-menu-categories-link' and text()='Categorías']")
    private WebElement categoriesButton;

    // Actions
    public void openPage(String url) {
        driver.get(url);
    }

    public void clickCategories() {
        wait.until(ExpectedConditions.elementToBeClickable(categoriesButton)).click();
    }

    public void acceptCookies() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cookiesButton)).click();
        } catch (TimeoutException e) {
            System.out.println("No cookies acceptance button found.");
        }
    }

    public void searchFor(String query) {
        wait.until(ExpectedConditions.visibilityOf(searchField)).sendKeys(query);
        searchButton.click();
    }

    public boolean isResultsHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(resultsHeader)).isDisplayed();
    }

    public String getResultsHeaderText() {
        return resultsHeader.getText();
    }

    public List<WebElement> getProductTitles() {
        return productTitles;
    }

    public boolean areAllProductTitlesRelevant(String keyword) {
        for (WebElement title : productTitles) {
            if (!title.getText().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("Irrelevant product title: " + title.getText());
                return false;
            }
        }
        return true;
    }

    public void clickCategory(String categoryName) {
        By categoryLink = By.xpath("//a[contains(text(), '" + categoryName + "')]");
        WebElement categoryButton = wait.until(ExpectedConditions.elementToBeClickable(categoryLink));
        categoryButton.click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateBack() {
        driver.navigate().back();
    }
}