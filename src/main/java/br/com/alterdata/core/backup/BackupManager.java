package br.com.alterdata.core.backup;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.AlreadyBoundException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import br.com.alterdata.core.backup.data.RestoreBackupHandler;
import br.com.alterdata.core.postgres.data.DatabaseConnection;
import br.com.alterdata.core.util.Validator;

public final class BackupManager {

	private static BackupManager instance;

	private Queue<RestoreBackupHandler> doRestoreBackupQueue;
	private Queue<DatabaseConnection> doMakeBackupQueue;

	public BackupManager() {
		instance = this;
		doRestoreBackupQueue = new LinkedBlockingQueue<>();
		doMakeBackupQueue = new LinkedBlockingQueue<>();
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

		getDoRestoreQueue().add(new RestoreBackupHandler(databaseName, backupFile));
	}

	public void queueToDoBackupPool(DatabaseConnection databaseConnection)
			throws AlreadyBoundException {
		Validator.validateDBConnectionInfo(databaseConnection);

		if (containsOnAnyPool(databaseConnection.getDatabaseName()))
			throw new AlreadyBoundException( String.format("\"%s\" already exists in the pool.",
					databaseConnection.getDatabaseName()) );

		getDoBackupQueue().add(databaseConnection);
	}

	public boolean containsOnAnyPool(String name) {
		return containsOnDoBackupQueue(name) || 
				containsOnDoRestoreQueue(name);
	}
	
	public boolean containsOnDoRestoreQueue(String name) {		
		return getDoRestoreQueue().stream()
				.anyMatch(rbh -> rbh.getDatabase().equalsIgnoreCase(name));
	}

	public boolean containsOnDoBackupQueue(String name) {		
		return getDoBackupQueue().stream()
				.anyMatch(dbc -> dbc.getDatabaseName().equalsIgnoreCase(name));
	}

	public Queue<RestoreBackupHandler> getDoRestoreQueue() {
		return doRestoreBackupQueue;
	}

	/* uses databaseconnection to generate the desired backup */
	public Queue<DatabaseConnection> getDoBackupQueue() {
		return doMakeBackupQueue;
	}


}
