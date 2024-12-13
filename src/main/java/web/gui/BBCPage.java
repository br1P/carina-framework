package web.gui;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class BBCPage {
    private WebDriver driver;

    // Elementos de la página, ajustamos el selector al contenido de BBC
    private By articleSelector = By.cssSelector("article");  // Selector ajustado para BBC

    public BBCPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Método para abrir la página de BBC
    public void openPage() {
        driver.get("https://www.bbc.com/");
    }

    // Verificar que el título de la página sea correcto
    public boolean isTitleCorrect() {
        return driver.getTitle().contains("BBC");
    }

    // Desplazarse por la página
    public void scrollPage(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }

    // Esperar hasta que el contenido sea visible
    public WebElement waitForContentToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(articleSelector));
    }

    // Verificar si el contenido es visible
    public boolean isContentDisplayed(WebElement content) {
        return content.isDisplayed();
    }
}