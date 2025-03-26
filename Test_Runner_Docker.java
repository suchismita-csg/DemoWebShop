package ui.creative.testrunners;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import ui.creative.testcomponents.ThreadLocalManager;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/java/ui/creative/features",tags= "@DWS_01",glue = "ui/creative/stepdefinitions", monochrome = true,plugin = { "html:target/cucumber.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class Test_Runner_Docker extends AbstractTestNGCucumberTests{
	
	
	@Parameters("BrowserType")
    @BeforeClass
    public void beforeClass(@Optional String browser) throws AWTException, IOException, InterruptedException {
        Thread.sleep(0);
        ThreadLocalManager.setBrowserName(browser);
    }
	
	@BeforeClass
	public void beforeCalss() throws AWTException {
		
		
	}
}
 
