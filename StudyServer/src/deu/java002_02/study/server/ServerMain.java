package deu.java002_02.study.server;

import java.util.Scanner;

import deu.java002_02.study.db.main.DB;

public class ServerMain
{
	public static void main(String[] args)
	{
		DB db = new DB();
		db.start();

		Server sv = new Server(10);
		sv.start();

		Scanner sc = new Scanner(System.in);
		String line = null;

		do
		{
			line = sc.nextLine();
		}
		while(!line.equals("stop"));

		sc.close();
		sv.stop();
		db.stop();

		System.out.println("서버 종료합니다.");
	}
}