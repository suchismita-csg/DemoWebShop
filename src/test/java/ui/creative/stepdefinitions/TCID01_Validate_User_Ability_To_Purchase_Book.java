package ui.creative.stepdefinitions;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import com.aventstack.extentreports.model.Test;

import ui.creative.testcomponents.BaseTest;
import ui.creative.testcomponents.ExcelFileReader;
import ui.creative.testcomponents.TestSetup;

public class TCID01_Validate_User_Ability_To_Purchase_Book extends BaseTest {

	TestSetup testSetup;

	public TCID01_Validate_User_Ability_To_Purchase_Book(TestSetup testSetup) throws IOException {
		this.testSetup = testSetup;
	}
	
	
	@Then("I select book and add to cart")
	public void i_select_book_and_add_to_cart() throws IOException {
	    test=logInfo.get().createNode("I select book and add to cart");
	    reportInfo("I select book and add to cart");
	    testSetup.pageObjectManager.getDemoWebShopBooksPage().SelectBookAndaddtoCart();
	    reportScreenshot(testSetup.driver);
	}

	@Then("I order the book")
	public void i_order_the_book() throws IOException {
		test=logInfo.get().createNode("I order the book");
		 reportInfo("I order the book");
		 testSetup.pageObjectManager.getDemoWebShopBooksPage().OrderBook();
		 reportScreenshot(testSetup.driver);
	}

	@Then("I validate book purchased successfully")
	public void i_validate_book_purchased_successfully() throws IOException, InterruptedException {
	    test = logInfo.get().createNode("I validate book purchased successfully");
	    reportInfo("I validate book purchased successfully");
	    
	    String successMessage = testSetup.pageObjectManager.getDemoWebShopBooksPage().validateBookPurchase();
	    
	    String expectedMessage = "Your order has been successfully processed!"; 

	    if (successMessage.equals(expectedMessage)) {
	        reportPass("Book purchased successfully", successMessage, expectedMessage);
	    } else {
	        reportFail("Book purchase failed", successMessage, expectedMessage);
	    }

	    reportScreenshot(testSetup.driver);
	}




	
}
