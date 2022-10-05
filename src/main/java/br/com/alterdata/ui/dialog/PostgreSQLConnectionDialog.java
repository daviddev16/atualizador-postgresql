package br.com.alterdata.ui.dialog;

import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import br.com.alterdata.MainUI;
import br.com.alterdata.core.BackToFront;
import br.com.alterdata.core.backup.BackupManager;
import br.com.alterdata.core.postgres.Query;
import br.com.alterdata.core.postgres.data.ConnectionInfo;
import br.com.alterdata.core.postgres.data.DatabaseConnection;
import br.com.alterdata.ui.panel.MigrationPanel;
import br.com.alterdata.ui.util.SwingFactory;

import static br.com.alterdata.ui.util.SwingFactory.*;

import java.awt.event.ActionListener;
import java.rmi.AlreadyBoundException;
import java.sql.SQLException;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.HeadlessException;

public class PostgreSQLConnectionDialog extends JDialog {

	private static final long serialVersionUID = -8962031111288482162L;
	private JTextField txtLocalhost;
	private JPasswordField passwordField;
	private JTextField textField_2;
	private JTextField textField_4;

	/* connectar no PostgreSQL com preparedStatement */

	/**
	 * Launch the application.
	 */

	/* create a sigleton for homePanel  */
	public static void open(MigrationPanel homePanel) {
		PostgreSQLConnectionDialog dialog = new PostgreSQLConnectionDialog(homePanel);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}


	/**
	 * Create the dialog.
	 */
	public PostgreSQLConnectionDialog(MigrationPanel homePanel) {
		setResizable(false);
		setTitle("Conexão com o PostgreSQL atual");
		setBounds(100, 100, 515, 242);
		setModal(true);
		getContentPane().setLayout(null);

		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBorder(SwingFactory.createTitledBorder("Configuração de conexão"));
		panel.setBounds(10, 10, 480, 185);
		getContentPane().add(panel);
		panel.setLayout(null);

		JTextField hostTextField = rowTextField("Host:", "localhost", 0, 0, panel);
		JTextField portTextField = rowTextField("Porta:", "5432", 0, 27, panel);
		JTextField userTextField = rowTextField("Usuário:", "postgres", 0, 54, panel);
		JPasswordField passwdTextField = rowPasswordField("Senha:", "#abc123#", 0, 81, panel);

		JButton btnNewButton = new JButton("Conectar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ConnectionInfo connInfo = new ConnectionInfo(hostTextField.getText(), portTextField.getText(), 
						userTextField.getText(), new String(passwdTextField.getPassword()));

				Set<String> dbs = null;
				try {

					dbs = Query.queryAllDatabases(connInfo);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

				/* if dbs == null or dbs is empty, failed to query" */
				
				JOptionPane.showMessageDialog(null, dbs);

				BackupManager bkpManager = BackupManager.getBackupManager();
				for(String dbName : dbs) {
					try {
						bkpManager.queueToDoBackupPool(new DatabaseConnection(dbName, connInfo));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					BackToFront.updateBackupListUI(homePanel.getBackupListUI());
				}

			}
		});
		btnNewButton.setBounds(307, 142, 156, 26);
		panel.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("<html><i><left>Aviso: Não será possível importar bancos de dados <br>sem antes ter uma conexão com o PostgreSQL atual.</left></i></html>");
		lblNewLabel_1.setForeground(new Color(139, 0, 0));
		lblNewLabel_1.setBounds(15, 142, 282, 26);
		panel.add(lblNewLabel_1);
	}
}
