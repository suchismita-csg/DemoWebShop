package ui.creative.testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ReadXL extends SuperTestNG {


	static FileInputStream fis;
	//static Workbook wb;
	static FileOutputStream fos;
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;

	//check for an entry with same scenario name and build Number
	public static int CheckBuildNum(String path, String sheet)
	{
		try
		{

			//			System.out.println("File going to be lock now . . .");
			while (((ReentrantLock) fileLock).isLocked()) {
				//                System.out.println("Excel file is locked. Waiting for it to be unlocked...");
				Thread.sleep(1000); // Adjust the sleep duration as needed
			}
			// Acquire the lock before accessing the Excel file
			fileLock.lock();

			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(path);

			String query = "SELECT * FROM " + sheet;
			Recordset recordset = connection.executeQuery(query);
			int i=0;
			while (recordset.next())
			{
				String cellValue = recordset.getField("ScenarioName"); // First column (0-indexed)
				if(cellValue.equals(ThreadLocalManager.getScenarioName()))
				{
					String build = recordset.getField("BuildNumber");
					String browser = recordset.getField("Browser");
					//					System.out.println(browser+" : Browser name in while writting result summary . . .");

					if(build.equals(buildNumber) && browser.equalsIgnoreCase(ThreadLocalManager.getBrowserName()))
					{
						recordset.close();
						connection.close();
						fileLock.unlock();
						return i;
					}
					//return i;
				}
				i++;
			}
			recordset.close();
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		fileLock.unlock();
		return -1;

	}

	//Use to write cell in excel sheet
	public static void	writeCell(String path,String sheet,String txt,int row,int col) //throws InvalidFormatException, IOException
	{
		try{	

			//			System.out.println("File going to be lock now . . .");
			while (((ReentrantLock) fileLock).isLocked()) {
				//                System.out.println("Excel file is locked. Waiting for it to be unlocked...");
				Thread.sleep(1000); // Adjust the sleep duration as needed
			}
			// Acquire the lock before accessing the Excel file
			fileLock.lock();

			File file = new File(path);
			fis = new FileInputStream(file);
			XSSFWorkbook xssfwb= new XSSFWorkbook(fis);
			//XSSFRow r = xssfwb.getSheet(sheet).createRow(row);
			XSSFRow r = xssfwb.getSheet(sheet).getRow(row);

			r.createCell(col).setCellValue(txt);
			FileOutputStream fout= new FileOutputStream(path);
			xssfwb.write(fout);
			fout.close();
			fis.close();

			fileLock.unlock();

		}catch(Exception e)
		{
			//System.out.println();
			e.printStackTrace(System.out);
			//	LogStatus.LogAction("Write to Excel", e.getMessage(), ":Warn");
		}
	}
	public static String[][] getExcelDataIn2DArray(String Path,String SheetName) throws Exception {
		String[][] excelDataArray = null;
		try {

			FileInputStream ExcelFile = new FileInputStream(Path);

			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int numOfColumns = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
			int numOfRows = ExcelWSheet.getPhysicalNumberOfRows();

			excelDataArray = new String[numOfRows-1][numOfColumns];

			for (int i= 1 ; i < numOfRows; i++) {

				for (int j=0; j < numOfColumns; j++) {
					excelDataArray[i-1][j] = ExcelWSheet.getRow(i).getCell(j).getStringCellValue();
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excelDataArray;
	}


	public static ArrayList<String> xlToArrayList(String path,String sheet,int row)
	{
		ArrayList<String> colHeaders=new ArrayList<>();
		try
		{
			String newLine = System.getProperty("line.separator");
			int col=ReadXL.XLCol(path, sheet, row);
			for(int i=0;i<col;i++)
			{
				if(ExcelWBook.getSheet(sheet).getRow(row).getCell(i)==null){
					colHeaders.add(null);
				}else{
					colHeaders.add(ExcelWBook.getSheet(sheet).getRow(row).getCell(i).getStringCellValue().toString());
				}
			}
		}catch(Exception e)
		{
			System.out.print("Execption in Method ::xlToArrayList");
		}
		return colHeaders;
	}





	/*
	 * method to get the column number based on the column header value
	 * input parameter XL path, sheet name and column header value
	 */
	public static int getCol(String path,String sheet,String search)
	{
		String cellValue;
		int col=-1;
		int noOfCol=XLCol(path,sheet,0);
		try{
			fis = new FileInputStream(path);
			ExcelWBook =  new XSSFWorkbook(fis);
			for(int i=0;i<=0;i++)
			{
				for(int j=0;j<noOfCol;j++)
				{
					try{
						cellValue=ExcelWBook.getSheet(sheet).getRow(i).getCell(j).getStringCellValue();
						if(cellValue.equals(search))
						{
							col=j;
							break;
						}else
						{
							continue;
						}
					}
					catch(Exception e)
					{
						continue;
					}
				}
			}
		}catch(Exception e)
		{
			return -1;
		}
		return col;
	}


	public static int getRow(String path,String sheet,String search)
	{
		String cellValue;
		int row=-1;
		int noOfrows=XLRow(path,sheet);
		try
		{
			fis = new FileInputStream(path);
			ExcelWBook = new XSSFWorkbook(fis);

			for(int i=1;i<=noOfrows;i++)
			{
				try{
					cellValue=ExcelWBook.getSheet(sheet).getRow(i).getCell(0).getStringCellValue();
					if(cellValue.equals(search))
					{
						row=i;
						break;
					}else
					{
						continue;
					}
				}catch(Exception e)
				{
					//  add log statement here
				}
			}
		}catch(Exception e){
			//  add log statement here
		}

		return row;
	}




	/*
	 *  To get the XL cell value
	 *  Input parameters, XL path, sheet name, row and column number
	 */
	public static String XLCellValue(String path,String sheet,int row,int cell)
	{

		String cellValue;
		//RichTextString cellValue1;
		try
		{
			fis = new FileInputStream(path);
			ExcelWBook =new XSSFWorkbook(fis);
			cellValue=ExcelWBook.getSheet(sheet).getRow(row).getCell(cell).getStringCellValue();
			fis.close();
			// cellValue1=wb.getSheet(sheet).getRow(row).getCell(cell).getRichStringCellValue();
		}
		catch(Exception e)
		{
			//System.out.println(e.getMessage());
			//	LogStatus.LogAction("Reading From Excel", e.getMessage(), ":Warn");
			return cellValue=" ";

		}
		return cellValue;
	}

	/*
	 *  To get the XL cell value
	 *  Input parameters, XL path, sheet name, row number and column header
	 */
	public static String XLCellValue(String path,String sheet,int row,String colName)
	{

		String cellValue;
		int cell=getCol(path,sheet,colName);
		//RichTextString cellValue1;
		try
		{
			fis = new FileInputStream(path);
			ExcelWBook =new XSSFWorkbook(fis);
			cellValue=ExcelWBook.getSheet(sheet).getRow(row).getCell(cell).getStringCellValue();
			// cellValue1=wb.getSheet(sheet).getRow(row).getCell(cell).getRichStringCellValue();
			fis.close();
		}
		catch(Exception e)
		{
			return cellValue=" ";

		}
		return cellValue;
	}

	/*
	 *  To get the XL cell value
	 *  Input parameters, XL path, sheet name, row header and column header
	 */
	public static String XLCellValue(String path,String sheet,String rowName,String colName)
	{

		String cellValue;
		int cell=getCol(path,sheet,colName);
		int row=getRow(path,sheet,rowName);
		//RichTextString cellValue1;
		try
		{
			fis = new FileInputStream(path);
			ExcelWBook =new XSSFWorkbook(fis);
			cellValue=ExcelWBook.getSheet(sheet).getRow(row).getCell(cell).getStringCellValue();
			// cellValue1=wb.getSheet(sheet).getRow(row).getCell(cell).getRichStringCellValue();
			fis.close();
		}
		catch(Exception e)
		{
			//	LogStatus.LogAction("Reading From Excel", e.getMessage(), ":Warn");
			return cellValue=" ";

		}
		return cellValue;
	}

	/*
	 * To write value to the specified cell
	 * input parameter: XL path,sheet name,text,row and col values
	 */


	/*
	 *  To get the integer XL cell value
	 *  Input parameters, XL path, sheet name, row and column number
	 */
	public static double XLCellNumericValue(String path,String sheet,int row,int cell)
	{

		double cellValue;
		try
		{
			fis = new FileInputStream(path);
			ExcelWBook =new XSSFWorkbook(fis);
			cellValue=ExcelWBook.getSheet(sheet).getRow(row).getCell(cell).getNumericCellValue();
			fis.close();
		}
		catch(Exception e)
		{
			//LogStatus.LogAction("Reading From Excel", e.getMessage(), ":Warn");
			return cellValue=-1;

		}
		return cellValue;
	}
	/*
	 *  To get the XL row number
	 *  Input parameters, XL path and  sheet name
	 */
	public static int XLRow(String path,String sheet)
	{
		int xlRow;
		try{
			fis = new FileInputStream(path);
			ExcelWBook = new XSSFWorkbook(fis);
			xlRow=ExcelWBook.getSheet(sheet).getLastRowNum();
			fis.close();
		}catch(Exception e)
		{
			//	LogStatus.LogAction("Reading From Excel", e.getMessage(), ":Warn");
			return -1;
		}
		return xlRow;
	}

	public static int XLCol(String path,String sheet,int row)
	{
		int xlCol;
		try{
			fis = new FileInputStream(path);
			ExcelWBook = new XSSFWorkbook(fis);
			xlCol=ExcelWBook.getSheet(sheet).getRow(row).getLastCellNum();
			fis.close();
		}catch(Exception e)
		{
			//LogStatus.LogAction("Reading From Excel", e.getMessage(), ":Warn");
			return -1;
		}
		return xlCol;
	}

	public static int LastRowNum(String path, String sheet) {
		int rowNum;
		try {
			fis = new FileInputStream(path);
			ExcelWBook = new XSSFWorkbook(fis);
			rowNum = ExcelWBook.getSheet(sheet).getLastRowNum();

			ExcelWBook.getSheet(sheet).createRow(rowNum+1).createCell(1).setCellValue("New Entry");
			rowNum = ExcelWBook.getSheet(sheet).getLastRowNum();

			fis.close();
		} catch (Exception e) {

			return -1;
		}
		return rowNum;
	}

	//Get the last empty row from the excel
	public static int LastEmptyRow(String path, String sheet)
	{
		try
		{

			//			System.out.println("File going to be lock now . . .");
			while (((ReentrantLock) fileLock).isLocked()) {
				//                System.out.println("Excel file is locked. Waiting for it to be unlocked...");
				Thread.sleep(1000); // Adjust the sleep duration as needed
			}
			// Acquire the lock before accessing the Excel file
			fileLock.lock();

			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(path);

			String query = "SELECT * FROM " + sheet;
			Recordset recordset = connection.executeQuery(query);

			int lastRowNumber = recordset.getCount();

			int i=0;

			while (recordset.next()) {
				String cellValue = recordset.getField("TimeStamp"); // First column (0-indexed)


				if(cellValue.isEmpty())
				{
					recordset.close();
					connection.close();
					fileLock.unlock();
					return i;
				}
				i++;
			}
			recordset.close();
			connection.close();
			fileLock.unlock();
			return lastRowNumber;

		}
		catch(Exception e)
		{

		}
		fileLock.unlock();
		return -1;

	}



	public static int CheckScenarioInList(String path, String sheet)
	{
		try
		{
			//			System.out.println("File going to be lock now . . .");
			while (((ReentrantLock) fileLock).isLocked()) {
				//                System.out.println("Excel file is locked. Waiting for it to be unlocked...");
				Thread.sleep(1000); // Adjust the sleep duration as needed
			}
			// Acquire the lock before accessing the Excel file
			fileLock.lock();


			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(path);

			String query = "SELECT * FROM " + sheet;
			Recordset recordset = connection.executeQuery(query);
			int i=0;
			while (recordset.next())
			{
				String cellValue = recordset.getField("ScenarioName"); // First column (0-indexed)
				if(cellValue.equals(ThreadLocalManager.getScenarioName()))
				{
					fileLock.unlock();
					return i;
				}
				i++;
			}
			recordset.close();
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		fileLock.unlock();
		return -1;

	}

	public static int LastEmptyRowForCrossBrowserSummary(String path, String sheet)
	{
		try
		{
			//			System.out.println("File going to be lock now . . .");
			while (((ReentrantLock) fileLock).isLocked()) {
				//                System.out.println("Excel file is locked. Waiting for it to be unlocked...");
				Thread.sleep(1000); // Adjust the sleep duration as needed
			}
			// Acquire the lock before accessing the Excel file
			fileLock.lock();



			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(path);

			String query = "SELECT * FROM " + sheet;
			Recordset recordset = connection.executeQuery(query);

			int lastRowNumber = recordset.getCount();

			int i=0;

			while (recordset.next()) {
				String cellValue = recordset.getField("ScenarioName"); // First column (0-indexed)


				if(cellValue.isEmpty())
				{
					recordset.close();
					connection.close();
					fileLock.unlock();
					return i;
				}
				i++;
			}
			recordset.close();
			connection.close();
			fileLock.unlock();
			return lastRowNumber;

		}
		catch(Exception e)
		{

		}
		fileLock.unlock();
		return -1;

	}





}



