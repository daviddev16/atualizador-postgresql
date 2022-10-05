package br.com.alterdata.ui.type;


import br.com.alterdata.core.backup.data.RestoreBackupHandler;

public class FileBackupDataInfo implements BackupListDataInfo {

	private RestoreBackupHandler restoreBackupHandler;
	
	public FileBackupDataInfo(RestoreBackupHandler restoreBackupHandler) {
		this.restoreBackupHandler = restoreBackupHandler;
	}

	public RestoreBackupHandler getRestoreBackupHandler() {
		return restoreBackupHandler;
	}
	
	@Override
	public String getName() {
		return getRestoreBackupHandler().getDatabase() != null 
				? getRestoreBackupHandler().getDatabase() : "???";
	}

	@Override
	public String getMessage() {
		return getRestoreBackupHandler().getBackupFile() != null 
				? getRestoreBackupHandler().getBackupFile().getPath() : "/???";
	}
	
}
