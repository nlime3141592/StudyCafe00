package deu.java002_02.study.ni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public class ConnectionModule implements IConnectionModule
{
	private Connection m_dbConnection;
	// private boolean m_isClosed;

	public ConnectionModule(Connection _dbConnection)
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

	public ResultSet executeQuery(String _sqlFormat, Object... _parameters)
	{
		try
		{
			PreparedStatement state = m_dbConnection.prepareStatement(_sqlFormat);
			
			for(int i = 0; i < _parameters.length; ++i)
				state.setObject(i + 1, _parameters[i]);

			return state.executeQuery();
		}
		catch(SQLTimeoutException _sqlTimeEx)
		{
			return null;
		}
		catch(SQLException _sqlEx)
		{
			return null;
		}
	}

	public int executeUpdate(String _sqlFormat, Object... _parameters)
	{
		try
		{
			PreparedStatement state = m_dbConnection.prepareStatement(_sqlFormat);
			
			for(int i = 0; i < _parameters.length; ++i)
				state.setObject(i + 1, _parameters[i]);

			return state.executeUpdate();
		}
		catch(SQLTimeoutException _sqlTimeEx)
		{
			return -1;
		}
		catch(SQLException _sqlEx)
		{
			return -1;
		}
	}
}