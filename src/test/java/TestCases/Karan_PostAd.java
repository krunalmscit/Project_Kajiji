package TestCases;

import driverManagement.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.AddDetailsPage;
import pageObjects.LandingPage;
import pageObjects.PostAdPage;
import pageObjects.SigninPage;

import java.util.List;

public class Karan_PostAd extends DriverManager {

    LandingPage landingPage;
    SigninPage signinPage;
    PostAdPage postAdPage;
    AddDetailsPage addDetailsPage;


    /*
    * Note: This is not a test case.
    * It is a common sign-in method that can be called when required to sign-inCreated by: Karan
    * Created on: March 18th, 2019
    * */
    @BeforeTest
    public void signinSuccessfully() {
        getBrowser();
        landingPage = new LandingPage(driver);
        signinPage = landingPage.clickOnSignin()
                .afterClickOnSignin()
                .enterEmail("kselproj.2019@gmail.com")
                .enterPassword("Kselproj2019*")
                .checkTheCheckBox()
                .clickLogin();
    }

    /* TC3 --> Title of the advertisment with exactly 8 characters
     * Description: Validate that the minimum number of characters accepted are 8. The next button will be disabled
     * if the characters are less than 8.
     * Created by: Karan
     * Created on March 17th, 2019
     * Updated by: Karan
     * Updated on: MArch 18th, 2019
     * */
    @Test
    public void postAdMin8chars(){
        WebElement avatar = driver.findElement(By.xpath("//div[text() = 'A']"));
        while(!avatar.isDisplayed()){
            signinPage.enterEmail("kselproj.2019@gmail.com")
                    .enterPassword("Kselproj2019*")
                    .clickLogin();
        }

            WebElement postAdBtn = driver.findElement(By.xpath("//a[@title='Post ad']"));
            setClickableWait(postAdBtn);
            postAdPage = landingPage
                    .clickOnPostAdBtn()
                    .afterClickingPostAdBtn();
            System.out.println("Post ad clicked!");

            WebElement titleField = driver.findElement(By.xpath("//*[@name='AdTitleForm']"));
            setClickableWait(titleField);
            postAdPage.editAdTitleFiled("11111111");


            WebElement NxtBtn = driver.findElement(By.xpath("//*[text() ='Next']"));
            Boolean NextBtnIsEnabled = NxtBtn.isEnabled();
            Assert.assertTrue(NextBtnIsEnabled, "true");
            setWait(NxtBtn);
            postAdPage.clickNextBtn();

    }


    @Test(dependsOnMethods = "postAdMin8chars")
    public void PriviewAdWitoutAddress(){
        System.out.println("Starting TestCase 3");

        WebElement text = driver.findElement(By.xpath("//*[text() = 'Select a category']"));
        setFluentWait(text);
        String expectedText = text.getText();
        Assert.assertEquals(expectedText, "Select a category", "Error: The text does not exist");

        WebElement selectCatgoriesSection = driver.findElement(By.xpath("//div[@class='allCategoriesContainer-1722591519']"));
        List<WebElement> selectACategory = selectCatgoriesSection.findElements(By.xpath("//li[@class='categoryListItem-3123839590']"));

        int numberOfCategories = selectACategory.size();

        for(int i = 0; i<numberOfCategories;i++){
            String currentCategory = selectACategory.get(i).getText();
            if(currentCategory.equals("Services")){
                selectACategory.get(i).click();
                break;
            }
        }

        System.out.println("Clicked on Services link");

        WebElement subCategorySection = driver.findElement(By.xpath("//ul[@class='categoryList-1515474558']"));
        List<WebElement> allCategories = subCategorySection.findElements(By.xpath("//li[@class='categoryListItem-3123839590']"));

        int totalNumberOfCategories = allCategories.size();

        for(int i = 0; i<totalNumberOfCategories;i++){
            WebElement currentElement = allCategories.get(i);
            setClickableWait(currentElement);
            String currentCategory = currentElement.getText();
            if(currentCategory.equals("Tutors & Languages")){
                allCategories.get(i).click();
                break;
            }
        }
        System.out.println("Clicked on -T and L- link");


        //WebElement tutorLanguageLink = driver.findElement(By.xpath("//*[text() = 'Tutors & Languages']"));
        WebElement descriptionFiled = driver.findElement(By.id("pstad-descrptn"));
        setClickableWait(descriptionFiled);
        postAdPage.enterDiscription("Description goes here!!!")
                .enterPhoneNumber("6479054166")
                .selectBasicPackage();
        WebElement previewBtn = driver.findElement(By.cssSelector("#PostAdPreview"));
                postAdPage.clickPreviewBtn();
        System.out.println("Preview button clicked");

        addDetailsPage = new AddDetailsPage(driver);
        addDetailsPage.validateErrorMsg();



    }
}