package br.com.alterdata.ui.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.function.Function;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class SwingFactory {

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
	
	public static Border createTitledBorder(String title) {
		return new TitledBorder(new LineBorder(Color.LIGHT_GRAY), title, TitledBorder.LEADING, 
				TitledBorder.TOP, null, Color.GRAY);
	}

	public static JLabel createLabel(String title, int x, int y, int width, int height) {
		JLabel label = new JLabel(title);
		label.setBounds(x, y, width, height);
		return label;
	}
	
	public static JCheckBox createCheckBoxTo(int x, int y, int width, int height, JPanel contentPane) {
		JCheckBox checkBox = new JCheckBox();
		checkBox.setBounds(x, y, width, height);
		contentPane.add(checkBox);
		return checkBox;
	}

	public static JSeparator createSeparator(int x, int y, int width, int height) {
		JSeparator separator = new JSeparator();
		separator.setBounds(x, y, width, height);
		return separator;
	}

	public static void addPopup(Component component, final JPopupMenu popup, Function<JComponent, Boolean> function) {
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
				if (!function.apply(comp))
					return;
				
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	public static JPopupMenu createSingleItemMenu(String title) {
		JPopupMenu menu = new JPopupMenu();
		menu.add(new JMenuItem(title));
		return menu;
	}
	
	public static JPanel createEmptyPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setLayout(null);
		return panel;
	}
}
