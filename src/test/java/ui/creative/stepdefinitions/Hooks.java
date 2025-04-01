package ui.creative.stepdefinitions;


import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import ui.creative.testcomponents.BaseTest;
//readxlimport ui.creative.testcomponents.ReadXL;
import ui.creative.testcomponents.TestSetup;
import ui.creative.testcomponents.ThreadLocalManager;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class Hooks extends BaseTest {

	public Hooks() throws IOException{
		super();
	}
	
	@Before
	public void TestConfigSetup(Scenario scenario) throws Exception {
		
		this.scenario = scenario; .
		test = extent.createTest(scenario.getName());
	    logInfo.set(test);
	    softAssertThreadLocal.set(new SoftAssert());
//for parallel execution------------------------
	    
	    ThreadLocalManager.setScenarioName(scenario.getName());
	    
	    String []module =scenario.getName().split(" ");
	    test.assignCategory(module[0]);
	}
	
	@After
	public void teardown(Scenario scenario) throws InterruptedException {
		softAssertThreadLocal.get().assertAll();
	}
}
	

