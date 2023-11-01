package deu.java002_02.study.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import deu.java002_02.study.main.StudyThread;
import deu.java002_02.study.ni.StandardNetworkModule;

public class Server extends StudyThread
{
	private static final int c_PORT = 28170;
	// private static final int c_PORT_CUSTOMER = 28171;
	// private static final int c_PORT_PROVIDER = 28172;

	private ServerSocket m_svSocket;
	// private ServerSocket m_svCustomer;
	// private ServerSocket m_svProvider;

	private StudyThread[] m_clientThreads;
	private int m_clientCount;

	public Server(int _capacity)
	{
		m_clientThreads = new StudyThread[Math.max(2, _capacity)];
		m_clientCount = 0;
	}

	@Override
	public void run()
	{
		while(super.isRun())
		{
			try
			{
				addEndpoint(m_svSocket.accept());
			}
			catch (IOException e)
			{
				// NOTE: 서버가 닫힐 때 이 곳이 수행됩니다.
			}
		}
	}

	public boolean start()
	{
		try
		{
			m_svSocket = new ServerSocket(c_PORT);
			System.out.println("서버가 열렸습니다.");
			super.start();
			return false;
		}
		catch (IOException e)
		{
			System.out.println(String.format("포트 번호 %d를 사용할 수 없습니다.", c_PORT));
			return false;
		}
	}

	public boolean stop()
	{
		if(super.isRun())
		{
			for(int i = 0; i < m_clientThreads.length; ++i)
			{
				if(m_clientThreads[i] != null)
					m_clientThreads[i].stop();
			}

			try { m_svSocket.close(); }
			catch (IOException e) { }

			return super.stop();
		}
		
		return false;
	}

	public int getClientCount()
	{
		return m_clientCount;
	}

	private void addEndpoint(Socket _socket)
	{
		int index = 0;

		while(index < m_clientThreads.length && m_clientThreads[index++] != null);

		if(index == m_clientThreads.length)
		{
			System.out.println("사용자 입장 거절됨.");
			return;
		}

		int serviceCode = 0;
		String host = _socket.getInetAddress().getHostAddress();
		int port = _socket.getPort();

		try
		{
			_socket.setSoTimeout(10000);
			serviceCode = _socket.getInputStream().read();
			_socket.setSoTimeout(0);
		}
		catch (IOException e)
		{
			System.out.println(host + ":" + port + "의 클라이언트 유형을 알 수 없습니다.");
		}

		switch(serviceCode)
		{
		case 1:
			++m_clientCount;
			m_clientThreads[index] = new CustomerThread(new StandardNetworkModule(_socket));
			m_clientThreads[index].start();
			System.out.println(host + ":" + port + "가 Customer로 참여하였습니다.");
			break;
		case 2:
			++m_clientCount;
			m_clientThreads[index] = new ProviderThread(new StandardNetworkModule(_socket));
			m_clientThreads[index].start();
			System.out.println(host + ":" + port + "가 Provider로 참여하였습니다.");
			break;
		default:
			System.out.println(host + ":" + port + "에서 잘못된 클라이언트 유형으로 접속 시도하여 연결을 차단합니다.");
			break;
		}
	}
}
