package deu.java002_02.tprovider.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import deu.java002_02.tprovider.gui.TestGui;

public class TProviderMain
{
	public static TProviderThread thread;

	public static void main(String[] args)
	{
		Socket socket = new Socket();
		SocketAddress address = new InetSocketAddress("localhost", 28170);

		try
		{
			socket.connect(address, 10 * 1000);

			thread = new TProviderThread(socket);
			thread.start();

			new TestGui();
		}
		catch (IOException e)
		{
			System.out.println("서버와의 연결이 끊어졌습니다.");
		}
	}
}
