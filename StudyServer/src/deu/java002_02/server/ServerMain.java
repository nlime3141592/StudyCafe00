package deu.java002_02.server;

import java.util.Scanner;

public class ServerMain
{
	public static void main(String[] args)
	{
		Server sv = new Server(10);

		Scanner sc = new Scanner(System.in);
		String line = null;

		do
		{
			line = sc.nextLine();
		}
		while(!line.equals("quit"));

		sv.stop();
		sc.close();

		System.out.println("서버 종료합니다.");
	}
}