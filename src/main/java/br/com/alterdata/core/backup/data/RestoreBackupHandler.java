package br.com.alterdata.core.backup.data;

import java.io.File;

public final class RestoreBackupHandler {

	private String database;
	private File backupFile;
	
	public RestoreBackupHandler(String database, File backupFile) {
		this.database = database;
		this.backupFile = backupFile;
	}
	
	public String getDatabase() {
		return database;
	}

	public File getBackupFile() {
		return backupFile;
	}
	
	@Override
	public String toString() {
		return database;
	}

}
