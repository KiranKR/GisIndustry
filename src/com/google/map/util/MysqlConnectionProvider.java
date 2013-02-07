package com.google.map.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnectionProvider implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MysqlConnectionProvider INST;
	private static Connection conn = null;

	public static Connection getNewConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		if (INST == null) {
			INST = new MysqlConnectionProvider();
		}
		return INST.createConnection("localhost:3306", "gisindustry", "root", "root");
		//return INST.createConnection("localhost:3306", "gisarea", "root", "AmplE@7032");
		// url="jdbc:mysql://localhost:3306/confluence?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf8"maxActive="15"maxIdle="7"validationQuery="Select 1"
		// return INST.createConnection("localhost", lsDbNme,
		// "mohan032_iscroot", "isc123$");
	}

	public Connection createConnection(String host, String database, String user, String password) throws SQLException {
		String url = new String("jdbc:mysql://" + host + "/" + database);
		return DriverManager.getConnection(url, user, password);
	}

	/**
	 * 
	 * free connection to connection pool
	 * 
	 * @param rs
	 * @param stmt
	 * @param pstmt
	 * @param conn
	 */

	public static void releaseConnection(ResultSet rs, Statement stmt, PreparedStatement pstmt, Connection conn) {
		// System.out.println("Free db connection");
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}
}
