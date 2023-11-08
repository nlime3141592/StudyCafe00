package deu.java002_02.study.tconsole.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import deu.java002_02.study.customer.service.JoinService;
import deu.java002_02.study.customer.service.LoginService;
import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.NetworkLiteral;
import deu.java002_02.study.ni.NetworkModule;

public class TConsoleMain
{
	public static void main(String[] args)
	{
		main_joinService();
		// main_loginService();
	}
	
	private static void main_joinService()
	{
		Socket socket = new Socket();
		SocketAddress address = new InetSocketAddress("182.219.102.71", 10073);

		try
		{
			socket.connect(address, 10 * 1000);

			NetworkModule netModule = new NetworkModule(socket);
			netModule.writeByte(1);

			JoinService js = new JoinService("secondId2", "secondPw", "secondNk2");
			js.bindNetworkModule(netModule);
			js.tryExecuteService();
		}
		catch (IOException e)
		{
			System.out.println("Occured IOException.");
		}
	}

	private static void main_loginService()
	{
		Socket socket = new Socket();
		SocketAddress address = new InetSocketAddress("localhost", 28170);

		try
		{
			socket.connect(address, 10 * 1000);

			NetworkModule netModule = new NetworkModule(socket);
			netModule.writeByte(1);

			LoginService ls = new LoginService("secondId", "secondPw");
			ls.bindNetworkModule(netModule);
			ls.tryExecuteService();
		}
		catch (IOException e)
		{
			System.out.println("Occured IOException.");
		}
	}
}
