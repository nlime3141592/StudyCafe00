package deu.java002_02.study.provider.main;

import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import deu.java002_02.study.provider.gui.SeatButton;

class TestFrame extends JFrame
{
	private static ImageIcon s_m_space = new ImageIcon("src/deu/java002_02/study/provider/resource/Space.png");

	public TestFrame()
	{
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		super.setLayout(null);
		
		int width = s_m_space.getIconWidth();
		int height = s_m_space.getIconHeight();

		SeatButton button01 = new SeatButton(1);
		button01.setLocation(160, 48);
		SeatButton button02 = new SeatButton(2);
		button02.setLocation(160, 80);
		SeatButton button03 = new SeatButton(3);
		button03.setLocation(160, 112);
		SeatButton button04 = new SeatButton(4);
		button04.setLocation(160, 144);
		SeatButton button05 = new SeatButton(5);
		button05.setLocation(160, 176);
		
		SeatButton button06 = new SeatButton(6);
		button06.setLocation(256, 48);
		SeatButton button07 = new SeatButton(7);
		button07.setLocation(256, 80);
		SeatButton button08 = new SeatButton(8);
		button08.setLocation(256, 112);
		SeatButton button09 = new SeatButton(9);
		button09.setLocation(256, 144);
		SeatButton button10 = new SeatButton(10);
		button10.setLocation(256, 176);
		
		SeatButton button11 = new SeatButton(11);
		button11.setLocation(288, 48);
		SeatButton button12 = new SeatButton(12);
		button12.setLocation(288, 80);
		SeatButton button13 = new SeatButton(13);
		button13.setLocation(288, 112);
		SeatButton button14 = new SeatButton(14);
		button14.setLocation(288, 144);
		SeatButton button15 = new SeatButton(15);
		button15.setLocation(288, 176);
		SeatButton button16 = new SeatButton(16);
		button16.setLocation(288, 272);
		SeatButton button17 = new SeatButton(17);
		button17.setLocation(288, 304);
		SeatButton button18 = new SeatButton(18);
		button18.setLocation(288, 336);
		SeatButton button19 = new SeatButton(19);
		button19.setLocation(288, 368);
		SeatButton button20 = new SeatButton(20);
		button20.setLocation(288, 400);
		
		SeatButton button21 = new SeatButton(21);
		button21.setLocation(384, 48);
		SeatButton button22 = new SeatButton(22);
		button22.setLocation(384, 80);
		SeatButton button23 = new SeatButton(23);
		button23.setLocation(384, 112);
		SeatButton button24 = new SeatButton(24);
		button24.setLocation(384, 144);
		SeatButton button25 = new SeatButton(25);
		button25.setLocation(384, 176);
		SeatButton button26 = new SeatButton(26);
		button26.setLocation(384, 272);
		SeatButton button27 = new SeatButton(27);
		button27.setLocation(384, 304);
		SeatButton button28 = new SeatButton(28);
		button28.setLocation(384, 336);
		SeatButton button29 = new SeatButton(29);
		button29.setLocation(384, 368);
		SeatButton button30 = new SeatButton(30);
		button30.setLocation(384, 400);
		
		SeatButton button31 = new SeatButton(31);
		button31.setLocation(416, 48);
		SeatButton button32 = new SeatButton(32);
		button32.setLocation(416, 80);
		SeatButton button33 = new SeatButton(33);
		button33.setLocation(416, 112);
		SeatButton button34 = new SeatButton(34);
		button34.setLocation(416, 144);
		SeatButton button35 = new SeatButton(35);
		button35.setLocation(416, 176);
		SeatButton button36 = new SeatButton(36);
		button36.setLocation(416, 272);
		SeatButton button37 = new SeatButton(37);
		button37.setLocation(416, 304);
		SeatButton button38 = new SeatButton(38);
		button38.setLocation(416, 336);
		SeatButton button39 = new SeatButton(39);
		button39.setLocation(416, 368);
		SeatButton button40 = new SeatButton(40);
		button40.setLocation(416, 400);
		
		SeatButton button41 = new SeatButton(41);
		button41.setLocation(512, 48);
		SeatButton button42 = new SeatButton(42);
		button42.setLocation(512, 80);
		SeatButton button43 = new SeatButton(43);
		button43.setLocation(512, 112);
		SeatButton button44 = new SeatButton(44);
		button44.setLocation(512, 144);
		SeatButton button45 = new SeatButton(45);
		button45.setLocation(512, 176);
		SeatButton button46 = new SeatButton(46);
		button46.setLocation(512, 272);
		SeatButton button47 = new SeatButton(47);
		button47.setLocation(512, 304);
		SeatButton button48 = new SeatButton(48);
		button48.setLocation(512, 336);
		SeatButton button49 = new SeatButton(49);
		button49.setLocation(512, 368);
		SeatButton button50 = new SeatButton(50);
		button50.setLocation(512, 400);

		SeatButton button51 = new SeatButton(51);
		button51.setLocation(544, 48);
		SeatButton button52 = new SeatButton(52);
		button52.setLocation(544, 80);
		SeatButton button53 = new SeatButton(53);
		button53.setLocation(544, 112);
		SeatButton button54 = new SeatButton(54);
		button54.setLocation(544, 144);
		SeatButton button55 = new SeatButton(55);
		button55.setLocation(544, 176);
		SeatButton button56 = new SeatButton(56);
		button56.setLocation(544, 272);
		SeatButton button57 = new SeatButton(57);
		button57.setLocation(544, 304);
		SeatButton button58 = new SeatButton(58);
		button58.setLocation(544, 336);
		SeatButton button59 = new SeatButton(59);
		button59.setLocation(544, 368);
		SeatButton button60 = new SeatButton(60);
		button60.setLocation(544, 400);

		super.getContentPane().add(button01);
		super.getContentPane().add(button02);
		super.getContentPane().add(button03);
		super.getContentPane().add(button04);
		super.getContentPane().add(button05);

		super.getContentPane().add(button06);
		super.getContentPane().add(button07);
		super.getContentPane().add(button08);
		super.getContentPane().add(button09);
		super.getContentPane().add(button10);

		super.getContentPane().add(button11);
		super.getContentPane().add(button12);
		super.getContentPane().add(button13);
		super.getContentPane().add(button14);
		super.getContentPane().add(button15);
		super.getContentPane().add(button16);
		super.getContentPane().add(button17);
		super.getContentPane().add(button18);
		super.getContentPane().add(button19);
		super.getContentPane().add(button20);

		super.getContentPane().add(button21);
		super.getContentPane().add(button22);
		super.getContentPane().add(button23);
		super.getContentPane().add(button24);
		super.getContentPane().add(button25);
		super.getContentPane().add(button26);
		super.getContentPane().add(button27);
		super.getContentPane().add(button28);
		super.getContentPane().add(button29);
		super.getContentPane().add(button30);

		super.getContentPane().add(button31);
		super.getContentPane().add(button32);
		super.getContentPane().add(button33);
		super.getContentPane().add(button34);
		super.getContentPane().add(button35);
		super.getContentPane().add(button36);
		super.getContentPane().add(button37);
		super.getContentPane().add(button38);
		super.getContentPane().add(button39);
		super.getContentPane().add(button40);
		
		super.getContentPane().add(button41);
		super.getContentPane().add(button42);
		super.getContentPane().add(button43);
		super.getContentPane().add(button44);
		super.getContentPane().add(button45);
		super.getContentPane().add(button46);
		super.getContentPane().add(button47);
		super.getContentPane().add(button48);
		super.getContentPane().add(button49);
		super.getContentPane().add(button50);
		
		super.getContentPane().add(button51);
		super.getContentPane().add(button52);
		super.getContentPane().add(button53);
		super.getContentPane().add(button54);
		super.getContentPane().add(button55);
		super.getContentPane().add(button56);
		super.getContentPane().add(button57);
		super.getContentPane().add(button58);
		super.getContentPane().add(button59);
		super.getContentPane().add(button60);

		JLabel label = new JLabel();
		label.setIcon(s_m_space);
		label.setSize(width, height);
		super.getContentPane().add(label);

		super.setVisible(true);
		
		Insets inset = super.getInsets();
		super.setSize(width + inset.left + inset.right, height + inset.bottom + inset.top);
	}
}

public class ProviderMain
{
	public static void main(String[] args)
	{
		new TestFrame();
	}
}
