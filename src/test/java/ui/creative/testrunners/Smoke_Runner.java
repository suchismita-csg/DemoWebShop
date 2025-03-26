package ui.creative.testrunners;

import java.awt.AWTException;

import org.testng.annotations.BeforeClass;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/java/ui/creative/features",tags= "@Smoke",glue = "ui/creative/stepdefinitions", monochrome = true,plugin = { "html:target/cucumber.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class Smoke_Runner extends AbstractTestNGCucumberTests{
	@BeforeClass
	public void beforeCalss() throws AWTException {
		
		
	}
}
 
