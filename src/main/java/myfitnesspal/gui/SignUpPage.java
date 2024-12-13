package myfitnesspal.gui;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import myfitnesspal.components.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignUpPage extends HomePage {

    @FindBy(xpath = "//button")
    ExtendedWebElement continueBtn;
//
    @FindBy(xpath = "//button")
    ExtendedWebElement bigStepNext;
//
    @FindBy(xpath = "//form")
    ExtendedWebElement form;


    @FindBy(xpath = "//form")
    InputNameM nameModal;

    @FindBy(xpath = "//form")
    GoalsM goalsM;

    @FindBy(xpath = "//form")
    LooseWeightM looseWeightM;


    @FindBy(xpath = "//button")
    ExtendedWebElement generalNext;

    @FindBy(xpath = "//form")
    ActivityM activityM;

    @FindBy(xpath = "//form")
    BirthM birthM;

    @FindBy(xpath = "//form")
    PhysicalM physicalM;

    @FindBy(xpath = "//form")
    WeeklyGoalsM weeklyGoalM;

    @FindBy(xpath = "//form")
    UserM UserM;

    @FindBy(xpath = "//form")
    UserNameM userNameM;

    @FindBy(xpath = "//form")
    PermissionM permissionM;

    @FindBy(xpath = "//form")
    DailyGoalM dailyGoalM;



    public SignUpPage(WebDriver driver) {
        super(driver);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waiting));//wait 10 secs
        wait.until(ExpectedConditions.urlContains("create"));
    }

    public void clickContinue(){
        continueBtn.click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waiting));
        wait.until(ExpectedConditions.urlContains("input-name"));
    }

    //page 1 of form enter the name
    public void enterName(String name){
        nameModal.enterName(name);
    }
    public void clickNextFromName(){
        nameModal.clickNext();
    }
    //page 2 goals
    public void selectGoalOption(){

        goalsM.selectFirstOption();
    }

    public void clickNextFromGoals(){
        goalsM.clickNext();
    }

    public void clickNextBigStep(){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waiting));
        wait.until(ExpectedConditions.urlContains("big-step"));
        bigStepNext.click();
    }

    // page 3 opt loose weight
    public void clickGeneralNext(){
        WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(waiting));
        wait.until(ExpectedConditions.urlContains("affirmation"));
        generalNext.click();
    }

    public void selectLoseWeightOption(){

        looseWeightM.selectFirstOption();
    }

    public void selectNextFromLoseWeight(){
        looseWeightM.clickNext();
    }
    //part 4 activty level
    public void selectActivityOption(){
        activityM.selectFirstOption();
    }

    public void selectNextFromActivity(){
        activityM.clickNext();
    }
   //part 5 birth and sex
    public void selectSex(){
        birthM.selectSex();
    }

    public void selectBirthday(String birthday){
        birthM.selectBirthday(birthday);
    }

    public void selectNextFromBirth(){
        birthM.clickNext();
    }
    //part 6 height and weight

    public void selectHeight(String height){
        physicalM.enterHeight(height);
    }

    public void selectWeight(String weight){
        physicalM.enterWeight(weight);
    }

    public void selectIdealWeight(String weight){
        physicalM.enterIdealWeight(weight);
    }

    public void selectNextFromPhysical(){
        physicalM.clickNext();
    }
    //part 7 weekly goals
    public void selectWeeklyGoals(){
        weeklyGoalM.selectFirstOption();
    }

    public void selectNextFromWeeklyGoals(){
        weeklyGoalM.clickNext();
    }

    //part 8 user email and password
    public void putEmail(String email){
        UserM.enterEmail(email);
    }

    public void putPassword(String password){
        UserM.enterPassword(password);
    }

    public void checkConditions(){
        UserM.checkTermsAndConditions();
    }

    public void selectNextFromUserPanel(){
        UserM.clickContinue();
    }

    public void putUsername(String username){
        WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(waiting));
        wait.until(ExpectedConditions.urlContains("username"));
        userNameM.enterUserName(username);
    }

    public void clickNextUsername(){
        userNameM.clickNext();
    }

    public void checkAll(){
        permissionM.acceptConsents();
    }

    public void clickFinish(){
        permissionM.clickFinish();
    }

    public String getCongratulationsMessage(){
        return dailyGoalM.getCongratulationsText();
    }

    public void clickNewsCheck(){
        dailyGoalM.clickNewsCheckBox();
    }


    @Override
    public boolean isPageOpened() {
        return getDriver().getCurrentUrl().contains("account/create");
    }
}
