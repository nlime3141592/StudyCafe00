package deu.java002_02.study.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.ni.IConnectionModule;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public class SeatTimerService extends Service implements INetworkService, IConnectionService
{
	private INetworkModule m_netModule;
	private IConnectionModule m_conModule;
	private boolean m_executing;

	@Override
	public boolean tryExecuteService()
	{
		if(m_executing)
			return false;

		m_executing = true;

		// NOTE: 데이터베이스 서비스 진입
		long nowTime = System.nanoTime();
		long endTime = nowTime + (long)(1e+9 * 1);

		while(m_conModule == null && nowTime < endTime)
			nowTime = System.nanoTime();

		if(nowTime >= endTime)
		{
			m_netModule.writeLine(NetworkLiteral.EOF);
			m_netModule.writeLine(NetworkLiteral.ERROR);
			System.out.println("SeatTimerService: Cannot access DB.");
			m_executing = false;
			return false;
		}

		String sql = "SELECT seatid, TIMEDIFF(tend, current_timestamp) FROM reserves WHERE current_timestamp >= tbeg AND current_timestamp < tend GROUP BY seatid";
		ResultSet rs = m_conModule.executeQuery(sql);

		// NOTE: 서비스 결과 반환
		try
		{
			int resultCount = 0;

			while(rs.next())
			{
				for(int i = 0; i < 2; ++i)
					m_netModule.writeLine(rs.getString(i + 1));

				++resultCount;
			}

			m_netModule.writeLine(NetworkLiteral.EOF);

			if(resultCount > 0)
				m_netModule.writeLine(NetworkLiteral.SUCCESS);
			else
				m_netModule.writeLine(NetworkLiteral.FAILURE);

			m_executing = false;
			return resultCount > 0;
		}
		catch(SQLException _sqlEx)
		{
			m_netModule.writeLine(NetworkLiteral.EOF);
			m_netModule.writeLine(NetworkLiteral.ERROR);
			m_executing = false;
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
