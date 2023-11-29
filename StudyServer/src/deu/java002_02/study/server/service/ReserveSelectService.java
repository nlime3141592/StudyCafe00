package deu.java002_02.study.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.ni.IConnectionModule;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public class ReserveSelectService extends Service implements INetworkService, IConnectionService
{
	private INetworkModule m_netModule;
	private IConnectionModule m_conModule;

	@Override
	public boolean tryExecuteService()
	{
		// NOTE: 정보 수신
		int count = 0;
		String[] lines = new String[2]; // NOTE: EOF 문자열 수신을 포함하여 버퍼 용량을 1 늘려서 설정함.

		while(count < lines.length)
		{
			lines[count] = m_netModule.readLine();
			
			if(lines[count] == null)
			{
				m_netModule.writeLine(NetworkLiteral.EOF);
				m_netModule.writeLine(NetworkLiteral.ERROR);
				return false;
			}
			else if(lines[count].equals(NetworkLiteral.EOF))
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
			m_netModule.writeLine(NetworkLiteral.EOF);
			m_netModule.writeLine(NetworkLiteral.ERROR);
			System.out.println("ReserveSelectService: Cannot access DB.");
			return false;
		}

		String sql = "SELECT res_id, seatid, tbeg, tend, resdate FROM reserves WHERE uuid = ? AND tbeg > CURRENT_TIMESTAMP ORDER BY tbeg";
		ResultSet rs = m_conModule.executeQuery(sql, uuid);

		// NOTE: 서비스 결과 반환
		try
		{
			int resultCount = 0;

			while(rs.next())
			{
				for(int i = 0; i < 5; ++i)
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
