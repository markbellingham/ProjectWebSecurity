package project_web_sec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Controller {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		
		// Create an instance of the DAO in order to use its methods
		DAO dao = new DAO();
		
		// Create an ArrayList of all the pages to be monitored
		ArrayList<String> addresses = new ArrayList<String>();
		addresses.add("http://localhost/ProjectWebSec/home.php");
		addresses.add("http://localhost/ProjectWebSec/about.php");
		addresses.add("http://localhost/ProjectWebSec/products.php");
		addresses.add("http://localhost/ProjectWebSec/contact.php");
		addresses.add("http://localhost/ProjectWebSec/css/stylesheet.css");
		
		// Loop through the pages
		for(String address: addresses) {
			
			// Create a URL from the String and print it to the console for troubleshooting
			URL url = new URL(address);
			System.out.println(url);
			
			// Use BufferedReader to read the stream of data from the network
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			
			// Convert the data stream in the BufferedReader to a String
			String input, pageSource = "";
			while ((input = br.readLine()) != null) {
				pageSource += input;
			}
			br.close();
			
			// Get a hash value form the page source
			String sha512 = HashFunction.hash(pageSource);
			
			// Print the hash value for troubleshooting
			System.out.println(sha512);
			
			// Create a new page object and insert it into the database
			Page page = new Page(address, sha512);
			dao.insertPage(page);
		}
	}

}
