package project_web_sec;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;

public class LogReader {
 
	public static ArrayList<String> readLog(String errorTime) {
 
		File logFile = new File("/opt/lampp/logs/access_log");
    	FileInputStream fis = null;
       	BufferedInputStream bis = null;
       	DataInputStream dis = null;
       	String ipAddress = "";
       	ArrayList<String> ipAddresses = new ArrayList<String>();
 
       	try {
       		fis = new FileInputStream(logFile);
       		bis = new BufferedInputStream(fis);
       		dis = new DataInputStream(bis);
       		
       		while (dis.available() != 0) {
       			String logEntry = dis.readLine();
       			
       			String[] logEntryParts = logEntry.split(" ");
       			String logDate = logEntryParts[3];
       			logDate = logDate.substring(1);
       			
       			System.out.println("errorTime: " + errorTime);
       			System.out.println("logDate: " + logDate);
       			System.out.println();
       			
       			if (logDate.regionMatches(0, errorTime, 0, 17)) {
       				ipAddress = logEntryParts[0];
       				System.out.println("Log IP Address: " + ipAddress);
       				
       				if (!ipAddresses.contains(ipAddress)) {
           				ipAddresses.add(ipAddress);	
       				}
       	       		fis.close();
       	       		bis.close();
       	       		dis.close();
       			} 			

       		}
       		
       		fis.close();
       		bis.close();
       		dis.close();
 
       	} catch (IOException e) {
       		e.printStackTrace();
       	}
		return ipAddresses;

	}
}