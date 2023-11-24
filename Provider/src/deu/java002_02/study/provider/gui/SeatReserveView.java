package deu.java002_02.study.provider.gui;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import deu.java002_02.study.provider.main.ProviderMain;
import deu.java002_02.study.provider.service.ReserveCancelService;

public class SeatReserveView extends View
{
	private int m_width;
	private int m_height;
	
	private SeatReserveTable m_table;

	public SeatReserveView(int _seatNumber)
	{
		super(String.format("좌석 번호 #%d", _seatNumber));
		super.setLayout(new BorderLayout());
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		m_width = 640;
		m_height = 480;

		m_table = new SeatReserveTable();

		super.getContentPane().add(new JScrollPane(m_table), BorderLayout.CENTER);

		// TODO: 실제 예약 취소 기능을 수행하려면 이 코드를 해당 위치로 옮긴다.
		ReserveCancelService service = new ReserveCancelService(this, 2);
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

	public void addRow(int _reserveId, int _uuid, String _nickname, String _beginTime, String _endTime)
	{
		m_table.addRow(_reserveId, _uuid, _nickname, _beginTime, _endTime);
	}

	public void removeRow(int _reserveId)
	{
		m_table.removeRow(String.valueOf(_reserveId));
	}
}
