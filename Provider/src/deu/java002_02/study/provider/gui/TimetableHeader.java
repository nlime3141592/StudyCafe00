package deu.java002_02.study.provider.gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimetableHeader extends JPanel
{
	private JLabel m_lbDay;
	private JLabel m_lbTime;
	private JLabel m_lbEnabled;
	private JPanel m_separatorBlack;
	private JPanel m_separatorGray;
	
	public TimetableHeader()
	{
		super.setLayout(null);
		super.setLocation(0, 0);
		super.setSize(640, 60);
		
		m_lbDay = getDayLabel();
		m_lbTime = getTimeLabel();
		m_lbEnabled = getEnableLabel();
		m_separatorBlack = getSeparatorBlack();
		m_separatorGray = getSeparatorGray();
		super.add(m_lbDay);
		super.add(m_lbTime);
		super.add(m_lbEnabled);
		super.add(m_separatorBlack);
		super.add(m_separatorGray);
	}

	private JLabel getDayLabel()
	{
		JLabel label = new JLabel();
		label.setText("요일");
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setSize(40, 60);
		label.setLocation(20, 0);
		return label;
	}
	
	private JLabel getTimeLabel()
	{
		JLabel label = new JLabel();
		label.setText("운영 시간");
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setSize(150, 60);
		label.setLocation(90, 0);
		return label;
	}
	
	private JLabel getEnableLabel()
	{
		JLabel label = new JLabel();
		label.setText("운영 유무");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setSize(120, 60);
		label.setLocation(520, 0);
		return label;
	}
	
	private JPanel getSeparatorBlack()
	{
		JPanel panel = new JPanel();
		panel.setSize(640, 3);
		panel.setLocation(0, 0);
		panel.setBackground(Color.BLACK);
		return panel;
	}

	private JPanel getSeparatorGray()
	{
		JPanel panel = new JPanel();
		panel.setSize(640, 1);
		panel.setLocation(0, 59);
		panel.setBackground(Color.GRAY);
		return panel;
	}
}
