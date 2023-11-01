package deu.java002_02.study.ni;

import java.sql.PreparedStatement;

public interface IConnectionModule
{
	PreparedStatement getPreparedState(String _sql);
}
