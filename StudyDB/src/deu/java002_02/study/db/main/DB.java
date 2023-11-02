package deu.java002_02.study.db.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

import deu.java002_02.study.main.StudyThread;
import deu.java002_02.study.ni.IConnectionService;
import deu.java002_02.study.ni.ConnectionModule;

public class DB extends StudyThread
{
	private static final String c_HOST = "localhost";
	private static final int c_PORT = 3306;
	private static final String c_DB_NAME = "testbase";
	private static final String c_ROOT_ID = "root";
	private static final String c_ROOT_PW = "1234";

	private ConnectionModule m_conModule;
	private ConcurrentLinkedQueue<IConnectionService> m_conQueue;

	public DB()
	{
		m_conQueue = new ConcurrentLinkedQueue<IConnectionService>();
	}

	@Override
	public void run()
	{
		while(super.isRun())
		{
			while(m_conQueue.size() > 0)
			{
				// NOTE: 3초의 작업 시간을 줍니다. 만약 3초 안에 작업을 끝내지 못했다면, DB Connection이 강제로 종료됩니다.
				long beginTime = System.nanoTime();
				long timeout = (long)(1e+9 * 3);

				m_conQueue.peek().bindConnectionModule(m_conModule);

				while(!m_conQueue.peek().isConnectionEnded() && timeout > 0)
					timeout -= (System.nanoTime() - beginTime);

				m_conQueue.poll().bindConnectionModule(null);
			}
		}
	}

	public void start()
	{
		super.start();

		String dbUrl = String.format("jdbc:mariadb://%s:%d/%s",
				c_HOST,
				c_PORT,
				c_DB_NAME
				);

		try
		{
			Connection dbConnection = DriverManager.getConnection(dbUrl, c_ROOT_ID, c_ROOT_PW);
			m_conModule = new ConnectionModule(dbConnection);

			System.out.println("(DB=" + dbUrl + ")에 접속했습니다.");
		}
		catch(SQLException _sqlEx)
		{
			// _sqlEx.printStackTrace();
			System.out.println("(DB=" + dbUrl + ")에 접속할 수 없습니다.");
		}
	}

	public void stop()
	{
		m_conModule.stop();
		super.stop();
	}

	public void requestService(IConnectionService _service)
	{
		m_conQueue.offer(_service);
	}
}
