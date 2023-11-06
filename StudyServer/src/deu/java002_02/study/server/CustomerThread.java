package deu.java002_02.study.server;

import deu.java002_02.study.main.IService;
import deu.java002_02.study.main.StudyThread;
import deu.java002_02.study.main.ThreadState;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.server.service.JoinService;
import deu.java002_02.study.server.service.LoginService;

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
		case "JOIN_SERVICE":
			JoinService js = new JoinService();
			js.bindNetworkModule(this.getNetworkModule());
			ServerMain.getDB().requestService(js);
			return js;
		case "LOGIN_SERVICE":
			LoginService ls = new LoginService();
			ls.bindNetworkModule(this.getNetworkModule());
			ServerMain.getDB().requestService(ls);
			return ls;
		default:
			return null;
		}
	}
}
