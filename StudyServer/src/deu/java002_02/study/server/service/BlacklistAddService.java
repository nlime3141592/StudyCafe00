package deu.java002_02.study.server.service;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.ni.IConnectionModule;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public class BlacklistAddService extends Service implements INetworkService, IConnectionService
{
	private INetworkModule m_netModule;
	private IConnectionModule m_conModule;

	@Override
	public boolean tryExecuteService()
	{
		long nowTime = System.nanoTime();
		long endTime = nowTime + (long)(1e+9 * 1);

		while(m_conModule == null && nowTime < endTime)
			nowTime = System.nanoTime();

		if(nowTime >= endTime)
		{
			System.out.println("BlacklistAddService: Cannot access DB.");
			return false;
		}
		
		int count = 0;
		String[] lines = new String[2]; // NOTE: EOF ���ڿ� ������ �����Ͽ� ���� �뷮�� 1 �÷��� ������.

		while(count < lines.length)
		{
			lines[count] = m_netModule.readLine();
			
			if(lines[count].equals(NetworkLiteral.EOF))
				break;
			
			++count;
		}
		
		String uuid = lines[0];

		String sql = "update userinfo set blacked = 1, bdate = current_timestamp where uuid = ?";

		boolean serviceSuccess = m_conModule.executeUpdate(sql, uuid) > 0;

		if(serviceSuccess)
			m_netModule.writeLine(NetworkLiteral.SUCCESS);
		else
			m_netModule.writeLine(NetworkLiteral.FAILURE);

		return serviceSuccess;
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