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
			// 독서실 이용자 측 서비스는 1이지만, 현재는 테스트를 위해 독서실 운영자 측 서비스에 구현되어 있습니다.
			// 추후에 1로 변경해야 합니다.
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
			// 독서실 이용자 측 서비스는 1이지만, 현재는 테스트를 위해 독서실 운영자 측 서비스에 구현되어 있습니다.
			// 추후에 1로 변경해야 합니다.
			netModule.writeByte(2);

			// NOTE: 필수 정보
			netModule.writeLine("JOIN_SERVICE"); // 서비스 헤더
			netModule.writeLine("testid1");
			netModule.writeLine("testpw");
			netModule.writeLine("1"); // 클라이언트 유형 (1: 독서실 이용자, 2: 독서실 운영자)
			netModule.writeLine("myname");
			netModule.writeLine("mynickname1");
			
			// NOTE: 선택 정보
			// 선택 정보를 전송하지 않으려면 NetworkLiteral.NULL을 입력합니다.
			netModule.writeLine("0123456789"); // 전화번호
			netModule.writeLine(NetworkLiteral.NULL); // 이메일 주소

			// 필수 정보
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
