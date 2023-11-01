package deu.java002_02.study.tprovider.service;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.tprovider.gui.TestGui;

public class TService03 implements INetworkService
{
	private TestGui m_gui;
	
	private INetworkModule m_netModule;
	
	public TService03(TestGui _gui)
	{
		m_gui = _gui;
	}

	@Override
	public void onService()
	{
		m_netModule.writeLine("READ_SERVER_TIME_SERVICE");
		m_gui.getLabel2().setText(m_netModule.readLine());
	}

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}
}
