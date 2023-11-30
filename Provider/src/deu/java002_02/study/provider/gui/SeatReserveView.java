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
		super(String.format("�¼� ��ȣ #%d", _seatNumber));
		super.setLayout(new BorderLayout());
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		ReserveCancel = new JButton("���� ���");
		ReserveCancel.setEnabled(false); // ó������ ��ư ��Ȱ��ȭ
		
		ReserveCancel.addActionListener(e -> {
		    int reservationNumber = m_table.getReservationNumber();

		    // TODO: ���õ� ���� ��ȣ�� ���� ��ȿ�� �˻縦 �����ϰ� ���� ó���� �߰� (��: ���� ��ȣ�� ��ȿ���� ���� ���)

		    ReserveCancelService service = new ReserveCancelService(this, reservationNumber);
		    ProviderMain.getProviderThread().registerEventService(service);

		    while (!service.isServiceEnded())
		        continue;

		    if (service.isExecutionSuccess())
		        JOptionPane.showMessageDialog(null, "������ ����߽��ϴ�.");
		    else
		        JOptionPane.showMessageDialog(null, "�� �� ���� ���� �߻�.");
		});
		super.getContentPane().add(ReserveCancel, BorderLayout.SOUTH);
		
		m_width = 640;
		m_height = 480;

		m_table = new SeatReserveTable();

		m_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // ���õ� ���� ����� ������ ȣ��Ǵ� �޼���
                updateButtonState();
            }
        });
		
		super.getContentPane().add(new JScrollPane(m_table), BorderLayout.CENTER);
	}
	
    private void updateButtonState() {
        // ���õ� ���� �ִ��� Ȯ��
        boolean rowSelected = m_table.getSelectedRow() != -1;
        // ��ư Ȱ��ȭ �Ǵ� ��Ȱ��ȭ
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