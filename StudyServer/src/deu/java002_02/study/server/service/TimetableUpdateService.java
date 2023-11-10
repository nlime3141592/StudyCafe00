package deu.java002_02.study.server.service;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.ni.IConnectionModule;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public class TimetableUpdateService extends Service implements INetworkService, IConnectionService
{
	private INetworkModule m_netModule;
	private IConnectionModule m_conModule;

	public boolean tryExecuteService()
	{
		// NOTE: ���� ����
		int count = 0;
		String[] lines = new String[29]; // NOTE: EOF ���ڿ� ������ �����Ͽ� ���� �뷮�� 1 �÷��� ������.

		while(count < lines.length)
		{
			lines[count] = m_netModule.readLine();
			
			if(lines[count] == null)
			{
				m_netModule.writeLine(NetworkLiteral.ERROR);
				return false;
			}
			else if(lines[count].equals(NetworkLiteral.EOF))
				break;

			++count;
		}
		
		// NOTE: �����ͺ��̽� ���� ����
		long nowTime = System.nanoTime();
		long endTime = nowTime + (long)(1e+9 * 1);

		while(m_conModule == null && nowTime < endTime)
			nowTime = System.nanoTime();

		if(nowTime >= endTime)
		{
			m_netModule.writeLine(NetworkLiteral.ERROR);
			System.out.println("TimetableUpdateService: Cannot access DB.");
			return false;
		}

		String sql = "UPDATE service_on_air SET day = ?, service_enable = ?, tbeg = ?, tend = ?";

		for(int i = 0; i < 7; ++i)
			m_conModule.executeUpdate(sql, lines[4 * i + 0], lines[4 * i + 1], lines[4 * i + 2], lines[4 * i + 3]);

		// NOTE: ���� ��� ��ȯ
		m_netModule.writeLine(NetworkLiteral.SUCCESS);
		return true;
	}

	@Override
	public void bindConnectionModule(IConnectionModule _conModule)
	{
		m_conModule = _conModule;
	}

	@Override
	public boolean isConnectionEnded()
	{
		return m_conModule == null;
	}

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}
}