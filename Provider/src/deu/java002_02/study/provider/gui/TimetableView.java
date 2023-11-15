package deu.java002_02.study.provider.gui;

import java.awt.Insets;

import javax.swing.JFrame;

import deu.java002_02.study.provider.main.ProviderMain;
import deu.java002_02.study.provider.service.TimetableSelectService;

public class TimetableView extends View
{
	private final static String[] DAY_STRINGS = { "월", "화", "수", "목", "금", "토", "일" };

	private int m_width;
	private int m_height;
	
	private TimetableHeader m_header;
	private TimetableTrailer m_trailer;
	private TimetableRow[] m_rows;

	public TimetableView(String _title)
	{
		super(_title);
		super.setLayout(null);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		m_width = 640;
		m_height = 480;
		
		m_header = new TimetableHeader(this);
		m_trailer = new TimetableTrailer(this);
		m_rows = new TimetableRow[7];
		
		super.add(m_header);
		super.add(m_trailer);

		for(int i = 0; i < 7; ++i)
		{
			m_rows[i] = new TimetableRow(DAY_STRINGS[i]);
			m_rows[i].setLocation(0, 60 + 50 * i);
			super.add(m_rows[i]);
		}

		TimetableSelectService service = new TimetableSelectService(this);
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

	public TimetableRow getTimetableRow(int _day)
	{
		return m_rows[_day];
	}
}
