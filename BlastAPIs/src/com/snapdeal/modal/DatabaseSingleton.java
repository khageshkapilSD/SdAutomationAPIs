package com.snapdeal.modal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSingleton {

	private static DatabaseSingleton INSTANCE = new DatabaseSingleton();
	
	private Connection conn;
	static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
    static final String DB_URL="jdbc:mysql://localhost:3306/BLAST_db";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "12345";
	
    private DatabaseSingleton(){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	public static DatabaseSingleton getInstance(){
		return INSTANCE;
	}
	
	public Connection getConnection(){
		return conn;
	}
	
	public ResultSet executeSQL(String sql) throws SQLException{
		Statement stmt;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			return rs;		
	}
	
	public int executeUpdate(String sql) throws SQLException{
		Statement stmt;
			stmt = conn.createStatement();
			 int status = stmt.executeUpdate(sql);
			return status;		
	}
}



