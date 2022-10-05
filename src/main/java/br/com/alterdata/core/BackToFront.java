package br.com.alterdata.core;


import br.com.alterdata.core.backup.BackupManager;
import br.com.alterdata.ui.list.BackupList;
import br.com.alterdata.ui.type.FileBackupDataInfo;
import br.com.alterdata.ui.type.ImportBackupDataInfo;

/* backend to frontend */
public final class BackToFront {

	/* call everytime a backup stuff is changed */
	public static void updateBackupListUI(BackupList backupList) {
		/* clear up all ui backup elements */
		backupList.clear();
		/* send all backupmanager stuff to ui */
		BackupManager backupManager = BackupManager.getBackupManager();
		backupManager.getDoBackupQueue().forEach(dbConn 
				-> backupList.insert( new ImportBackupDataInfo(dbConn) ));
		
		backupManager.getDoRestoreQueue().forEach(rbh 
				-> backupList.insert( new FileBackupDataInfo(rbh) ));
	}
	
}
