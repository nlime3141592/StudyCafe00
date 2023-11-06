package deu.java002_02.study.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.ni.IConnectionModule;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public final class ReadUserDataService extends Service implements INetworkService, IConnectionService
{
	private INetworkModule m_netModule;
	private IConnectionModule m_conModule;

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}

	@Override
	public void bindConnectionModule(IConnectionModule _conModule)
	{
		m_conModule = _conModule;
	}

	@Override
	public boolean tryExecuteService()
	{
		long nowTime = System.nanoTime();
		long endTime = nowTime + (long)(1e+9 * 1);

		while(m_conModule == null && nowTime < endTime)
			nowTime = System.nanoTime();

		if(nowTime >= endTime)
		{
			m_netModule.writeLine(NetworkLiteral.EOF);
			m_netModule.writeLine(NetworkLiteral.ERROR);
			System.out.println("ReadUserDataService: Cannot access DB.");
			return false; // NOTE: 서비스가 올바로 이루어지지 않았다면 false를 반환합니다.
		}

		try
		{
			String sql = "select uuid, nickname, blacked, bdate from userinfo where uuid = ?";
			String uuidStr = m_netModule.readLine();

			ResultSet result = m_conModule.executeQuery(sql, uuidStr);

			if(result == null || !result.next())
			{
				m_netModule.writeLine(NetworkLiteral.EOF);
				m_netModule.writeLine(NetworkLiteral.ERROR);
				System.out.println("ReadUserDataService: Cannot response to client.");
				return false; // NOTE: 서비스가 올바로 이루어지지 않았다면 false를 반환합니다.
			}
			else
			{
				// NOTE: ResultSet의 인덱스는 1번부터 시작합니다.
				String uuid = result.getString(1);
				String nickname = result.getString(2);
				String blacked = result.getString(3);
				String bdate = result.getString(4);
				
				// NOTE: 쿼리한 문자들을 네트워크 모듈을 통해 상대방에게 전송합니다.
				m_netModule.writeLine(uuid);
				m_netModule.writeLine(nickname);
				m_netModule.writeLine(blacked);
				m_netModule.writeLine(bdate);
				m_netModule.writeLine(NetworkLiteral.EOF);
				m_netModule.writeLine(NetworkLiteral.SUCCESS);
	
				System.out.println("ReadUserDataService: OK");
				return true; // NOTE: 서비스가 올바로 이루어졌다면 true를 반환합니다.
			}
		}
		catch (SQLException e)
		{
			m_netModule.writeLine(NetworkLiteral.EOF);
			m_netModule.writeLine(NetworkLiteral.ERROR);
			System.out.println("ReadUserDataService: Occured sql exception.");
			return false; // NOTE: 서비스가 올바로 이루어지지 않았다면 false를 반환합니다.
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
