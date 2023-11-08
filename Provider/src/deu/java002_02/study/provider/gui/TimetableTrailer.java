package deu.java002_02.study.provider.gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TimetableTrailer extends JPanel
{
	private JButton m_saveButton;
	
	public TimetableTrailer()
	{
		super.setLayout(null);
		super.setSize(640, 70);
		super.setLocation(0, 410);
		super.setBackground(Color.LIGHT_GRAY);
		
		m_saveButton = getSaveButton();
		
		super.add(m_saveButton);
	}

	private JButton getSaveButton()
	{
		JButton button = new JButton();
		button.setText("¿˙¿Â");
		button.setSize(60, 30);
		button.setLocation(550, 20);
		return button;
	}
}