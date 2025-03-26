package ui.creative.testcomponents;

import org.openqa.selenium.WebDriver;

import ui.creative.pageobjects.DWS_CommonPage;
import ui.creative.pageobjects.DWS_HomePage;
import ui.creative.pageobjects.DWS_BooksPage;




public class PageObjectManager {
	
	
	public WebDriver driver;
	public DWS_CommonPage CommonPage;
	public DWS_HomePage DWSH;
	public DWS_BooksPage BooksPage;
	
	
	

	public PageObjectManager(WebDriver driver) {
		this.driver=driver;
	}
	
	public DWS_CommonPage getDemoWebShopCommonPage() {
		CommonPage = new DWS_CommonPage(driver);
		return CommonPage;
	}
	
	public DWS_BooksPage getDemoWebShopBooksPage() {
		BooksPage = new DWS_BooksPage(driver);
		return BooksPage;
	}
	



	
	
}
