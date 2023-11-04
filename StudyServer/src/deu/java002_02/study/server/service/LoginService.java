package deu.java002_02.study.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import deu.java002_02.study.ni.IConnectionModule;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public class LoginService extends Service implements INetworkService, IConnectionService
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
			System.out.println("LoginService: Cannot access DB.");
			return false;
		}

		int count = 0;
		String[] lines = new String[3]; // NOTE: EOF 문자열 수신을 포함하여 버퍼 용량을 1 늘려서 설정함.

		while(count < lines.length)
		{
			lines[count] = m_netModule.readLine();
			
			if(lines[count].equals(NetworkLiteral.EOF))
				break;
			
			++count;
		}

		String id = lines[0];
		String pw = lines[1];
		
		boolean serviceSuccess = tryAuthenticateUser(id, pw);
		
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

	private boolean tryAuthenticateUser(String _id, String _pw)
	{
		String sql = "SELECT COUNT(*) FROM account WHERE id = ? AND pw = ?";
		ResultSet rs = m_conModule.executeQuery(sql, _id, _pw);

		try
		{
			return rs.next() && rs.getInt(1) > 0;
		}
		catch (SQLException _sqlEx)
		{
			// NOTE:
			// 예외가 발생한다면 인증 성공했는지 알 수 없으므로
			// 무조건 false를 반환합니다.
			return false;
		}
	}
}
