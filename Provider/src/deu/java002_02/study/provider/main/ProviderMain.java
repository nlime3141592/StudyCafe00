package deu.java002_02.study.provider.main;

import java.net.Socket;

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

			new SeatView("예약 현황판").showView();
		}
		catch(Exception _e)
		{
			
		}
	}

	public static ProviderThread getProviderThread()
	{
		return s_m_pThread;
	}
}
