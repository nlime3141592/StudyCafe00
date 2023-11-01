package deu.java002_02.study.tprovider.main;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;

import deu.java002_02.study.main.StudyThread;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;

public class TProviderThread extends StudyThread
{
	private final static int c_FIXED_SERVICE_TIME = 20; // NOTE: 16ms���� �ǽð� ó���� �����մϴ�.

	private INetworkModule m_netModule;
	private ConcurrentLinkedQueue<INetworkService> m_serviceEventQueue;
	private CopyOnWriteArraySet<INetworkService> m_updatingServices;

	public TProviderThread(INetworkModule _netModule)
	{
		super();

		m_serviceEventQueue = new ConcurrentLinkedQueue<INetworkService>();
		m_updatingServices = new CopyOnWriteArraySet<INetworkService>();
		m_netModule = _netModule;
	}
	
	public void registerService(INetworkService _service)
	{
		if(m_updatingServices.contains(_service))
			return;

		_service.bindNetworkModule(m_netModule);
		m_updatingServices.add(_service);
	}

	public void unregisterService(INetworkService _service)
	{
		if(!m_updatingServices.contains(_service))
			return;

		m_updatingServices.remove(_service);
		_service.bindNetworkModule(null);
	}

	public void enqueueService(INetworkService _service)
	{
		m_serviceEventQueue.offer(_service);
		// _service.onService(super.getNetworkModule());
	}

	@Override
	public void run()
	{
		long fixedServiceTime = 0;

		while(super.isRun())
		{
			while(m_serviceEventQueue.size() > 0)
				executeService(m_serviceEventQueue.poll());

			if(fixedServiceTime > 0)
				fixedServiceTime -= System.nanoTime();
			else
			{
				fixedServiceTime += (long)(1e+6 * TProviderThread.c_FIXED_SERVICE_TIME);

				// NOTE: �ǽð����� ó���� �����Դϴ�.
				for(INetworkService service : m_updatingServices)
					service.onService();
			}
		}
	}
	
	@Override
	public boolean start()
	{
		if(!super.start())
			return false;
		
		m_netModule.writeByte(2);
		return true;
	}

	private void executeService(INetworkService _service)
	{
		_service.bindNetworkModule(m_netModule);
		_service.onService();
	}
}
