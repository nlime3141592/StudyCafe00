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
import deu.java002_02.study.server.service.ReadFileService;
import deu.java002_02.study.server.service.ReadServerTimeService;
import deu.java002_02.study.server.service.ReadUserDataService;
import deu.java002_02.study.server.service.ReserveCancelService;
import deu.java002_02.study.server.service.SeatSelectProviderService;
import deu.java002_02.study.server.service.SeatTimerService;
import deu.java002_02.study.server.service.TimetableSelectService;
import deu.java002_02.study.server.service.TimetableUpdateService;

public final class ProviderThread extends StudyThread
{
	private INetworkModule m_netModule;

	public ProviderThread(INetworkModule _netModule)
	{
		super();
		
		m_netModule = _netModule;
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

						if(service instanceof IConnectionService)
							((IConnectionService)service).bindConnectionModule(null);
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
		switch(_header)
		{
		case "END_CONNECTION":
			this.stop();
			return null;

		// NOTE: 테스트용 서비스
		case "READ_FILE_SERVICE_TEST":
			return new ReadFileService("C:/Programming/Java/StudyCafe/TestFiles/FileMonitorService.txt");
		case "READ_USER_DATA_SERVICE":
			return new ReadUserDataService();
		case "READ_SERVER_TIME_SERVICE":
			return new ReadServerTimeService();

		// NOTE: 독서실 운영자 측 서버 서비스
		case "SEAT_TIMER_SERVICE":
			return new SeatTimerService();
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

		default:
			System.out.println("ProviderThread: Invalid header requested.");
			System.out.println("  _header == " + _header);
			return null;
		}
	}
}
