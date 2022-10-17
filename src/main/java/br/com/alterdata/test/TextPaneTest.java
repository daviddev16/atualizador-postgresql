package br.com.alterdata.test;
import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import javax.swing.border.*;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import br.com.alterdata.core.ui.LafInitializer;

import javax.swing.GroupLayout.Alignment;

public class TextPaneTest extends JFrame
{
	private JPanel topPanel;
	private JTextPane tPane;

	public TextPaneTest() throws BadLocationException
	{
		LafInitializer.setup();
		topPanel = new JPanel();        

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);            

		EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));

		setBounds(0, 0, 100, 100);

		getContentPane().add(topPanel);
		topPanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		topPanel.add(scrollPane, BorderLayout.CENTER);

		tPane = new JTextPane() {

			@Override
			public void paint(Graphics g) {

				 Graphics2D g2 = (Graphics2D) g;
			        // Experiment with different rendering hints
			        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
			                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
			                RenderingHints.VALUE_RENDER_QUALITY);
			        super.paint(g2);
			}
			
		};
		tPane.setBackground(new Color(51, 0, 51));
		scrollPane.setViewportView(tPane);
		tPane.setBorder(eb);
		//tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		tPane.setMargin(new Insets(5, 5, 5, 5));
		
		setVisible(true);   
	}

	 /*private void appendToPane(JTextPane tp, String msg, Color c)
	    {
	        StyleContext sc = StyleContext.getDefaultStyleContext();
	        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
	        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Consolas");
	        aset = sc.addAttribute(aset, StyleConstants.FontSize, 13);
	        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
	        int len = tp.getDocument().getLength();
	        tp.setCaretPosition(len);
	        tp.setCharacterAttributes(aset, false);
	        tp.replaceSelection(msg);
	    }*/

	public static void main(String... args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try {
					new TextPaneTest();
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}