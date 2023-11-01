package deu.java002_02.study.ni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StandardConnectionModule implements IConnectionModule
{
	private Connection m_dbConnection;

	public StandardConnectionModule(Connection _dbConnection)
	{
		m_dbConnection = _dbConnection;
	}

	public final void stop()
	{
		try
		{
			m_dbConnection.close();
		}
		catch(SQLException _sqlEx)
		{
			
		}
	}

	public PreparedStatement getPreparedState(String _sql)
	{
		try
		{
			return m_dbConnection.prepareStatement(_sql);
		}
		catch (SQLException e)
		{
			System.out.println("DB ������ �����߽��ϴ�.");
			return null;
		}
	}
}
