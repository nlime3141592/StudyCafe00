package deu.java002_02.study.tprovider.service;

import java.util.Vector;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;
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
	public boolean tryExecuteService()
	{
		m_netModule.writeLine("READ_USER_DATA_SERVICE");
		m_netModule.writeLine("1"); // NOTE: uuid 값을 전송함.

		Vector<String> lines = new Vector<String>(7);

		while(lines.size() < lines.capacity())
		{
			String line = m_netModule.readLine();

			if(line == null)
				return false;
			else if(line.equals(NetworkLiteral.EOF))
				break;

			lines.add(line);
		}

		String networkResult = m_netModule.readLine();
		System.out.println("Networking Result: " + networkResult);

		String message = "";

		message += String.format("  uuid     : %s\n", lines.get(0));
		message += String.format("  nickname : %s\n", lines.get(1));
		message += String.format("  blacked  : %s\n", lines.get(2));
		message += String.format("  bdate    : %s", lines.get(3));

		m_gui.getLabel1().setText(message);
		System.out.println(message);
		return true;
	}

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}
}