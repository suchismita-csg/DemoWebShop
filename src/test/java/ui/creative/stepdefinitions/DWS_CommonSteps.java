package ui.creative.stepdefinitions;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import ui.creative.testcomponents.BaseTest;
import ui.creative.testcomponents.ExcelFileReader;
import ui.creative.testcomponents.PageObjectManager;
import ui.creative.testcomponents.TestSetup;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DWS_CommonSteps extends BaseTest {

	TestSetup testSetup;

	public DWS_CommonSteps(TestSetup testSetup) throws IOException {
		this.testSetup = testSetup;
	}

//Login

	@Given("I sign in to the Demo Web Shop with valid credentials")
	public void i_sign_in_to_the_demo_web_shop_with_valid_credentials() throws Exception {

	    test = logInfo.get().createNode("I sign in to the Demo Web Shop with valid credentials");

	    Map<String, String> testData = ExcelFileReader.getDataInMap("DemoWebShopLogin", "TEST");

	    reportInfo("I'm validating if the DemoWebShop has launched successfully or not.");

	    String currenturl = testSetup.pageObjectManager.getDemoWebShopCommonPage().launchDemoWebShop();

	    if (currenturl != null && !currenturl.isEmpty() && currenturl.equals(testData.get("URL"))) {
	        reportPass("URL", testData.get("URL"), currenturl);
	    } else {
	        reportFail("URL", testData.get("URL"), currenturl);
	    }
	    

	    reportInfo("I'm validating if the DemoWebShop login is successful.");

	    boolean ImgStatus = testSetup.pageObjectManager.getDemoWebShopCommonPage().LogintoDemoWebshop();

	    if (ImgStatus) {
	        reportPass("LoginStatus", "login successful.", "login successful.");
	    } else {
	        reportFail("LoginStatus", "Login failed.", "Login failed.");
	    }

	    reportScreenshot(testSetup.driver);
	}


	@Given("I close the browser window")
	public void i_close_the_browser_window() {
		
		logInfo.get().createNode("I close the browser window");
		testSetup.pageObjectManager.CommonPage.CloseDemoWebShop();

	}
	
	@Then("I navigate to Books page")
	public void i_navigate_to_books_page() throws IOException {
	    test=logInfo.get().createNode("I navigate to Books page");
	    
	    String pageTitle = testSetup.pageObjectManager.getDemoWebShopBooksPage().navigateToBookPage();
	    System.out.println("PageTitle"+pageTitle);

	    String expectedTitle = "BOOKS"; 

	    if (pageTitle.equals(expectedTitle)) {
	    	reportInfo("NavigateToBookPage");
	    	reportScreenshot(testSetup.driver);
	        reportPass("NavigationSuccessful", expectedTitle, pageTitle);
	    } else {
	        reportFail("Navigation Failed", expectedTitle, pageTitle);
	    }
	}

	
	
	
	



}
