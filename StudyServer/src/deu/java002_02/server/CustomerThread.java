package deu.java002_02.server;

import java.net.Socket;

import deu.java002_02.ni.INetworkModule;
import deu.java002_02.ni.NetworkThread;

public class CustomerThread extends NetworkThread
{
	public CustomerThread(Socket _socket)
	{
		super(_socket);
	}

	@Override
	public final void onService(INetworkModule _netModule)
	{
		String header = _netModule.readLine();
		
		if(header == null)
		{
			super.stop();
			return;
		}
	}
}
