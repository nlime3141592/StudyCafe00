package deu.java002_02.study.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import deu.java002_02.study.main.StudyThread;
import deu.java002_02.study.main.ThreadState;
import deu.java002_02.study.ni.NetworkModule;

public class Server extends StudyThread
{
	private static final int c_PORT = 25565;
	// private static final int c_PORT_CUSTOMER = 28171;
	// private static final int c_PORT_PROVIDER = 28172;

	private ServerSocket m_svSocket;
	// private ServerSocket m_svCustomer;
	// private ServerSocket m_svProvider;

	private AcceptThread m_acceptThread;
	private Vector<StudyThread> m_clientThreads;
	private int m_capacity;

	private class AcceptThread extends StudyThread
	{
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
					// NOTE: ������ ���� �� �� ���� ����˴ϴ�.
				}
			}
		}
	}

	public Server(int _capacity)
	{
		m_capacity = Math.max(2, _capacity);
		m_clientThreads = new Vector<StudyThread>();
	}

	@Override
	public void run()
	{
		while(super.isRun())
		{
			for(int i = m_clientThreads.size() - 1; i >= 0; --i)
				if(m_clientThreads.get(i) != null && m_clientThreads.get(i).getThreadState() == ThreadState.END)
					m_clientThreads.remove(i);
		}
	}

	public void start()
	{
		try
		{
			m_svSocket = new ServerSocket(c_PORT);
			m_acceptThread = new AcceptThread();

			System.out.println("������ ���Ƚ��ϴ�.");

			m_acceptThread.start();
			super.start();
		}
		catch (IOException e)
		{
			System.out.println(String.format("��Ʈ ��ȣ %d�� ����� �� �����ϴ�.", c_PORT));
		}
	}

	public void stop()
	{
		if(super.isRun())
		{
			m_acceptThread.stop();

			for(int i = m_clientThreads.size() - 1; i >= 0; --i)
				if(m_clientThreads.get(i) != null)
					m_clientThreads.get(i).stop();

			try { m_svSocket.close(); }
			catch (IOException e) { }

			super.stop();
		}
	}

	public int getClientCount()
	{
		return m_clientThreads.size();
	}

	private void addEndpoint(Socket _socket)
	{
		if(m_clientThreads.size() >= m_capacity)
		{
			System.out.println("����� ���� ������.");
			try {
				_socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			System.out.println(host + ":" + port + "�� Ŭ���̾�Ʈ ������ �� �� �����ϴ�.");
		}

		switch(serviceCode)
		{
		case 1:
			CustomerThread cThread = new CustomerThread(new NetworkModule(_socket));
			cThread.start();
			while(!cThread.isRun());
			m_clientThreads.add(cThread);
			System.out.println(host + ":" + port + "�� Customer�� �����Ͽ����ϴ�.");
			break;
		case 2:
			ProviderThread pThread = new ProviderThread(new NetworkModule(_socket));
			pThread.start();
			while(!pThread.isRun());
			m_clientThreads.add(pThread);
			System.out.println(host + ":" + port + "�� Provider�� �����Ͽ����ϴ�.");
			break;
		default:
			System.out.println(host + ":" + port + "���� �߸��� Ŭ���̾�Ʈ �������� ���� �õ��Ͽ� ������ �����մϴ�.");
			break;
		}
	}
}
