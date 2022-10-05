package br.com.alterdata;

import static br.com.alterdata.ui.util.SwingFactory.createEmptyPanel;
import static br.com.alterdata.ui.util.SwingFactory.createSeparator;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.management.InstanceAlreadyExistsException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import br.com.alterdata.core.ui.Icons;
import br.com.alterdata.ui.panel.MigrationPanel;

public class MainUI extends JFrame {

	private static final long serialVersionUID = 6120316214655901236L;
	private static MainUI instance;
	
	private MigrationPanel migrationPage; 
	
	private JTabbedPane tabbedPane;

	private MainUI() throws InstanceAlreadyExistsException {
		
		if (instance != null)
			throw new InstanceAlreadyExistsException("A instance of MainUI already exists.");
		
		/* setting up the JFrame */
		setTitle("Atualizador do PostgreSQL");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 521, 700);
		setLocationRelativeTo(null);

		setContentPane(createEmptyPanel());
		
		/* separator between the logo and tabbedpane */
		getContentPane().add(createSeparator(0, 89, 509, 2));
		
		createTabbedPane();
		createMigrationPage();
		
		instance = this;
	}
	
	public static void openUI() {
		EventQueue.invokeLater(() -> {
			try {
				MainUI frame = new MainUI();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		});
	}
	
	public void createMigrationPage() {
		
		/* creating JTabbedPanel panel */
		JPanel migrationPanel = new JPanel();
		migrationPanel.setBorder(null);
		migrationPanel.setLayout(new BorderLayout(0, 0));

		/* adding scroll to the page */
		JScrollPane migrationScrollPanel = new JScrollPane();
		migrationScrollPanel.setBorder(null);
		migrationPanel.add(migrationScrollPanel, BorderLayout.CENTER);
		
		/* creating the tab page */
		migrationPage = new MigrationPanel();
		migrationScrollPanel.setViewportView(migrationPage);

		/* adding to the tabbedPanel */
		tabbedPane.addTab("Configuração de migração", Icons.MIGRATION_TAB_ICON, migrationPanel, null);
	
	}
	
	public void createTabbedPane() {
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 91, 504, 570);
		tabbedPane.setBorder(null);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		/* adds to the contentPane frame */
		getContentPane().add(tabbedPane);
	}
	
	public MigrationPanel getMigrationPageUI() {
		return migrationPage;
	}
	
}
