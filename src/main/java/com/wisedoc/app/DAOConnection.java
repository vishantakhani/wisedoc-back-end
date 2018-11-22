package com.wisedoc.app;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DAOConnection {
	static Connection con=null;
	static Statement st=null;

	public DAOConnection() {
		if(con==null)
		{
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con =  DriverManager.getConnection("jdbc:mysql://192.168.43.186:3306/dbwisedoc","root","pass");
				con.setAutoCommit(true);
				st=con.createStatement();
			} catch (Exception e) {
				System.out.println("Exception in DAO Class"+e);
			}
		}
		
	}

	

	public void exQuery(String query) {
		try {
			System.out.println(query);
			
		System.out.println(st.executeUpdate(query));
System.out.println("Executed");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("while executing "+e);
		}
	}

	public ResultSet selectQuery(String query) {
		ResultSet rs = null;
		try {
			rs =  st.executeQuery(query);
			return rs;
		} catch (Exception e) {
			System.out.println("while selecting "+e);
		}
		return rs;
	}
}
