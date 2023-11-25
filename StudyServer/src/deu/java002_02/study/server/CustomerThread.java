package deu.java002_02.study.server;

import deu.java002_02.study.main.IService;
import deu.java002_02.study.main.StudyThread;
import deu.java002_02.study.main.ThreadState;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.server.service.*;

public class CustomerThread extends StudyThread
{
	private INetworkModule m_netModule;

	public CustomerThread(INetworkModule _netModule)
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

		System.out.println("CustomerThread: new Customer entered.");
	}

	@Override
	public void stop()
	{
		if(super.getThreadState() != ThreadState.RUNNING)
			return;

		System.out.println("CustomerThread: a Customer exited.");
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

		// NOTE: 독서실 이용자 측 서버 서비스
		case "JOIN_SERVICE":
			return new JoinService();
		case "LOGIN_SERVICE":
			return new LoginService();
		case "SEAT_TIMER_SERVICE":
			return new SeatTimerService();
		case "RESERVE_SERVICE":
			return new ReserveService();
		case "RESERVE_CANCEL_SERVICE":
			return new ReserveCancelService();
		case "RESERVE_SELECT_SERVICE":
			return new ReserveSelectService();
		case "TIMETABLE_SELECT_SERVICE":
			return new TimetableSelectCustomerService();
		case "RESERVABLE_WEEKDAY_SELECT_SERVICE":
			return new ReservableWeekdaySelectService();
		case "SEAT_SELECT_SERVICE":
			return new SeatSelectCustomerService();
		case "RESERVED_HOUR_SELECT_SERVICE":
			return new ReservedHourSelectService();

		default:
			System.out.println("CustomerThread: Invalid header requested.");
			System.out.println("  _header == " + _header);
			return null;
		}
	}
}
