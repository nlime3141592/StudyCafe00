package deu.java002_02.study.customer.service;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public class LoginService implements INetworkService
{
	private INetworkModule m_netModule;
	
	private String m_id;
	private String m_pw;
	
	private String m_uuid;

	public LoginService(String _id, String _pw)
	{
		m_id = _id;
		m_pw = _pw;
	}

	@Override
	public boolean tryExecuteService()
	{
		m_netModule.writeLine("LOGIN_SERVICE");
		m_netModule.writeLine(m_id);
		m_netModule.writeLine(m_pw);
		m_netModule.writeLine(NetworkLiteral.EOF);

		String response = m_netModule.readLine();		
		System.out.println(response);

		switch(response)
		{
		case NetworkLiteral.SUCCESS:
			m_uuid = m_netModule.readLine();
			return true;
		case "<BLACKED_ACCOUNT>":
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

	public String getUuid()
	{
		return m_uuid;
	}
}
