package com.huang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBC {
	
	public static Connection getConnection(){
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Email", "root", "");
		} catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}

	public static void insertEmailAddress(Connection conn, String email_address) throws SQLException{
		String insertSQL = "INSERT INTO email (address) VALUES (?)";
		PreparedStatement ps = conn.prepareStatement(insertSQL);
		ps.setString(1, email_address);
		ps.execute();
	}
	
	public static void updateEmailAddress(Connection conn, String old_address, String new_address) throws SQLException{
		String updateSQL = "UPDATE email SET address = ? WHERE address = ?";
		PreparedStatement ps = conn.prepareStatement(updateSQL);
		ps.setString(1, new_address);
		ps.setString(2, old_address);
		ps.execute();
	}
	
	public static void deleteEmailAddress(Connection conn, String email_address) throws SQLException{
		String deleteSQL = "DELETE FROM email WHERE address = ?";
		PreparedStatement ps = conn.prepareStatement(deleteSQL);
		ps.setString(1, email_address);
		ps.execute();
	}
	
	//返回一个字符串数组
	public static String[] searchEmailAddress(Connection conn) throws SQLException{
		String searchSQL = "SELECT * FROM email";
		
		PreparedStatement ps = conn.prepareStatement(searchSQL);
		ResultSet rs = ps.executeQuery();
		
		ArrayList<String> list = new ArrayList<String>();
		
		while(rs.next()){
			list.add(rs.getString("address"));
		}
		
		String[] addresses = (String[])list.toArray(new String[0]);
		return addresses;
	}
}
