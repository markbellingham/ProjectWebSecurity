package project_web_sec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BlockIP {
	
	public static void appendIP(String ipAddress) {
		
		// Define the string to append to the htaccess file
		String textToInsert = "deny from " + ipAddress;
		
		// Define the location of the htaccess file
		String htaccessFile = "/home/mark/Sites/ProjectWebSec/.htaccess";
		
		try {
			// Get a new BufferedWriter
			BufferedWriter writer = new BufferedWriter(new FileWriter(htaccessFile, true));
		
			// Append the contents
			writer.write(textToInsert);
			writer.newLine();
			
			// Close the file
			writer.close();
			
			System.err.println("IP address " + ipAddress + " blocked.");
		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
