package br.com.alterdata.core.postgres.data;

public final class DatabaseConnection {

	private String databaseName;
	private ConnectionInfo connection;

	public DatabaseConnection(String databaseName, ConnectionInfo connection) {
		this.databaseName = databaseName;
		this.connection = connection;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public ConnectionInfo getConnection() {
		return connection;
	}

}