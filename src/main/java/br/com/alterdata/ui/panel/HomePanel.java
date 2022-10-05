package br.com.alterdata.ui.panel;

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
import br.com.alterdata.core.postgres.data.ConnectionInfo;
import br.com.alterdata.core.postgres.data.DatabaseConnection;
import br.com.alterdata.ui.dialog.PostgreSQLConnectionDialog;
import br.com.alterdata.ui.list.BackupList;
import br.com.alterdata.ui.type.BackupListDataInfo;
import br.com.alterdata.ui.type.FileBackupDataInfo;
import br.com.alterdata.ui.type.ImportBackupDataInfo;
import br.com.alterdata.ui.util.BorderFactory2;
import br.com.alterdata.ui.util.SwingUtilities;
import br.com.alterdata.util.Property;

import static br.com.alterdata.ui.util.SwingUtilities.*;
import static br.com.alterdata.ui.util.BorderFactory2.*;

public final class HomePanel extends JPanel {

	private JTextField txtpostgresqlhome;
	private JTextField txtpastaTempDo;
	private BackupList databaseBackupInfoList;

	@SuppressWarnings("serial")
	public HomePanel() {
		setPreferredSize(new Dimension(501, 1000));
		setMinimumSize(new Dimension(501, 1000));
		setLayout(null);
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(BorderFactory2.createTitledBorder("Configuração de instalação"));
		panel_2.setBounds(10, 13, 476, 91);
		add(panel_2);
		panel_2.setLayout(null);
		setBorder(null);
		
		panel_2.add(createLabel("Selecione a versão de destino:", 15, 24, 166, 26));

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"postgresql-x64-9.6-pkg.zip", "postgresql-x32-9.6-pkg.zip"}));
		comboBox.setBounds(191, 24, 275, 26);
		panel_2.add(comboBox);

		JLabel lblCaminhoDaPasta = new JLabel("Caminho da pasta data:");
		lblCaminhoDaPasta.setBounds(15, 55, 166, 26);
		panel_2.add(lblCaminhoDaPasta);

		txtpostgresqlhome = SwingUtilities.checkedTextField(Property.DEFAULT_POSTGRESQL_FOLDER, new JCheckBox() 
		{
			{
				setBounds(445, 55, 21, 26);
				panel_2.add(this);
			}
		});
		showContentWhenClicked(txtpostgresqlhome, 2, "Caminho");
		txtpostgresqlhome.setEditable(false);
		txtpostgresqlhome.setText(Property.DEFAULT_POSTGRESQL_FOLDER);
		txtpostgresqlhome.setBounds(191, 55, 250, 26);
		panel_2.add(txtpostgresqlhome);
		txtpostgresqlhome.setColumns(10);
		
		JPopupMenu popupMenu = new JPopupMenu();
		MainUI.addPopup(txtpostgresqlhome, popupMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Localizar pasta...");
		popupMenu.add(mntmNewMenuItem);

		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBorder(createTitledBorder("Configuração de backup"));
		panel_2_1.setBounds(10, 114, 476, 462);
		add(panel_2_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 24, 456, 225);
		panel_2_1.add(scrollPane_1);

		databaseBackupInfoList = new BackupList();
		/*databaseBackupInfoList.insertInfo(new FileBackupDataInfo(new File("C:\\Users\\David\\Downloads\\ALTERDATA_PACK.postgres_backup"), "ALTERDATA_PACK"));
		databaseBackupInfoList.insertInfo(new FileBackupDataInfo(new File("C:\\Users\\David\\Downloads\\ALTERDATA_PACK1.postgres_backup"), "ALTERDATA_PACK1"));
		databaseBackupInfoList.insertInfo(new ImportBackupDataInfo( new DatabaseConnection("ALTERDATA", new ConnectionInfo("localhost", "5432", "postgres"))));*/
		scrollPane_1.setViewportView(databaseBackupInfoList);

		JButton btnNewButton = new JButton("<html><center>Importar banco de dados<br>de um arquivo de backup</<center></html>");
		btnNewButton.setBounds(10, 254, 191, 44);
		panel_2_1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("<html><center>Importar banco de dados<br>de uma conexão</<center></html>");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PostgreSQLConnectionDialog.open(HomePanel.this);
			}
		});
		btnNewButton_1.setBounds(211, 254, 191, 44);
		panel_2_1.add(btnNewButton_1);
		
		panel_2_1.add(createLabel("Caminho para backups: ", 10, 305, 166, 26));

		txtpastaTempDo = checkedTextField("[Pasta Temp do Atualizador]", new JCheckBox() 
		{
			{
				setBounds(440, 305, 21, 26);
				panel_2_1.add(this);
			}
		});
		txtpastaTempDo.setEditable(false);
		txtpastaTempDo.setColumns(10);
		txtpastaTempDo.setBounds(186, 305, 250, 26);
		panel_2_1.add(txtpastaTempDo);

		JButton btnNewButton_1_1 = new JButton("");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(databaseBackupInfoList.getSelectedIndex() == -1)
					return;
				((DefaultListModel<BackupListDataInfo>)databaseBackupInfoList.getModel()).remove(databaseBackupInfoList.getSelectedIndex());
			}
		});
		btnNewButton_1_1.setIcon(new ImageIcon("icons/remove_selected_database_icon.png"));
		btnNewButton_1_1.setToolTipText("Remover banco de dados selecionado...");
		btnNewButton_1_1.setBounds(412, 254, 54, 44);
		panel_2_1.add(btnNewButton_1_1);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(176, 790, 146, 14);
		add(progressBar);

		JPanel panel_4 = new JPanel();

		panel_4.setLayout(null);



	}
	
	public BackupList getBackupListUI() {
		return databaseBackupInfoList;
	}
}
