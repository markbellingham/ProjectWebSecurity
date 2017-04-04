package project_web_sec;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LogReader {
 
	public static ArrayList<String> readLog(String errorTime) {
 
		File logFile = new File("/opt/lampp/logs/access_log");
    	FileInputStream fis = null;
       	BufferedInputStream bis = null;
       	DataInputStream dis = null;
       	String ipAddress = "";
       	String logDate = "";
       	ArrayList<String> ipAddresses = new ArrayList<String>();
 
       	try {
       		fis = new FileInputStream(logFile);
       		bis = new BufferedInputStream(fis);
       		dis = new DataInputStream(bis);
       		
       		while (dis.available() != 0) {
       			String logEntry = dis.readLine();
       			
       			// Split the log entry into its constituent parts and extract the date
       			String[] logEntryParts = logEntry.split(" ");
       			logDate = logEntryParts[3];
       			logDate = logDate.substring(1);
       			
       			// Get the minute value from the date and convert to integer
       			int logDate_minuteVal = Integer.parseInt(logDate.substring(15, 17));
       			int errorTime_minuteVal = Integer.parseInt(errorTime.substring(15,17));
       			
       			// Compare the minute value from the log with that from the program 
   				if (logDate_minuteVal == errorTime_minuteVal || 
   						logDate_minuteVal == errorTime_minuteVal - 1) {
   					
   	       			System.out.println("errorTime: " + errorTime);
   	       			System.out.println("logDate:   " + logDate);

   					// If there is a match, the IP address for that 
   					// entry is the one we are looking for
   	       			ipAddress = logEntryParts[0];
   	       			System.out.println("Log IP Address: " + ipAddress);
   	       			System.out.println();
   				}

   				// Create an ArrayList of all found IP addresses
   				// Some will be duplicate because the web server logs an IP address for each 
   				// component of the page that is downloaded
   				if (!ipAddresses.contains(ipAddress) && !ipAddress.equals("127.0.0.1")) {
       				ipAddresses.add(ipAddress);
   				}

   			}
       		
       		// Close the buffers
       		fis.close();
       		bis.close();
       		dis.close();
 
       	} catch (IOException e) {
       		e.printStackTrace();
       	}
		return ipAddresses;

	}
}