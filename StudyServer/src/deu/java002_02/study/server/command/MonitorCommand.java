package deu.java002_02.study.server.command;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import deu.java002_02.study.main.StudyThread;
import deu.java002_02.study.server.ServerMain;

class MonitorFrame extends JFrame
{
	private JLabel m_clientCountLabel;

	public MonitorFrame()
	{
		super.setTitle("Server Monitor");
		super.setLayout(null);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		m_clientCountLabel = new JLabel();
		m_clientCountLabel.setSize(390, 20);
		m_clientCountLabel.setLocation(5, 5);
		m_clientCountLabel.setOpaque(true);
		m_clientCountLabel.setHorizontalAlignment(JLabel.RIGHT);
		m_clientCountLabel.setBackground(Color.WHITE);

		this.setClientCount(0);

		super.getContentPane().add(m_clientCountLabel);

		super.setVisible(true);

		Insets inset = super.getInsets();
		super.setSize(400 + inset.left + inset.right, 300 + inset.bottom + inset.top);
	}

	public void setClientCount(int _count)
	{
		m_clientCountLabel.setText(String.format("Client Count : %03d ", _count));
	}
}

class Monitor extends StudyThread
{
	private MonitorFrame m_frame;

	public Monitor(MonitorFrame _frame)
	{
		m_frame = _frame;
	}

	@Override
	public void run()
	{
		long fixedUpdateTime = 0;

		while(super.isRun() && m_frame.isDisplayable())
		{
			long beginTime = System.nanoTime();
			
			if(fixedUpdateTime > 0)
				fixedUpdateTime -= (System.nanoTime() - beginTime);
			else
			{
				fixedUpdateTime += (long)(1e+6 * 20);

				// TODO: 실시간 로직 처리를 이 곳에서 수행할 수 있습니다.
				m_frame.setClientCount(ServerMain.getServer().getClientCount());
			}
		}

		System.out.println("Monitor: 종료됨.");
		this.stop();
	}
}

public class MonitorCommand
{
	public MonitorCommand()
	{
		new Monitor(new MonitorFrame()).start();
	}
}