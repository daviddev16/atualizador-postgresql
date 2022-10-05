package br.com.alterdata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import br.com.alterdata.core.backup.BackupManager;
import br.com.alterdata.ui.list.BackupList;
import br.com.alterdata.ui.panel.HomePanel;
import br.com.alterdata.ui.type.FileBackupDataInfo;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;

public class MainUI extends JFrame {

	private static final long serialVersionUID = 6120316214655901236L;

	private JPanel contentPane;
	private JTextField txtpostgresqlhome;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new BackupManager();
					
					JFrame.setDefaultLookAndFeelDecorated(true);
					JDialog.setDefaultLookAndFeelDecorated(true);

					UIManager.put("TitlePane.menuBarEmbedded", true);

					System.setProperty("flatlaf.animation", "true");
					System.setProperty("flatlaf.uiScale", "1.0");

					UIManager.put("JButton.menuBarEmbedded", true);
					FlatLaf.setUseNativeWindowDecorations(true);
				
					FlatIntelliJLaf.setup();
					
					MainUI frame = new MainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainUI() {
		setTitle("Atualizador do PostgreSQL");
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 521, 700);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 91, 504, 570);
		tabbedPane.setBorder(null);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		contentPane.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		tabbedPane.addTab("Configuração de migração", new ImageIcon("icons/migration_configuration_icon.png"), panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		HomePanel homePanel = new HomePanel();
		scrollPane.setViewportView(homePanel);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setLayout(null);

		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 89, 509, 2);
		contentPane.add(separator);
		
		
	}
	public static void addPopup(Component component, final JPopupMenu popup) {
		
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {

				JComponent comp = (JComponent) e.getComponent();
				if(comp instanceof JTextField && !((JTextField)comp).isEditable())
					return;
				
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
