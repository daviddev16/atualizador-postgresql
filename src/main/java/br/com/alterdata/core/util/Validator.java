package br.com.alterdata.core.util;

import java.util.Objects;

import br.com.alterdata.core.postgres.data.DatabaseConnection;

public final class Validator {

	public static boolean checkStringIsNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static String validateString(String name, String str) {
		if(checkStringIsNullOrEmpty(str))
			throw new NullPointerException(name + " is null or empty.");
		return str;
	}
	
	public static void validateDBConnectionInfo(DatabaseConnection connection) {
		Objects.requireNonNull(connection, "DatabaseConnection is null.");
		Objects.requireNonNull(connection.getConnection(), "DatabaseConnection is null.");
		Objects.requireNonNull(connection.getDatabaseName(), "DatabaseName is null.");
		validateString("Host", connection.getConnection().getHost());
		validateString("Port", connection.getConnection().getPort());
		validateString("Username", connection.getConnection().getUsername());
		validateString("Password", connection.getConnection().getPassword());
	}
	
}
