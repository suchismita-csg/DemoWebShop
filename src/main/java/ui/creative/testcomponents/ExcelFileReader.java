package ui.creative.testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelFileReader extends BaseTest {
    
    public ExcelFileReader() throws IOException {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static Map<String, String> getDataInMap(String SheetName, String fieldId) throws Exception {
        Map<String, String> TestDatainMap = new TreeMap<String, String>();

        // Query to get the data for the given TCID
        String query = String.format("SELECT * from %s where TCID = '%s'", SheetName, fieldId);
        System.out.println("Query: " + query); // Debug: Print the query being executed

        Fillo fillo = new Fillo();
        Connection conn = null;
        Recordset recodset = null;

        try {
            // Getting the connection and executing the query
            conn = fillo.getConnection(System.getProperty("user.dir") + TestDataPath);
            recodset = conn.executeQuery(query);

            if (!recodset.next()) {
                System.out.println("No records found for TCID: " + fieldId); // Debug: Log when no records are found
                return new TreeMap<String, String>(); // Return an empty map
            }

            // Fetching fields and populating the map
            do {
                for (String field : recodset.getFieldNames()) {
                    TestDatainMap.put(field, recodset.getField(field)); // Storing data into map
                }
            } while (recodset.next());

        } catch (FilloException e) {
            e.printStackTrace();
            throw new Exception("Test Data can't be found: " + e.getMessage());
        } finally {
            if (conn != null) conn.close();
        }

        return TestDatainMap; // Returning the map containing all the necessary data
    }

}
