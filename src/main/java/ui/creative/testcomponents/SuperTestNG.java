package ui.creative.testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
//import com.aventstack.extentreports.reporter.configuration.ViewName;

import java.util.Map;
import java.util.TreeMap;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

public class SuperTestNG {
	public static FileInputStream fis;
	public static Properties prop;
	public static ThreadLocal<Properties> property = new ThreadLocal<Properties>();
	public static int rowCountForParallel = 1;
	
	public static ExtentSparkReporter report = null;
	
	public static ExtentReports extent = null;
	public ExtentTest test = null;
	public static ThreadLocal<ExtentTest> logInfo = new ThreadLocal<ExtentTest>(); // Thread safe
	public static String configpath = null;
	public static String runConfigSheetname = null;
	public static String env = null;
	public static String testDataFile = null;
	public static String baseURI = null;
	
	public static ThreadLocal<SoftAssert> softAssertThreadLocal = new ThreadLocal<>();
	
	public static String bearertoken = null;
	public static String TestDataPath = null;
	public static final String propertyFilePath = System.getProperty("user.dir")
			+ "//src//test//resources//globalSettings.properties";
	public static int rowcount = 1;
	public static String fileName;
	public static String folderName;
	public WebDriver driver;
	
	public static ThreadLocal<String> browserName = new ThreadLocal<>();
	public static ThreadLocal<String> scenarioName = new ThreadLocal<>();
	
	public static String totalScenrios;
	public static String startTime;
	public static String endTime;
	public static String duration;

	public static String dbconnection=null;
	public static String dbusername=null;
	public static String dbpassword=null;
	public 	static Scenario scenario;
	public static String reportPath="https://globalcsg.sharepoint.com/:i:/r/sites/DT-EngineeringTeam-CT_QaAnt/Shared%20Documents/CT_QaAnt/Reports/creativelogo.png?csf=1&web=1";
	public static String buildNumber ="";
	public static String typeOfTest ="Regression";

	protected static final Lock fileLock = new ReentrantLock();
	
	
	
	
	public static ExtentReports InitializeExtentReports() throws IOException {
		folderName = typeOfTest+"_"+env.toUpperCase()+"_" + setFolderName();
		fileName = "Run_" + getTimeStamp();
		String reportLocation = System.getProperty("user.dir") + "/"  +"AutomationReports"+"/"+folderName+ "/" + "ExtentReport" + ".html";
		report = new ExtentSparkReporter(reportLocation);	
		report.config().setEncoding("utf-8");
		System.out.println("Extent Report location initialized . . .");


		extent = new ExtentReports();
		extent.attachReporter(report);
		extent.setSystemInfo("Application", "Demo Webshop Application");
		extent.setSystemInfo("Environment URL", "https://demowebshop.tricentis.com/");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		System.out.println("System Info. set in Extent Report");

		return extent;
	}
	
	public static String getTimeStamp() {
		Locale locale = new Locale("en", "UK");
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
		String pattern = "MMMMM dd,yyyy HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, dateFormatSymbols);

		String timestamp = simpleDateFormat.format(new Date());
		return timestamp;
	}
	public static String setFolderName() {
		Locale locale = new Locale("en", "UK");
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
		String pattern = "d_MMM_YY HH_mm_ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, dateFormatSymbols);

		String timestamp = simpleDateFormat.format(new Date());
		return timestamp;
	}

	public static String getDuration(String st,String et) throws ParseException {
		Locale locale = new Locale("en", "UK");
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
		String pattern = "MMMMM dd,yyyy HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, dateFormatSymbols);
		Date datetime1 = simpleDateFormat.parse(st);
		Date datetime2 = simpleDateFormat.parse(et);

		// Calculate the difference between the datetimes in milliseconds
		long diffInMillies = Math.abs(datetime1.getTime() - datetime2.getTime());

		// Convert the milliseconds to formatted string
		long diffSeconds = diffInMillies / 1000 % 60;
		long diffMinutes = diffInMillies / (60 * 1000) % 60;
		long diffHours = diffInMillies / (60 * 60 * 1000) % 24;
		long diffDays = diffInMillies / (24 * 60 * 60 * 1000);

		String durationTime = String.format("%02d:%02d:%02d",diffHours, diffMinutes, diffSeconds);
		durationTime = insertCharAtIndex(durationTime,'h',2);
		durationTime = insertCharAtIndex(durationTime,'m',6);
		durationTime = insertCharAtIndex(durationTime,'s',10);
		return durationTime;
	}

	public static String insertCharAtIndex(String str, char ch, int index) {
		String result = null;
		if (index < 0 || index > str.length()) {
			result = str;
		} else {
			result = str.substring(0, index) + ch + str.substring(index);
		}
		return result;
	}

	
	public String getModuleName(String scenarioName) throws FilloException {
	    // Debugging: Print query being executed
	    String formu = String.format("SELECT * from %s where TestCaseName = '%s'", "ModulePage", scenarioName);
	    System.out.println("Executing query: " + formu);

	    Map<String, String> TestDataInMap = new TreeMap<String, String>();
	    Fillo fillo = new Fillo();
	    Connection connect = null;
	    Recordset rs = null;

	    try {
	        // Get the connection to the Excel file
	        connect = fillo.getConnection(System.getProperty("user.dir") + TestDataPath);
	        
	        // Execute the query
	        rs = connect.executeQuery(formu);
	        
	        // Check if any records were found
	        if (!rs.next()) {
	            System.out.println("No records found for TestCaseName: " + scenarioName);
	            return null; // Return null if no records found
	        }

	        // Iterate through the records and populate the map
	        do {
	            for (String str : rs.getFieldNames()) {
	                TestDataInMap.put(str, rs.getField(str));
	            }
	        } while (rs.next());

	    } catch (FilloException e) {
	        System.out.println("Error executing Fillo query: " + e.getMessage());
	        throw e; // Rethrow exception or handle it based on your needs
	    } finally {
	        if (connect != null) connect.close(); // Close the connection
	    }

	    // Return the "Module" value from the map
	    return TestDataInMap.get("Module");
	}


	//If not passed any argument selecting 0 Build number automatically
	public String getBuildNumber() {

		if(buildNumber==null)
			buildNumber = 0+"";
		return buildNumber;
	}

	//If not passed any argument selecting TEST environment automatically
	public void setUpEnvironment() {
		if(env == null)
			env = "TEST";
	}

	
	// selecting TestData excel sheet Based on Environment!
	public void setUpTestDataEnvironment() {
		if(env.equalsIgnoreCase("TEST")) {
			TestDataPath = "/src/test/java/ui/creative/testdata/test/TestData.xlsx";
		}
		else if(env.equalsIgnoreCase("DEV")) {
			TestDataPath = "/src/test/java/ui/creative/testdata/dev/TestData.xlsx";
		}
		else if(env.equalsIgnoreCase("UAT")){
			TestDataPath = "/src/test/java/ui/creative/testdata/uat/TestData.xlsx";
		}
	}
	
	 
	
	@BeforeSuite
	public void resetResultSummaryExcelSheet() {
		try {
            String filePath = System.getProperty("user.dir")+"/ResultsSummary.xlsx";

            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);
            
            String sheetName = "CrossBrowserParallelResult";
            Sheet sheet = workbook.getSheet(sheetName);

            // Clear values, excluding the first row and first column
            for (int rowIdx = 1; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
                Row row = sheet.getRow(rowIdx);
                for (int colIdx = 1; colIdx < row.getLastCellNum(); colIdx++) {
                    Cell cell = row.getCell(colIdx);
                    if (cell != null) {
                        row.removeCell(cell);
                    }
                }
            }

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);

            fos.close();
            fis.close();

            System.out.println("Contents cleared except for the first row and first column.");
        } catch (IOException e) {
            e.printStackTrace();
        }
      
	}
	
	
	
}
