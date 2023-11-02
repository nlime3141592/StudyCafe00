package deu.java002_02.study.server;

import deu.java002_02.study.main.IService;
import deu.java002_02.study.main.StudyThread;
import deu.java002_02.study.main.ThreadState;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.server.service.ReadFileService;
import deu.java002_02.study.server.service.ReadServerTimeService;
import deu.java002_02.study.server.service.ReadUserDataService;

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
		while(super.isRun() && !m_netModule.isClosed())
		{
			String header = m_netModule.readLine();

			if(header != null)
			{
				IService service = switchService(header);
				
				if(service != null)
					service.tryExecuteService();
			}
		}

		this.stop();
	}

	protected INetworkModule getNetworkModule()
	{
		return m_netModule;
	}

	private IService switchService(String _header)
	{
		switch(_header)
		{
		case "END_CONNECTION":
			return null;
		case "READ_FILE_SERVICE_TEST":
			ReadFileService rfs = new ReadFileService("C:/Programming/Java/StudyCafe/TestFiles/FileMonitorService.txt");
			((INetworkService)rfs).bindNetworkModule(this.getNetworkModule());
			return rfs;
		case "READ_USER_DATA_SERVICE":
			ReadUserDataService ruds = new ReadUserDataService();
			((INetworkService)ruds).bindNetworkModule(this.getNetworkModule());
			ServerMain.getDB().requestService(ruds);
			return ruds;
		case "READ_SERVER_TIME_SERVICE":
			ReadServerTimeService rsts = new ReadServerTimeService();
			((INetworkService)rsts).bindNetworkModule(this.getNetworkModule());
			return rsts;
		default:
			System.out.println("ProviderThread: Invalid header requested.");
			return null;
		}
	}
}
