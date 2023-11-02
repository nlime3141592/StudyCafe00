package deu.java002_02.study.server.service;

import java.time.LocalDateTime;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;

public class ReadServerTimeService extends Service implements INetworkService
{
	private INetworkModule m_netModule;
	
	@Override
	public boolean tryExecuteService()
	{
		String localDateTime = LocalDateTime.now().toString();
		m_netModule.writeLine(localDateTime);
		return true;
	}

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}
}
