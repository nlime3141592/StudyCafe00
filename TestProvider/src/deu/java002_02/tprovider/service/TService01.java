package deu.java002_02.tprovider.service;

import deu.java002_02.ni.INetworkService;
import deu.java002_02.tprovider.gui.TestGui;
import deu.java002_02.ni.INetworkModule;

public class TService01 implements INetworkService
{
	private TestGui m_gui;
	
	public TService01(TestGui _gui)
	{
		m_gui = _gui;
	}

	@Override
	public void onService(INetworkModule _netModule)
	{
		_netModule.writeLine("FILE_MONITOR_SERVICE");

		m_gui.getLabel().setText("");

		while(true)
		{
			String line = _netModule.readLine();

			if(line == null || line.equals("<EOF>"))
				break;

			m_gui.getLabel().setText(m_gui.getLabel().getText() + "[" + line + "]");
		}
	}
}