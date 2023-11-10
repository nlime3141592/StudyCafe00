package deu.java002_02.study.server.service;

import java.time.LocalDateTime;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;

// TEST: 서버-클라이언트 통신 테스트를 위한 테스트 서비스 클래스입니다.
// TODO: 서버, 클라이언트 양쪽 서비스 클래스 구현이 끝난 후 삭제합니다.
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
