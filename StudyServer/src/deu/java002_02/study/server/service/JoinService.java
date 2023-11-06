package deu.java002_02.study.server.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import deu.java002_02.study.main.Service;
import deu.java002_02.study.ni.IConnectionModule;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.ni.NetworkLiteral;

public class JoinService extends Service implements INetworkService, IConnectionService
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
			System.out.println("JoinService: Cannot access DB.");
			return false;
		}

		int count = 0;
		String[] lines = new String[5]; // NOTE: EOF 문자열 수신을 포함하여 버퍼 용량을 1 늘려서 설정함.

		while(count < lines.length)
		{
			lines[count] = m_netModule.readLine();

			if(lines[count] == null) // NOTE: 통신할 수 없는 상태이므로 EOF를 보낼 수 없다. 바로 return false;
				return false;
			if(lines[count].equals(NetworkLiteral.EOF))
				break;

			++count;
		}

		String id = lines[0];
		String pw = lines[1];
		String ctype = lines[2];
		String nkname = lines[3];

		if(isInvalidClientType(ctype))
		{
			m_netModule.writeLine("<INVALID_CLIENT_TYPE>");
			return false;
		}
		if(isDuplicatedId(id))
		{
			m_netModule.writeLine("<DUPLICATED_ID>");
			return false;
		}
		if(isDuplicatedNickname(nkname))
		{
			m_netModule.writeLine("<DUPLICATED_NICKNAME>");
			return false;
		}

		// NOTE: Add new account
		String sqlAccount = "INSERT INTO account (id, pw, ctype) VALUES (?, ?, ?)";
		m_conModule.executeUpdate(sqlAccount, id, pw, ctype);

		int uuid = getUuidById(id);

		// NOTE: Add new user informations
		String sqlUserInfo = "INSERT INTO userinfo (uuid, nickname) VALUES (?, ?)";
		m_conModule.executeUpdate(sqlUserInfo, uuid, nkname);

		m_netModule.writeLine(NetworkLiteral.SUCCESS);

		return true;
	}

	@Override
	public void bindConnectionModule(IConnectionModule _conModule)
	{
		m_conModule = _conModule;
	}

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}

	@Override
	public boolean isConnectionEnded()
	{
		return m_conModule == null;
	}

	private boolean isDuplicatedId(String _id)
	{
		String sql = "SELECT COUNT(*) FROM account WHERE id = ?";
		ResultSet rs = m_conModule.executeQuery(sql, _id);
		
		try
		{
			return rs.next() && rs.getInt(1) > 0;
		}
		catch (SQLException _sqlEx)
		{
			// NOTE:
			// 예외가 발생한다면 중복된 ID인지 확인이 불가능하므로
			// 일단 중복이 있다고 '가정'하여 함수를 호출하는 쪽에서 서비스를 사용할 수 없도록 제한합니다. 
			return true;
		}
	}

	private boolean isDuplicatedNickname(String _nkname)
	{
		String sql = "SELECT COUNT(*) FROM userinfo WHERE nickname = ?";
		ResultSet rs = m_conModule.executeQuery(sql, _nkname);
		
		try
		{
			return rs.next() && rs.getInt(1) > 0;
		}
		catch (SQLException _sqlEx)
		{
			// NOTE:
			// 예외가 발생한다면 중복된 Nickname인지 확인이 불가능하므로
			// 일단 중복이 있다고 '가정'하여 함수를 호출하는 쪽에서 서비스를 사용할 수 없도록 제한합니다. 
			return true;
		}
	}

	private boolean isInvalidClientType(String _ctype)
	{
		switch(_ctype)
		{
		case "1":
		case "2":
			return false;
		default:
			return true;
		}
	}

	private int getUuidById(String _id)
	{
		try
		{
			ResultSet rs = m_conModule.executeQuery("SELECT (uuid) FROM account WHERE id = ?", _id);
			rs.next();
			int id = Integer.parseInt(rs.getString(1));
			return id;
		}
		catch (SQLException e)
		{
			return 0;
		}
	}
}
