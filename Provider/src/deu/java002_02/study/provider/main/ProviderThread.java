package deu.java002_02.study.provider.main;

import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;

import deu.java002_02.study.main.IService;
import deu.java002_02.study.main.StudyThread;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;

public class ProviderThread extends StudyThread
{
	private final static int c_FIXED_SERVICE_TIME = 20; // NOTE: 20ms마다 실시간 서비스 처리를 수행합니다.

	private INetworkModule m_netModule;
	private ConcurrentLinkedQueue<IService> m_eventServices;
	private CopyOnWriteArraySet<IService> m_realTimeServices;

	public ProviderThread(INetworkModule _netModule)
	{
		super();

		m_netModule = _netModule;
		m_eventServices = new ConcurrentLinkedQueue<IService>();
		m_realTimeServices = new CopyOnWriteArraySet<IService>();
	}

	@Override
	public void run()
	{
		long servicingTime = System.nanoTime();

		while(super.isRun())
		{
			while(m_eventServices.size() > 0)
			{
				IService service = m_eventServices.poll();
				service.tryExecuteService();
			}

			long currentNanoTime = System.nanoTime();

			if(currentNanoTime >= servicingTime)
			{
				while(currentNanoTime >= servicingTime)
					servicingTime += (long)(1e+6 * ProviderThread.c_FIXED_SERVICE_TIME);

				for(IService service : m_realTimeServices)
				{
					if(service != null)
						service.tryExecuteService();
				}
			}
		}
	}

	public void registerEventService(IService _service)
	{
		if(_service == null)
			return;

		if(_service instanceof INetworkService)
			((INetworkService)_service).bindNetworkModule(m_netModule);

		m_eventServices.offer(_service);
	}

	public void registerRealTimeService(IService _service)
	{
		// NOTE: 실시간 서비스 객체의 유효성을 보장하기 위해 이벤트 서비스로 넘겨줘서 실시간 서비스를 추가함.
		this.registerEventService(new IService()
		{
			@Override
			public boolean tryExecuteService()
			{
				if(_service == null || m_realTimeServices.contains(_service))
					return false;
				
				if(_service instanceof INetworkService)
					((INetworkService)_service).bindNetworkModule(m_netModule);
				
				m_realTimeServices.add(_service);
				return true;
			}
		});
	}

	public void unregisterRealTimeService(IService _service)
	{
		// NOTE: 실시간 서비스 객체의 유효성을 보장하기 위해 이벤트 서비스로 넘겨줘서 실시간 서비스를 삭제함.
		this.registerEventService(new IService()
		{
			@Override
			public boolean tryExecuteService()
			{
				if(_service == null || !m_realTimeServices.contains(_service))
					return false;

				m_realTimeServices.remove(_service);
				
				if(_service instanceof INetworkService)
					((INetworkService)_service).bindNetworkModule(null);

				return true;
			}
		});
	}
}
