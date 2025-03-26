package ui.creative.testcomponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

//222import ui.creative.componentgroups.ConfigFileReader;
import ui.creative.componentgroups.ReusableLibrary;

public class TestSetup {
	
	public WebDriver driver;
	public PageObjectManager pageObjectManager;
	public BaseTest baseTest;
	public ReusableLibrary reusableLibrary;
	public ExcelFileReader excelReader;
	
	public TestSetup() throws IOException, InterruptedException{
		
		baseTest = new BaseTest();
		driver = baseTest.initializeDriver();	
		pageObjectManager = new PageObjectManager(driver);
		reusableLibrary = new ReusableLibrary(driver);
		excelReader = new ExcelFileReader();
	}
	
	
	

}
