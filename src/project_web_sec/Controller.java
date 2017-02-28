package project_web_sec;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Controller {

	public static void main(String[] args) {
		
		String sha512 = null;
		
		// Create an instance of the DAO in order to use its methods
		DAO dao = new DAO();
		
		// Remove all entries from the pages table in the database, ready to insert new ones
		dao.deletePage("http%");
		
		// Create an ArrayList of all the pages to be monitored
		ArrayList<String> addresses = new ArrayList<String>();
		addresses.add("http://localhost/ProjectWebSec/home.php");
		addresses.add("http://localhost/ProjectWebSec/about.php");
		addresses.add("http://localhost/ProjectWebSec/products.php");
		addresses.add("http://localhost/ProjectWebSec/contact.php");
		addresses.add("http://localhost/ProjectWebSec/css/stylesheet.css");
		
		for (String address : addresses) {
			
			// Get a hash for each page
			try {
				sha512 = GetPageHash.get(address);
			} catch (NoSuchAlgorithmException | IOException e) {
				e.printStackTrace();
			}
			
			// Create a new page object and insert it into the database
			System.out.println("Page insert: " + address);
			System.out.println("Hash insert: " + sha512);
			Page page = new Page(address, sha512);
			dao.insertPage(page);
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
				
				// Get a hash for each page
				try {
					sha512 = GetPageHash.get(address);
				} catch (NoSuchAlgorithmException | IOException e) {
					e.printStackTrace();
				}
				
				// Check if the new hashes match the ones in the database
				if(dbHashes.get(address).equals(sha512)) {
					System.out.println("Page: " + address);
					System.out.println("Hash from db: " + dbHashes.get(address));
					System.out.println("New page hash: " + sha512);
					System.out.println("Hash match success!!");
				} else {
					System.out.println("Page: " + address);
					System.out.println("Hash from db: " + dbHashes.get(address));
					System.err.println("New page hash: " + sha512);
					System.err.println("Hash match fail.");
				}
			}
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("Sleep interrupted");
			}
		}
	}
}
