package deu.java002_02.study.provider.main;

import java.net.Socket;

import javax.swing.JOptionPane;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.NetworkModule;
import deu.java002_02.study.provider.gui.SeatView;

public class ProviderMain
{
	private static ProviderThread s_m_pThread;

	public static void main(String[] args)
	{
		try
		{
			Socket socket = new Socket("localhost", 25565);
			INetworkModule netModule = new NetworkModule(socket);
			netModule.writeByte(2);
			
			s_m_pThread = new ProviderThread(netModule);
			s_m_pThread.start();

			SeatView view = new SeatView("예약 현황판");
			view.showView();
		}
		catch(Exception _e)
		{
			JOptionPane.showMessageDialog(null, "서버에 연결할 수 없습니다.");
		}
	}

	public static ProviderThread getProviderThread()
	{
		return s_m_pThread;
	}
}
