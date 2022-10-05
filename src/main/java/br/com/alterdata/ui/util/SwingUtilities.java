package br.com.alterdata.ui.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public final class SwingUtilities {

	public static JTextField checkedTextField(String defaultText, JCheckBox checkBox) {
		Objects.requireNonNull(checkBox);
		JTextField textField = new JTextField(defaultText);
		checkBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				textField.setEditable(checkBox.isSelected());
				textField.setText(checkBox.isSelected() ? "" : defaultText);
			}
		});
		return textField;
	}
	
	public static JTextField rowTextField(String title, String defaultFieldText, int x, int y, JPanel panel) {
		panel.add(createLabel(title, 15 + x, 23 + y, 71, 26) );
		JTextField textField = new JTextField();
		textField.setText(defaultFieldText);
		textField.setBounds(86 + x, 23 + y, 377, 26);
		panel.add(textField);
		return textField;
	}
	
	public static JPasswordField rowPasswordField(String title, String defaultFieldText, int x, int y, JPanel panel) {
		panel.add(createLabel(title, 15 + x, 23 + y, 71, 26) );
		JPasswordField passwordField = new JPasswordField();
		passwordField.setText(defaultFieldText);
		passwordField.setBounds(86 + x, 23 + y, 377, 26);
		panel.add(passwordField);
		return passwordField;
	}

	public static void showContentWhenClicked(JTextField textField, int clickCount, String title) {
		Objects.requireNonNull(textField);
		textField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() >= clickCount)
					JOptionPane.showMessageDialog(null, textField.getText(), title, 
							JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
	}

	public static JLabel createLabel(String title, int x, int y, int width, int height) {
		JLabel label = new JLabel(title);
		label.setBounds(x, y, width, height);
		return label;
	}
	
}
