package project_web_sec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class RestoreWebsite {
	
	public static void restore() {
		
		// Define backup files and add them to an ArrayList
		Path backup_home = Paths.get("/home/mark/Sites/ProjectWebSec_backup/home.php");
		Path backup_about = Paths.get("/home/mark/Sites/ProjectWebSec_backup/about.php");
		Path backup_products = Paths.get("/home/mark/Sites/ProjectWebSec_backup/products.php");
		Path backup_contact = Paths.get("/home/mark/Sites/ProjectWebSec_backup/contact.php");
		Path backup_styles = Paths.get("/home/mark/Sites/ProjectWebSec_backup/css/stylesheet.css");
		
		ArrayList<Path> backup_pages = new ArrayList<Path>();
		
		backup_pages.add(backup_home);
		backup_pages.add(backup_about);
		backup_pages.add(backup_products);
		backup_pages.add(backup_contact);
		backup_pages.add(backup_styles);
		
		// Define production files and add them to an ArrayList
		Path home = Paths.get("/home/mark/Sites/ProjectWebSec/home.php");
		Path about = Paths.get("/home/mark/Sites/ProjectWebSec/about.php");
		Path products = Paths.get("/home/mark/Sites/ProjectWebSec/products.php");
		Path contact = Paths.get("/home/mark/Sites/ProjectWebSec/contact.php");
		Path styles = Paths.get("/home/mark/Sites/ProjectWebSec/css/stylesheet.css");
		
		ArrayList<Path> live_pages = new ArrayList<Path>();
		
		live_pages.add(home);
		live_pages.add(about);
		live_pages.add(products);
		live_pages.add(contact);
		live_pages.add(styles);
		
		for (int i = 0; i < backup_pages.size(); i++) {
			Path from = backup_pages.get(i);
			Path to = live_pages.get(i);
			try {
				Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}