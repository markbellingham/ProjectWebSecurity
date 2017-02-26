package project_web_sec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		String url = "jdbc:mysql://localhost:3306/";
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
	
	// Method for inserting a page into the database
	public void insertPage(Page page) {
		String sql = "INSERT INTO pages (page_name, page_hash) VALUES (?, ?)";
		Connection conn = openConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, page.getPageName());
			pstmt.setString(2, page.getPageHash());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}
	
	// Method that returns a single page from the database when provided with a page name
	public Page getOnePage(String pageName) {
		Page page = null;
		String sql = "SELECT * FROM pages WHERE page_name = ?";
		Connection conn = openConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pageName);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				pageName = rs.getString("page_name");
				String pageHash = rs.getString("page_hash");
				page = new Page(pageName, pageHash);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return page;
	}
	
	// Method that updates a page record in the database
	public void updatePage(Page page) {
		String sql = "UPDATE pages SET page_hash = ? WHERE page_name = ?";
		Connection conn = openConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, page.getPageHash());
			pstmt.setString(2, page.getPageName());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}
	
	// Method that deletes a page record from the database
	public void deletePage(Page page) {
		String sql = "DELETE FROM pages WHERE page_name = ?";
		Connection conn = openConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, page.getPageName());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}
}
