package deu.java002_02.study.tprovider.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import deu.java002_02.study.tprovider.gui.event.ServiceEvent01;
import deu.java002_02.study.tprovider.gui.event.ServiceEvent02;
import deu.java002_02.study.tprovider.main.TProviderMain;
import deu.java002_02.study.tprovider.service.TService03;

public class TestGui extends JFrame
{
	private JButton button1;
	private JButton button2;
	private JLabel label1;
	private JLabel label2;

	public TestGui()
	{
		super.setTitle("Sample GUI");
		// super.setSize(640, 480);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setResizable(false);
		super.setLayout(null);
		
		button1 = new JButton("Get File");
		button1.setLocation(5, 5);
		button1.setSize(100, 40);
		button1.addActionListener(new ServiceEvent01(this));
		
		button2 = new JButton("Get Info");
		button2.setLocation(110, 5);
		button2.setSize(100, 40);
		button2.addActionListener(new ServiceEvent02(this));

		label1 = new JLabel("test message");
		label1.setLocation(5, 105);
		label1.setSize(630, 370);
		label1.setOpaque(true);
		label1.setBackground(Color.WHITE);
		label1.setFont(new Font("Arial", Font.PLAIN, 20));
		
		label2 = new JLabel("LocalDateTime");
		label2.setLocation(5, 50);
		label2.setSize(630, 50);
		label2.setOpaque(true);
		label2.setBackground(Color.WHITE);
		label2.setFont(new Font("Arial", Font.PLAIN, 20));
		
		super.add(button1);
		super.add(button2);
		super.add(label1);
		super.add(label2);
		
		TProviderMain.thread.registerService(new TService03(this));

		super.setVisible(true);
		
		Insets inset = super.getInsets();
		super.setSize(640 + inset.left + inset.right, 480 + inset.bottom + inset.top);
	}

	public JLabel getLabel1()
	{
		return label1;
	}
	
	public JLabel getLabel2()
	{
		return label2;
	}
}
