package br.com.alterdata.core.backup;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.AlreadyBoundException;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.management.InstanceAlreadyExistsException;

import br.com.alterdata.core.Metrics;
import br.com.alterdata.core.backup.data.RestoreBackupHandler;
import br.com.alterdata.core.postgres.Query;
import br.com.alterdata.core.postgres.data.DatabaseConnection;
import br.com.alterdata.core.util.Validator;

public final class BackupManager {

	private static BackupManager instance;

	private Queue<RestoreBackupHandler> doRestoreBackupQueue;
	private Queue<DatabaseConnection> doMakeBackupQueue;

	BackupManager() throws InstanceAlreadyExistsException {

		if (instance != null)
			throw new InstanceAlreadyExistsException("A instance of BackupManager already exists.");

		doRestoreBackupQueue = new LinkedBlockingQueue<>();
		doMakeBackupQueue = new LinkedBlockingQueue<>();
	}

	public static void createInstance() throws InstanceAlreadyExistsException {
		new BackupManager();
	}

	public static BackupManager getBackupManager() {
		return instance;
	}

	/* all backup files that will be restored */
	public void queueToDoRestorePool(File backupFile, String databaseName) 
			throws FileNotFoundException, AlreadyBoundException {

		if (containsOnAnyPool(databaseName))
			throw new AlreadyBoundException( String.format("\"%s\" already exists in the pool.", databaseName) );

		else if (!backupFile.exists())
			throw new FileNotFoundException( String.format("[%s]: Backup file doesn't exists.", 
					backupFile.getAbsolutePath()));

		/* check if the current thread can read the backup file */
		System.getSecurityManager().checkRead(backupFile.getAbsolutePath());

		getDoRestorePool().add(new RestoreBackupHandler(databaseName, backupFile));
	}

	public void queueToDoBackupPool(DatabaseConnection databaseConnection)
			throws AlreadyBoundException {

		Validator.validateDBConnectionInfo(databaseConnection);

		if (containsOnAnyPool(databaseConnection.getDatabaseName()))
			throw new AlreadyBoundException( String.format("\"%s\" already exists in the pool.",
					databaseConnection.getDatabaseName()) );

		/* refatorar metrics */
		try {
			Metrics.databaseSize.put(databaseConnection.getDatabaseName(), 
					Double.parseDouble(Query.queryDatabaseSize(databaseConnection.getConnection(), databaseConnection.getDatabaseName())));
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getDoBackupPool().add(databaseConnection);
	}

	public void removeFromDoRestoreQueue(RestoreBackupHandler rbh) {

		if(Metrics.databaseSize.containsKey(rbh.getDatabase())) {
			Metrics.databaseSize.remove(rbh.getDatabase());
		}

		getDoRestorePool().remove(rbh);
	}

	public void removeFromDoBackupQueue(DatabaseConnection dbConn) {

		if(Metrics.databaseSize.containsKey(dbConn.getDatabaseName())) {
			Metrics.databaseSize.remove(dbConn.getDatabaseName());
		}

		getDoBackupPool().remove(dbConn);
	}


	public boolean containsOnAnyPool(String name) {
		return containsOnDoBackupPool(name) || 
				containsOnDoRestorePool(name);
	}

	public boolean containsOnDoRestorePool(String name) {		
		return getDoRestorePool().stream()
				.anyMatch(rbh -> rbh.getDatabase().equalsIgnoreCase(name));
	}

	public boolean containsOnDoBackupPool(String name) {		
		return getDoBackupPool().stream()
				.anyMatch(dbc -> dbc.getDatabaseName().equalsIgnoreCase(name));
	}



	public Queue<RestoreBackupHandler> getDoRestorePool() {
		return doRestoreBackupQueue;
	}

	/* uses databaseconnection to generate the desired backup */
	public Queue<DatabaseConnection> getDoBackupPool() {
		return doMakeBackupQueue;
	}


}
