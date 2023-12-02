package deu.java002_02.study.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.ni.IConnectionModule;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public class ReserveService extends Service implements INetworkService, IConnectionService
{
	private INetworkModule m_netModule;
	private IConnectionModule m_conModule;

	@Override
	public boolean tryExecuteService()
	{
		// NOTE: 정보 수신
		int count = 0;
		String[] lines = new String[5]; // NOTE: EOF 문자열 수신을 포함하여 버퍼 용량을 1 늘려서 설정함.

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

		String seatid = lines[0];
		String uuid = lines[1];
		String beginServiceTime = lines[2];
		String endServiceTime = lines[3];

		// NOTE: 데이터베이스 서비스 진입
		long nowTime = System.nanoTime();
		long endTime = nowTime + (long)(1e+9 * 1);

		while(m_conModule == null && nowTime < endTime)
			nowTime = System.nanoTime();

		if(nowTime >= endTime)
		{
			m_netModule.writeLine(NetworkLiteral.ERROR);
			System.out.println("ReserveService: Cannot access DB.");
			return false;
		}

		try
		{
			String sql0 = "SELECT COUNT(*) FROM reserves WHERE seatid = ? AND tbeg = ? AND tend = ?";
			ResultSet result = m_conModule.executeQuery(sql0, seatid, beginServiceTime, endServiceTime);
			result.next();
			boolean available = Integer.parseInt(result.getString(1)) == 0;
			
			if(!available)
			{
				m_netModule.writeLine(NetworkLiteral.FAILURE);
				return false;
			}
		}
		catch (NumberFormatException e)
		{
			// e.printStackTrace();
			return false;
		}
		catch (SQLException e)
		{
			// e.printStackTrace();
			return false;
		}

		String sql1 = "INSERT INTO reserves SET seatid = ?, uuid = ?, tbeg = ?, tend = ?, resdate = current_timestamp";
		boolean serviceSuccess = m_conModule.executeUpdate(sql1, seatid, uuid, beginServiceTime, endServiceTime) > 0;

		// NOTE: 서비스 결과 반환
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
