package project_web_sec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark
 * Data Accessor Object
 * Provides methods for connecting to the database
 * Provides methods for inserting, retrieving, updating and deleting records from the database
 * All SQL statements are in the form of Prepared Statement to protect against SQL injection attacks
 * All methods will close the database connection even if the statement fails * 
 */

public class DAO {

	public DAO() {}
	
	// Method for connecting to the database, returns an open connection
	private Connection openConnection() {		
		String userid = "mark";
		String userpass = "Excite10";
		String url = "jdbc:mysql://localhost:3306/web_sec_project";
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userid, userpass);
			conn.createStatement();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.err.println(e);
		}
		return conn;		
	}
	
	// Method for closing the connection to the database
	private void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Method for returning the page names
	public ArrayList<String> getPageNames() {
		String sql = "SELECT page_name FROM pages";
		Connection conn = openConnection();
		ArrayList<String> pageNames = new ArrayList<String>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String pageName = rs.getString("page_name");
				pageNames.add(pageName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return pageNames;
	}
	
	// Method for inserting a page hash into the database
	public void insertPageHash(Page page) {
		String sql = "UPDATE pages SET page_hash = ? WHERE page_name LIKE ?";
		Connection conn = openConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, page.getPageHash());
			pstmt.setString(2, page.getPageName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}
	
	// Method that returns all pages as a HashMap
	public Map<String,String> getAllPages() {
		Map<String, String> pages = new HashMap<>();
		String sql = "SELECT * FROM pages";
		Connection conn = openConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String pageName = rs.getString("page_name");
				String pageHash = rs.getString("page_hash");
				pages.put(pageName, pageHash);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return pages;
	}
	
	// Method that deletes a page record from the database
	public void deletePageHash(String param) {
		String sql = "UPDATE pages SET page_hash = NULL WHERE page_name like ?";
		Connection conn = openConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}
	
	// Method for inserting an IP address into the database
	public void insertIP(String ipAddress, String date) {
		String sql = "INSERT INTO ip_addresses (ip_address, date_accessed) VALUES (?, ?)";
		Connection conn = openConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ipAddress);
			pstmt.setString(2, date);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}
	
	// Method that returns all IP addresses as a HashMap
	public Map<String,String> getAllIPs() {
		Map<String, String> ipAddresses = new HashMap<>();
		String sql = "SELECT * FROM ip_addresses";
		Connection conn = openConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String ipAddress = rs.getString("ip_address");
				String date = rs.getString("date_accessed");
				ipAddresses.put(ipAddress, date);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return ipAddresses;
	}
}
