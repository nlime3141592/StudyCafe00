package deu.java002_02.study.provider.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import deu.java002_02.study.provider.main.ProviderMain;
import deu.java002_02.study.provider.service.BlacklistSelectService;

public class BlacklistView extends View
{
	private int m_width;
	private int m_height;

	private BlacklistTable m_table;
	private JButton m_deleteButton;
	private JButton m_addButton;
	private JPanel m_buttonPanel;
	
	private BlacklistAddView m_addView;
	private BlacklistRemoveView m_removeView;

	public BlacklistView(String _title)
	{
		super(_title);
		super.setLayout(new BorderLayout());
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		m_width = 640;
		m_height = 480;

		m_table = new BlacklistTable();
		m_deleteButton = new JButton("삭제");
		m_deleteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO: 삭제 버튼을 눌렀을 때 보여줄 GUI를 생성합니다.
				m_removeView.show();
			}
		});
		m_addButton = new JButton("추가");
		m_addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO: 추가 버튼을 눌렀을 때 보여줄 GUI를 생성합니다.
				m_addView.show();
			}
		});
		m_buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		m_buttonPanel.add(m_deleteButton);
		m_buttonPanel.add(m_addButton);
		m_addView = new BlacklistAddView(this);
		m_removeView = new BlacklistRemoveView(this);

		super.getContentPane().add(new JScrollPane(m_table), BorderLayout.CENTER);
		super.getContentPane().add(m_buttonPanel, BorderLayout.SOUTH);
		
		BlacklistSelectService service = new BlacklistSelectService(this);
		ProviderMain.getProviderThread().registerEventService(service);
	}

	@Override
	public void showView()
	{
		super.setVisible(true);

		Insets inset = super.getInsets();
		int l = inset.left;
		int t = inset.top;
		int r = inset.right;
		int b = inset.bottom;
		super.setSize(m_width + l + r, m_height + b + t);
	}

	@Override
	public void hideView()
	{
		super.setVisible(false);
		super.setSize(0, 0);
	}

	public boolean addRow(int _uuid, String _name, String _time)
	{
		return m_table.addRow(_uuid, _name, _time);
	}

	public boolean removeRow(int _uuid)
	{
		return m_table.removeRow(_uuid);
	}
}
