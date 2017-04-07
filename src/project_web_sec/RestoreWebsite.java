package project_web_sec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class RestoreWebsite {
	
	public static void restore() {
		
		DAO dao = new DAO();
		
		// Create ArrayLists of type Path to store the locations of the live and backup files
		ArrayList<Path> backup_pages = new ArrayList<Path>();
		ArrayList<Path> live_pages = new ArrayList<Path>();
		
		// Get the page URLs from the database
		ArrayList<String> pageNames = dao.getPageNames();
		
		// For each URL returned, extract the page name and define the path for the live and backup locations
		for (String page : pageNames) {
			page = page.substring(31);
			Path path = Paths.get("/home/mark/Sites/ProjectWebSec/" + page);
			live_pages.add(path);
		}
		
		for (String page : pageNames) {
			page = page.substring(31);
			Path path = Paths.get("/home/mark/Sites/ProjectWebSec_backup/" + page);
			backup_pages.add(path);
		}
		
		
		// Replace the live pages with those from the backup
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