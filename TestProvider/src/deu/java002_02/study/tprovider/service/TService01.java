package deu.java002_02.study.tprovider.service;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.tprovider.gui.TestGui;

public class TService01 implements INetworkService
{
	private TestGui m_gui;
	
	private INetworkModule m_netModule;
	
	public TService01(TestGui _gui)
	{
		m_gui = _gui;
	}
	
	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}

	@Override
	public boolean tryExecuteService()
	{
		m_netModule.writeLine("READ_FILE_SERVICE_TEST");

		m_gui.getLabel1().setText("");

		while(true)
		{
			String line = m_netModule.readLine();

			if(line == null || line.equals("<EOF>"))
				return true;

			m_gui.getLabel1().setText(m_gui.getLabel1().getText() + "[" + line + "]");
		}
	}
}