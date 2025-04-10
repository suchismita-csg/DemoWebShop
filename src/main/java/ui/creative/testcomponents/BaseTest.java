package ui.creative.testcomponents;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
//import com.aventstack.extentreports.reporter.configuration.ViewName;



import io.github.bonigarcia.wdm.WebDriverManager;



import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

//import io.cucumber.java.Scenario as sc;


/*This class is responsible for loading the configurations from properties files, 
Initializing the WebDriver, Implicit Waits, Extent Reports, and also to create the object of FileInputStream 
which is responsible for pointing towards the file from which the data should be read.*/



public class BaseTest extends SuperTestNG{

        
	public BaseTest() throws IOException {

		prop = new Properties();
		fis = new FileInputStream(propertyFilePath);
		prop.load(fis);
		
		property.set(prop);
		
	}
	
	public static void innitializePropertyFile() throws IOException {
		prop = new Properties();
		fis = new FileInputStream(propertyFilePath);
		prop.load(fis);
		
		property.set(prop);
		
	}
	
	public WebDriver initializeDriver() throws IOException {
	    // Log the browser name
	    String browser = ThreadLocalManager.getBrowserName();
	    System.out.println(browser);

	    // Set default browser to Chrome if no browser is specified
	    if (browser == null) {
	        ThreadLocalManager.setBrowserName("chrome");
	        browser = "chrome";  // Default to Chrome if not set
	    }

	    boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true")); // Default to false

	    if (driver == null) {
	        // Initialize WebDriver based on the selected browser
	        switch (browser.toLowerCase()) {
	            case "edge":
	                setupEdgeDriver(isHeadless);
	                break;
	            case "chrome":
	                setupChromeDriver(isHeadless);
	                break;
	            case "firefox":
	                setupFirefoxDriver(isHeadless);
	                break;
	            default:
	                throw new UnsupportedOperationException("Browser " + browser + " is not supported.");
	        }

	        // Configure the WebDriver
	        configureDriver();
	    }

	    // Log the browser name in Extent report
	    extent.setSystemInfo("Browser Name", browser + " - " + ThreadLocalManager.getScenarioName());

	    return driver;
	}

	// Setup Edge WebDriver
	private void setupEdgeDriver(boolean isHeadless) {
	    WebDriverManager.edgedriver().setup();
	    EdgeOptions options = new EdgeOptions();
	    if (isHeadless) {
	        options.addArguments("--headless");
	    }
	    configureEdgeOptions(options); // Use specific Edge configuration
	    driver = new EdgeDriver(options);
	}

	// Setup Chrome WebDriver
	private void setupChromeDriver(boolean isHeadless) {
	    WebDriverManager.chromedriver().setup();
	    ChromeOptions options = new ChromeOptions();
	    if (isHeadless) {
	        options.addArguments("--headless");
	        options.addArguments("--disable-gpu");  // Optional for headless mode
	        options.addArguments("--no-sandbox");   // Optional for headless mode
	    }
	    configureChromeOptions(options); // Use Chrome configuration
        
        // Set a realistic User-Agent string
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
	    driver = new ChromeDriver(options);
	}

	// Setup Firefox WebDriver
	private void setupFirefoxDriver(boolean isHeadless) {
	    WebDriverManager.firefoxdriver().setup();
	    FirefoxOptions options = new FirefoxOptions();
	    if (isHeadless) {
	        options.addArguments("-headless");
	    }
	    driver = new FirefoxDriver(options);
	}

	// Configure common browser options for Chrome
	private void configureChromeOptions(ChromeOptions options) {
	    options.addArguments("--disable-dev-shm-usage");
	    options.addArguments("--remote-allow-origins=*");
	    options.setAcceptInsecureCerts(true);

	    // Set download directory
	    Map<String, Object> prefs = new HashMap<>();
	    prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "Downloads");
	    options.setExperimentalOption("prefs", prefs);
	}

	// Configure specific browser options for Edge
	private void configureEdgeOptions(EdgeOptions options) {
	    options.addArguments("--start-maximized");
	    options.addArguments("--disable-gpu");
	    options.addArguments("--disable-dev-shm-usage");
	    options.addArguments("--remote-allow-origins=*");
	    options.setAcceptInsecureCerts(true);

	    // Set download directory
	    Map<String, Object> prefs = new HashMap<>();
	    prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "Downloads");
	    options.setExperimentalOption("prefs", prefs);
	}

	// Configure common WebDriver settings (for window size, timeouts, etc.)
	private void configureDriver() {
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	    driver.manage().window().setSize(new Dimension(1920, 1080));
	    driver.manage().window().setPosition(new Point(0, 0));
	    driver.manage().window().maximize();
	}



	public void closeBrowser() {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
	    String browserNameLast = cap.getBrowserName().toLowerCase();
		driver.close();
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}

	public static void zoomOut(int s) throws AWTException {
		Robot robot = new Robot();
		for (int i = 0; i < s; i++) {

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	public static void zoomIn(int s) throws AWTException {
		Robot robot = new Robot();
		for (int i = 0; i < s; i++) {

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ADD);
			robot.keyRelease(KeyEvent.VK_ADD);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	public void reportInfo(String msg) {
		test.pass(msg);
	}

	public void reportPass(String title, String exp, String act) {
		String message = "<b>" + "Expected " + title + " : " + "</b>" + "<font color=" + "green>" + exp + "</font>"
				+ "\t" + "<b>" + "Actual " + title + " : " + "</b>" + "<font color=" + "green>" + act + "</font>";
		test.pass(message);
	}

	public void reportFail(String title, String exp, String act) {
		String message = "<b>" + "Expected " + title + " : " + "</b>" + "<font color=" + "red>" + exp + "</font>" + "\t"
				+ "<b>" + "Actual " + title + " : " + "</b>" + "<font color=" + "red>" + act + "</font>";
		test.fail(message);
		softAssertThreadLocal.get().assertEquals(act, exp);
	}

	public void reportPayload(String msg) {
		Markup m = MarkupHelper.createCodeBlock(msg, CodeLanguage.JSON);
		test.info(m);
	}

	public void validateField(String title, String expected, String actual) {

		if (expected.equals(actual))
			reportPass(title, expected, actual);
		else
			reportFail(title, expected, actual);
	}

	public void validateNullValues(String title, String actual) {

		if (actual == null)
			reportPass(title, "shold have null ", "contain null");
		else
			reportFail(title, "shold have null ", "contain null");
	}

	public void reportScreenshot(String status, String exp, String act, WebDriver driver) throws IOException {

		String message = "<b>" + "Expected : " + "</font>" + "</b>" + exp + "\t" + "<b>" + "Actual : " + "</b>" + act;
		if (status.equals("PASS"))
			test.pass(message, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
		else if (status.equals("FAIL"))
			test.fail(message, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
		else if (status.equals("INFO"))
			test.info(message, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
	}

	public void reportScreenshot(WebDriver driver) throws IOException {

		test.info("<b>" + "<font color=" + "orange>" + "Screenshot" + "</b>",
				MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
	}

	public String captureScreenShot(WebDriver driver) throws IOException {
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		String dest = System.getProperty("user.dir") + "/"+"AutomationReports"+"/"+folderName+ "/" +"ScreenShots"+"/"+ getcurrentdateandtime() + ".jpg";
		File target = new File(dest);
		FileUtils.copyFile(src, target);
		return dest;
	}

	private static String getcurrentdateandtime() {
		String str = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
			Date date = new Date();
			str = dateFormat.format(date);
			str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
		} catch (Exception e) {
		}
		return str;
	}

	public void testStepHandle(String teststatus, WebDriver driver, ExtentTest extenttest, Throwable throwable) {
		switch (teststatus) {
		case "FAIL":
			extenttest.fail(MarkupHelper.createLabel("Test Case is Failed : ", ExtentColor.RED));
			extenttest.log(Status.FAIL, throwable.fillInStackTrace());

			try {
				extenttest.addScreenCaptureFromPath(captureScreenShot(driver));
			} catch (IOException e) {
				// e.printStackTrace();
			}
			softAssertThreadLocal.get().fail();
			break;

		case "PASS":
			extenttest.pass(MarkupHelper.createLabel("Test Case is Passed : ", ExtentColor.GREEN));
			break;

		default:
			break;
		}
	}
	
	//Random String Generator

	public String randomStringGenerator() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		Date date = new Date();
		String timestamp = sdf.format(date);
		String randomString = "testauto" + timestamp;
		return randomString;
	}

	//Random Integer Generator
	public String randomIntGenerator() {

		Random rand = new Random();
        int num = rand.nextInt((int) Math.pow(10, 10));
        return String.valueOf(num);

	}

	//// Random Email Generator

	public String randomEmailGenerator() {

		String[] emailProviders = {"gmail.com", "yahoo.com", "hotmail.com", "aol.com", "outlook.com"};
		Random random = new Random();
        String randomString = "testauto";
        for (int i = 0; i < 4; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            randomString += c;
        }
        return randomString + "@" + emailProviders[random.nextInt(emailProviders.length)];

	}

	public static String getRamdomMAcAddress() {
		Random rand = new Random();
		return String.format("%02x-%02x-%02x-%02x-%02x", rand.nextInt(256), 0x5e, rand.nextInt(256), 0x53, 0xaf);
	}



}
