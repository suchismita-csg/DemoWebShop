package ui.creative.dynamictestrunners;

// Import necessary libraries here

import java.awt.AWTException;

import java.io.IOException;

import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.Optional;

import org.testng.annotations.Parameters;


import ui.creative.testcomponents.ThreadLocalManager;

import io.cucumber.testng.AbstractTestNGCucumberTests;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/ui/creative/features", tags = "@TCID02", glue = "ui/creative/stepdefinitions", monochrome = true, plugin = { "html:target/cucumber.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })
public class Cross_Browser_Parallel_Property_Runner_01_2 extends AbstractTestNGCucumberTests {

    @Parameters("BrowserType")
    @BeforeClass
    public void beforeClass(@Optional String browser) throws AWTException, IOException, InterruptedException {
        Thread.sleep(0);
        ThreadLocalManager.setBrowserName(browser);
    }

    @AfterClass
    public void afterClass() {
    }
}
