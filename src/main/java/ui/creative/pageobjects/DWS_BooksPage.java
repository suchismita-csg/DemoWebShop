package ui.creative.pageobjects;

import java.util.Map;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ui.creative.componentgroups.ReusableLibrary;

public class DWS_BooksPage extends ReusableLibrary
{
		WebDriver driver;
		
		public DWS_BooksPage(WebDriver driver) {
			super(driver);
			this.driver=driver;
			PageFactory.initElements(driver, this);
		}
		
		
		
		@FindBy(xpath="(//a[contains(text(),'Books') or @class='top-menu-triangle'])[1]")
		WebElement bookTabBtn;
		
		@FindBy(xpath="//img[@alt='Picture of Computing and Internet']")
		WebElement ComputingandInternetBook;
		
		@FindBy(xpath="(//input[@value='Add to cart'])[1]")
		WebElement AddtoCart;
		
		@FindBy(xpath="//a[text()='shopping cart']")
		WebElement ShoppingCart;
		
		@FindBy(xpath = "//input[@name='removefromcart']")
		WebElement removeFromCartCheckbox;
		
		@FindBy(id = "termsofservice")
		WebElement termsOfServiceCheckbox;
		
		@FindBy(xpath = "//button[@id='checkout']")
		WebElement checkoutButton;

		@FindBy(xpath = "//input[@title='Continue']")
		WebElement continueButton;
		
		@FindBy(xpath="(//input[@title='Continue' and @type='button'])[2]")
		WebElement shippingAddressContinueBtn;
		
		@FindBy(xpath="(//input[@class='button-1 shipping-method-next-step-button' and @type='button'])")
		WebElement shippingMethodContinueBtn;
		
		@FindBy(xpath="(//input[@class='button-1 payment-method-next-step-button' and @type='button'])")
		WebElement paymentMethodContinueBtn;
		
		@FindBy(xpath="(//input[@class='button-1 payment-info-next-step-button' and @type='button'])")
		WebElement paymentInformationContinueBtn;
		
		@FindBy(xpath = "//input[@class='button-1 confirm-order-next-step-button']")
		WebElement confirmButton;
		
		@FindBy(xpath = "//div//strong[text()='Your order has been successfully processed!']")
		WebElement orderSuccessMessage;







			
			
			
		
		public String navigateToBookPage() {
			waitForElementToBeClickableThenClick(bookTabBtn);
			String BooksPageTitle = waitForPresenceOfElementThenGetText(bookTabBtn);
			return BooksPageTitle;
		}



	public void SelectBookAndaddtoCart() {
		waitForElementToBeClickableThenClick(AddtoCart);
	}
		
		public void OrderBook() {
			waitForElementToBeClickableThenClick(ShoppingCart);
			waitForElementToBeClickableThenClick(termsOfServiceCheckbox);
			waitForElementToBeClickableThenClick(checkoutButton);
			waitForElementToBeClickableThenClick(continueButton);
			waitForElementToBeClickableThenClick(shippingAddressContinueBtn);
			waitForElementToBeClickableThenClick(shippingMethodContinueBtn);
			waitForElementToBeClickableThenClick(paymentMethodContinueBtn);
			waitForElementToBeClickableThenClick(paymentInformationContinueBtn);
			waitForElementToBeClickableThenClick(confirmButton);
			
		}
		
		public String validateBookPurchase() throws InterruptedException {
			String SuccessMessage = waitForPresenceOfElementThenGetText(orderSuccessMessage);
			System.out.println(SuccessMessage);
			return SuccessMessage;
		}
		
		
	
		
}
