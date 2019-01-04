package com.objectfrontier.training.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionManager {

	private static HikariDataSource dataSource;

	static {

		HikariConfig config = new HikariConfig("/conn.properties");
		config.setMaximumPoolSize(3);
		dataSource = new HikariDataSource(config);
	}

	public Connection initConnection() throws SQLException, IOException {

		Connection  conn = dataSource.getConnection();
		conn.setAutoCommit(false);
		return conn;

	}

	public  void releaseConnection(Connection conn, boolean flag) throws SQLException {

		if (flag == true) {
			conn.commit();
		} else {
			conn.rollback();
		}
		conn.close();
	}

	public static void main(String[] args) throws SQLException, IOException {

		ConnectionManager connectionManager = new ConnectionManager();
		Connection con = connectionManager.initConnection();
		System.out.println(con);
		connectionManager.releaseConnection(con, false);
	}

}
