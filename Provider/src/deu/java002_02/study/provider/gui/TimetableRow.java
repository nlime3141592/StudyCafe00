package deu.java002_02.study.provider.gui;

import java.awt.Color;
import java.time.LocalTime;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimetableRow extends JPanel
{
	private final static Integer[] HOURS = {
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
		20, 21, 22, 23
	};

	private final static Integer[] MINUTES = {
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
		10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
		20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
		30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
		40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
		50, 51, 52, 53, 54, 55, 56, 57, 58, 59
	};

	private JLabel m_lbDay;
	private JLabel m_lbWave;
	private JPanel m_lbSeparator;
	private JComboBox<Integer> m_begHH;
	private JComboBox<Integer> m_begMM;
	private JComboBox<Integer> m_endHH;
	private JComboBox<Integer> m_endMM;
	private JCheckBox m_checkBox;

	public TimetableRow(String _day)
	{
		super.setLayout(null);
		super.setSize(640, 50);

		m_lbDay = new JLabel(_day);
		m_lbDay.setSize(40, 50);
		m_lbDay.setLocation(20, 0);

		m_lbWave = new JLabel("~");
		m_lbWave.setSize(20, 50);

		m_lbSeparator = new JPanel();
		m_lbSeparator.setSize(640, 1);
		m_lbSeparator.setLocation(0, 49);
		m_lbSeparator.setBackground(Color.LIGHT_GRAY);

		m_checkBox = new JCheckBox();
		m_checkBox.setSize(20, 20);
		m_checkBox.setLocation(570, 15);

		m_begHH = getTimeBox(HOURS);
		m_begMM = getTimeBox(MINUTES);
		m_endHH = getTimeBox(HOURS);
		m_endMM = getTimeBox(MINUTES);

		m_lbWave.setLocation(210, 0);
		
		m_begHH.setLocation(90, 15);
		m_begMM.setLocation(150, 15);
		m_endHH.setLocation(230, 15);
		m_endMM.setLocation(290, 15);
		
		super.add(m_lbDay);
		super.add(m_lbWave);
		super.add(m_lbSeparator);
		super.add(m_begHH);
		super.add(m_begMM);
		super.add(m_endHH);
		super.add(m_endMM);
		super.add(m_checkBox);
	}

	public void setRun(boolean _isRun)
	{
		m_checkBox.setSelected(_isRun);
	}

	public boolean isRun()
	{
		return m_checkBox.isSelected();
	}

	public void setOpentime(int _hh, int _mm)
	{
		m_begHH.setSelectedIndex(_hh);
		m_begMM.setSelectedIndex(_mm);
	}

	public void setClosetime(int _hh, int _mm)
	{
		m_endHH.setSelectedIndex(_hh);
		m_endMM.setSelectedIndex(_mm);
	}
	
	public void setOpentimeHour(int _hh)
	{
		m_begHH.setSelectedIndex(_hh);
	}
	
	public void setOpentimeMinute(int _mm)
	{
		m_begMM.setSelectedIndex(_mm);
	}
	
	public void setClosetimeHour(int _hh)
	{
		m_endHH.setSelectedIndex(_hh);
	}
	
	public void setClosetimeMinute(int _mm)
	{
		m_endMM.setSelectedIndex(_mm);
	}
	
	public int getOpentimeHour()
	{
		return Integer.parseInt((String)m_begHH.getSelectedItem());
	}
	
	public int getOpentimeMinute()
	{
		return Integer.parseInt((String)m_begMM.getSelectedItem());
	}
	
	public int getClosetimeHour()
	{
		return Integer.parseInt((String)m_endHH.getSelectedItem());
	}
	
	public int getClosetimeMinute()
	{
		return Integer.parseInt((String)m_endMM.getSelectedItem());
	}
	
	public String getOpentimeString()
	{
		return String.format("%s:%s:00", m_begHH.getSelectedItem(), m_begMM.getSelectedItem());
	}
	
	public String getClosetimeString()
	{
		return String.format("%s:%s:00", m_endHH.getSelectedItem(), m_endHH.getSelectedItem());
	}

	private JComboBox<Integer> getTimeBox(Integer[] _elements)
	{
		JComboBox<Integer> box = new JComboBox<Integer>(_elements);
		
		box.setSize(55, 20);
		box.setEditable(false);
		box.setSelectedIndex(0);
		
		return box;
	}
}