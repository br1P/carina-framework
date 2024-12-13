package com.zebrunner.carina.demo;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.utils.R;
import myfitnesspal.gui.LogPage;
import myfitnesspal.gui.SignUpPage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import myfitnesspal.objects.MFPUser;

import java.time.Duration;

public class MyFitnessPalSignInTest implements IAbstractTest {

    @Test( description = "test sign up and create account")
    @MethodOwner(owner = "Bruno")
    public void testSignUp(){
        WebDriver driver = getDriver();
        LogPage logPage = new LogPage(driver);
        logPage.open();

        SignUpPage signUp = logPage.goToSignUp();

        Assert.assertTrue(signUp.isPageOpened()); //looks fine until here
        signUp.clickContinue();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("input-name"));


        MFPUser user = new MFPUser();
        user.setName(R.TESTDATA.get("user.name"));
        user.setBirthDate(R.TESTDATA.get("user.birthDate"));
        user.setHeightFeet(R.TESTDATA.get("user.heightFeet"));
        user.setWeight(R.TESTDATA.get("user.weight"));

        user.setUsername(R.TESTDATA.get("user.username"));
        user.setEmail(R.TESTDATA.get("user.email"));
        user.setPassword(R.TESTDATA.get("user.password"));

        signUp.enterName(user.getName()); //form part 1 enter your name
        signUp.clickNextFromName();

        signUp.selectGoalOption(); //form part 2 select goals
        signUp.clickNextFromGoals();


        signUp.clickNextBigStep();// click next on the message


        signUp.selectLoseWeightOption();//form part 3 select loose weight opt
        signUp.selectNextFromLoseWeight();

        signUp.clickGeneralNext();

        signUp.selectActivityOption();//form part 4 select activity levels
        signUp.selectNextFromActivity();

        signUp.selectSex(); //form part 5 select sex and birthDate
        signUp.selectBirthday(user.getBirthDate());
        signUp.selectNextFromBirth();

        signUp.selectHeight(user.getHeightFeet());//form part 6 weight and height(in feet)
        signUp.selectWeight(user.getWeight());
        signUp.selectIdealWeight(user.getWeight());
        signUp.selectNextFromPhysical();

        signUp.selectWeeklyGoals(); //form part 7 weekly goals
        signUp.selectNextFromWeeklyGoals();

        signUp.putEmail(user.getEmail());//form part 8 email and password
        signUp.putPassword(user.getPassword());
        signUp.checkConditions();
        signUp.selectNextFromUserPanel();

        signUp.putUsername(user.getUsername());
        signUp.clickNextUsername();

        signUp.checkAll();
        signUp.clickFinish();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            Thread.sleep(9000); // Pause for me
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(signUp.getCongratulationsMessage(),"Congratulations!","Congratulations did not appear :/");

        signUp.clickNewsCheck();



    }

}