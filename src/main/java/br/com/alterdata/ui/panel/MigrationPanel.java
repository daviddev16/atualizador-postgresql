package br.com.alterdata.ui.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import br.com.alterdata.MainUI;
import br.com.alterdata.core.BackToFront;
import br.com.alterdata.core.MemoryChecker;
import br.com.alterdata.core.backup.BackupManager;
import br.com.alterdata.core.installation.Distribution;
import br.com.alterdata.core.mics.Defaults;
import br.com.alterdata.core.postgres.data.ConnectionInfo;
import br.com.alterdata.core.postgres.data.DatabaseConnection;
import br.com.alterdata.ui.dialog.PostgreSQLConnectionDialog;
import br.com.alterdata.ui.list.BackupList;
import br.com.alterdata.ui.type.BackupListDataInfo;
import br.com.alterdata.ui.type.FileBackupDataInfo;
import br.com.alterdata.ui.type.ImportBackupDataInfo;
import br.com.alterdata.ui.util.SwingFactory;

import static br.com.alterdata.ui.util.SwingFactory.*;

public final class MigrationPanel extends JPanel {

	private static final long serialVersionUID = 3221673804106471575L;

	private JTextField txtpastaTempDo;

	private BackupList databaseBackupInfoList;

	private JPanel installationConfigPanel;
	private JComboBox<Distribution> postgresDistComboBox;
	private JTextField postgresHomeTextField;

	@SuppressWarnings("serial")
	public MigrationPanel() {


		setPreferredSize(new Dimension(501, 1000));
		setMinimumSize(new Dimension(501, 1000));
		setLayout(null);
		setBorder(null);

		createInstallationConfigPanel();


		JPanel backupConfigPanel = new JPanel();
		backupConfigPanel.setLayout(null);
		backupConfigPanel.setBorder(createTitledBorder("Configuração de backup"));
		backupConfigPanel.setBounds(10, 114, 476, 343);
		add(backupConfigPanel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 24, 456, 225);
		backupConfigPanel.add(scrollPane_1);

		databaseBackupInfoList = new BackupList();
		/*databaseBackupInfoList.insertInfo(new FileBackupDataInfo(new File("C:\\Users\\David\\Downloads\\ALTERDATA_PACK.postgres_backup"), "ALTERDATA_PACK"));
		databaseBackupInfoList.insertInfo(new FileBackupDataInfo(new File("C:\\Users\\David\\Downloads\\ALTERDATA_PACK1.postgres_backup"), "ALTERDATA_PACK1"));
		databaseBackupInfoList.insertInfo(new ImportBackupDataInfo( new DatabaseConnection("ALTERDATA", new ConnectionInfo("localhost", "5432", "postgres"))));*/
		scrollPane_1.setViewportView(databaseBackupInfoList);

		JButton btnNewButton = new JButton("<html><center>Importar banco de dados<br>de um arquivo de backup</<center></html>");
		btnNewButton.setBounds(10, 254, 191, 44);
		backupConfigPanel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("<html><center>Importar banco de dados<br>de uma conexão</<center></html>");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PostgreSQLConnectionDialog.open(MigrationPanel.this);
			}
		});
		btnNewButton_1.setBounds(211, 254, 191, 44);
		backupConfigPanel.add(btnNewButton_1);

		backupConfigPanel.add(createLabel("Caminho para backups: ", 10, 305, 166, 26));

		txtpastaTempDo = checkedTextField("[Pasta Temp do Atualizador]", new JCheckBox() 
		{
			{
				setBounds(440, 305, 21, 26);
				backupConfigPanel.add(this);
			}
		});
		txtpastaTempDo.setEditable(false);
		txtpastaTempDo.setColumns(10);
		txtpastaTempDo.setBounds(186, 305, 250, 26);
		backupConfigPanel.add(txtpastaTempDo);

		JButton btnNewButton_1_1 = new JButton("");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(databaseBackupInfoList.getSelectedIndex() == -1)
					return;

				//((DefaultListModel<BackupListDataInfo>)databaseBackupInfoList.getModel()).remove(databaseBackupInfoList.getSelectedIndex());

				if (databaseBackupInfoList.getSelectedValue() instanceof FileBackupDataInfo) {
					BackupManager.getBackupManager().removeFromDoRestoreQueue(
							((FileBackupDataInfo)databaseBackupInfoList.getSelectedValue()).getRestoreBackupHandler());

				} else if (databaseBackupInfoList.getSelectedValue() instanceof ImportBackupDataInfo) {
					BackupManager.getBackupManager().removeFromDoBackupQueue(
							((ImportBackupDataInfo)databaseBackupInfoList.getSelectedValue()).getDatabaseConnection());
				}

				BackToFront.updateBackupListUI(databaseBackupInfoList);
			}
		});
		btnNewButton_1_1.setIcon(new ImageIcon("icons/remove_selected_database_icon.png"));
		btnNewButton_1_1.setToolTipText("Remover banco de dados selecionado...");
		btnNewButton_1_1.setBounds(412, 254, 54, 44);
		backupConfigPanel.add(btnNewButton_1_1);


		JPanel memoryConfigPanel = new JPanel();
		memoryConfigPanel.setBounds(10, 461, 476, 343);
		add(memoryConfigPanel);
		memoryConfigPanel.setLayout(null);
		memoryConfigPanel.setBorder(createTitledBorder("Verificação de espaço disponível"));

		JPanel panel_4 = new JPanel();

		panel_4.setLayout(null);

	}

	public void createInstallationConfigPanel() {

		/* setting up the installation "section" */
		installationConfigPanel = new JPanel();
		installationConfigPanel.setBorder(createTitledBorder("Configuração de instalação"));
		installationConfigPanel.setBounds(10, 13, 476, 91);
		installationConfigPanel.setLayout(null);
		
		postgresDistComboBox = new JComboBox<Distribution>();
		DefaultComboBoxModel<Distribution> model = new DefaultComboBoxModel<Distribution>();
		model.addElement(new Distribution("postgresql-x64-9.6-pkg.zip", null));
		postgresDistComboBox.setModel(model);
		postgresDistComboBox.setBounds(191, 24, 275, 26);
		

		/* configurando o textfield para ser editavel ou não */
		postgresHomeTextField = checkedTextField(
				Defaults.DEFAULT_POSTGRESQL_FOLDER,  
				createCheckBoxTo(445, 55, 21, 26, installationConfigPanel)
				);

		postgresHomeTextField.setEditable(false);
		postgresHomeTextField.setText(Defaults.DEFAULT_POSTGRESQL_FOLDER);
		postgresHomeTextField.setBounds(191, 55, 250, 26);
		postgresHomeTextField.setColumns(10);

		showContentWhenClicked(postgresHomeTextField, 2, "Caminho");

		/* só aparece se o text estiver editavel. */
		JPopupMenu locateFolderPopupMenu = SwingFactory.createSingleItemMenu("Localizar pasta...");
		/*((JMenuItem) popupMenu.getComponent(0)).addActionListener((al) -> {
			System.out.println("ok.");
		});*/

		addPopup(postgresHomeTextField, locateFolderPopupMenu, 
				(component) -> component instanceof JTextField && ((JTextField)component).isEditable());
		
		/* adicionando componentes no painel e depois o painel no root */
		installationConfigPanel.add(postgresDistComboBox);
		installationConfigPanel.add(postgresHomeTextField);
		installationConfigPanel.add(createLabel("Selecione a versão de destino:", 15, 24, 166, 26));
		installationConfigPanel.add(createLabel("Caminho da pasta data:", 15, 55, 166, 26));
		
		add(installationConfigPanel);
	}

	public JTextField getPostgresHomeTextField() {
		return postgresHomeTextField;
	}

	public JComboBox<Distribution> getDistributionComboBox() {
		return postgresDistComboBox;
	}

	public BackupList getBackupListUI() {
		return databaseBackupInfoList;
	}
}
