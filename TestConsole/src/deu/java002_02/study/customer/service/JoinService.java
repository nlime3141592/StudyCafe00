package deu.java002_02.study.customer.service;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public class JoinService implements INetworkService
{
	private String m_id;
	private String m_pw;
	private String m_nickname;
	
	public JoinService(String _id, String _pw, String _nickname)
	{
		m_id = _id;
		m_pw = _pw;
		m_nickname = _nickname;
	}

	// JoinService.java
	
	private INetworkModule m_netModule;
	
	// NOTE:
	// 독서실 이용자 측 클라이언트가 사용하는 회원 가입 서비스 객체입니다.
	// 회원 가입 서비스를 서버에 요청하고 응답받기 위해
	// tryExecuteService() 함수를 재정의하고
	// INetworkModule 인터페이스의 함수를 사용해 통신합니다.
	@Override
	public boolean tryExecuteService()
	{
		m_netModule.writeLine("JOIN_SERVICE");
		m_netModule.writeLine(m_id);
		m_netModule.writeLine(m_pw);
		m_netModule.writeLine("1");
		m_netModule.writeLine(m_nickname);
		m_netModule.writeLine(NetworkLiteral.EOF);
	
		String response = m_netModule.readLine();		
		System.out.println(response);
	
		switch(response)
		{
		case NetworkLiteral.SUCCESS:
			return true;
		default:
			return false;
		}
	}

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}
}