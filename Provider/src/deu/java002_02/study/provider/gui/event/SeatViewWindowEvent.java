package deu.java002_02.study.provider.gui.event;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import deu.java002_02.study.provider.gui.SeatView;
import deu.java002_02.study.provider.main.ProviderMain;
import deu.java002_02.study.provider.service.SeatTimerService;

public class SeatViewWindowEvent extends WindowAdapter
{
	private SeatView m_view;
	private SeatTimerService m_seatTimerService;

	public SeatViewWindowEvent(SeatView _view)
	{
		m_view = _view;
		m_seatTimerService = new SeatTimerService(_view);
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		ProviderMain.getProviderThread().registerRealTimeService(m_seatTimerService);
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		ProviderMain.getProviderThread().unregisterRealTimeService(m_seatTimerService);
	}
}
