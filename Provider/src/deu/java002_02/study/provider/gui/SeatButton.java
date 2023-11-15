package deu.java002_02.study.provider.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import deu.java002_02.study.provider.main.ProviderMain;
import deu.java002_02.study.provider.service.SeatSelectService;

public class SeatButton extends JButton implements ActionListener
{
	private static int s_m_refCount = 0;

	private static ImageIcon s_m_img0;
	private static ImageIcon s_m_img1;
	private static ImageIcon s_m_img2;
	private static ImageIcon s_m_img3;

	private static int s_m_width;
	private static int s_m_height;

	private JLabel m_lbSummary;
	private int m_seatNumber = -1;

	public SeatButton(int _seatNumber)
	{
		if(s_m_refCount == 0)
		{
			s_m_img0 = new ImageIcon("../resources/Seat0.png");
			s_m_img1 = new ImageIcon("../resources/Seat1.png");
			s_m_img2 = new ImageIcon("../resources/Seat2.png");
			s_m_img3 = new ImageIcon("../resources/Seat3.png");

			s_m_width = s_m_img0.getIconWidth();
			s_m_height = s_m_img0.getIconHeight();
		}

		++s_m_refCount;

		super.setIcon(s_m_img0);
		super.setDisabledIcon(s_m_img1);
		super.setPressedIcon(s_m_img2);
		super.setRolloverIcon(s_m_img3);
		super.setSize(s_m_width, s_m_height);

		super.setOpaque(false);
		super.setContentAreaFilled(false);
		super.setBorderPainted(false);

		m_lbSummary = new JLabel(String.format("#%d", _seatNumber));
		m_lbSummary.setSize(super.getWidth(), super.getHeight());
		m_lbSummary.setHorizontalAlignment(JLabel.CENTER);
		m_lbSummary.setVerticalAlignment(JLabel.CENTER);
		m_lbSummary.setForeground(Color.BLACK);

		m_seatNumber = _seatNumber;

		super.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		SeatReserveView view = new SeatReserveView(String.format("ÁÂ¼® ¹øÈ£ #%d", m_seatNumber));
		SeatSelectService service = new SeatSelectService(view, m_seatNumber);
		ProviderMain.getProviderThread().registerEventService(service);
		view.showView();
	}

	@Override
	protected void finalize()
	{
		--s_m_refCount;
		
		if(s_m_refCount == 0)
		{
			s_m_img0 = null;
			s_m_img1 = null;
			s_m_img2 = null;
			s_m_img3 = null;
		}
	}

	public JLabel getSummaryLabel()
	{
		return m_lbSummary;
	}
}
