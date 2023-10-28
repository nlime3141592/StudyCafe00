package deu.java002_02.tprovider.main;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import deu.java002_02.ni.INetworkModule;
import deu.java002_02.ni.INetworkService;
import deu.java002_02.ni.NetworkThread;

public class TProviderThread extends NetworkThread
{
	private Queue<INetworkService> m_serviceQueue;

	public TProviderThread(Socket _socket)
	{
		super(_socket);
		
		m_serviceQueue = new LinkedList<INetworkService>();

		super.getNetworkModule().writeByte(2);
	}

	@Override
	public void onService(INetworkModule _netModule)
	{
		// TODO: 실시간 처리를 새로운 스레드로 분리합니다.

		// NOTE: 요청이 들어온 서비스 우선 처리

		while(m_serviceQueue.size() > 0)
		{
			System.out.println("처리됨");
			m_serviceQueue.poll().onService(_netModule);
		}
	}

	public void enqueueService(INetworkService _service)
	{
		m_serviceQueue.offer(_service);
	}
}
