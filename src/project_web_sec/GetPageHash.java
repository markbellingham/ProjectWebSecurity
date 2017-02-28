package project_web_sec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class GetPageHash {
	
	public static String get(String address) throws IOException, NoSuchAlgorithmException {
		
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
			// System.out.println(sha512);
			
			return sha512;
	}
}
