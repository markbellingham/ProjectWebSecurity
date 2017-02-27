package project_web_sec;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Controller {

	public static void main(String[] args) {
		
		// Create an instance of the DAO in order to use its methods
		DAO dao = new DAO();
		GetPageHash getPageHash = new GetPageHash();
		
		// Create an ArrayList of all the pages to be monitored
		ArrayList<String> addresses = new ArrayList<String>();
		addresses.add("http://localhost/ProjectWebSec/home.php");
		addresses.add("http://localhost/ProjectWebSec/about.php");
		addresses.add("http://localhost/ProjectWebSec/products.php");
		addresses.add("http://localhost/ProjectWebSec/contact.php");
		addresses.add("http://localhost/ProjectWebSec/css/stylesheet.css");
		
		// Loop through the pages
		for(String address : addresses) {
			
			String sha512 = null;
			try {
				sha512 = getPageHash.get(address);
			} catch (NoSuchAlgorithmException | IOException e) {
				e.printStackTrace();
			}
			
			// Create a new page object and insert it into the database
			Page page = new Page(address, sha512);
			dao.insertPage(page);
		}
	}
}
