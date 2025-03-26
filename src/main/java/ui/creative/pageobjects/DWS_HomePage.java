package ui.creative.pageobjects;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ui.creative.componentgroups.ReusableLibrary;

public class DWS_HomePage extends ReusableLibrary
{
		WebDriver driver;
		
		public DWS_HomePage(WebDriver driver) {
			super(driver);
			this.driver=driver;
			PageFactory.initElements(driver, this);
		}
		
		
	
		
}
