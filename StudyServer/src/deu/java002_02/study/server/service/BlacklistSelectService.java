package deu.java002_02.study.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.ni.IConnectionModule;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public class BlacklistSelectService extends Service implements INetworkService, IConnectionService
{
	private INetworkModule m_netModule;
	private IConnectionModule m_conModule;

	@Override
	public boolean tryExecuteService()
	{
		// NOTE: 데이터베이스 서비스 진입
		long nowTime = System.nanoTime();
		long endTime = nowTime + (long)(1e+9 * 1);

		while(m_conModule == null && nowTime < endTime)
			nowTime = System.nanoTime();

		if(nowTime >= endTime)
		{
			m_netModule.writeLine(NetworkLiteral.EOF);
			m_netModule.writeLine(NetworkLiteral.ERROR);
			System.out.println("BlacklistSelectService: Cannot access DB.");
			return false;
		}

		String sql = "SELECT uuid, nickname, bdate FROM userinfo WHERE blacked = 1 ORDER BY uuid";
		ResultSet rs = m_conModule.executeQuery(sql);

		// NOTE: 서비스 결과 반환
		try
		{
			int resultCount = 0;

			while(rs.next())
			{
				for(int i = 0; i < 3; ++i)
					m_netModule.writeLine(rs.getString(i + 1));

				++resultCount;
			}

			m_netModule.writeLine(NetworkLiteral.EOF);

			if(resultCount > 0)
				m_netModule.writeLine(NetworkLiteral.SUCCESS);
			else
				m_netModule.writeLine(NetworkLiteral.FAILURE);

			return resultCount > 0;
		}
		catch(SQLException _sqlEx)
		{
			m_netModule.writeLine(NetworkLiteral.EOF);
			m_netModule.writeLine(NetworkLiteral.ERROR);
			return false;
		}
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
