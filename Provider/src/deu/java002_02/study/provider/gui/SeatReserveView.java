package deu.java002_02.study.provider.gui;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import deu.java002_02.study.provider.main.ProviderMain;
import deu.java002_02.study.provider.service.ReserveCancelService;

public class SeatReserveView extends View
{
	private int m_width;
	private int m_height;
	
	private SeatReserveTable m_table;
	private JButton ReserveCancel;
	
	public SeatReserveView(int _seatNumber)
	{
		super(String.format("좌석 번호 #%d", _seatNumber));
		super.setLayout(new BorderLayout());
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		ReserveCancel = new JButton("예약 취소");
		ReserveCancel.setEnabled(false); // 처음에는 버튼 비활성화
		
		ReserveCancel.addActionListener(e -> {
		    int reservationNumber = m_table.getReservationNumber();

		    // TODO: 선택된 예약 번호에 대한 유효성 검사를 수행하고 예외 처리를 추가 (예: 예약 번호가 유효하지 않은 경우)

		    ReserveCancelService service = new ReserveCancelService(this, reservationNumber);
		    ProviderMain.getProviderThread().registerEventService(service);

		    while (!service.isServiceEnded())
		        continue;

		    if (service.isExecutionSuccess())
		        JOptionPane.showMessageDialog(null, "예약을 취소했습니다.");
		    else
		        JOptionPane.showMessageDialog(null, "알 수 없는 오류 발생.");
		});
		super.getContentPane().add(ReserveCancel, BorderLayout.SOUTH);
		
		m_width = 640;
		m_height = 480;

		m_table = new SeatReserveTable();

		m_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 선택된 행이 변경될 때마다 호출되는 메서드
                updateButtonState();
            }
        });
		
		super.getContentPane().add(new JScrollPane(m_table), BorderLayout.CENTER);
	}
	
    private void updateButtonState() {
        // 선택된 행이 있는지 확인
        boolean rowSelected = m_table.getSelectedRow() != -1;
        // 버튼 활성화 또는 비활성화
        ReserveCancel.setEnabled(rowSelected);
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