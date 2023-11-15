package deu.java002_02.study.provider.main;

import deu.java002_02.study.provider.gui.SeatView;

public class ProviderMain
{
	private static ProviderThread s_m_pThread;

	public static void main(String[] args)
	{
		s_m_pThread = new ProviderThread(null);
		s_m_pThread.start();

		new SeatView("예약 현황판").showView();
	}

	public static ProviderThread getProviderThread()
	{
		return s_m_pThread;
	}
}
