package deu.java002_02.study.ni;

import java.sql.ResultSet;

public interface IConnectionModule
{
	ResultSet executeQuery(String _sqlFormat, Object... _parameters);
	int executeUpdate(String _sqlFormat, Object... _parameters);
}
