package br.com.alterdata.core.ui;

import java.io.File;

import javax.swing.ImageIcon;

/* all resources are saved in the program location path */
public final class Icons {

	private static final String ICONS_PATH = "icons";
	
	public static final ImageIcon MIGRATION_TAB_ICON = createIcon("migration_configuration_icon");
	
	private static ImageIcon createIcon(String iconPath) {
		return new ImageIcon(String.format("%s%s%s.%s", ICONS_PATH, File.separator, iconPath, "png"));
	}
	
}
