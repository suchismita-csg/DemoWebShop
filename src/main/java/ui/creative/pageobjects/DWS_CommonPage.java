package ui.creative.pageobjects;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ui.creative.componentgroups.ReusableLibrary;
import ui.creative.testcomponents.ExcelFileReader;
import ui.creative.testcomponents.TestSetup;

public class DWS_CommonPage extends ReusableLibrary
{
	WebDriver driver;

	public DWS_CommonPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath = "//a[@class='ico-login']")		
	WebElement LoginBtn;
 
    @FindBy(xpath= "//div//input[@id='Email']")
    WebElement emailInputField;
	
	@FindBy(xpath = "//div//input[@id='Password']")		
	WebElement PassWordInputField;
	
	@FindBy(xpath = "//div//input[@value='Log in']")		
	WebElement SignInBtn;
	
	
	@FindBy(xpath = "//img[@alt='Tricentis Demo Web Shop']")		
	WebElement DemoImg;
	
	
	public String launchDemoWebShop() throws Exception {
		
		Map<String, String> testData = ExcelFileReader.getDataInMap("DemoWebShopLogin", "TEST");
		driver.get(testData.get("URL"));
		String currenturl = driver.getCurrentUrl();
		return currenturl;
	}
	
	public  boolean LogintoDemoWebshop() throws Exception {
		
		Map<String, String> testData = ExcelFileReader.getDataInMap("DemoWebShopLogin", "TEST");
		waitForElementToBeClickableThenClick(LoginBtn);
		waitForElementToBeClickableThenSendkeys(emailInputField, testData.get("USERNAME"));
		waitForElementToBeClickableThenSendkeys(PassWordInputField, testData.get("PASSWORD"));
		waitForElementToBeClickableThenClick(SignInBtn);
		
		if (DemoImg.isDisplayed()) {
		    return true;
		} else {
		   return false;
		}


	}
	
	
	
	public void CloseDemoWebShop() {
       
		driver.close();
		


	}



}
