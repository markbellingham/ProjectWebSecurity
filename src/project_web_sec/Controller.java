package project_web_sec;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Controller {

	public static void main(String[] args) {
		
		String sha512 = null;
		
		// Create an instance of the Data Accessor Object in order to use its methods
		DAO dao = new DAO();
		
		// Remove all old entries from the pages table in the database
		// When the website has been updated, the program starts from afresh
		dao.deletePageHash("http%");
		
		// Create an ArrayList of all the pages to be monitored
		ArrayList<String> addresses = new ArrayList<String>();
		addresses = dao.getPageNames();
		
		for (String address : addresses) {			
			// Get a hash for each page
			try {
				sha512 = GetPageHash.hash(address);
			} catch (NoSuchAlgorithmException | IOException e) {
				e.printStackTrace();
			}
			
			// Create a new page object and insert it into the database
			System.out.println("Page insert: " + address);
			System.out.println("Hash insert: " + sha512);
			Page page = new Page(address, sha512);
			dao.insertPageHash(page);
		}
		
		// Create an ArrayList of hashes in the database
		// Only need to get these once since they should not change unless the administrator indicates so
		Map<String, String> dbHashes = new HashMap<String, String>();
		dbHashes = dao.getAllPages();
		
		while (true) {
			
			// Blank line for legibility
			System.out.println();
			
			// Loop through the pages in the ArrayList			
			for(String address : addresses) {
				
				// Body of the email
				String body = "";
				
				// Get a hash for each page
				try {
					sha512 = GetPageHash.hash(address);
				} catch (NoSuchAlgorithmException | IOException e) {
					System.err.println("Error file missing: " + address);
					body += "Error file missing: " + address;
				}
				
				String dbHash = dbHashes.get(address);
				
				System.out.println("Page: " + address);
				System.out.println("Hash from db: " + dbHash);
				
				// Check if the new hashes match the ones in the database
				if(dbHash.equals(sha512)) {
					System.out.println("Hash match success!!");
				} else {
					// If there is no match, generate and send an email with details to the administrator
					System.err.println("New page hash: " + sha512);
					System.err.println("Hash match fail.");
					
					// Get the current time and pass to the Log Reader to extract 
					// the IP addresses that were accessing at the time
					String time = getTime();
					System.out.println(time);
					ArrayList<String> newIPAddresses = LogReader.readLog(time);
					
					// Construct and send email to the Administrator 
					body += ("Page that was modified: " + address + 
							"\nOriginal Hash: " + dbHash + 
							"\nNew Hash: " + sha512 + "\n" + 
							"\nTime of change: " + time);
					
					// Get a list of IP addresses that have previously been flagged
					Map<String, String> dbIPAddresses = new HashMap<String, String>();
					dbIPAddresses = dao.getAllIPs();
					
					// Compare old with new. All IPs are included in the email to the administrator
					for (String ipAddress : newIPAddresses) {
						// Put the IP address into the database with time accessed
						dao.insertIP(ipAddress, time);
						// Message to the admin informs whether this IP address is known already
						if (!dbIPAddresses.containsKey(ipAddress)) {
							body += "\nNew IP Address: " + ipAddress;
						} else {
							body += "\nIP Address matched and blocked!: " + ipAddress;
							
							// If the IP address is known to the system
							// block it using the .htaccess file
							BlockIP.appendIP(ipAddress);
						}
					}
					Email.sendEmail("projectwebsec@gmail.com", body);
					
					// Restore the website from the backup
					RestoreWebsite.restore();
				}
			}
			try {
				TimeUnit.SECONDS.sleep(60);
			} catch (InterruptedException e) {
				System.out.println("Sleep interrupted");
			}
		}
	}
	
	// Method that returns the current time
	public static String getTime() {
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:kk:mm:ss");
		String currentTime = sdf.format(currentDate);
		return currentTime;
	}
}
