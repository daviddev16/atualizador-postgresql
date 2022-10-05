package br.com.alterdata.core.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;

public final class LafInitializer {

	public static void setup() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		UIManager.put("TitlePane.menuBarEmbedded", true);
		System.setProperty("flatlaf.animation", "true");
		System.setProperty("flatlaf.uiScale", "1.0");
		UIManager.put("JButton.menuBarEmbedded", true);
		FlatLaf.setUseNativeWindowDecorations(true);
		FlatIntelliJLaf.setup();
	}
	
}
