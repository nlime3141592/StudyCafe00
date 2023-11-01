package deu.java002_02.study.tprovider.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.time.*;

import deu.java002_02.study.ni.StandardNetworkModule;
import deu.java002_02.study.tprovider.gui.TestGui;

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

			thread = new TProviderThread(new StandardNetworkModule(socket));
			thread.start();

			new TestGui();
		}
		catch (IOException e)
		{
			if(thread != null)
				thread.stop();

			System.out.println("서버와의 연결이 끊어졌습니다.");
		}
	}
}
