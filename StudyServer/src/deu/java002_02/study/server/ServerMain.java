package deu.java002_02.study.server;

import java.util.Scanner;

import deu.java002_02.study.db.main.DB;
import deu.java002_02.study.server.command.MonitorCommand;

public class ServerMain
{
	private static Server s_m_server;
	private static DB s_m_db;

	public static void main(String[] args)
	{
		s_m_db = new DB();
		s_m_db.start();

		s_m_server = new Server(10);
		s_m_server.start();

		Scanner sc = new Scanner(System.in);
		String line = null;

		do
		{
			line = sc.nextLine();
			
			// NOTE: 콘솔창 커맨드 기능
			// console commands
			switch(line)
			{
			case "monitor":
				new MonitorCommand();
				break;
			case "stop":
				break;
			default:
				System.out.println("잘못된 서버 명령어를 입력했습니다.");
				break;
			}
		}
		while(!line.equals("stop"));

		sc.close();
		s_m_server.stop();
		s_m_db.stop();

		System.out.println("서버 종료합니다.");
	}

	public static Server getServer()
	{
		return s_m_server;
	}

	public static DB getDB()
	{
		return s_m_db;
	}
}