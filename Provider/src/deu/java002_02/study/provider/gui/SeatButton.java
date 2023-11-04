package deu.java002_02.study.provider.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SeatButton extends JButton implements ActionListener
{
	private static int s_m_refCount = 0;

	private static ImageIcon s_m_img0;
	private static ImageIcon s_m_img1;
	private static ImageIcon s_m_img2;
	private static ImageIcon s_m_img3;

	private static int s_m_width;
	private static int s_m_height;

	private int m_seatNumber = -1;

	public SeatButton(int _seatNumber)
	{
		if(s_m_refCount == 0)
		{
			s_m_img0 = new ImageIcon("src/deu/java002_02/study/provider/resource/Seat0.png");
			s_m_img1 = new ImageIcon("src/deu/java002_02/study/provider/resource/Seat1.png");
			s_m_img2 = new ImageIcon("src/deu/java002_02/study/provider/resource/Seat2.png");
			s_m_img3 = new ImageIcon("src/deu/java002_02/study/provider/resource/Seat3.png");

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

		m_seatNumber = _seatNumber;
		
		super.addActionListener(this);
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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("seat #" + m_seatNumber + " clicked.");
		
		if(Math.random() < 0.1)
		{
			super.setEnabled(false);
			System.out.println("    Icon disabled...");
		}
	}
}
