package deu.java002_02.study.server;

import deu.java002_02.study.main.StudyThread;
import deu.java002_02.study.ni.INetworkModule;

public class CustomerThread extends StudyThread
{
	private INetworkModule m_netModule;

	public CustomerThread(INetworkModule _netModule)
	{
		super();
		
		m_netModule = _netModule; 
	}

	@Override
	public void run()
	{
		System.out.println("CustomerThread: new Customer entered.");

		while(super.isRun())
		{
			String header = this.getNetworkModule().readLine();

			if(header == null)
			{
				super.stop();
				return;
			}

			switch(header)
			{
			default:
				System.out.println("CustomerThread: Invalid header requested.");
				break;
			}
		}
		
		System.out.println("CustomerThread: a Customer exited.");
	}

	protected INetworkModule getNetworkModule()
	{
		return m_netModule;
	}
}
