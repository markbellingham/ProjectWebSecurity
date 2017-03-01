package project_web_sec;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunction {
	public static String hash (String pageSource) throws NoSuchAlgorithmException {
		
		// Convert the String to bytes as required by MessageDigest
		byte [] input = pageSource.getBytes();
		
		// Get a Message Digest object that uses the SHA512 algorithm
		MessageDigest SHA512 = MessageDigest.getInstance("SHA-512");
		
		// Compute digest (hash value) of the page
		SHA512.update(input);
		byte [] digest = SHA512.digest();
		
		// Convert byte digest to hex format
		StringBuffer hexDigest = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			hexDigest.append(Integer.toString((digest[i]&0xff) + 0x100,16).substring(1));
		}		
		return hexDigest.toString();
	}
}
