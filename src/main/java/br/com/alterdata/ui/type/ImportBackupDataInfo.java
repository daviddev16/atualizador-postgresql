package br.com.alterdata.ui.type;

import br.com.alterdata.core.postgres.data.DatabaseConnection;

public class ImportBackupDataInfo implements BackupListDataInfo {

	private DatabaseConnection databaseConnection;
	
	public ImportBackupDataInfo(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public DatabaseConnection getDatabaseConnection() {
		return databaseConnection;
	}
	
	@Override
	public String getName() {
		return databaseConnection.getDatabaseName();
	}

	@Override
	public String getMessage() {
		return "Um backup dessa base será feito";
	}

}
