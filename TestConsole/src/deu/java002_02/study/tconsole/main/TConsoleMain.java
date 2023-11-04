package deu.java002_02.study.tconsole.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.NetworkLiteral;
import deu.java002_02.study.ni.NetworkModule;

public class TConsoleMain
{
	public static void main(String[] args)
	{
		// main_loginService();
		// main_joinService();
	}
	
	private static void main_loginService()
	{
		Socket socket = new Socket();
		SocketAddress address = new InetSocketAddress("localhost", 28170);

		try
		{
			socket.connect(address, 10 * 1000);
			
			NetworkModule netModule = new NetworkModule(socket);
			
			// TODO:
			// ������ �̿��� �� ���񽺴� 1������, ����� �׽�Ʈ�� ���� ������ ��� �� ���񽺿� �����Ǿ� �ֽ��ϴ�.
			// ���Ŀ� 1�� �����ؾ� �մϴ�.
			netModule.writeByte(2);
			
			netModule.writeLine("LOGIN_SERVICE");
			netModule.writeLine("testid1");
			netModule.writeLine("testpw");
			netModule.writeLine(NetworkLiteral.EOF);

			String response = netModule.readLine();

			System.out.println("<LOGIN_SERVICE>");
			if(response == null)
				System.out.println("  Request failed.");
			else
				System.out.println("  response of server : " + response);
		}
		catch (IOException e)
		{
			System.out.println("Occured IOException.");
		}
	}

	private static void main_joinService()
	{
		Socket socket = new Socket();
		SocketAddress address = new InetSocketAddress("localhost", 28170);

		try
		{
			socket.connect(address, 10 * 1000);
			
			NetworkModule netModule = new NetworkModule(socket);

			// TODO:
			// ������ �̿��� �� ���񽺴� 1������, ����� �׽�Ʈ�� ���� ������ ��� �� ���񽺿� �����Ǿ� �ֽ��ϴ�.
			// ���Ŀ� 1�� �����ؾ� �մϴ�.
			netModule.writeByte(2);

			// NOTE: �ʼ� ����
			netModule.writeLine("JOIN_SERVICE"); // ���� ���
			netModule.writeLine("testid1");
			netModule.writeLine("testpw");
			netModule.writeLine("1"); // Ŭ���̾�Ʈ ���� (1: ������ �̿���, 2: ������ ���)
			netModule.writeLine("myname");
			netModule.writeLine("mynickname1");
			
			// NOTE: ���� ����
			// ���� ������ �������� �������� NetworkLiteral.NULL�� �Է��մϴ�.
			netModule.writeLine("0123456789"); // ��ȭ��ȣ
			netModule.writeLine(NetworkLiteral.NULL); // �̸��� �ּ�

			// �ʼ� ����
			netModule.writeLine(NetworkLiteral.EOF);

			String response = netModule.readLine();

			System.out.println("<JOIN_SERVICE>");
			if(response == null)
				System.out.println("  Request failed.");
			else
				System.out.println("  response of server : " + response);

			netModule.stop();
			socket.close();
		}
		catch (IOException e)
		{
			System.out.println("Occured IOException.");
		}
	}
}
