package deu.java002_02.tprovider.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import deu.java002_02.tprovider.gui.event.ServiceEvent01;

public class TestGui extends JFrame
{
	private JButton button;
	private JLabel label;

	public TestGui()
	{
		super.setTitle("Sample GUI");
		super.setSize(640, 480);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setResizable(false);
		super.setLayout(null);
		
		button = new JButton("Load From Server");
		button.setLocation(5, 5);
		button.setSize(100, 40);
		button.addActionListener(new ServiceEvent01(this));
		
		label = new JLabel("test message");
		label.setLocation(5, 50);
		label.setSize(630, 425);
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		
		super.add(button);
		super.add(label);

		super.setVisible(true);
	}

	public JLabel getLabel()
	{
		return label;
	}
}
