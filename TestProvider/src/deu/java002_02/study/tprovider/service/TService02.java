package deu.java002_02.study.tprovider.service;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.tprovider.gui.TestGui;

public class TService02 implements INetworkService
{
	private TestGui m_gui;
	
	private INetworkModule m_netModule;
	
	public TService02(TestGui _gui)
	{
		m_gui = _gui;
	}

	@Override
	public void onService()
	{
		m_netModule.writeLine("READ_USER_DATA_SERVICE");

		String str = "";
		str += "/" + m_netModule.readLine();
		str += "/" + m_netModule.readLine();
		str += "/" + m_netModule.readLine();
		str += "/" + m_netModule.readLine();
		str += "/" + m_netModule.readLine();
		str += "/" + m_netModule.readLine();

		System.out.println(str);
		m_gui.getLabel1().setText(str);

		m_netModule.writeLine("Provider: I successfully got user info.");
	}

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}
}