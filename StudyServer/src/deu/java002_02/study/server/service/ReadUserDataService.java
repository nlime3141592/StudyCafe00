package deu.java002_02.study.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import deu.java002_02.study.ni.ConnectionModule;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;

public final class ReadUserDataService extends Service implements INetworkService, IConnectionService
{
	private INetworkModule m_netModule;
	private ConnectionModule m_conModule;

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}

	@Override
	public void bindConnectionModule(ConnectionModule _conModule)
	{
		m_conModule = _conModule;
	}

	@Override
	public boolean tryExecuteService()
	{
		long beginTime = System.nanoTime();
		long leftAvailableTime = (long)1e+9;
		
		while(m_conModule == null && leftAvailableTime > 0)
			leftAvailableTime -= (System.nanoTime() - beginTime);

		if(leftAvailableTime <= 0)
		{
			m_conModule = null;
			System.out.println("ReadUserDataService: Cannot access DB.");
			return false;
		}

		try
		{
			String sql = "select * from users where serverid = ?";

			ResultSet result = m_conModule.executeQuery(sql, 1);

			if(result == null || !result.next())
			{
				System.out.println("ReadUserDataService: Cannot response to client.");
				m_conModule = null;
				return false;
			}

			// NOTE: ResultSet의 인덱스는 1번부터 시작합니다.
			String uuid = result.getString(1);
			String nk = result.getString(2);
			String id = result.getString(3);
			String pw = result.getString(4);
			String reg = result.getString(5);
			String bl = result.getString(6);

			m_netModule.writeLine(uuid);
			m_netModule.writeLine(nk);
			m_netModule.writeLine(id);
			m_netModule.writeLine(pw);
			m_netModule.writeLine(reg);
			m_netModule.writeLine(bl);
			// m_netModule.writeLine(NetworkLiteral.EOF);

			System.out.println(m_netModule.readLine());
			System.out.println("ReadUserDataService: OK");
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("ReadUserDataService: Occured sql exception.");
			return false;
		}
		finally
		{
			m_conModule = null;
		}
	}

	@Override
	public boolean isConnectionEnded()
	{
		return m_conModule == null;
	}
}
