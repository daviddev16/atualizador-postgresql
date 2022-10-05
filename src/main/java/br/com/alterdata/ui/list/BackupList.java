package br.com.alterdata.ui.list;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import br.com.alterdata.core.postgres.data.ConnectionInfo;
import br.com.alterdata.ui.Colors;
import br.com.alterdata.ui.type.BackupListDataInfo;
import br.com.alterdata.ui.type.FileBackupDataInfo;
import br.com.alterdata.ui.type.ImportBackupDataInfo;

public class BackupList extends JList<BackupListDataInfo> implements ListCellRenderer<BackupListDataInfo> {

	private static final long serialVersionUID = 9112140296593329958L;

	private DefaultListModel<BackupListDataInfo> model = new DefaultListModel<>();

	public BackupList() {
		super();
		setModel(model);
		setCellRenderer(this);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends BackupListDataInfo> list,
			BackupListDataInfo value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {

		JLabel label = new JLabel();
		label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		if(isSelected) {
			label.setOpaque(true);
			label.setForeground(Color.WHITE);
		}
		else {
			label.setForeground(Colors.NICE_BLACK);
			label.setOpaque(false);
		}
		label.setBackground(Colors.NICE_BLACK);
		label.setIconTextGap(10);
		label.setHorizontalTextPosition(JLabel.RIGHT);

		if(value instanceof FileBackupDataInfo) {
			label.setText("<html>"+value.getName()+"<br>"+value.getMessage()+"</html>");
			label.setIcon(new ImageIcon("icons/restore_from_file_icon.png"));
		}
		else if(value instanceof ImportBackupDataInfo){
			label.setIcon(new ImageIcon("icons/build_backup_before_icon.png"));
			ConnectionInfo conn = ((ImportBackupDataInfo)value).getDatabaseConnection().getConnection();
			label.setText("<html>"+value.getName()+"<br><font color=\"#E49B0F\"><i>[Aviso]: "+value.getMessage()+
					"</i></font><br>┗ Conexão: "+ conn.getHost()+":"+conn.getPort() +"<br>┗ User: "+conn.getUsername()+"</html>");
		}
		return label;
	}
	
	public void insert(BackupListDataInfo backupListDataInfo) {
		model.addElement(backupListDataInfo);
	}
	
	public void clear() {
		model.clear();
	}

}
