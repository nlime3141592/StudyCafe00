package deu.java002_02.server;

import java.net.Socket;

import deu.java002_02.ni.INetworkModule;
import deu.java002_02.ni.NetworkThread;
import deu.java002_02.server.service.FileMonitorService;
import deu.java002_02.server.service.MyService01;

public final class ProviderThread extends NetworkThread
{
	public ProviderThread(Socket _socket)
	{
		super(_socket);
	}

	@Override
	public void onService(INetworkModule _netModule)
	{
		String header = _netModule.readLine();
		
		if(header == null)
		{
			super.stop();
			return;
		}

		switch(header)
		{
		case "MY_SERVICE_01":
			new MyService01().onService(_netModule);
			break;
		case "FILE_MONITOR_SERVICE":
			new FileMonitorService().onService(_netModule);
			break;
		default:
			System.out.println("잘못된 문자열 수신함.");
			break;
		}
	}
}
