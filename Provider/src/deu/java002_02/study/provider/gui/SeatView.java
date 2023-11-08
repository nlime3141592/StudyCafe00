package deu.java002_02.study.provider.gui;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import deu.java002_02.study.main.IService;
import deu.java002_02.study.provider.main.ProviderMain;

public class SeatView extends View
{
	private int m_width;
	private int m_height;
	private SeatButton[] m_seatButtons;
	private JMenuBar m_menuBar;
	private JLabel m_spaceLabel;

	// NOTE: �ǽð����� ó���� ����
	private IService m_realTimeServiceReserved01;
	private IService m_realTimeServiceReserved02;

	public SeatView(String _title)
	{
		super(_title);
		super.setLayout(null);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// NOTE: �޴� �ٸ� �����մϴ�.
		m_menuBar = new JMenuBar();
		m_menuBar.add(new JMenu("�"));
		m_menuBar.add(new JMenu("����"));
		m_menuBar.getMenu(0).add(new JMenuItem("� �ð� ����"));
		m_menuBar.getMenu(1).add(new JMenuItem("������Ʈ"));
		m_menuBar.getMenu(0).getItem(0).addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO: � �ð� ���� �������� ���� �ڵ带 �ۼ��մϴ�.
				TimetableView view = new TimetableView("������ � �ð� ����");
				view.showView();
			}
		});
		m_menuBar.getMenu(1).getItem(0).addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO: ������Ʈ �������� ���� �ڵ带 �ۼ��մϴ�.
				BlacklistView view = new BlacklistView("������Ʈ �߰�/����");
				view.showView();
			}
		});
		super.setJMenuBar(m_menuBar);

		// NOTE: �¼� ��ư�� �����մϴ�.
		m_seatButtons = new SeatButton[35];
		m_seatButtons[0] = createSeatButton(1, 160, 48);
		m_seatButtons[1] = createSeatButton(2, 160, 80);
		m_seatButtons[2] = createSeatButton(3, 160, 112);
		m_seatButtons[3] = createSeatButton(4, 160, 144);
		m_seatButtons[4] = createSeatButton(5, 160, 176);
		m_seatButtons[5] = createSeatButton(6, 256, 48);
		m_seatButtons[6] = createSeatButton(7, 256, 80);
		m_seatButtons[7] = createSeatButton(8, 256, 112);
		m_seatButtons[8] = createSeatButton(9, 256, 144);
		m_seatButtons[9] = createSeatButton(10, 256, 176);
		m_seatButtons[10] = createSeatButton(11, 288, 48);
		m_seatButtons[11] = createSeatButton(12, 288, 80);
		m_seatButtons[12] = createSeatButton(13, 288, 112);
		m_seatButtons[13] = createSeatButton(14, 288, 144);
		m_seatButtons[14] = createSeatButton(15, 288, 176);
		m_seatButtons[15] = createSeatButton(16, 384, 48);
		m_seatButtons[16] = createSeatButton(17, 384, 80);
		m_seatButtons[17] = createSeatButton(18, 384, 112);
		m_seatButtons[18] = createSeatButton(19, 384, 144);
		m_seatButtons[19] = createSeatButton(20, 384, 176);
		m_seatButtons[20] = createSeatButton(21, 416, 48);
		m_seatButtons[21] = createSeatButton(22, 416, 80);
		m_seatButtons[22] = createSeatButton(23, 416, 112);
		m_seatButtons[23] = createSeatButton(24, 416, 144);
		m_seatButtons[24] = createSeatButton(25, 416, 176);
		m_seatButtons[25] = createSeatButton(26, 512, 48);
		m_seatButtons[26] = createSeatButton(27, 512, 80);
		m_seatButtons[27] = createSeatButton(28, 512, 112);
		m_seatButtons[28] = createSeatButton(29, 512, 144);
		m_seatButtons[29] = createSeatButton(30, 512, 176);
		m_seatButtons[30] = createSeatButton(31, 544, 48);
		m_seatButtons[31] = createSeatButton(32, 544, 80);
		m_seatButtons[32] = createSeatButton(33, 544, 112);
		m_seatButtons[33] = createSeatButton(34, 544, 144);
		m_seatButtons[34] = createSeatButton(35, 544, 176);

		for(int i = 0; i < m_seatButtons.length; ++i)
		{
			if(m_seatButtons[i] == null)
				continue;

			super.getContentPane().add(m_seatButtons[i].getSummaryLabel());
			super.getContentPane().add(m_seatButtons[i]);
		}

		// NOTE: ���� ������ �̹����� �����ɴϴ�.
		ImageIcon spaceImage = new ImageIcon("src/deu/java002_02/study/provider/resource/Space640x352.png");
		m_width = spaceImage.getIconWidth();
		m_height = spaceImage.getIconHeight();

		// NOTE: ���� �������� �׸��ϴ�.
		m_spaceLabel = new JLabel();
		m_spaceLabel.setIcon(spaceImage);
		m_spaceLabel.setSize(m_width, m_height);
		super.getContentPane().add(m_spaceLabel);

		// NOTE: �ǽð� ���� ��ü�� ����ϴ�.
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
		int menuHeight = m_menuBar.getHeight();
		super.setSize(m_width + l + r, m_height + b + t + menuHeight);

		// NOTE: �ǽð� ���� ��ü�� Thread�� �˷��ݴϴ�.
		ProviderMain.getProviderThread().registerRealTimeService(m_realTimeServiceReserved01);
		ProviderMain.getProviderThread().registerRealTimeService(m_realTimeServiceReserved02);
	}

	@Override
	public void hideView()
	{
		// NOTE: �ǽð� ���񽺸� �ߴ��մϴ�.
		ProviderMain.getProviderThread().unregisterRealTimeService(m_realTimeServiceReserved01);
		ProviderMain.getProviderThread().unregisterRealTimeService(m_realTimeServiceReserved02);

		super.setVisible(false);
		super.setSize(0, 0);
	}

	private SeatButton createSeatButton(int _seatId, int _posX, int _posY)
	{
		SeatButton button = new SeatButton(_seatId);
		button.setLocation(_posX, _posY);
		button.getSummaryLabel().setLocation(_posX, _posY);
		return button;
	}
}
