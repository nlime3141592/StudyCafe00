package deu.java002_02.study.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.ni.IConnectionModule;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public class OnAirTimeTableService extends Service implements INetworkService, IConnectionService
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
			m_netModule.writeLine(NetworkLiteral.ERROR);
			System.out.println("OnAirTimeTableService: Cannot access DB.");
			return false;
		}

		String sql = "select day, service_enable, tbeg, tend from service_on_air";
		ResultSet rs = m_conModule.executeQuery(sql);
		
		try
		{
			while(rs.next())
				for(int i = 0; i < 4; ++i)
					m_netModule.writeLine(rs.getString(i + 1));

			m_netModule.writeLine(NetworkLiteral.EOF);
			return true;
		}
		catch(SQLException _sqlEx)
		{
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
