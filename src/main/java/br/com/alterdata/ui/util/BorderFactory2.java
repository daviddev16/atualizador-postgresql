package br.com.alterdata.ui.util;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public final class BorderFactory2 {

	public static Border createTitledBorder(String title) {
		return new TitledBorder(new LineBorder(Color.LIGHT_GRAY), title, TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY);
	}

}
