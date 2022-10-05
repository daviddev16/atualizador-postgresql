package br.com.alterdata.core.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.alterdata.core.postgres.data.ConnectionInfo;
import br.com.alterdata.core.postgres.data.DatabaseConnection;
import br.com.alterdata.core.util.Validator;

public final class PostgreSQL {

	public static synchronized Connection createConnection(DatabaseConnection dbConnInfo) throws SQLException {
		Validator.validateDBConnectionInfo(dbConnInfo);
		ConnectionInfo connInfo = dbConnInfo.getConnection();
		return DriverManager.getConnection(String.format("jdbc:postgresql://%s:%s/%s", 
				connInfo.getHost(), connInfo.getPort(), dbConnInfo.getDatabaseName()), 
				connInfo.getUsername(), connInfo.getPassword());
	}

	public static synchronized ResultSet executeQuery(DatabaseConnection dbConnInfo, final String query) throws SQLException {
		Validator.validateString("Query", query);
		Connection connection = createConnection(dbConnInfo);
		Statement statement = connection.createStatement();
		final ResultSet resultSet = statement.executeQuery(query);
		connection.close();
		return resultSet;
	}
	
	public static synchronized ResultSet executeQuery(ConnectionInfo connInfo, String query) throws SQLException {
		return executeQuery(new DatabaseConnection("", connInfo), query);
	}

}
