package deu.java002_02.study.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

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
		// NOTE: 정보 수신
		int count = 0;
		String[] lines = new String[1]; // NOTE: EOF 문자열 수신을 포함하여 버퍼 용량을 1 늘려서 설정함.

		while(count < lines.length)
		{
			lines[count] = m_netModule.readLine();
			
			if(lines[count] == null)
			{
				m_netModule.writeLine(NetworkLiteral.ERROR);
				return false;
			}
			if(lines[count].equals(NetworkLiteral.EOF))
				break;
			
			++count;
		}

		String uuid = lines[0];

		// NOTE: 데이터베이스 서비스 진입
		long nowTime = System.nanoTime();
		long endTime = nowTime + (long)(1e+9 * 1);

		while(m_conModule == null && nowTime < endTime)
			nowTime = System.nanoTime();

		if(nowTime >= endTime)
		{
			m_netModule.writeLine(NetworkLiteral.ERROR);
			System.out.println("BlacklistAddService: Cannot access DB.");
			return false;
		}

		String sqlUpdate = "UPDATE userinfo SET blacked = 1, bdate = current_timestamp WHERE uuid = ? AND blacked = 0";
		boolean serviceSuccess = m_conModule.executeUpdate(sqlUpdate, uuid) > 0;

		// NOTE: 서비스 결과 반환
		if(serviceSuccess)
		{
			String sqlSelect = "SELECT nickname, bdate FROM userinfo WHERE uuid = ? ORDER BY uuid";
			ResultSet rs = m_conModule.executeQuery(sqlSelect, uuid);
			
			try
			{
				rs.next();
				m_netModule.writeLine(rs.getString(1));
				m_netModule.writeLine(rs.getString(2));
				m_netModule.writeLine(NetworkLiteral.SUCCESS);
			}
			catch (SQLException e)
			{
				m_netModule.writeLine(NetworkLiteral.EOF);
				m_netModule.writeLine(NetworkLiteral.FAILURE);
			}
		}
		else
		{
			m_netModule.writeLine(NetworkLiteral.EOF);
			m_netModule.writeLine(NetworkLiteral.FAILURE);
		}

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
