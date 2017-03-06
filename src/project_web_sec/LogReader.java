package project_web_sec;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.*;

public class LogReader {
 
	public static String readLog(String errorTime) {
 
		File logFile = new File("/opt/lampp/logs/access_log");
    	FileInputStream fis = null;
       	BufferedInputStream bis = null;
       	DataInputStream dis = null;
       	String ipAddress = "";
 
       	try {
       		fis = new FileInputStream(logFile);
       		bis = new BufferedInputStream(fis);
       		dis = new DataInputStream(bis);
       		
       		while (dis.available() != 0) {
       			String logEntry = dis.readLine();
       			
       			String[] logEntryParts = logEntry.split(" ");
       			String logDate = logEntryParts[3];
       			logDate = logDate.substring(1);
       			
       			if (logDate.regionMatches(0, errorTime, 0, 17)) {
       				ipAddress = logEntryParts[0];
       				System.out.println("Log IP Address: " + ipAddress);
       	       		fis.close();
       	       		bis.close();
       	       		dis.close();
       				return ipAddress;
       			} 			

       		}
       		
       		fis.close();
       		bis.close();
       		dis.close();
 
       	} catch (IOException e) {
       		e.printStackTrace();
       	}
		return "Error: IP Address Not Found";
	}
}