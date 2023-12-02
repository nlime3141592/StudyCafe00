package deu.java002_02.study.server;

import deu.java002_02.study.main.IService;
import deu.java002_02.study.main.StudyThread;
import deu.java002_02.study.main.ThreadState;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.server.service.BlacklistAddService;
import deu.java002_02.study.server.service.BlacklistRemoveService;
import deu.java002_02.study.server.service.BlacklistSelectService;
import deu.java002_02.study.server.service.ReserveCancelService;
import deu.java002_02.study.server.service.SeatSelectProviderService;
import deu.java002_02.study.server.service.SeatTimerService;
import deu.java002_02.study.server.service.TimetableSelectService;
import deu.java002_02.study.server.service.TimetableUpdateService;

public final class ProviderThread extends StudyThread
{
	private INetworkModule m_netModule;
	
	// NOTE: 실시간 처리를 수행하는 서비스는 필드에 둡니다.
	private SeatTimerService m_seatTimerService;

	public ProviderThread(INetworkModule _netModule)
	{
		super();
		
		m_netModule = _netModule;
		
		m_seatTimerService = new SeatTimerService();
	}

	@Override
	public void start()
	{
		if(super.getThreadState() != ThreadState.READY)
			return;

		super.start();

		System.out.println("ProviderThread: new Provider entered.");
	}

	@Override
	public void stop()
	{
		if(super.getThreadState() != ThreadState.RUNNING)
			return;

		System.out.println("ProviderThread: a Provider exited.");
		super.stop();
	}

	@Override
	public void run()
	{
		try
		{
			while(super.isRun() && !m_netModule.isClosed())
			{
				String header = m_netModule.readLine();
	
				if(header != null)
				{
					IService service = switchService(header);

					if(service != null)
					{
						if(service instanceof INetworkService)
							((INetworkService)service).bindNetworkModule(m_netModule);
						if(service instanceof IConnectionService)
							ServerMain.getDB().requestService((IConnectionService)service);

						service.tryExecuteService();
					}
				}
			}
			
			this.stop();
		}
		catch(Exception _ex)
		{
			_ex.printStackTrace();
		}
		finally
		{
			this.stop();
		}
	}

	private IService switchService(String _header)
	{
		// System.out.println("Switching Header (Provider) : " + _header);

		switch(_header)
		{
		case "END_CONNECTION":
			this.stop();
			return null;

		// NOTE: 독서실 운영자 측 서버 서비스
		case "SEAT_SELECT_SERVICE":
			return new SeatSelectProviderService();
		case "RESERVE_CANCEL_SERVICE":
			return new ReserveCancelService();
		case "BLACKLIST_SELECT_SERVICE":
			return new BlacklistSelectService();
		case "BLACKLIST_ADD_SERVICE":
			return new BlacklistAddService();
		case "BLACKLIST_REMOVE_SERVICE":
			return new BlacklistRemoveService();
		case "TIMETABLE_SELECT_SERVICE":
			return new TimetableSelectService();
		case "TIMETABLE_UPDATE_SERVICE":
			return new TimetableUpdateService();
		case "SEAT_TIMER_SERVICE":
			return m_seatTimerService;

		default:
			System.out.println("ProviderThread: Invalid header requested.");
			System.out.println("  _header == " + _header);
			return null;
		}
	}
}
