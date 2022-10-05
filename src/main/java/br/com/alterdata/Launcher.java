package br.com.alterdata;

import br.com.alterdata.core.backup.BackupManager;
import br.com.alterdata.core.ui.LafInitializer;

public class Launcher {

	public static void main(String[] args) {
		try {
			System.out.println("Launching...");
			/* setting up back-end stuff */
			BackupManager.createInstance();
			/* setting up front-end stuff */
			LafInitializer.setup();
			MainUI.openUI();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
}
