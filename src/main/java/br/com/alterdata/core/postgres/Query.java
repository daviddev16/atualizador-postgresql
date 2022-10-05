package br.com.alterdata.core.postgres;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.alterdata.core.postgres.data.ConnectionInfo;

public final class Query {
	
	public static final String QUERY_GET_ALL_DATABASES = "select datname FROM pg_database WHERE datistemplate='f' AND datacl IS NOT NULL;";
	public static final String QUERY_GET_DATABASE_SIZE = "select pg_database_size('%s')";
	
	public static Set<String> queryAllDatabases(ConnectionInfo connInfo) throws SQLException {
		Set<String> databases = new HashSet<>();
		ResultSet resultSet = PostgreSQL.executeQuery(connInfo, QUERY_GET_ALL_DATABASES);
		while(resultSet.next()) {
			synchronized (databases) {
				databases.add(resultSet.getString(1));	
			}
		}
		return databases;
	}
	
	public static String queryDatabaseSize(ConnectionInfo connInfo, String db) throws SQLException {
		ResultSet resultSet = PostgreSQL.executeQuery(connInfo, String.format(QUERY_GET_DATABASE_SIZE, db));
		while(resultSet.next()) {
			return resultSet.getString(01);
		}
		return "0";
	}

	public static void main(String[] args) throws SQLException {

		//Set<String> dbs = queryAllDatabases(new ConnectionInfo("localhost", "5432", "postgres", "#abc123#"));
		//dbs.forEach(System.out::println);
		System.out.println(queryDatabaseSize(new ConnectionInfo("localhost", "5432", "postgres", "#abc123#"), "ALTERDATA_PACK"));
	}

	
}
