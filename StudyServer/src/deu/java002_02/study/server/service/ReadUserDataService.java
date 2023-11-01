package deu.java002_02.study.server.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import deu.java002_02.study.db.main.DB;
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
	public void onService()
	{
		long timeout = (long)(1e+9 * 3);

		while(m_conModule == null && timeout > 0)
			timeout -= System.nanoTime();

		if(timeout <= 0)
		{
			m_conModule = null;
			System.out.println("3초 안에 응답이 없음.");
			return;
		}

		String sql = "select * from users where serverid = ?";

		// NOTE: SQL 문장에 서식 문자 등 후처리가 필요하다면 PreparedStatement 객체를 사용할 수 있습니다.
		PreparedStatement state = m_conModule.getPreparedState(sql);

		try
		{
			// NOTE: PreparedStatement의 인덱스는 1번부터 시작합니다.
			state.setString(1, m_netModule.readLine());
System.out.println("ruds 1");
			ResultSet result = state.executeQuery();
			System.out.println("ruds 2");
			if(!result.next())
			{
				System.out.println("ReadUserDataService: Cannot response to client.");
				m_conModule = null;
				return;
			}
			System.out.println("ruds 3");
			// NOTE: ResultSet의 인덱스는 1번부터 시작합니다.
			String uuid = result.getString(1);
			String nk = result.getString(2);
			String id = result.getString(3);
			String pw = result.getString(4);
			String reg = result.getString(5);
			String bl = result.getString(6);
			System.out.println("ruds 4");
			m_netModule.writeLine(uuid);
			m_netModule.writeLine(nk);
			m_netModule.writeLine(id);
			m_netModule.writeLine(pw);
			m_netModule.writeLine(reg);
			m_netModule.writeLine(bl);
			// m_netModule.writeLine(NetworkLiteral.EOF);

			System.out.println(m_netModule.readLine());
			System.out.println("ReadUserDataService: OK");
		}
		catch (SQLException e)
		{
			System.out.println("ReadUserDataService: Occured sql exception.");
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
