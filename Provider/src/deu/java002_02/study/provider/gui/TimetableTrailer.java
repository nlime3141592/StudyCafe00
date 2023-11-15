package deu.java002_02.study.provider.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import deu.java002_02.study.provider.main.ProviderMain;
import deu.java002_02.study.provider.service.TimetableUpdateService;

public class TimetableTrailer extends JPanel
{
	private TimetableView m_view;
	private JButton m_saveButton;
	
	public TimetableTrailer(TimetableView _view)
	{
		super.setLayout(null);
		super.setSize(640, 70);
		super.setLocation(0, 410);
		super.setBackground(Color.LIGHT_GRAY);

		m_view = _view;

		m_saveButton = new JButton();
		m_saveButton.setText("����");
		m_saveButton.setSize(60, 30);
		m_saveButton.setLocation(550, 20);
		m_saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ProviderMain.getProviderThread().registerEventService(new TimetableUpdateService(m_view));
				JOptionPane.showMessageDialog(null, "������ �����߽��ϴ�.");
			}
		});

		super.add(m_saveButton);
	}
}